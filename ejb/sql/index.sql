CREATE INDEX idx_raw_materiel_purchase_date ON RawMaterielPurchase(date_purchase);
CREATE INDEX idx_raw_materiel_purchase ON RawMaterielPurchase(id_raw_materiel);
CREATE INDEX idx_initial_sponge_id ON InitialSponge(id);
CREATE INDEX idx_initial_sponge_pp ON InitialSponge(purchase_price);


CREATE INDEX idx_initial_spp ON InitialSponge(purchase_price);
CREATE INDEX idx_initial_sponge_machine ON InitialSponge(id_machine);
CREATE INDEX idx_raw_material_purchase_date ON RawMaterielPurchase(date_purchase, id_raw_materiel);