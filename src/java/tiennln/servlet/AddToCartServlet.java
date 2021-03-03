/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import tiennln.items.ItemsDTO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(AddToCartServlet.class);

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

        String itemName = request.getParameter("txtItemName");
        String lastSearch = request.getParameter("txtLastSearchValue");
        String lastCategory = request.getParameter("txtLastCategory");
        String lastPriceStart = request.getParameter("txtLastPriceStart");
        String lastPriceEnd = request.getParameter("txtLastPriceEnd");

        String url = "DispatchServlet"
                + "?btnAction=Search"
                + "&txtSearch=" + lastSearch
                + "&txtCategory=" + lastCategory
                + "&txtPriceStart=" + lastPriceStart
                + "&txtPriceEnd=" + lastPriceEnd;

        try {
            ItemsDAO dao = new ItemsDAO();
            dao.searchItems(lastSearch, lastPriceStart, lastPriceEnd, lastCategory, 1);
            List<ItemsDTO> searchResult = dao.getListResult();

            ItemsDTO item = null;

            for (ItemsDTO dto : searchResult) {
                if (dto.getName().equals(itemName)) {
                    item = dto;
                    break;
                }
            }

            HttpSession session = request.getSession();
            CartObject cart = (CartObject) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartObject();
            }
            cart.addItemIntoCart(item);
            session.setAttribute("CART", cart);

            HashMap<ItemsDTO, Integer> listCart = cart.getCartItem();
            Iterator<Map.Entry<ItemsDTO, Integer>> it = listCart.entrySet().iterator();
            while (it.hasNext()) {
                HashMap.Entry<ItemsDTO, Integer> itemTemp = (Map.Entry<ItemsDTO, Integer>) it.next();
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
