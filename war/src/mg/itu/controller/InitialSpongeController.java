package mg.itu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mg.itu.model.InitialSponge;

@WebServlet("/controller/InitialSpongeController")
public class InitialSpongeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
    {
        try {
            String mode = req.getParameter("mode");

            // insertion form
            if (mode != null && mode.equals("create")) {
                req.setAttribute("template_content", "/WEB-INF/views/insert-initial-sponge.jsp");
                req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
            }

            // list of initial sponge
            else {
                InitialSponge[] sponges = new InitialSponge().getAll(InitialSponge.class, null);

                req.setAttribute("sponges", sponges);
                req.setAttribute("template_content", "/WEB-INF/views/list-initial-sponge.jsp");
                req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
            }
        }    
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
    {
        
        // Retrieve form data
        try {
            double purchasePrice = Double.parseDouble(req.getParameter("purchasePrice"));
            String isTransformed = req.getParameter("isTransformed");
            double dimLength = Double.parseDouble(req.getParameter("dimLength"));
            double dimWidth = Double.parseDouble(req.getParameter("dimWidth"));
            double dimHeight = Double.parseDouble(req.getParameter("dimHeight"));

            InitialSponge initialSponge = new InitialSponge();

            initialSponge.setPurchasePrice(purchasePrice);
            initialSponge.setIsTransformed(isTransformed);
            initialSponge.setDimLength(dimLength);
            initialSponge.setDimWidth(dimWidth);
            initialSponge.setDimHeight(dimHeight);

            boolean savedInitialSponge = initialSponge.save(null, "InitialSponge", "seq_initial_sponge.NEXTVAL");

            if (savedInitialSponge) 
            { req.setAttribute("message", "Initial sponge inserted successfully!"); } 
            
            else 
            { req.setAttribute("message", "Failed to insert initial sponge."); }

        } catch (Exception e) {
            req.setAttribute("message", "Error processing form data: " + e.getMessage());
            e.printStackTrace();
        }

        // forward to the view with a message
        req.setAttribute("template_content", "/WEB-INF/views/insert-initial-sponge.jsp");
        req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
    }
}