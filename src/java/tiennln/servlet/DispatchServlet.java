/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

    private final String HOME_PAGE = "homePage.jsp";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String SEARCH_CONTROLLER = "SearchServlet";
    private final String STARTUP_CONTROLLER = "StartUpServlet";
    private final String ADD_TO_CART_CONTROLLER = "AddToCartServlet";
    private final String VIEW_CART_CONTROLLER = "ViewCartServlet";
    private final String LOG_OUT_CONTROLLER = "LogOutServlet";
    private final String UPDATE_CART_CONTROLLER = "UpdateCartServlet";
    private final String DELETE_ITEM_CART_CONTROLLER = "DeleteItemCartServlet";
    private final String CONFIRM_CART_CONTROLLER = "ConfirmCartServlet";
    private final String LOGIN_BY_GOOGLE_CONTROLLER = "LoginByGoogleServlet";
    private final String SETTING_NAME_CONTROLLER = "SettingNameServlet";
    private final String CHECK_OUT_CONTROLLER = "CheckOutServlet";
    private final String VIEW_SHOPPING_HISTORY_CONTROLLER = "ViewShoppingHistoryServlet";
    private final String UPDATE_ITEM_INFORMATION_CONTROLLER = "UpdateItemInformationServlet";
    private final String DELETE_ITEM_CONTROLLER = "DeleteItemServlet";
    private final String ADD_ITEM_CONTROLLER = "AddItemServlet";

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
        String button = request.getParameter("btnAction");

        try {
            if (button == null) {
                url = STARTUP_CONTROLLER;
            } else if (button.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (button.equals("Search")) {
                url = SEARCH_CONTROLLER;
            } else if (button.equals("Add To Cart")) {
                url = ADD_TO_CART_CONTROLLER;
            } else if (button.equals("View Cart")) {
                url = VIEW_CART_CONTROLLER;
            } else if (button.equals("Logout")) {
                url = LOG_OUT_CONTROLLER;
            } else if (button.equals("updateCart")) {
                url = UPDATE_CART_CONTROLLER;
            } else if (button.equals("deleteItem")) {
                url = DELETE_ITEM_CART_CONTROLLER;
            } else if (button.equals("confirmCart")) {
                url = CONFIRM_CART_CONTROLLER;
            } else if (button.equals("googleLogin")) {
                url = LOGIN_BY_GOOGLE_CONTROLLER;
            } else if (button.equals("setupName")) {
                url = SETTING_NAME_CONTROLLER;
            } else if (button.equals("checkOut")) {
                url = CHECK_OUT_CONTROLLER;
            } else if (button.equals("viewShoppingHistory")) {
                url = VIEW_SHOPPING_HISTORY_CONTROLLER;
            } else if (button.equals("Save Changed")) {
                url = UPDATE_ITEM_INFORMATION_CONTROLLER;
            } else if (button.equals("Search History")) {
                url = VIEW_SHOPPING_HISTORY_CONTROLLER;
            } else if (button.equals("Delete")) {
                url = DELETE_ITEM_CONTROLLER;
            } else if (button.equals("Add Item")) {
                url = ADD_ITEM_CONTROLLER;
            }
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
