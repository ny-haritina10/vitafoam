package mg.itu.model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import mg.itu.base.BaseModel;

public class LossTreshold extends BaseModel<LossTreshold> {

    private int id;
    private String label;
    private double thetha;

    public LossTreshold() 
    { }

    public LossTreshold(int id, String label, double thetha) {
        this.setId(id);
        this.setLabel(label);
        this.setThetha(thetha);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getThetha() {
        return thetha;
    }

    public void setThetha(double thetha) {
        this.thetha = thetha;
    }

    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<String, Object>();

        fields.put("id", id);
        fields.put("label", label);
        fields.put("thetha", thetha);

        return fields;
    }

    @Override
    protected LossTreshold mapRow(ResultSet result) 
        throws Exception 
    {
        LossTreshold loss = new LossTreshold();

        loss.setId(result.getInt("id"));
        loss.setLabel(result.getString("label"));
        loss.setThetha(result.getDouble("thetha"));

        return loss;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    protected void setId(int id) {
        this.id = id;
    }
}