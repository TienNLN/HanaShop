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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tiennln.items.ItemsDAO;
import tiennln.items.ItemsDTO;
import tiennln.users.UsersDAO;
import tiennln.users.UsersDTO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "StartUpServlet", urlPatterns = {"/StartUpServlet"})
public class StartUpServlet extends HttpServlet {

    private final String HOME_PAGE = "homePage.jsp";
    private final Logger logger = Logger.getLogger(StartUpServlet.class);

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

        String url = HOME_PAGE;

        HttpSession session = null;
        List<ItemsDTO> itemDTO = null;

        try {
            session = request.getSession();
            ItemsDAO itemDao = new ItemsDAO();
            itemDao.getAllCategory();

            List<String> listCategory = itemDao.getListCategory();

            if (!listCategory.isEmpty()) {
                session.setAttribute("LIST_CATEGORY", listCategory);
            }

            String pageNumberAdminString = request.getParameter("pageNumberAdmin");
            if (pageNumberAdminString != null) {
                int pageNumber = Integer.parseInt(pageNumberAdminString);
                itemDTO = itemDao.loadListItems(pageNumber);
            } else {
                itemDTO = itemDao.loadListItems(1);
            }

            int numberOfPageAdmin = itemDao.getNumberOfPage();
            session.setAttribute("NUMBER_OF_PAGE_ADMIN", numberOfPageAdmin);

            if (!itemDTO.isEmpty()) {
                session.setAttribute("LIST_ITEMS", itemDTO);
            }

            session = request.getSession(false);
            if (session != null) {
                UsersDTO user = (UsersDTO) session.getAttribute("LAST_USER");
                if (user != null) {
                    String username = user.getUserID();

                    String password = user.getPassword();
                    UsersDAO userDao = new UsersDAO();
                    if (userDao.checkLogin(username, password) != null) {
                        url = "DispatchServlet"
                                + "?btnAction=Login"
                                + "&txtUsername=" + username
                                + "&txtPassword=" + password;
                    }
                }
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
