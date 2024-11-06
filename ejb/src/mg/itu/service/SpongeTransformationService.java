package mg.itu.service;

import mg.itu.model.InitialSponge;
import mg.itu.model.SpongeTransformation;

public class SpongeTransformationService {
    
    public static void insert(InitialSponge initialBlock, String transformationDate) 
        throws Exception
    {
        SpongeTransformation spongeTransformation = new SpongeTransformation();

        spongeTransformation.setInitialSponge(initialBlock); 
        spongeTransformation.setDateTransformation(java.sql.Date.valueOf(transformationDate));
        spongeTransformation.insert(null);
    } 

    public static SpongeTransformation getLastSpongeTransformationInserted() 
        throws Exception
    {
        return new SpongeTransformation().getById(new SpongeTransformation().getMaxId(null, "SpongeTransformation"), SpongeTransformation.class, null);
    }
}