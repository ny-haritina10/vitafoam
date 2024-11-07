package mg.itu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("controller/MainController")
public class MainController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        try {
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