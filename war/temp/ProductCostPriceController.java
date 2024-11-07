package mg.itu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.model.ProductCostPrice;

@WebServlet("/controller/ProductCostPriceController")
public class ProductCostPriceController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
    {
        try {
            ProductCostPrice[] totals = new ProductCostPrice().getAll(ProductCostPrice.class, null, "v_product_cost_price");
            
            req.setAttribute("totals", totals);
            req.setAttribute("template_content", "/WEB-INF/views/product-cost-price.jsp");
            req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
