    package mg.itu.controller;

    import java.io.IOException;

    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;

    import mg.itu.model.MinimalisticProfits;

    @WebServlet("/controller/MinimalisticProfitsController")
    public class MinimalisticProfitsController extends HttpServlet {
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException 
        {
            try {
                MinimalisticProfits[] totals = new MinimalisticProfits().getAll(MinimalisticProfits.class, null, "v_minimalistic_profits");
                
                req.setAttribute("totals", totals);
                req.setAttribute("template_content", "/WEB-INF/views/minimalistic-profits.jsp");
                req.getRequestDispatcher("/WEB-INF/templates/home.jsp").forward(req, resp);
            } 
            
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }