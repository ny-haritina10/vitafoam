--
-- TRANSFORMATION DETAIL 
--    
CREATE OR REPLACE VIEW v_transformation_detail AS
SELECT
    -- SpongeTransformation details
    st.id AS transformation_id,
    st.date_transformation,
    
    -- Initial Sponge details
    isg.id AS initial_sponge_id,
    isg.purchase_price AS initial_purchase_price,
    isg.dim_length AS initial_length,
    isg.dim_width AS initial_width,
    isg.dim_height AS initial_height,
    isg.is_transformed,
    
    -- Product Transformation details
    pt.id AS product_transformation_id,
    p.id AS product_id,
    p.label AS product_label,
    p.selling_price AS product_selling_price,
    p.dim_length AS product_length,
    p.dim_width AS product_width,
    p.dim_height AS product_height,
    pt.quantity AS product_quantity,
    
    -- Remaining Transformation details
    rt.id AS remaining_transformation_id,
    rt.id_initial_sponge AS remaining_initial_sponge_id,
    
    -- Main Initial Sponge ID
    get_primary_parent_id(isg.id) AS main_initial_sponge_id
    
FROM
    SpongeTransformation st
    -- Join with InitialSponge for initial sponge details
    JOIN InitialSponge isg ON st.id_initial_sponge = isg.id
    
    -- Join with ProductTransformation and Product for product details
    LEFT JOIN ProductTransformation pt ON pt.id_sponge_transformation = st.id
    LEFT JOIN Product p ON pt.id_product = p.id
    
    -- Join with RemainingTransformation for remaining sponge details
    LEFT JOIN RemainingTransformation rt ON rt.id_sponge_transformation = st.id

ORDER BY 
    st.id;


--
-- TOTAL AMOUNT MANUFACTURED PRODUCT
--
CREATE OR REPLACE VIEW v_transformation_total_amount AS
SELECT
    st.id AS transformation_id,
    st.date_transformation,
    SUM(p.selling_price * pt.quantity) AS total_manufactured_amount
FROM
    SpongeTransformation st
JOIN ProductTransformation pt ON st.id = pt.id_sponge_transformation
JOIN Product p ON pt.id_product = p.id
GROUP BY
    st.id, st.date_transformation;

--
-- MOST PROFITABLE PRODUCT 
-- 
CREATE OR REPLACE VIEW v_most_profitable_product AS
SELECT *
FROM (
    SELECT
        p.id AS product_id,
        p.label AS product_label,
        p.selling_price,
        p.dim_length,
        p.dim_width,
        p.dim_height,
        (p.dim_length * p.dim_width * p.dim_height) AS volume,
        (p.selling_price / NULLIF((p.dim_length * p.dim_width * p.dim_height), 0)) AS price_to_volume_ratio
    FROM
        Product p
    ORDER BY
        price_to_volume_ratio DESC
)
WHERE ROWNUM = 1;


--
-- MINIMUM VOLUME PRODUCT
--
CREATE OR REPLACE VIEW v_minimum_volume_product AS
SELECT * FROM (
    SELECT
        p.id AS product_id,
        p.label AS product_label,
        p.selling_price,
        p.dim_length,
        p.dim_width,
        p.dim_height,
        (p.dim_length * p.dim_width * p.dim_height) AS volume
    FROM
        Product p
    ORDER BY
        volume ASC
)
WHERE ROWNUM = 1;

--
-- OPTIMISTIC PROFIT   
--
CREATE OR REPLACE VIEW v_optimistic_profits AS
SELECT 
    i.id AS initial_sponge_id,
    i.purchase_price,
    i.dim_length,
    i.dim_width,
    i.dim_height,
    (i.dim_length * i.dim_width * i.dim_height) AS initial_volume,
    calculate_optimistic_profit(i.id) AS optimistic_profit,
    p.id AS product_id,
    p.label AS product_label,
    p.selling_price AS product_price,
    (p.dim_length * p.dim_width * p.dim_height) AS product_volume,
    FLOOR(
        (i.dim_length * i.dim_width * i.dim_height) /
        NULLIF((p.dim_length * p.dim_width * p.dim_height), 0)
    ) AS possible_quantity
