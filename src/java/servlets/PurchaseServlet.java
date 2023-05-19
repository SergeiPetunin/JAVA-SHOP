/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Customer;
import entity.Purchase;
import entity.Shoe;
import entity.User;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.CustomerFacade;
import session.PurchaseFacade;
import session.ShoeFacade;

/**
 *
 * @author pupil
 */
@WebServlet(name = "PurchaseServlet",loadOnStartup = 1, urlPatterns = {
    "/newPurchase",
    "/createPurchase",
    "/listPurchases",
    "/statisticForm",
    "/calcStatistic",
})
public class PurchaseServlet extends HttpServlet {

   @EJB private PurchaseFacade purchaseFacade;
   @EJB private ShoeFacade shoeFacade;
   @EJB private CustomerFacade customerFacade;
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        switch (path) {
            case "/newPurchase":
                request.setAttribute("listCustomers", customerFacade.findAll());
                request.setAttribute("listShoes", shoeFacade.findAll());
                request.getRequestDispatcher("/WEB-INF/purchase/createPurchase.jsp").forward(request, response);
                break;
            case "/createPurchase":
                String customerId = request.getParameter("customerId");
                String shoeId = request.getParameter("shoeId");
                String qtty = request.getParameter("qtty");
                Customer customer = customerFacade.find(Long.parseLong(customerId));
                Shoe shoe = shoeFacade.find(Long.parseLong(shoeId));
                
                Purchase purchase = new Purchase();
                purchase.setCustomer(customer);
                purchase.setShoe(shoe);
                purchase.setQtty(Integer.parseInt(qtty));
                purchase.setPurchaseDate(new GregorianCalendar().getTime());
                purchaseFacade.create(purchase);
                request.setAttribute("info", "Покупка завершена");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                
                shoe.setCountItem(shoe.getCountItem() - purchase.getQtty());
                shoeFacade.edit(shoe);
                
                customer.setCash(customer.getCash() - shoe.getPrice() * purchase.getQtty());
                customerFacade.edit(customer);
                break;
            case "/listPurchases":
                HttpSession session = request.getSession(false);
                if(session == null) {
                    request.setAttribute("info", "У вас нет прав. Авторизуйтесь.");
                    request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
                    break;
                }
                User authUser = (User) session.getAttribute("authUser");
                if(authUser == null) {
                    request.setAttribute("info", "У вас нет прав. Авторизуйтесь.");
                    request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
                    break;
                }
                if(
                        !authUser.getRoles().contains(LoginServlet.Roles.ADMINISTRATOR.toString())
                        ||
                        !authUser.getRoles().contains(LoginServlet.Roles.MANAGER.toString())
                    ) {
                    request.setAttribute("info", "У вас нет прав. Авторизуйтесь.");
                    request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
                    break;
                }
                request.setAttribute("listPurchases", purchaseFacade.findAll());
                request.getRequestDispatcher("/WEB-INF/purchase/listPurchase.jsp").forward(request, response);
                break;
            case "/statisticForm":
                SimpleDateFormat sdt = new SimpleDateFormat("y");
                Integer year = Integer.parseInt(sdt.format(new Date()));
                List<Integer> years = new ArrayList<>();
                years.add(year - 1);
                years.add(year);
                request.setAttribute("years", years);
                request.getRequestDispatcher("/WEB-INF/statistic/changeDateStatistic.jsp").forward(request, response);
                break;
            case "/calcStatistic":
                String yearStr = request.getParameter("year");
                String month = request.getParameter("month");
                String day = request.getParameter("day");
                if((month == null || month.isEmpty()) && (day == null || day.isEmpty())){
                    request.setAttribute("period", yearStr+" год");
                }else if(day == null || day.isEmpty() && (month != null || !month.isEmpty())){
                    request.setAttribute("period", month+" месяц");
                }else{
                    request.setAttribute("period",yearStr +" год, "+ month+" месяц, "+day+" день");
                }
                Map<Shoe,Integer> mapShoeRange = purchaseFacade.getPurchaseInPeriod(yearStr,month,day);
                if(mapShoeRange.isEmpty()){
                    request.setAttribute("info", "В этот период покупки не совершались");
                }else{
                    request.setAttribute("mapShoeRange", mapShoeRange);
                }
                request.getRequestDispatcher("/WEB-INF/statistic/statisticForm.jsp").forward(request, response);

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
