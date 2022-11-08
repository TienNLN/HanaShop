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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import tiennln.items.PlantDAO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddItemServlet", urlPatterns = {"/AddItemServlet"})
public class AddItemServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(AddItemServlet.class);
    private final String ADD_NEW_ITEM_FAIL_PAGE = "addNewItemFail.jsp";

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

        String txtName = request.getParameter("txtName");
        String txtImage = request.getParameter("txtImage");
        String txtDescription = request.getParameter("txtDescription");
        String txtPriceString = request.getParameter("txtPrice");
        String txtCategory = request.getParameter("txtCategory");
        String txtQuantityString = request.getParameter("txtQuantity");
        
        float txtPrice = -1;
        int txtQuantity = -1;

        boolean foundErr = false;

        String url = ADD_NEW_ITEM_FAIL_PAGE;

        try {
            if (txtName.isEmpty()) {
                request.setAttribute("ITEM_NAME_EMPTY_ERR", "Item Name cannot be empty");
                foundErr = true;
            }
            if (txtImage.isEmpty()) {
                request.setAttribute("ITEM_IMG_EMPTY_ERR", "Item Image cannot be empty");
                foundErr = true;
            }
            if (txtDescription.isEmpty()) {
                request.setAttribute("ITEM_DESCRIPTION_EMPTY_ERR", "Item Description cannot be empty");
                foundErr = true;
            }
            if (txtPriceString.isEmpty()) {
                request.setAttribute("ITEM_PRICE_EMPTY_ERR", "Item Price cannot be empty");
                foundErr = true;
            }
            if (txtCategory.isEmpty()) {
                request.setAttribute("ITEM_CATEGORY_EMPTY_ERR", "Item Category cannot be empty");
                foundErr = true;
            }
            if (txtQuantityString.isEmpty()) {
                request.setAttribute("ITEM_QUANTITY_EMPTY_ERR", "Item Quantity cannot be empty");
                foundErr = true;
            }

            if (!foundErr) {
                txtPrice = Float.parseFloat(txtPriceString);
                txtQuantity = Integer.parseInt(txtQuantityString);
                
                PlantDAO itemDao = new PlantDAO();
                itemDao.addNewItem(txtName, txtImage, txtDescription, txtPrice, txtCategory, txtQuantity);
                
                url = "DispatchServlet";
            }
        } catch (NamingException ex) {
            logger.error(ex.getMessage());
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        } finally {
            if(foundErr){
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
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