FROM 
    InitialSponge i
    CROSS JOIN (
        SELECT p.*
        FROM (
            SELECT 
                p.*,
                RANK() OVER (
                    ORDER BY (selling_price / NULLIF((dim_length * dim_width * dim_height), 0)) DESC
                ) as rnk
            FROM Product p
        ) p
        WHERE rnk = 1
    ) p
WHERE 
    i.is_transformed = 'FALSE';

--
-- MINIMALISTIC PROFIT
--
CREATE OR REPLACE VIEW v_minimalistic_profits AS
SELECT 
    i.id AS initial_sponge_id,
    i.purchase_price,
    i.dim_length,
    i.dim_width,
    i.dim_height,
    (i.dim_length * i.dim_width * i.dim_height) AS initial_volume,
    calculate_minimalistic_profit(i.id) AS minimalistic_profit,
    p.product_id,
    p.product_label,
    p.selling_price AS product_price,
    p.volume AS product_volume,
    FLOOR(
        (i.dim_length * i.dim_width * i.dim_height) /
        NULLIF(p.volume, 0)
    ) AS possible_quantity
FROM 
    InitialSponge i
    CROSS JOIN v_minimum_volume_product p
WHERE 
    i.is_transformed = 'FALSE';

--
-- NEW COST PRICE 
-- 
CREATE OR REPLACE VIEW v_product_cost_price AS
SELECT
    p.id AS product_id,
    p.label AS product_label,
    p.selling_price AS product_selling_price,
    (p.dim_length * p.dim_width * p.dim_height) * 
    AVG(isg.purchase_price / NULLIF((isg.dim_length * isg.dim_width * isg.dim_height), 0)) AS product_cost_price
FROM
    Product p
JOIN ProductTransformation pt ON pt.id_product = p.id
JOIN SpongeTransformation st ON pt.id_sponge_transformation = st.id
JOIN InitialSponge isg ON st.id_initial_sponge = isg.id
GROUP BY 
    p.id, 
    p.label, 
    p.selling_price,
    p.dim_length, 
    p.dim_width, 
    p.dim_height
ORDER BY p.id;

/*
    - cost price per cubic meter of initial bloc
    - volume product
    - qte product per initial bloc
    - for each initial bloc
        . value = volume product x cost price per cubic meter of initial bloc x qte product per initial bloc
    - total qte of product
    - get total value for each initial bloc
    - cost_price_ponderee 
        total_value / total_qte 
*/

-- 
-- TOTAL QTE PRODUCES 
-- 
CREATE OR REPLACE VIEW v_total_qte_produced AS
SELECT
    p.id,
    SUM(pt.quantity) AS total_qte
FROM 
    Product p
JOIN ProductTransformation pt ON p.id = pt.id_product
GROUP BY p.id;

--
-- VALUE PER PRODUCT 
-- 
CREATE OR REPLACE VIEW v_product_value_per_source AS
SELECT
    isg.id AS id_source,
    isg.purchase_price AS source_purchase_price,
    (isg.dim_height * isg.dim_length * isg.dim_width) AS volume_source,
    (isg.purchase_price / ((isg.dim_height * isg.dim_length * isg.dim_width))) AS cost_cubic_meter,
    p.id AS id_product,
    (p.dim_height * p.dim_length * p.dim_width) AS volume_product,
    pt.quantity AS qte_produced_per_source,
    (p.dim_height * p.dim_length * p.dim_width) * ((isg.purchase_price / ((isg.dim_height * isg.dim_length * isg.dim_width)))) * pt.quantity AS value_product_per_source
FROM
    Product p
JOIN ProductTransformation pt ON pt.id_product = p.id
JOIN SpongeTransformation st ON pt.id_sponge_transformation = st.id
JOIN InitialSponge isg ON st.id_initial_sponge = isg.id
ORDER BY isg.id, p.id;

--
-- TOTAL VALUE PER SOURCE
-- 
CREATE OR REPLACE VIEW v_total_value_per_source AS
SELECT  
    id_product,
    SUM(value_product_per_source) AS total_value
