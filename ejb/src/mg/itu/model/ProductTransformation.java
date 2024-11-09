package mg.itu.model;

import mg.itu.base.BaseModel;
import mg.itu.database.Database;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProductTransformation extends BaseModel<ProductTransformation> {

    private int id;
    private SpongeTransformation spongeTransformation; 
    private Product product;                          
    private int quantity;

    // Constructors
    public ProductTransformation() {}

    public ProductTransformation(int id, SpongeTransformation spongeTransformation, Product product, int quantity) 
    {
        setId(id);
        setSpongeTransformation(spongeTransformation);
        setProduct(product);
        setQuantity(quantity);
    }

    public void insert(Connection connection) throws SQLException {
        PreparedStatement statement = null;
    
        try {
            if (connection == null) {
                connection = Database.getConnection();
            }
    
            String sql = "INSERT INTO ProductTransformation (id, id_sponge_transformation, id_product, quantity) VALUES (seq_product_transformation.NEXTVAL, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            
            // Prepare values for debugging
            int spongeTransformationId = this.spongeTransformation != null ? this.spongeTransformation.getId() : 0;
            int productId = this.product != null ? this.product.getId() : 0;
            int quantityValue = this.quantity;
    
            // Debug logging
            System.out.println("Inserting ProductTransformation with values:");
            System.out.println("Sponge Transformation ID: " + spongeTransformationId);
            System.out.println("Product ID: " + productId);
            System.out.println("Quantity: " + quantityValue);
    
            // Set parameters for the prepared statement
            statement.setInt(1, spongeTransformationId); 
            statement.setInt(2, productId); 
            statement.setInt(3, quantityValue);
    
            int affectedRows = statement.executeUpdate();
    
            if (affectedRows == 0) {
                throw new SQLException("Inserting ProductTransformation failed, no rows affected.");
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
    }

    public SpongeTransformation getSpongeTransformation() {
        return spongeTransformation;
    }

    public void setSpongeTransformation(SpongeTransformation spongeTransformation) {
        if (spongeTransformation == null) {
            throw new IllegalArgumentException("Sponge transformation cannot be null.");
        }
        this.spongeTransformation = spongeTransformation;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.quantity = quantity;
    }

    // Mapping ResultSet to ProductTransformation object
    @Override
    protected ProductTransformation mapRow(ResultSet result) throws Exception {
        ProductTransformation transformation = new ProductTransformation();
        transformation.setId(result.getInt("id"));
        
        SpongeTransformation spongeTransformation = new SpongeTransformation().getById(result.getInt("id_sponge_transformation"), SpongeTransformation.class, null, "SpongeTransformation");
        transformation.setSpongeTransformation(spongeTransformation);

        Product product = new Product().getById(result.getInt("id_product"), Product.class, null, "Product");
        transformation.setProduct(product);

        transformation.setQuantity(result.getInt("quantity"));

        return transformation;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("id", id);
        fields.put("id_sponge_transformation", spongeTransformation != null ? spongeTransformation.getId() : null);
        fields.put("id_product", product != null ? product.getId() : null);
        fields.put("quantity", quantity);

        return fields;
    }
}