CREATE OR REPLACE FUNCTION calculate_optimistic_profit(
    p_initial_sponge_id IN NUMBER
) RETURN NUMBER IS
    v_init_volume NUMBER;
    v_prod_volume NUMBER;
    v_prod_price NUMBER;
    v_quantity NUMBER;
    v_profit NUMBER;
    v_transformed VARCHAR2(10);
BEGIN
    -- First check if the sponge is already transformed
    SELECT is_transformed 
    INTO v_transformed
    FROM InitialSponge 
    WHERE id = p_initial_sponge_id;
    
    -- If sponge is already transformed, return 0
    IF v_transformed = 'TRUE' THEN
        RETURN 0;
    END IF;
    
    -- Calculate initial sponge volume
    SELECT (dim_length * dim_width * dim_height)
    INTO v_init_volume
    FROM InitialSponge
    WHERE id = p_initial_sponge_id;
    
    -- Get most profitable product directly from Product table
    SELECT 
        selling_price,
        (dim_length * dim_width * dim_height)
    INTO 
        v_prod_price,
        v_prod_volume
    FROM (
        SELECT 
            p.*,
            RANK() OVER (
                ORDER BY (selling_price / NULLIF((dim_length * dim_width * dim_height), 0)) DESC
            ) as rnk
        FROM Product p
    ) ranked_products
    WHERE rnk = 1;
    
    -- Calculate how many products can be made (floor division)
    v_quantity := FLOOR(v_init_volume / NULLIF(v_prod_volume, 0));
    
    -- Calculate optimistic profit
    v_profit := v_quantity * v_prod_price;
    
    RETURN v_profit;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RETURN 0;
END;
/

/* ==================================== */
CREATE OR REPLACE FUNCTION calculate_minimalistic_profit(
    p_initial_sponge_id IN NUMBER
) RETURN NUMBER IS
    v_init_volume NUMBER;
    v_min_prod_volume NUMBER;
    v_min_prod_price NUMBER;
    v_quantity NUMBER;
    v_profit NUMBER;
    v_transformed VARCHAR2(10);
BEGIN
    -- First check if the sponge is already transformed
    SELECT is_transformed 
    INTO v_transformed
    FROM InitialSponge 
    WHERE id = p_initial_sponge_id;
    
    -- If sponge is already transformed, return 0
    IF v_transformed = 'TRUE' THEN
        RETURN 0;
    END IF;
    
    -- Calculate initial sponge volume
    SELECT (dim_length * dim_width * dim_height)
    INTO v_init_volume
    FROM InitialSponge
    WHERE id = p_initial_sponge_id;
    
    -- Get minimum volume product details from existing view
    SELECT 
        selling_price,
        volume
    INTO 
        v_min_prod_price,
        v_min_prod_volume
    FROM v_minimum_volume_product;
    
    -- Calculate how many products can be made (floor division)
    v_quantity := FLOOR(v_init_volume / NULLIF(v_min_prod_volume, 0));
    
    -- Calculate minimalistic profit
    v_profit := v_quantity * v_min_prod_price;
    
    RETURN v_profit;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
    WHEN OTHERS THEN
        RETURN 0;
END;
/