-- View to calculate sponge volume
CREATE OR REPLACE VIEW V_SPONGE_VOLUME AS
SELECT 
    id,
    dim_length * dim_width * dim_height AS sponge_volume
FROM InitialSponge;

-- View to get FIFO prices for raw materials (pre-calculated)
CREATE OR REPLACE VIEW V_RAW_MATERIAL_FIFO_PRICES AS
SELECT 
    id_raw_materiel,
    date_purchase,
    unit_price,
    qte,
    DENSE_RANK() OVER (PARTITION BY id_raw_materiel ORDER BY date_purchase, id) AS purchase_sequence,
    SUM(qte) OVER (PARTITION BY id_raw_materiel ORDER BY date_purchase) AS cumulative_qty
FROM RawMaterielPurchase
WHERE qte > 0;

-- View to calculate theoretical purchase price
CREATE OR REPLACE VIEW V_THEORETICAL_PRICE AS
WITH RawMaterialRequirements AS (
    SELECT 
        s.id AS sponge_id,
        s.date_creation,
        v.sponge_volume,
        cmf.id_raw_materiel,
        cmf.qte * v.sponge_volume AS required_quantity
    FROM InitialSponge s
    JOIN V_SPONGE_VOLUME v ON s.id = v.id
    CROSS JOIN CubicMeterFormula cmf
), MaterialPricing AS (
    SELECT 
        r.sponge_id,
        r.id_raw_materiel,
        r.required_quantity,
        f.unit_price,
        LEAST(f.qte, r.required_quantity) AS used_quantity,
        f.date_purchase
    FROM RawMaterialRequirements r
    JOIN V_RAW_MATERIAL_FIFO_PRICES f ON r.id_raw_materiel = f.id_raw_materiel 
        AND f.date_purchase <= r.date_creation
)
SELECT 
    sponge_id,
    SUM(used_quantity * unit_price) AS theoretical_price
FROM MaterialPricing
GROUP BY sponge_id;

-- View to calculate practical purchase price
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

-- View to calculate practical price with random variation
CREATE OR REPLACE VIEW V_PRACTICAL_PRICE AS
WITH RandomVariation AS (
    SELECT 
        s.id AS sponge_id,
        v.sponge_volume,
        r.price_per_cubic_meter * (1 + ((TRUNC(DBMS_RANDOM.VALUE(5, 15)) / 100))) AS adjusted_price_per_cubic_meter
    FROM InitialSponge s
    JOIN V_SPONGE_VOLUME v ON s.id = v.id
    CROSS JOIN V_REFERENCE_BLOCK_SUMMARY r
)
SELECT 
    sponge_id,
    sponge_volume * adjusted_price_per_cubic_meter AS practical_price
FROM RandomVariation;

-- Final result view
CREATE OR REPLACE VIEW V_MACHINE_PRICE_SUMMARY AS
SELECT 
    i.id_machine,
    m.label AS machine_name,
    SUM(prac.practical_price) AS total_pratique_price,
    SUM(theo.theoretical_price) AS total_theorique_price,
    SUM(prac.practical_price) - SUM(theo.theoretical_price) AS ecart
FROM InitialSponge i
JOIN Machine m ON i.id_machine = m.id
JOIN V_PRACTICAL_PRICE prac ON i.id = prac.sponge_id
JOIN V_THEORETICAL_PRICE theo ON i.id = theo.sponge_id
WHERE i.purchase_price = 0
GROUP BY i.id_machine, m.label;