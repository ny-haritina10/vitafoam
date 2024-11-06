package mg.itu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.model.InitialSponge;
import mg.itu.model.LossTreshold;
import mg.itu.model.Product;
import mg.itu.model.ProductTransformation;
import mg.itu.model.RemainingTransformation;
import mg.itu.model.SpongeTransformation;
import mg.itu.model.TransformationDetail;
import mg.itu.service.InitialSpongeService;
import mg.itu.service.LossTresholdService;
import mg.itu.service.ProductService;
import mg.itu.service.RemainingTransformationService;
import mg.itu.service.SpongeTransformationService;

@WebServlet("/controller/TransformationController")
public class TransformationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
    {
        String mode = req.getParameter("mode");

        try {
            // insert 
            if (mode != null && mode.equals("create")) {
                InitialSponge[] sponges = new InitialSponge().getAll(InitialSponge.class, null);
                Product[] products = new Product().getAll(Product.class, null);

                req.setAttribute("sponges", sponges);
                req.setAttribute("products", products);
                req.setAttribute("template_content", "/WEB-INF/views/insert-transformation.jsp");
                req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
            }

            // list
            else {
                TransformationDetail[] details = new TransformationDetail().getAll(TransformationDetail.class, null, "v_transformation_detail");
                
                req.setAttribute("details", details);
                req.setAttribute("template_content", "/WEB-INF/views/list-transformation.jsp");
                req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
            }   
        } 
        
        catch (Exception e) 
        { e.printStackTrace(); }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
    {
        try {
            int initialSpongeId = Integer.parseInt(req.getParameter("initialSponge"));
            String transformationDate = req.getParameter("transformationDate");

            // remaining dimensions
            double remainingLength = Double.parseDouble(req.getParameter("remainingLength"));
            double remainingWidth = Double.parseDouble(req.getParameter("remainingWidth"));
            double remainingHeight = Double.parseDouble(req.getParameter("remainingHeight"));

            Product[] products = new Product().getAll(Product.class, null); 

            // map all product with their quantities  
            Map<Integer, Double> productQuantities = ProductService.mapProductQuantities(products, req);

            // retrieve initial block 
            InitialSponge initialBlock = new InitialSponge().getById(initialSpongeId, InitialSponge.class, null);

            // verify loss constraints
            LossTreshold loss = LossTresholdService.getCurrentLossthTreshold();

            double volumeInitialBlock = InitialSpongeService.getVolume(initialBlock);
            double acceptedLoss = InitialSpongeService.getAcceptedLoss(initialBlock, loss);
            double sumVolume = ProductService.getMappedProductsTotalVolume(productQuantities);
            double volumeRemaining = (remainingHeight * remainingLength * remainingWidth);

            // loss threshold exceeded
            if (volumeInitialBlock - (sumVolume + volumeRemaining) >= acceptedLoss) {
                String message = String.format(
                    "The calculated total volume exceeds the allowable loss threshold.\n" +
                    "  - Initial Sponge Volume: %.2f m³\n" +
                    "  - Accepted Loss Threshold: %.2f m³\n" +
                    "  - Total Product Volume: %.2f m³\n" +
                    "  - Remaining Volume: %.2f m³\n" +
                    "  - Exceeding Volume: %.2f m³",
                    volumeInitialBlock, acceptedLoss, sumVolume, volumeRemaining, 
                    (volumeInitialBlock - (sumVolume + volumeRemaining)) - acceptedLoss
                );

                req.setAttribute("message", message);
                req.setAttribute("messageType", "warning");
            } 
            
            else if(sumVolume > volumeInitialBlock) {
                String message = String.format(
                    "The total volume of products exceeds the initial sponge volume.\n" +
                    "  - Initial Sponge Volume: %.2f m³\n" +
                    "  - Total Product Volume: %.2f m³\n",
                    volumeInitialBlock, sumVolume
                );

                req.setAttribute("message", message);
                req.setAttribute("messageType", "warning");
            } 
            
            else {
                SpongeTransformationService.insert(initialBlock, transformationDate);

                SpongeTransformation lastSpongeTransformation = SpongeTransformationService.getLastSpongeTransformationInserted();
                ProductService.insertProductQuantities(productQuantities, lastSpongeTransformation);

                // check if the remaining volume is not zero
                if ((remainingLength * remainingWidth * remainingHeight) != 0) {
                    InitialSpongeService.insertRemaining(initialBlock, remainingLength, remainingWidth, remainingHeight);
                    InitialSpongeService.setIsTransformedFlag("TRUE", initialSpongeId, null);
    
                    RemainingTransformationService.insertRemainingTransformation(lastSpongeTransformation);    
                }

                req.setAttribute("message", "Transformation completed successfully! The initial sponge and products have been saved.");
                req.setAttribute("messageType", "success");
            }  
        } 
        
        catch (Exception e) {
            e.printStackTrace();
            
            req.setAttribute("message", "An unexpected error occurred during the transformation process. Please review the data and try again.");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }

        // forward to the view with a message
        req.setAttribute("template_content", "/WEB-INF/views/insert-transformation.jsp");
        req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
    }
}