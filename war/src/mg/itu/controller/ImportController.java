package mg.itu.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.generator.ImportCSVData;
import mg.itu.service.InitialSpongeService;


@WebServlet("controller/ImportController")
public class ImportController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        try {

            String path = "D:\\Studies\\ITU\\S5\\INF301_Architechture-Logiciel\\projet\\vitafoam\\ejb\\src\\mg\\itu\\generator\\initial_sponge_data.csv";
            ImportCSVData.importCSVToDatabase(path);

            req.setAttribute("message", "Data imported successfully !");
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