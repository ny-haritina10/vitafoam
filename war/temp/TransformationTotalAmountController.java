package mg.itu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.model.TransformationTotalAmount;

@WebServlet("/controller/TransformationTotalAmountController")
public class TransformationTotalAmountController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
    {
        try {
            TransformationTotalAmount[] totals = new TransformationTotalAmount().getAll(TransformationTotalAmount.class, null, "v_transformation_total_amount");
            
            req.setAttribute("totals", totals);
            req.setAttribute("template_content", "/WEB-INF/views/transformation-total-amount.jsp");
            req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}