CREATE OR REPLACE FUNCTION prix_revient_theorique(
--     p_sponge_id IN NUMBER
-- ) RETURN NUMBER IS
--     v_date_creation DATE;
--     v_volume NUMBER;
--     v_total_price NUMBER := 0;
--     v_ref_cursor SYS_REFCURSOR;
--     v_quantity NUMBER;
--     v_unit_price NUMBER;
-- BEGIN
--     DBMS_OUTPUT.PUT_LINE('#------------------------------------#');
--     -- Debugging: Input parameter
--     DBMS_OUTPUT.PUT_LINE('Input Parameter: p_sponge_id = ' || p_sponge_id);

--     -- Get sponge volume and creation date
--     SELECT 
--         date_creation,
--         calculate_sponge_volume(dim_length, dim_width, dim_height)
--     INTO 
--         v_date_creation, 
--         v_volume
--     FROM InitialSponge
--     WHERE id = p_sponge_id;

--     -- Debugging: Sponge details
--     DBMS_OUTPUT.PUT_LINE('v_date_creation: ' || TO_CHAR(v_date_creation, 'YYYY-MM-DD'));
--     DBMS_OUTPUT.PUT_LINE('v_volume: ' || v_volume);

--     -- Calculate price for each raw material in the formula
--     FOR raw_material IN (
--         SELECT 
--             cm.id_raw_materiel,
--             cm.qte * rp.unit_price AS required_quantity
--         FROM CubicMeterFormula cm
--         JOIN RawMaterielPurchase rp ON cm.id_raw_materiel = rp.id_raw_materiel 
--         WHERE rp.date_purchase <= v_date_creation
--     ) LOOP
--         -- Debugging: Raw material details
--         DBMS_OUTPUT.PUT_LINE('Processing raw material ID: ' || raw_material.id_raw_materiel);
--         DBMS_OUTPUT.PUT_LINE('Required quantity: ' || raw_material.required_quantity);

--         -- Get FIFO prices for this material
--         v_ref_cursor := get_fifo_prices(
--             v_date_creation,
--             raw_material.required_quantity,
--             raw_material.id_raw_materiel
--         );

--         LOOP
--             FETCH v_ref_cursor INTO v_quantity, v_unit_price;
--             EXIT WHEN v_ref_cursor%NOTFOUND;

--             -- Debugging: FIFO details
--             DBMS_OUTPUT.PUT_LINE('Fetched FIFO - Quantity: ' || v_quantity || ', Unit Price: ' || v_unit_price);

--             v_total_price := v_total_price + (v_quantity * v_unit_price);
--         END LOOP;

--         CLOSE v_ref_cursor;

--         -- Debugging: Accumulated total price
--         DBMS_OUTPUT.PUT_LINE('Accumulated total price: ' || v_total_price);
--     END LOOP;

--     -- Debugging: Final total price
--     DBMS_OUTPUT.PUT_LINE('Final total price: ' || v_total_price);

--     RETURN v_total_price;

-- EXCEPTION
--     WHEN OTHERS THEN
--         -- Debugging: Exception handling
--         DBMS_OUTPUT.PUT_LINE('Exception encountered: ' || SQLERRM);

--         IF v_ref_cursor%ISOPEN THEN
--             CLOSE v_ref_cursor;
--         END IF;

--         RETURN 0;
-- END;
-- /
