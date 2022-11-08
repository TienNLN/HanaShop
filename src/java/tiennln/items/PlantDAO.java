/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.items;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import tiennln.cart.CartObject;
import tiennln.util.DBHelper;

/**
 *
 * @author ADMIN
 */
public class PlantDAO implements Serializable {

    private List<String> listCategory;

    private List<PlantDTO> listResult;

    public List<String> getListCategory() {
        return listCategory;
    }

    public List<PlantDTO> getListResult() {
        return listResult;
    }

    public void getAllCategory()
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                listCategory = new ArrayList<>();
                String sqlString = "SELECT category FROM Items "
                        + "WHERE status=1";
                pst = cn.prepareStatement(sqlString);

                result = pst.executeQuery();
                while (result.next()) {
                    String categoryTemp = result.getString("category");
                    if (!listCategory.contains(categoryTemp)) {
                        listCategory.add(categoryTemp);
                    }
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

    public void searchItems(String name, String txtPriceStart, String txtPriceEnd, String category, int pageNumber)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;
        String sqlString = null;
        PlantDTO itemsTemp = null;

        float priceStart = -1;
        float priceEnd = -1;

        if (!txtPriceStart.isEmpty() && !txtPriceEnd.isEmpty()) {
            priceStart = Float.parseFloat(txtPriceStart);
            priceEnd = Float.parseFloat(txtPriceEnd);
        }

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                listResult = new ArrayList<>();
                if (category.isEmpty()) { // co Name
                    sqlString = "SELECT * "
                            + "FROM Plant "
                            + "WHERE name Like ? "
                            + "And status=1";
                    pst = cn.prepareStatement(sqlString);
                    pst.setString(1, "%" + name + "%");
                } else {
                    if (name.isEmpty()) { // co Category
                        sqlString
                                = "SELECT * "
                                + "FROM Plant "
                                + "WHERE category=? "
                                + "And status=1 "
                                + "ORDER BY createDate";

                        pst = cn.prepareStatement(sqlString);
                        pst.setInt(1, pageNumber);
                        pst.setString(2, category);

                    }
                }
                result = pst.executeQuery();

                while (result.next()) {
                    int id = result.getInt("id");
                    String nameResult = result.getString("name");
                    String img = result.getString("img");
                    String description = result.getString("description");
                    float price = result.getFloat("price");
                    category = result.getString("category");
                    boolean status = result.getBoolean("status");

                    itemsTemp = new PlantDTO(id, name, img, description, price, category, status);
                    listResult.add(itemsTemp);
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

    public boolean addNewItem(String itemName, String image, String description, float price, String category, int quantity)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                Date todayDate = new Date();

                long todayTime = todayDate.getTime();

                Timestamp today = new Timestamp(todayTime);

                String sqlString = "INSERT INTO Plant"
                        + "(name, image, description, price, createDate, category, status, quantity) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

                pst = cn.prepareStatement(sqlString);

                pst.setString(1, itemName);
                pst.setString(2, image);
                pst.setString(3, description);
                pst.setFloat(4, price);
                pst.setTimestamp(5, today);
                pst.setString(6, category);
                pst.setBoolean(7, true);
                pst.setInt(8, quantity);

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
}
