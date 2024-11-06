--
-- INIIAL SPONGE
--
INSERT INTO InitialSponge (id, purchase_price, is_transformed, dim_length, dim_width, dim_height) 
VALUES (seq_initial_sponge.NEXTVAL, 150000.00, 'FALSE', 50, 30, 20);

--
-- PRODUCT
-- 
INSERT INTO Product (id, label, selling_price, dim_length, dim_width, dim_height) 
VALUES (1, 'Matelas 2 places', 15000.00, 5, 3, 2);

INSERT INTO Product (id, label, selling_price, dim_length, dim_width, dim_height) 
VALUES (2, 'Matelas 1 place', 7000.00, 5, 3, 1);  

--
-- LOSS TRESHOLD
--
INSERT INTO LossTreshold (id, label, thetha) VALUES (seq_loss_treshold.NEXTVAL, 'Accepted Loss', 2);