FROM 
    v_product_value_per_source
GROUP BY id_product;

--
-- PRODUCT COST PRICE BY MOYENNE PONDEREE 
--
CREATE OR REPLACE VIEW v_product_cost_price_ponderee AS 
SELECT 
    vtvps.id_product,
    vtvps.total_value / vtqp.total_qte AS product_cost_price
FROM 
    v_total_value_per_source vtvps
JOIN v_total_qte_produced vtqp ON vtvps.id_product = vtqp.id
ORDER BY vtvps.id_product;

--
-- SOURCE FILLE RESTE 
-- 
CREATE OR REPLACE VIEW v_source_fille AS
SELECT
    pt.id AS trs,
    isp.id AS id_sponge_source,
    isp2.id AS id_sponge_fille 
FROM 
    RemainingTransformation rt
JOIN SpongeTransformation pt ON rt.id_sponge_transformation = pt.id
JOIN InitialSponge isp2 ON isp2.id = rt.id_initial_sponge
JOIN InitialSponge isp ON isp.id = pt.id_initial_sponge;

/* ============================================================================================= */
/* ============================================================================================= */
/* ============================================================================================= */
/* ============================================================================================= */
/* ============================================================================================= */

--
--
--
CREATE OR REPLACE VIEW V_RAW_MATERIAL_FIFO_ENTRY AS
SELECT 
    id_raw_materiel,
    date_purchase,
    unit_price,
    qte AS available_quantity,
    ROW_NUMBER() OVER (PARTITION BY id_raw_materiel ORDER BY date_purchase, id) AS lot_number
FROM RawMaterielPurchase
WHERE qte > 0
ORDER BY id_raw_materiel, date_purchase;

--
--
--
CREATE OR REPLACE VIEW v_raw_material_requirements AS
SELECT 
    s.id AS sponge_id,
    s.date_creation,
    v.sponge_volume,
    cmf.id_raw_materiel,
    cmf.qte * v.sponge_volume AS required_quantity
FROM InitialSponge s
JOIN V_SPONGE_VOLUME v ON s.id = v.id
CROSS JOIN CubicMeterFormula cmf
WHERE s.purchase_price = 0;

--
--
--
CREATE OR REPLACE VIEW V_REFERENCE_BLOCK_SUMMARY AS
WITH RefBlocks AS (
    SELECT 
        purchase_price,
        dim_length * dim_width * dim_height AS block_volume,
        ROWNUM AS rn
    FROM InitialSponge
    WHERE purchase_price > 0
    AND ROWNUM <= 3 -- number of refs
)
SELECT 
    SUM(purchase_price) AS total_purchase_price,
    SUM(block_volume) AS total_volume,
    DECODE(SUM(block_volume), 0, 0, SUM(purchase_price) / SUM(block_volume)) AS price_per_cubic_meter
FROM RefBlocks;

--
-- replace 4 by this when random (TRUNC(DBMS_RANDOM.VALUE(-10, 10))
--
-- WITH RandomVariation AS (
--     SELECT 
--         s.id AS sponge_id,
--         s.id_machine,
--         v.sponge_volume,
--         r.price_per_cubic_meter * (1 + 4 / 100) AS adjusted_price_per_cubic_meter
--     FROM InitialSponge s
--     JOIN V_SPONGE_VOLUME v ON s.id = v.id
--     CROSS JOIN V_REFERENCE_BLOCK_SUMMARY r
--     WHERE s.purchase_price = 0
-- )
-- SELECT 
--     id_machine,
--     SUM(sponge_volume * adjusted_price_per_cubic_meter) AS sum_practical_price
-- FROM RandomVariation
-- GROUP BY id_machine;
CREATE OR REPLACE VIEW v_pr_pratique AS
SELECT
    id_machine,
    SUM(purchase_price) AS sum_practical_price
FROM 
    InitialSponge
GROUP BY 
    id_machine;



-- 2022:4
-- 2023:3
-- 2024:1
-- ALL:3