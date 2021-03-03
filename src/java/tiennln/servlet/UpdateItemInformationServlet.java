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
import tiennln.items.ItemsDAO;
import tiennln.users.UsersDTO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UpdateItemInformationServlet", urlPatterns = {"/UpdateItemInformationServlet"})
public class UpdateItemInformationServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(UpdateItemInformationServlet.class);
    private final String START_UP_CONTROLLER = "StartUpServlet";

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

        String txtOldItemName = request.getParameter("txtOldItemName");
        String txtImgLink = request.getParameter("txtImgLink");
        String txtItemName = request.getParameter("txtItemName");
        String txtDescription = request.getParameter("txtDescription");
        String txtPrice = request.getParameter("txtPrice");
        String txtQuantity = request.getParameter("txtQuantity");
        String txtCategory = request.getParameter("txtCategory");
        String txtStatus = request.getParameter("txtStatus");

        String url = START_UP_CONTROLLER;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UsersDTO user = (UsersDTO) session.getAttribute("LAST_USER");
                String username = user.getUserID();

                ItemsDAO dao = new ItemsDAO();
                dao.updateItem(username, txtOldItemName, txtItemName, txtImgLink, txtDescription, Float.parseFloat(txtPrice), txtCategory, Integer.parseInt(txtQuantity), Boolean.parseBoolean(txtStatus));
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
