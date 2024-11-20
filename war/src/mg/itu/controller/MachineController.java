package mg.itu.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.model.MachinePriceResult;

@WebServlet("/controller/MachineController")
public class MachineController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException 
    {
        try {
            List<MachinePriceResult> result = MachinePriceResult.getAll();
            
            req.setAttribute("result", result);
            req.setAttribute("template_content", "/WEB-INF/views/machine-result.jsp");

            req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}