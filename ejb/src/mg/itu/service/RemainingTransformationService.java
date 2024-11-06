package mg.itu.service;

import mg.itu.model.InitialSponge;
import mg.itu.model.RemainingTransformation;
import mg.itu.model.SpongeTransformation;

public class RemainingTransformationService {

    public static void insertRemainingTransformation (
        SpongeTransformation lastSpongeTransformation
    ) 
        throws Exception
    {
        InitialSponge last = InitialSpongeService.getLastInitialSpongeInserted();
        RemainingTransformation remainingTransformation = new RemainingTransformation();

        remainingTransformation.setSpongeTransformation(lastSpongeTransformation);
        remainingTransformation.setInitialSponge(last);
        remainingTransformation.insert(null);
    }   
}