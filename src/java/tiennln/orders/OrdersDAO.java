/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.orders;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import tiennln.cart.CartObject;
import tiennln.util.DBHelper;

/**
 *
 * @author ADMIN
 */
public class OrdersDAO implements Serializable {

    private List<String> orderIDList = null;

    public List<String> getOrderIDList() {
        return orderIDList;
    }

    public OrdersDAO()
            throws NamingException, SQLException {
        loadOrderIDList();
    }

    // format of orderID is Order-year-codenumber
    public String confirmOrder(String userID, CartObject cart)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            String currentTime = formatDate.format(date);

            long m = date.getTime();
            Timestamp timeStamp = new Timestamp(m);

            String thisYear = currentTime.split("/")[2].split("\\s+")[0];

            String orderID = null;

            if (orderIDList.isEmpty()) {
                orderID = "Order-"
                        + thisYear + "-"
                        + "1";
            } else {
                int orderCode = -1;
//                String lastOrderID = orderIDList.get(orderIDList.size() - 1);

                String lastOrderID = null;

//                String lastOrderID = 
                int largestCode = -1;

                for (String temp : orderIDList) {
                    int orderIDCodeTemp = Integer.parseInt(temp.split("-")[2]);
                    if (orderIDCodeTemp > largestCode) {
                        largestCode = orderIDCodeTemp;
                        lastOrderID = temp;
                    }
                }
//
//                for (String temp : orderIDList) {
//                    int orderIDCodeTemp = Integer.parseInt(temp.split("-")[2]);
//                    if (orderIDCodeTemp > largestCode) {
//                        largestCode = orderIDCodeTemp;
//                    }
//                }

                String lastOrderIDYear = lastOrderID.split("-")[1];
                String lastOrderIDCode = lastOrderID.split("-")[2];

                if (lastOrderIDYear.equals(thisYear)) {
                    orderCode = Integer.parseInt(lastOrderIDCode) + 1;
                    orderID = "Order-"
                            + thisYear + "-"
                            + orderCode;
                } else {
                    orderID = "Order-"
                            + thisYear + "-"
                            + "1";
                }
            }

            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sqlString = "INSERT INTO Orders(orderID, userID, buyDateTime, totalAmount, isPaid) "
                        + "VALUES(?, ?, ?, ?, ?)";
                pst = cn.prepareStatement(sqlString);
                pst.setString(1, orderID);
                pst.setString(2, userID);
                pst.setTimestamp(3, timeStamp);
                pst.setFloat(4, cart.getTotalMoney());
                pst.setBoolean(5, false);

                int result = pst.executeUpdate();

                if (result > 0) {
                    orderIDList.add(orderID);
                    return orderID;
                }
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return null;
    }

    public void loadOrderIDList()
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                orderIDList = new ArrayList<>();

                String sqlString = "SELECT orderID FROM Orders";
                pst = cn.prepareStatement(sqlString);

                result = pst.executeQuery();
                while (result.next()) {
                    String orderID = result.getString("orderID");
                    orderIDList.add(orderID);
                }

            }
        } finally {
            if (result != null) {
                result.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public boolean setStatusPaid(String orderID, String userFullName, String userAddress, String userPhoneNumber)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBHelper.makeConnection();
//            String orderID = orderIDList.get(orderIDList.size() - 1);

            if (cn != null) {
                String sqlString = "UPDATE Orders "
                        + "SET isPaid=?, "
                        + "userFullName=?, "
                        + "userAddress=?, "
                        + "userPhoneNumber=? "
                        + "WHERE orderID=?";
                pst = cn.prepareStatement(sqlString);
                pst.setBoolean(1, true);
                pst.setString(2, userFullName);
                pst.setString(3, userAddress);
                pst.setString(4, userPhoneNumber);
                pst.setString(5, orderID);

                int result = pst.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }

        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return false;
    }

    public List<OrdersDTO> loadShoppingOrdersHistory(String username)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        List<OrdersDTO> listOrdersHistory = new ArrayList<>();

        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sqlString = "SELECT * FROM Orders "
                        + "WHERE userID=?";
                pst = cn.prepareStatement(sqlString);
                pst.setString(1, username);

                result = pst.executeQuery();
                while (result.next()) {
                    String orderID = result.getString("orderID");
                    String userID = result.getString("userID");
                    Timestamp buyDateTime = result.getTimestamp("buyDateTime");
                    float totalAmount = result.getFloat("totalAmount");
                    boolean isPaid = result.getBoolean("isPaid");
                    String userFullName = result.getString("userFullName");
                    String userAddress = result.getString("userAddress");
                    String userPhoneNumber = result.getString("userPhoneNumber");

                    OrdersDTO dto = new OrdersDTO(orderID, userID, buyDateTime.toString(), totalAmount, isPaid, userFullName, userAddress, userPhoneNumber);
                    listOrdersHistory.add(dto);
                }
                return listOrdersHistory;
            }
        } finally {
            if (result != null) {
                result.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return null;
    }
}
