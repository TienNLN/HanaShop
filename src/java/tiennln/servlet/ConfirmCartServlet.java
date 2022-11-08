/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tiennln.cart.CartObject;
import tiennln.items.PlantDAO;
import tiennln.orderdetail.OrderDetailsDAO;
import tiennln.orders.OrdersDAO;
import tiennln.users.AccountDTO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ConfirmCartServlet", urlPatterns = {"/ConfirmCartServlet"})
public class ConfirmCartServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(ConfirmCartServlet.class);
    private final String CHECK_OUT_PAGE = "checkOutPage.jsp";
    private final String CONFIRM_CART_FAIL_PAGE = "confirmCartFail.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = CONFIRM_CART_FAIL_PAGE;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                AccountDTO user = (AccountDTO) session.getAttribute("LAST_USER");
                if (user != null) {
                    CartObject cart = (CartObject) session.getAttribute("CART");
                    if (cart != null) {
                        String userID = user.getEmail();

                        PlantDAO itemDAO = new PlantDAO();
//                        List<String> outOfStorageItemsList = itemDAO.checkOutOfStorageItems(cart);

                        OrdersDAO ordersDAO = new OrdersDAO();
                        String orderID = ordersDAO.confirmOrder(userID, cart);

                        session.setAttribute("ORDER_LOADING", orderID);

                        OrderDetailsDAO orderDetailDAO = new OrderDetailsDAO();
                        orderDetailDAO.addItems(orderID, cart);
                        url = CHECK_OUT_PAGE;
                    }
                }
            }
        } catch (NamingException ex) {
            logger.error(ex.getMessage());
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        } finally {
            if (url.equals(CHECK_OUT_PAGE)) {
                response.sendRedirect(url);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
            out.close();
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
