/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.orderdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import tiennln.cart.CartObject;
import tiennln.items.PlantDTO;
import tiennln.util.DBHelper;

/**
 *
 * @author ADMIN
 */
public class OrderDetailsDAO implements Serializable {

    public void addItems(String orderID, CartObject cart)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        HashMap<PlantDTO, Integer> cartItems = cart.getCartItem();

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                String sqlString = "INSERT INTO OrderDetails "
                        + "VALUES(?, ?, ?)";
                pst = cn.prepareStatement(sqlString);
                cn.setAutoCommit(false);

                Iterator<Map.Entry<PlantDTO, Integer>> it = cartItems.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry<PlantDTO, Integer> itemTemp = (Map.Entry<PlantDTO, Integer>) it.next();

                    pst.setString(1, orderID);
                    pst.setString(2, itemTemp.getKey().getName());
                    pst.setInt(3, itemTemp.getValue());
                    pst.executeUpdate();
                }
                cn.commit();
                cn.setAutoCommit(true);
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public List<OrderDetailsDTO> loadShoppingOrderDetailsHistory(String orderID)
            throws NamingException, SQLException {

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        try {
            List<OrderDetailsDTO> listOrderDetailsHistory = new ArrayList<>();
            cn = DBHelper.makeConnection();

            if (cn != null) {
                String sqlString = "SELECT * FROM OrderDetails "
                        + "WHERE orderID=?";

                pst = cn.prepareStatement(sqlString);
                pst.setString(1, orderID);

                result = pst.executeQuery();

                while (result.next()) {
                    String itemName = result.getString("itemName");
                    int quantity = result.getInt("quantity");

                    OrderDetailsDTO dto = new OrderDetailsDTO(orderID, itemName, quantity);
                    listOrderDetailsHistory.add(dto);
                }
                return listOrderDetailsHistory;
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
