/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Customer;
import entity.User;
import java.io.IOException;
import java.util.Objects;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.CustomerFacade;
import session.UserFacade;
import tools.EncryptPassword;

/**
 *
 * @author pupil
 */
@WebServlet(name = "CustomerServlet", urlPatterns = {
    "/newCustomer",
    "/createCustomer",
    "/listCustomers",
    "/changeRole",
    "/editUserRole",
})
public class CustomerServlet extends HttpServlet {

    @EJB private CustomerFacade customerFacade;
    @EJB private UserFacade userFacade;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        User authUser = (User) session.getAttribute("authUser");
        String path = request.getServletPath();
        switch (path) {
            case "/newCustomer":
                request.getRequestDispatcher("/WEB-INF/customer/createCustomer.jsp").forward(request, response);
                break;
            case "/createCustomer":
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastname");
                String phone = request.getParameter("phone");
                String cash = request.getParameter("cash");
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                if(firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty() 
                        || phone == null || phone.isEmpty() || login == null || login.isEmpty()
                        || password == null || password.isEmpty()) {
                    request.setAttribute("info", "Не все поля заполнены");
                    request.getRequestDispatcher("/newCustomer").forward(request, response);
                    break;
                }
                Customer customer = new Customer();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setCash(Integer.parseInt(cash));
                customerFacade.create(customer);
                User user = new User();
                user.setLogin(login);
                EncryptPassword ep = new EncryptPassword();
                user.setSalt(ep.getSalt());
                user.setPassword(ep.getProtectedPassword(password, user.getSalt()));
                user.setCustomer(customer);
                user.getRoles().add(LoginServlet.Roles.USER.toString());
                try {
                    userFacade.create(user);
                } catch (Exception e) {
                    request.setAttribute("info", "Такой пользователь уже существует");
                    request.getRequestDispatcher("/newCustomer").forward(request, response);
                    break;
                }
                request.setAttribute("info", "Покупатель добавлен");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/listCustomers":
                
                if(session == null) {
                    request.setAttribute("info", "У вас нет прав. Авторизуйтесь.");
                    request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
                    break;
                }
                
                if(authUser == null) {
                    request.setAttribute("info", "У вас нет прав. Авторизуйтесь.");
                    request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
                    break;
                }
                if(
                        !authUser.getRoles().contains(LoginServlet.Roles.ADMINISTRATOR.toString())
                        ||
                        !authUser.getRoles().contains(LoginServlet.Roles.MANAGER.toString())
                    ){
                    request.setAttribute("info", "У вас нет прав. Авторизуйтесь.");
                    request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
                    break;
                }
                request.setAttribute("listCustomers", customerFacade.findAll());
                request.getRequestDispatcher("/WEB-INF/customer/listCustomer.jsp").forward(request, response);
                break;
            case "/changeRole":
                request.setAttribute("listRoles", LoginServlet.Roles.values());
                request.setAttribute("listUsers", userFacade.findAll());
                request.getRequestDispatcher("/WEB-INF/customer/changeRole.jsp").forward(request, response);
                break;
            case "/editUserRole":
                String btnDelete = request.getParameter("btnDelete");
                String btnAdd = request.getParameter("btnAdd");
                String userId = request.getParameter("userId");
                String roleName = request.getParameter("roleName");
                if(userId == null || userId.isEmpty() || roleName == null || roleName.isEmpty()){
                    request.setAttribute("info", "Не выбран пользователь или роль из списка");
                    request.getRequestDispatcher("/WEB-INF/customer/changeRole.jsp").forward(request, response);
                    break;
                }
                user = userFacade.find(Long.parseLong(userId));
                if(user.getLogin().equals("Administrator")){
                    request.setAttribute("info", "Этому пользователю изменить роль нет возможности");
                    request.getRequestDispatcher("/changeRole").forward(request, response);
                    break;
                }
                if(btnDelete == null || btnDelete.isEmpty()){
                    if(!user.getRoles().contains(roleName)){
                        user.getRoles().add(roleName);
                    }
                }else if(btnAdd == null || btnAdd.isEmpty()){
                    if(user.getRoles().contains(roleName)){
                        user.getRoles().remove(roleName);
                    }
                }
                userFacade.edit(user);
                
                if(!Objects.equals(user.getId(), authUser.getId())) {
                    request.getRequestDispatcher("/changeRole").forward(request, response);
                } else {
                    session.setAttribute("authUser", user);
                    response.sendRedirect(request.getContextPath() + "/changeRole");
                }
                
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
