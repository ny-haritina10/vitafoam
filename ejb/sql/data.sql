--
-- PRODUCT
-- 
INSERT INTO Product (id, label, selling_price, dim_length, dim_width, dim_height) 
VALUES (1, 'Matelas 2 places', 1500.00, 5, 3, 2);

INSERT INTO Product (id, label, selling_price, dim_length, dim_width, dim_height) 
VALUES (2, 'Matelas 1 place', 100.00, 3, 1.5, 2);

INSERT INTO Product (id, label, selling_price, dim_length, dim_width, dim_height) 
VALUES (3, 'Matelas pour enfant', 95.00, 2, 1, 2);

INSERT INTO Product (id, label, selling_price, dim_length, dim_width, dim_height) 
VALUES (4, 'Eponge', 25.00, 0.15, 0.15, 0.15);  

--
-- LOSS TRESHOLD
--
INSERT INTO LossTreshold (id, label, thetha) VALUES (seq_loss_treshold.NEXTVAL, 'Accepted Loss', 2);