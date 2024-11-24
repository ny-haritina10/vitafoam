--
-- CubicMeterFormula
--
INSERT INTO CubicMeterFormula (id, id_raw_materiel, qte) VALUES (seq_formula.NEXTVAL, 2, 2.5); 
INSERT INTO CubicMeterFormula (id, id_raw_materiel, qte) VALUES (seq_formula.NEXTVAL, 5, 3); 
INSERT INTO CubicMeterFormula (id, id_raw_materiel, qte) VALUES (seq_formula.NEXTVAL, 6, 0.5);

--
-- REF INTIIAL BLOC
--
INSERT INTO InitialSponge (id, purchase_price, is_transformed, dim_length, dim_width, dim_height, date_creation, id_machine)
VALUES (seq_initial_sponge.NEXTVAL, 3000000, 'FALSE', 102, 1, 1, TO_DATE('2024-05-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1);

INSERT INTO InitialSponge (id, purchase_price, is_transformed, dim_length, dim_width, dim_height, date_creation, id_machine)
VALUES (seq_initial_sponge.NEXTVAL, 3200000, 'FALSE', 125, 1, 1, TO_DATE('2024-05-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2);

INSERT INTO InitialSponge (id, purchase_price, is_transformed, dim_length, dim_width, dim_height, date_creation, id_machine)
VALUES (seq_initial_sponge.NEXTVAL, 4000000, 'FALSE', 147, 1, 1, TO_DATE('2024-05-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 3);



--
-- RawMaterielPurchase
--
INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 2, 400, TO_DATE('2024-05-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 4000);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 5, 600, TO_DATE('2024-05-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 5000);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 6, 200, TO_DATE('2024-05-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 7000);


INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 2, 600, TO_DATE('2024-06-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 4200);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 5, 780, TO_DATE('2024-06-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 4900);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 6, 10, TO_DATE('2024-06-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 7500);

/*================================================ */

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 2, 599000000, TO_DATE('2022-01-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 4200);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 5, 599000000, TO_DATE('2023-01-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 4900);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 6, 599000000, TO_DATE('2024-01-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 7500);