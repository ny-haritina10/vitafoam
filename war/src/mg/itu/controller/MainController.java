package mg.itu.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.model.InitialSponge;
import mg.itu.service.InitialSpongeService;


@WebServlet("controller/MainController")
public class MainController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        try {
            InitialSponge block = new InitialSponge();
            
            block.setDimHeight(1);
            block.setDimLength(1);
            block.setDimWidth(210);

            HashMap<String, Double> sum = InitialSpongeService.getSumVolumeAndSumPurchasePrice(3);
            double pratique = InitialSpongeService.getPrixRevientPratique(block, sum, -10, 10);

            System.out.println("#---------------# PR PRATIQUE: " + pratique);

            req.setAttribute("template_content", "/WEB-INF/views/main.jsp");
            req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);   
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        
    }
}