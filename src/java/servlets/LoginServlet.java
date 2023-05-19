/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Customer;
import entity.User;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.CoverFacade;
import session.ShoeFacade;
import session.CustomerFacade;
import session.UserFacade;
import tools.EncryptPassword;

/**
 *
 * @author pupil
 */
@WebServlet(name = "LoginServlet", loadOnStartup = 1, urlPatterns = {
    "/index",
    "/loginForm",
    "/login",
    "/logout",
})
public class LoginServlet extends HttpServlet {

    @EJB private UserFacade userFacade;
    @EJB private CustomerFacade customerFacade;
    @EJB private CoverFacade coverFacade;
    @EJB private ShoeFacade shoeFacade;
    
    static enum Roles { ADMINISTRATOR, MANAGER, USER };

    @Override
    public void init() throws ServletException {
        super.init();
        if(userFacade.count()>0) return;
        Customer customer = new Customer();
        customer.setFirstName("Sergei");
        customer.setLastName("P");
        customer.setPhone("555555");
        customer.setCash(10000);
        customerFacade.create(customer);
        
        User user = new User();
        user.setLogin("Administrator");
        EncryptPassword ep = new EncryptPassword();
        user.setSalt(ep.getSalt());
        user.setPassword(ep.getProtectedPassword("123456", user.getSalt()));
        user.setCustomer(customer);
        user.getRoles().add(LoginServlet.Roles.ADMINISTRATOR.toString());
        user.getRoles().add(LoginServlet.Roles.MANAGER.toString());
        user.getRoles().add(LoginServlet.Roles.USER.toString());
        userFacade.create(user);
    }
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        switch (path) {
            case "/index":
                request.setAttribute("listShoes", shoeFacade.findAll());
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
            case "/loginForm":
                request.getRequestDispatcher("loginForm.jsp").forward(request, response);
                break;
            case "/login":
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                User user = userFacade.findByLogin(login);
                if(user == null) {
                    request.setAttribute("info", "Нет такого пользователя или неправильный пароль1.");
                    request.getRequestDispatcher("/loginForm").forward(request, response);
                    break;
                }
                EncryptPassword ep = new EncryptPassword();
                password = ep.getProtectedPassword(password, user.getSalt());
                if(!password.equals(user.getPassword())) {
                    request.setAttribute("info", "Нет такого пользователя или неправильный пароль.");
                    request.getRequestDispatcher("/loginForm").forward(request, response);
                    break;
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("authUser", user);
                session.setAttribute("info", "Привет, " + user.getLogin() + "!");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/logout":
                session = request.getSession(false);
                if(session != null) {
                    session.invalidate();
                    request.setAttribute("info", "Вы вышли");
                }
                response.sendRedirect("index.jsp");
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
