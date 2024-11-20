
/* =================================================== */
/* =================================================== */
/* =================================================== */

-- TYPE
CREATE OR REPLACE TYPE machine_price_record AS OBJECT (
    id_machine NUMBER,
    machine_name VARCHAR2(50),
    total_pratique_price NUMBER,
    total_theorique_price NUMBER,
    ecart NUMBER
);
/

CREATE OR REPLACE TYPE machine_price_table IS TABLE OF machine_price_record;
/

-- Function to calculate volume
CREATE OR REPLACE FUNCTION calculate_sponge_volume(
    p_length IN NUMBER,
    p_width IN NUMBER,
    p_height IN NUMBER
) RETURN NUMBER IS
BEGIN
    RETURN p_length * p_width * p_height;
END;
/

-- Function to get FIFO prices for raw materials
CREATE OR REPLACE FUNCTION get_fifo_prices(
    p_date_creation IN DATE,
    p_required_quantity IN NUMBER,
    p_raw_materiel_id IN NUMBER
) RETURN SYS_REFCURSOR IS
    v_result SYS_REFCURSOR;
    v_allocated_qty NUMBER := 0;
    v_remaining_qty NUMBER;
    v_current_qty NUMBER;
BEGIN
    OPEN v_result FOR
    SELECT 
        quantity,
        unit_price
    FROM (
        SELECT 
            qte AS quantity,
            unit_price,
            date_purchase,
            ROW_NUMBER() OVER (ORDER BY date_purchase, id) AS rn
        FROM RawMaterielPurchase
        WHERE date_purchase <= p_date_creation 
        AND id_raw_materiel = p_raw_materiel_id
        AND qte > 0
    )
    WHERE 
        v_allocated_qty < p_required_quantity
    AND ROWNUM <= (
        SELECT COUNT(*) 
        FROM RawMaterielPurchase
        WHERE date_purchase <= p_date_creation 
        AND id_raw_materiel = p_raw_materiel_id
    );
    
    RETURN v_result;
END;
/

-- Function to get reference blocks summary
CREATE OR REPLACE FUNCTION get_reference_blocks_summary(
    p_limit IN NUMBER,
    p_sum_price OUT NUMBER,
    p_sum_volume OUT NUMBER
) RETURN NUMBER IS
BEGIN
    WITH reference_blocks AS (
        SELECT *
        FROM (
            SELECT *
            FROM InitialSponge
            ORDER BY id ASC
        )
        WHERE ROWNUM <= p_limit
    )
    SELECT 
        NVL(SUM(purchase_price), 0),
        NVL(SUM(calculate_sponge_volume(dim_length, dim_width, dim_height)), 0)
    INTO 
        p_sum_price,
        p_sum_volume
    FROM reference_blocks;
    
    RETURN 1;
END;
/

-- Function to generate random number
CREATE OR REPLACE FUNCTION random_between(
    p_min IN NUMBER,
    p_max IN NUMBER
) RETURN NUMBER IS
BEGIN
    RETURN FLOOR(DBMS_RANDOM.VALUE(p_min, p_max + 1));
END;
/

-- Main function to calculate theoretical purchase price
CREATE OR REPLACE FUNCTION prix_revient_theorique(
    p_sponge_id IN NUMBER
) RETURN NUMBER IS
    v_date_creation DATE;
    v_volume NUMBER;
    v_total_price NUMBER := 0;
    v_ref_cursor SYS_REFCURSOR;
    v_consumed_quantity NUMBER;
    v_current_quantity NUMBER;
    v_unit_price NUMBER;
    v_required_qte NUMBER;
