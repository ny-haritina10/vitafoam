package mg.itu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.model.MinimalisticProfits;
import mg.itu.model.OptimisticProfits;
import mg.itu.model.ProductCostPrice;
import mg.itu.model.ProductCostPricePonderee;
import mg.itu.model.TransformationTotalAmount;

@WebServlet("/controller/StockStatusController")
public class StockStatusController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
    {
        try {
            TransformationTotalAmount[] totalAmount = new TransformationTotalAmount().getAll(TransformationTotalAmount.class, null, "v_transformation_total_amount");
            MinimalisticProfits[] minimimProfit = new MinimalisticProfits().getAll(MinimalisticProfits.class, null, "v_minimalistic_profits");
            OptimisticProfits[] optimisticProfit = new OptimisticProfits().getAll(OptimisticProfits.class, null, "v_optimistic_profits");
            ProductCostPrice[] costPrice = new ProductCostPrice().getAll(ProductCostPrice.class, null, "v_product_cost_price");
            ProductCostPricePonderee[] costPricePonderee = new ProductCostPricePonderee().getAll(ProductCostPricePonderee.class, null, "v_product_cost_price_ponderee");  
            
            
            req.setAttribute("total-amount", totalAmount);
            req.setAttribute("minimim-profit", minimimProfit);
            req.setAttribute("optimistic-profit", optimisticProfit);
            req.setAttribute("cost-price", costPrice);
            req.setAttribute("cost-price-ponderee", costPricePonderee);

            req.setAttribute("template_content", "/WEB-INF/views/stock-status.jsp");
            req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}