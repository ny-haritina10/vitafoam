package mg.itu.model;

import mg.itu.base.BaseModel;
import mg.itu.database.Database;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpongeTransformation extends BaseModel<SpongeTransformation> {

    private int id;
    private InitialSponge initialSponge;
    private Date dateTransformation;

    // Constructors
    public SpongeTransformation() {}

    public SpongeTransformation(int id, InitialSponge initialSponge, Date dateTransformation) {
        setId(id);
        setInitialSponge(initialSponge);
        setDateTransformation(dateTransformation);
    }

    // Insert method
    public void insert(Connection connection) throws SQLException {
        PreparedStatement statement = null;

        try {
            if (connection == null) {
                connection = Database.getConnection();
            }

            String sql = "INSERT INTO SpongeTransformation (id, id_initial_sponge, date_transformation) VALUES (seq_sponge_transformation.NEXTVAL, ?, ?)";

            statement = connection.prepareStatement(sql);

            int initialSpongeId = this.initialSponge != null ? this.initialSponge.getId() : 0;
            Date transformationDate = this.dateTransformation;

            System.out.println("Inserting SpongeTransformation with values:");
            System.out.println("Initial Sponge ID: " + initialSpongeId);
            System.out.println("Date of Transformation: " + transformationDate);

            statement.setInt(1, initialSpongeId);
            statement.setDate(2, new java.sql.Date(transformationDate.getTime()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting SpongeTransformation failed, no rows affected.");
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
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
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

    public Date getDateTransformation() {
        return dateTransformation;
    }

    public void setDateTransformation(Date dateTransformation) {
        if (dateTransformation == null) {
            throw new IllegalArgumentException("Date of transformation cannot be null.");
        }
        this.dateTransformation = dateTransformation;
    }

    // Mapping ResultSet to SpongeTransformation object
    @Override
    protected SpongeTransformation mapRow(ResultSet result) throws Exception {
        SpongeTransformation transformation = new SpongeTransformation();
        transformation.setId(result.getInt("id"));

        InitialSponge initialSponge = new InitialSponge().getById(result.getInt("id_initial_sponge"), InitialSponge.class, null, "InitialSponge");
        transformation.setInitialSponge(initialSponge);
        transformation.setDateTransformation(result.getDate("date_transformation"));

        return transformation;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("id", id);
        fields.put("id_initial_sponge", initialSponge != null ? initialSponge.getId() : null);
        fields.put("date_transformation", dateTransformation);

        return fields;
    }
}