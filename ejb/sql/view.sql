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
    LEFT JOIN RemainingTransformation rt ON rt.id_sponge_transformation = st.id;


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
--
--
CREATE OR REPLACE VIEW v_product_cost_price AS
SELECT
    st.id_initial_sponge AS initial_sponge_id,
    isg.purchase_price AS initial_sponge_purchase_price,
    p.id AS product_id,
    p.label AS product_label,
    p.selling_price AS product_selling_price,
    (p.dim_length * p.dim_width * p.dim_height) * 
    (isg.purchase_price / NULLIF((isg.dim_length * isg.dim_width * isg.dim_height), 0)) AS product_cost_price
    
FROM
    Product p
JOIN ProductTransformation pt ON pt.id_product = p.id
JOIN SpongeTransformation st ON pt.id_sponge_transformation = st.id
JOIN InitialSponge isg ON st.id_initial_sponge = isg.id;

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