--
-- PRODUCT
-- 
INSERT INTO Product (id, label, selling_price, dim_length, dim_width, dim_height) 
VALUES (seq_product.NEXTVAL, 'u1', 20000.00, 16, 4, 2);

INSERT INTO Product (id, label, selling_price, dim_length, dim_width, dim_height) 
VALUES (seq_product.NEXTVAL, 'u2', 12000.00, 10, 7, 1); 

INSERT INTO Product (id, label, selling_price, dim_length, dim_width, dim_height) 
VALUES (seq_product.NEXTVAL, 'u3', 600.00, 5, 1, 1); 

--
-- LOSS TRESHOLD
--
INSERT INTO LossTreshold (id, label, thetha) VALUES (seq_loss_treshold.NEXTVAL, 'Accepted Loss', 20);

/* ============================================= */
/* ============================================= */
/* ============================================= */

--
-- MACHINE
-- 
INSERT INTO Machine (id, label) VALUES (seq_machine.NEXTVAL, 'Machine A');
INSERT INTO Machine (id, label) VALUES (seq_machine.NEXTVAL, 'Machine B');
INSERT INTO Machine (id, label) VALUES (seq_machine.NEXTVAL, 'Machine C');
INSERT INTO Machine (id, label) VALUES (seq_machine.NEXTVAL, 'Machine D');

--
-- Unit
--
INSERT INTO Unit (id, label) VALUES (seq_unit.NEXTVAL, 'kg');
INSERT INTO Unit (id, label) VALUES (seq_unit.NEXTVAL, 'l');
INSERT INTO Unit (id, label) VALUES (seq_unit.NEXTVAL, 'm');

--
-- RawMateriel
--
INSERT INTO RawMateriel (id, id_unit, label) VALUES (seq_raw_materiel.NEXTVAL, 3, 'Essence');
INSERT INTO RawMateriel (id, id_unit, label) VALUES (seq_raw_materiel.NEXTVAL, 2, 'Paper');
INSERT INTO RawMateriel (id, id_unit, label) VALUES (seq_raw_materiel.NEXTVAL, 2, 'Hardener');
