--
-- INTIIAL BLOC
--
INSERT INTO InitialSponge (id, purchase_price, is_transformed, dim_length, dim_width, dim_height) VALUES (seq_initial_sponge.NEXTVAL, 2000000, 'FALSE', 100, 20, 10);

INSERT INTO InitialSponge (id, purchase_price, is_transformed, dim_length, dim_width, dim_height) VALUES (seq_initial_sponge.NEXTVAL, 3000000, 'FALSE', 100, 40, 10);

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