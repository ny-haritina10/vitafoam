package mg.itu.service;

import mg.itu.model.LossTreshold;

public class LossTresholdService {
    
    public static LossTreshold getCurrentLossthTreshold() 
        throws Exception
    {
        LossTreshold[] losses = new LossTreshold().getAll(LossTreshold.class, null);
        System.out.println(losses[0].getId() + " | " + losses[0].getLabel() + " | " + losses[0].getThetha());
        return losses[0];
    }
}