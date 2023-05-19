/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;


import entity.Shoe;
import entity.User;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.ShoeFacade;
import session.CoverFacade;

/**
 *
 * @author pupil
 */
@WebServlet(name = "ShoeServlet", loadOnStartup = 1, urlPatterns = {
    "/newShoe",
    "/createShoe",
    "/listShoes",
})
public class ShoeServlet extends HttpServlet {

   @EJB private ShoeFacade shoeFacade;
   @EJB private CoverFacade coverFacade;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        switch (path) {
            case "/newShoe":
                request.setAttribute("listCovers", coverFacade.findAll());
                request.getRequestDispatcher("/WEB-INF/shoe/createShoe.jsp").forward(request, response);
                break;
            case "/createShoe":
                String name = request.getParameter("name");
                String price = request.getParameter("price");
                String countItem = request.getParameter("countItem");
                String coverId = request.getParameter("coverId");
                Shoe shoe = new Shoe();
                shoe.setName(name);
                shoe.setPrice(Double.parseDouble(price));
                shoe.setCountItem(Integer.parseInt(countItem));
                shoe.setCover(coverFacade.find(Long.parseLong(coverId)));
                shoeFacade.create(shoe);
                request.setAttribute("info", "Обувь добавлена");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/listShoes":
                request.setAttribute("listShoes", shoeFacade.findAll());
                request.getRequestDispatcher("/WEB-INF/shoe/listShoe.jsp").forward(request, response);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
