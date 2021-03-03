/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tiennln.cart.CartObject;
import tiennln.items.ItemsDAO;
import tiennln.orders.OrdersDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(CheckOutServlet.class);
    private final String CHECK_OUT_COMPLETE_PAGE = "checkOutComplete.html";
    private final String CHECK_OUT_FAIL_PAGE = "checkOutFail.html";

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

        String txtFullname = request.getParameter("txtFullname");
        String txtPhoneNumber = request.getParameter("txtPhoneNumber");
        String txtAddress = request.getParameter("txtAddress");
        String payment = request.getParameter("paymentMethod");
//        String totalMoney = request.getParameter("txtTotalMoney");

        String url = CHECK_OUT_FAIL_PAGE;

        try {
            if (payment.equalsIgnoreCase("cash")) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    CartObject cart = (CartObject) session.getAttribute("CART");

                    if (cart != null) {
                        String orderID = (String) session.getAttribute("ORDER_LOADING");

                        OrdersDAO ordersDAO = new OrdersDAO();
                        ordersDAO.setStatusPaid(orderID, txtFullname, txtAddress, txtPhoneNumber);

                        ItemsDAO itemDAO = new ItemsDAO();
                        itemDAO.updateItemAfterCheckoutBill(cart);
                        url = CHECK_OUT_COMPLETE_PAGE;

                        session.removeAttribute("CART");
                    }
                }
            } else if (payment.equalsIgnoreCase("paypal")) {
//                HttpSession session = request.getSession(false);
//
//                if (session != null) {
//                    CartObject cart = (CartObject) session.getAttribute("CART");
//
//                    if (cart != null) {
//                        String orderID = (String) session.getAttribute("ORDER_LOADING");
//
//                        url = "https://www.sandbox.paypal.com/cgi-bin/webscr?"
//                        + "business=sb-lhzne4845948@business.example.com"
//                        + "&cmd=_xclick"
//                        + "&item_name=" + orderID
//                        + "&amount=" + totalMoney
//                        + "&currency_code=USD"
//                        + "&return=checkOutComplete.html"
//                        + "&cancel_return=checkOutFail.html";
//                        
//                        session.removeAttribute("CART");
//                    }
//                }
            }

        } catch (NamingException ex) {
            logger.error(ex.getMessage());
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
