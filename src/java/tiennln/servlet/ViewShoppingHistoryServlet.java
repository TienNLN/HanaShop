/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import tiennln.items.ItemsDAO;
import tiennln.items.ItemsDTO;
import tiennln.orderdetail.OrderDetailsDAO;
import tiennln.orderdetail.OrderDetailsDTO;
import tiennln.orders.OrdersDAO;
import tiennln.orders.OrdersDTO;
import tiennln.users.UsersDTO;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ViewShoppingHistoryServlet", urlPatterns = {"/ViewShoppingHistoryServlet"})
public class ViewShoppingHistoryServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(ViewShoppingHistoryServlet.class);
    private final String VIEW_SHOPPING_HISTORY_PAGE = "viewShoppingHistory.jsp";

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

        String txtSearchItemName = request.getParameter("txtSearchItemName");
        String txtSearchHistoryDate = request.getParameter("txtSearchHistoryDate");

        String url = VIEW_SHOPPING_HISTORY_PAGE;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UsersDTO user = (UsersDTO) session.getAttribute("LAST_USER");
                if (user != null) {
                    String username = user.getUserID();

                    OrdersDAO orderDAO = new OrdersDAO();
                    List<OrdersDTO> listOrdersHistory = orderDAO.loadShoppingOrdersHistory(username);

                    HashMap<String, List<OrderDetailsDTO>> mapOrdersToOrderDetails = new HashMap<>();

                    ItemsDAO itemsDAO = new ItemsDAO();

                    List<ItemsDTO> listItemNameToItemDTO = itemsDAO.loadListItems();

                    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

                    for (OrdersDTO ordersDTOTemp : listOrdersHistory) {
                        List<OrderDetailsDTO> listOrderDetailsHistory = orderDetailsDAO.loadShoppingOrderDetailsHistory(ordersDTOTemp.getOrderID());
                        mapOrdersToOrderDetails.put(ordersDTOTemp.getOrderID(), listOrderDetailsHistory);
                    }

                    if (!mapOrdersToOrderDetails.isEmpty()) {
                        request.setAttribute("LIST_ORDERS_HISTORY", listOrdersHistory);
                        request.setAttribute("MAP_ORDERS_BY_ORDER_DETAILS", mapOrdersToOrderDetails);
                        request.setAttribute("LIST_ITEMS", listItemNameToItemDTO);
                    }

                    if (txtSearchItemName != null || txtSearchHistoryDate != null) {
                        List<OrdersDTO> listOrdersHistorySearch = new ArrayList<>();
                        HashMap<String, List<OrderDetailsDTO>> mapOrdersToOrderDetailsSearch = new HashMap<>();
                        List<OrderDetailsDTO> listOrderDetailsHistorySearch = new ArrayList<>();

                        if (!txtSearchHistoryDate.isEmpty() && txtSearchItemName.isEmpty()) { // ko co Search Item, Search bang Date
                            String day = txtSearchHistoryDate.split("-")[2];
                            String month = txtSearchHistoryDate.split("-")[1];
                            String year = txtSearchHistoryDate.split("-")[0];

                            for (OrdersDTO temp : listOrdersHistory) {
                                String tempDate = temp.getBuyDateTime().split("\\s+")[0];
                                String tempDay = tempDate.split("-")[2];
                                String tempMonth = tempDate.split("-")[1];
                                String tempYear = tempDate.split("-")[0];

                                if (tempYear.equalsIgnoreCase(year)) {
                                    if (tempMonth.equalsIgnoreCase(month)) {
                                        if (tempDay.equalsIgnoreCase(day)) {
                                            listOrdersHistorySearch.add(temp);
                                        }
                                    }
                                }
                            }

                            for (OrdersDTO ordersDTOTemp : listOrdersHistorySearch) {
                                List<OrderDetailsDTO> listOrderDetailsHistory = orderDetailsDAO.loadShoppingOrderDetailsHistory(ordersDTOTemp.getOrderID());
                                mapOrdersToOrderDetailsSearch.put(ordersDTOTemp.getOrderID(), listOrderDetailsHistory);
                            }

                            request.setAttribute("LIST_ORDERS_HISTORY", listOrdersHistorySearch);
                            request.setAttribute("MAP_ORDERS_BY_ORDER_DETAILS", mapOrdersToOrderDetailsSearch);
                        }

                        if (!txtSearchItemName.isEmpty() && txtSearchHistoryDate.isEmpty()) { // ko co Date, Search bang itemName
                            for (OrdersDTO temp : listOrdersHistory) {
                                List<OrderDetailsDTO> listOrderDetailsHistory = orderDetailsDAO.loadShoppingOrderDetailsHistory(temp.getOrderID());

                                for (OrderDetailsDTO orderDetailsDTO : listOrderDetailsHistory) {
                                    if (orderDetailsDTO.getItemName().equalsIgnoreCase(txtSearchItemName)) {
                                        listOrdersHistorySearch.add(temp);
                                        listOrderDetailsHistorySearch = listOrderDetailsHistory;
                                        mapOrdersToOrderDetailsSearch.put(temp.getOrderID(), listOrderDetailsHistorySearch);
                                    }
                                }
                            }
                            request.setAttribute("LIST_ORDERS_HISTORY", listOrdersHistorySearch);
                            request.setAttribute("MAP_ORDERS_BY_ORDER_DETAILS", mapOrdersToOrderDetailsSearch);
                        }

                        if (!txtSearchItemName.isEmpty() && !txtSearchHistoryDate.isEmpty()) { // Search bang Date, Search bang itemName
                            String day = txtSearchHistoryDate.split("-")[2];
                            String month = txtSearchHistoryDate.split("-")[1];
                            String year = txtSearchHistoryDate.split("-")[0];

                            for (OrdersDTO temp : listOrdersHistory) {
                                String tempDate = temp.getBuyDateTime().split("\\s+")[0];
                                String tempDay = tempDate.split("-")[2];
                                String tempMonth = tempDate.split("-")[1];
                                String tempYear = tempDate.split("-")[0];

                                if (tempYear.equalsIgnoreCase(year)) {
                                    if (tempMonth.equalsIgnoreCase(month)) {
                                        if (tempDay.equalsIgnoreCase(day)) {
                                            listOrdersHistorySearch.add(temp);
                                        }
                                    }
                                }
                            }
                            for (OrdersDTO temp : listOrdersHistorySearch) {
                                List<OrderDetailsDTO> listOrderDetailsHistory = orderDetailsDAO.loadShoppingOrderDetailsHistory(temp.getOrderID());

                                for (OrderDetailsDTO orderDetailsDTO : listOrderDetailsHistory) {
                                    if (orderDetailsDTO.getItemName().equalsIgnoreCase(txtSearchItemName)) {
                                        listOrderDetailsHistorySearch = listOrderDetailsHistory;
                                        mapOrdersToOrderDetailsSearch.put(temp.getOrderID(), listOrderDetailsHistorySearch);
                                    }
                                }
                            }
                            request.setAttribute("LIST_ORDERS_HISTORY", listOrdersHistorySearch);
                            request.setAttribute("MAP_ORDERS_BY_ORDER_DETAILS", mapOrdersToOrderDetailsSearch);
                        }
                    }
                }
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
