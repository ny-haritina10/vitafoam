package mg.itu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mg.itu.model.InitialSponge;
import mg.itu.service.InitialSpongeService;

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

            // edit form
            else if (mode != null && mode.equals("edit")) {
                int id = Integer.parseInt(req.getParameter("id"));
                InitialSponge sponge = new InitialSponge().getById(id, InitialSponge.class, null);
                
                if (sponge != null) {
                    req.setAttribute("sponge", sponge);
                    req.setAttribute("template_content", "/WEB-INF/views/edit-initial-sponge.jsp");
                    req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("InitialSpongeController");
                }
            }
            // list of initial sponge
            else {
                InitialSponge[] sponges = new InitialSponge().getAll(InitialSponge.class, null);
                req.setAttribute("sponges", sponges);
                req.setAttribute("template_content", "/WEB-INF/views/list-initial-sponge.jsp");
                req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
    {
        String mode = req.getParameter("mode");
        
        try {
            // Handle update
            if (mode != null && mode.equals("update")) {
                int id = Integer.parseInt(req.getParameter("id"));
                double purchasePrice = Double.parseDouble(req.getParameter("purchasePrice"));
                String isTransformed = req.getParameter("isTransformed");
                double dimLength = Double.parseDouble(req.getParameter("dimLength"));
                double dimWidth = Double.parseDouble(req.getParameter("dimWidth"));
                double dimHeight = Double.parseDouble(req.getParameter("dimHeight"));

                InitialSponge sponge = new InitialSponge();
                sponge.setId(id);
                sponge.setPurchasePrice(purchasePrice);
                sponge.setIsTransformed(isTransformed);
                sponge.setDimLength(dimLength);
                sponge.setDimWidth(dimWidth);
                sponge.setDimHeight(dimHeight);

                boolean updated = sponge.update(null, "InitialSponge");

                if (updated) {
                    resp.sendRedirect("InitialSpongeController");
                    return;
                } else {
                    req.setAttribute("message", "Failed to update initial sponge.");
                    req.setAttribute("sponge", sponge);
                    req.setAttribute("template_content", "/WEB-INF/views/edit-initial-sponge.jsp");
                }
            }
            // Handle create
            else {
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

                
                double nP = InitialSpongeService.getPurchasePriceV2ById();

                if (savedInitialSponge) {
                    req.setAttribute("message", "Initial sponge inserted successfully!");
                } else {
                    req.setAttribute("message", "Failed to insert initial sponge.");
                }
                req.setAttribute("template_content", "/WEB-INF/views/insert-initial-sponge.jsp");
            }
        } catch (Exception e) {
            req.setAttribute("message", "Error processing form data: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("template_content", mode != null && mode.equals("update") ? 
                "/WEB-INF/views/edit-initial-sponge.jsp" : "/WEB-INF/views/insert-initial-sponge.jsp");
        }
        
        req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
    }
}