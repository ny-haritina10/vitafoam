package mg.itu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.model.OptimisticProfits;

@WebServlet("/controller/OptimisticProfitsController")
public class OptimisticProfitsController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
    {
        try {
            OptimisticProfits[] totals = new OptimisticProfits().getAll(OptimisticProfits.class, null, "v_optimistic_profits");
            
            req.setAttribute("totals", totals);
            req.setAttribute("template_content", "/WEB-INF/views/optimistic-profits.jsp");
            req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}