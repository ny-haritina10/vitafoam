--
-- REF INTIIAL BLOC
--
INSERT INTO InitialSponge (id, purchase_price, is_transformed, dim_length, dim_width, dim_height, date_creation, id_machine)
VALUES (seq_initial_sponge.NEXTVAL, 1000000, 'FALSE', 250, 1, 1, TO_DATE('2024-11-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1);

INSERT INTO InitialSponge (id, purchase_price, is_transformed, dim_length, dim_width, dim_height, date_creation, id_machine)
VALUES (seq_initial_sponge.NEXTVAL, 1500000, 'FALSE', 175, 1, 1, TO_DATE('2024-11-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1);

INSERT INTO InitialSponge (id, purchase_price, is_transformed, dim_length, dim_width, dim_height, date_creation, id_machine)
VALUES (seq_initial_sponge.NEXTVAL, 1900000, 'FALSE', 225, 1, 1, TO_DATE('2024-11-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1);



-- test 
INSERT INTO InitialSponge (id, purchase_price, is_transformed, dim_length, dim_width, dim_height, date_creation, id_machine)
VALUES (seq_initial_sponge.NEXTVAL, 0, 'FALSE', 210, 1, 1, TO_DATE('2024-11-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1);

--
-- RawMaterielPurchase
--
INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 2, 2000, TO_DATE('2024-11-19 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 2000);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 5, 2000, TO_DATE('2024-11-19 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 500);

INSERT INTO RawMaterielPurchase (id, id_raw_materiel, qte, date_purchase, unit_price)
VALUES (seq_raw_materiel_purchase.NEXTVAL, 6, 2000, TO_DATE('2024-11-19 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1500);