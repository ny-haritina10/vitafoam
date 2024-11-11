package mg.itu.service;

import mg.itu.model.LossTreshold;

public class LossTresholdService {
    
    public static LossTreshold getCurrentLossthTreshold() 
        throws Exception
    {
        LossTreshold[] losses = new LossTreshold().getAll(LossTreshold.class, null);
        return losses[0];
    }
}