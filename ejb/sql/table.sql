-- Initial Sponge 
CREATE TABLE InitialSponge (
    id INT PRIMARY KEY,
    purchase_price DECIMAL(10, 2),
    is_transformed VARCHAR(10) DEFAULT 'FALSE',
    dim_length DECIMAL(10, 2) NOT NULL,
    dim_width DECIMAL(10, 2) NOT NULL,
    dim_height DECIMAL(10, 2) NOT NULL
);

-- Product 
CREATE TABLE Product (
    id INT PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    selling_price DECIMAL(10, 2) NOT NULL,
    dim_length DECIMAL(10, 2) NOT NULL,
    dim_width DECIMAL(10, 2) NOT NULL,
    dim_height DECIMAL(10, 2) NOT NULL
);

-- SpongeTransformation
CREATE TABLE SpongeTransformation (
    id INT PRIMARY KEY,
    id_initial_sponge REFERENCES InitialSponge(id),
    date_transformation DATE NOT NULL
);

-- ProductTransformation
CREATE TABLE ProductTransformation (
    id INT PRIMARY KEY,
    id_sponge_transformation REFERENCES SpongeTransformation(id),
    id_product REFERENCES Product(id),
    quantity INT NOT NULL
);

-- RemainingTransformation
CREATE TABLE RemainingTransformation (
    id INT PRIMARY KEY,
    id_sponge_transformation REFERENCES SpongeTransformation(id),
    id_initial_sponge REFERENCES InitialSponge
);

-- LossTreshold
CREATE TABLE LossTreshold (
    id iNT PRIMARY KEY,
    label VARCHAR(55) NOT NULL,
    thetha DECIMAL(10, 2) NOT NULL      -- theta is in percentage
);