BEGIN
    -- Get sponge volume and creation date
    SELECT 
        date_creation,
        calculate_sponge_volume(dim_length, dim_width, dim_height)
    INTO 
        v_date_creation, 
        v_volume
    FROM InitialSponge
    WHERE id = p_sponge_id;
    
    -- Calculate price for each raw material in the formula
    FOR raw_material IN (
        SELECT 
            id_raw_materiel,
            qte AS formula_quantity
        FROM CubicMeterFormula
    ) LOOP
        -- Calculate required quantity for this sponge
        v_required_qte := raw_material.formula_quantity * v_volume;

        DBMS_OUTPUT.PUT_LINE('Required_qte: ' || v_required_qte);
        DBMS_OUTPUT.PUT_LINE('Formula qte: ' || raw_material.formula_quantity);
        DBMS_OUTPUT.PUT_LINE('Volume: ' || v_volume);
        
        -- Get FIFO prices for this material
        v_ref_cursor := get_fifo_prices(
            v_date_creation,
            v_required_qte,
            raw_material.id_raw_materiel
        );
        
        -- Track remaining required quantity
        v_current_quantity := v_required_qte;
        
        LOOP
            FETCH v_ref_cursor INTO v_consumed_quantity, v_unit_price;
            EXIT WHEN v_ref_cursor%NOTFOUND;
            
            -- Determine the quantity to use from this batch
            v_consumed_quantity := LEAST(v_consumed_quantity, v_current_quantity);
            
            -- Add price for this batch
            v_total_price := v_total_price + (v_consumed_quantity * v_unit_price);
            
            -- Reduce remaining quantity
            v_current_quantity := v_current_quantity - v_consumed_quantity;
            
            -- Exit if all required quantity is consumed
            EXIT WHEN v_current_quantity <= 0;
        END LOOP;
        
        CLOSE v_ref_cursor;
    END LOOP;
    
    RETURN v_total_price;
EXCEPTION
    WHEN OTHERS THEN
        IF v_ref_cursor%ISOPEN THEN
            CLOSE v_ref_cursor;
        END IF;
        RETURN 0;
END;
/

-- Main function to calculate practical purchase price
CREATE OR REPLACE FUNCTION prix_revient_pratique(
    p_sponge_id IN NUMBER,
    p_reference_limit IN NUMBER,
    p_random_min IN NUMBER,
    p_random_max IN NUMBER
) RETURN NUMBER IS
    v_sum_purchase_price NUMBER;
    v_sum_volume NUMBER;
    v_price_per_cubic_meter NUMBER;
    v_random_percentage NUMBER;
    v_block_volume NUMBER;
    v_practical_price NUMBER;
    v_dummy NUMBER;
BEGIN
    -- Get reference blocks summary
    v_dummy := get_reference_blocks_summary(
        p_reference_limit, 
        v_sum_purchase_price, 
        v_sum_volume
    );
    
    -- Calculate price per cubic meter
    IF v_sum_volume = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Total reference volume cannot be zero');
    END IF;
    v_price_per_cubic_meter := v_sum_purchase_price / v_sum_volume;

    -- Get random percentage
    v_random_percentage := random_between(p_random_min, p_random_max) / 100;

    -- Get volume of the target block
    SELECT calculate_sponge_volume(dim_length, dim_width, dim_height)
    INTO v_block_volume
    FROM InitialSponge
    WHERE id = p_sponge_id;

    -- Calculate practical price
    v_practical_price := (v_price_per_cubic_meter + (v_price_per_cubic_meter * v_random_percentage)) * v_block_volume;

    RETURN v_practical_price;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Exception: NO_DATA_FOUND');
        RETURN 0;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Exception: ' || SQLERRM);
        RAISE;
END;
/

-- FINAL RESULT
CREATE OR REPLACE FUNCTION get_final_result(
    p_limit_ref IN INT,
    p_rand_min IN INT,
    p_rand_max IN INT
) RETURN machine_price_table PIPELINED IS
    v_total_pratique_price NUMBER;
    v_total_theorique_price NUMBER;
    v_ecart NUMBER;
BEGIN
    FOR r IN (
        SELECT 
            i.id_machine,
            m.label AS machine_name,
            SUM(prix_revient_pratique(i.id, p_limit_ref, p_rand_min, p_rand_max)) AS total_pratique_price,
            SUM(prix_revient_theorique(i.id)) AS total_theorique_price
        FROM 
            InitialSponge i
        JOIN 
            Machine m ON i.id_machine = m.id
        WHERE
            i.purchase_price = 0    -- remove reference block 
        GROUP BY 
            i.id_machine, m.label
    ) LOOP
        -- Assign to variables
        v_total_pratique_price := r.total_pratique_price;
        v_total_theorique_price := r.total_theorique_price;

        -- Calculate the ecart
        v_ecart := v_total_pratique_price - v_total_theorique_price;

        -- Pipe the result
        PIPE ROW(machine_price_record(
            r.id_machine,
            r.machine_name,
            v_total_pratique_price,
            v_total_theorique_price,
            v_ecart
        ));
    END LOOP;
    RETURN;
END;
/