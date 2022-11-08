/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.servlet;

import tiennln.items.PlantDAO;
import tiennln.items.PlantDTO;
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
import tiennln.users.AccountDTO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(SearchServlet.class);
    private final String HOME_PAGE = "homePage.jsp";
    private final String USER_PAGE = "userPage.jsp";

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

        String searchValue = request.getParameter("txtSearch");
        String category = request.getParameter("txtCategory");
        String priceStart = request.getParameter("txtPriceStart");
        String priceEnd = request.getParameter("txtPriceEnd");

        String pageNumberUserString = request.getParameter("pageNumberUser");

        String url = HOME_PAGE;
        HttpSession session = null;

        try {
            PlantDAO dao = new PlantDAO();

            session = request.getSession();

            if (pageNumberUserString == null) {
                dao.searchItems(searchValue, priceStart, priceEnd, category, 1);
            } else {
                int pageNumberUser = Integer.parseInt(pageNumberUserString);
                dao.searchItems(searchValue, priceStart, priceEnd, category, pageNumberUser);
            }

            List<PlantDTO> searchResult = dao.getListResult();

            if (searchResult.isEmpty()) {
                request.setAttribute("NO_SEARCH_RESULT", "No result matched !");
            } else {
                request.setAttribute("SEARCH_RESULT", searchResult);
            }
            request.setAttribute("LAST_SEARCH_VALUE", searchValue);
            request.setAttribute("LAST_SEARCH_CATEGORY", category);

            session = request.getSession(false);
            AccountDTO user = (AccountDTO) session.getAttribute("LAST_USER");
            if (user != null) {
                url = USER_PAGE;
            }

        } catch (NamingException ex) {
            logger.error(ex.getMessage());
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
