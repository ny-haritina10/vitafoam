--
-- CubicMeterFormula
--
INSERT INTO CubicMeterFormula (id, id_raw_materiel, qte) VALUES (seq_formula.NEXTVAL, 2, 1); 
INSERT INTO CubicMeterFormula (id, id_raw_materiel, qte) VALUES (seq_formula.NEXTVAL, 5, 1); 
INSERT INTO CubicMeterFormula (id, id_raw_materiel, qte) VALUES (seq_formula.NEXTVAL, 6, 0.5);

--
-- RawMaterielPurchase
--

-- 2024
INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 2, 224727345, TO_DATE('2024-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 6000);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 5, 224727345, TO_DATE('2024-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 600);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 6, 112363672.5, TO_DATE('2024-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 550);

-- 2023
INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 2, 225233313, TO_DATE('2023-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 5950);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 5, 225233313, TO_DATE('2023-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 500);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 6, 112616656.5, TO_DATE('2023-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 500);

-- 2022
INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 2, 224974632, TO_DATE('2022-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 5900);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 5, 224974632, TO_DATE('2022-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 400);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 6, 112487316, TO_DATE('2022-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 450);