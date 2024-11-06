package mg.itu.model;

import mg.itu.base.BaseModel;
import mg.itu.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RemainingTransformation extends BaseModel<RemainingTransformation> {

    private int id;
    private SpongeTransformation spongeTransformation; 
    private InitialSponge initialSponge;

    // Constructors
    public RemainingTransformation() {}

    public RemainingTransformation(int id, SpongeTransformation spongeTransformation, InitialSponge initialSponge) {
        setId(id);
        setSpongeTransformation(spongeTransformation);
        setInitialSponge(initialSponge);
    }

    // Insert method
    public void insert(Connection connection) throws SQLException {
        PreparedStatement statement = null;

        try {
            if (connection == null) {
                connection = Database.getConnection();
            }

            String sql = "INSERT INTO RemainingTransformation (id, id_sponge_transformation, id_initial_sponge) VALUES (seq_remaining_transformation.NEXTVAL, ?, ?)";

            statement = connection.prepareStatement(sql);

            // Prepare values for debugging
            int spongeTransformationId = this.spongeTransformation != null ? this.spongeTransformation.getId() : 0;
            int initialSpongeId = this.initialSponge != null ? this.initialSponge.getId() : 0;

            // Set parameters for the prepared statement
            statement.setInt(1, spongeTransformationId);
            statement.setInt(2, initialSpongeId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting RemainingTransformation failed, no rows affected.");
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

    public InitialSponge getInitialSponge() {
        return initialSponge;
    }

    public void setInitialSponge(InitialSponge initialSponge) {
        if (initialSponge == null) {
            throw new IllegalArgumentException("Initial sponge cannot be null.");
        }
        this.initialSponge = initialSponge;
    }

    // Mapping ResultSet to RemainingTransformation object
    @Override
    protected RemainingTransformation mapRow(ResultSet result) throws Exception {
        RemainingTransformation transformation = new RemainingTransformation();
        transformation.setId(result.getInt("id"));
        
        SpongeTransformation spongeTransformation = new SpongeTransformation().getById(result.getInt("id_sponge_transformation"), SpongeTransformation.class, null, "SpongeTransformation");
        transformation.setSpongeTransformation(spongeTransformation);

        InitialSponge initialSponge = new InitialSponge().getById(result.getInt("id_initial_sponge"), InitialSponge.class, null, "InitialSponge");
        transformation.setInitialSponge(initialSponge);

        return transformation;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("id", id);
        fields.put("id_sponge_transformation", spongeTransformation != null ? spongeTransformation.getId() : null);
        fields.put("id_initial_sponge", initialSponge != null ? initialSponge.getId() : null);

        return fields;
    }
}