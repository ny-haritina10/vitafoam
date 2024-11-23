package mg.itu.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mg.itu.generator.ImportCSVData;
import mg.itu.generator.InitialSpongeCSVGenerator;
import mg.itu.service.InitialSpongeService;


@WebServlet("controller/ImportController")
public class ImportController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        String mode = (String) req.getParameter("mode");
        String message = "";

        System.out.println("MODE: " + mode);

        try {
            if (mode != null && mode.equals("import_csv")) {
                String path = "D:\\Studies\\ITU\\S5\\INF301_Architechture-Logiciel\\projet\\vitafoam\\ejb\\src\\mg\\itu\\generator\\csv\\initial_sponge_data.csv";

                ImportCSVData.importCSVToDatabase(path);
                message += "Data imported and processed successfully ! Check out StockExit table";     
            }

            else if (mode != null && mode.equals("generate_csv")) {
                int numberOfRows = 100;
                String folderPath = "D:\\Studies\\ITU\\S5\\INF301_Architechture-Logiciel\\projet\\vitafoam\\ejb\\src\\mg\\itu\\generator\\csv";
                
                InitialSpongeCSVGenerator.generateCSV(numberOfRows, folderPath);
                message += "Data generated successfully !";
            }

            req.setAttribute("message", message);
            req.setAttribute("template_content", "/WEB-INF/views/main.jsp");
            
            req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
        } 
        
        catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
            req.setAttribute("messageType", "danger");
            
            req.setAttribute("template_content", "/WEB-INF/views/main.jsp");
            
            req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        
    }
}