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
public class ItemsDAO implements Serializable {

    private List<String> listCategory;

    private List<ItemsDTO> listResult;

    public List<String> getListCategory() {
        return listCategory;
    }

    public List<ItemsDTO> getListResult() {
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
                        + "WHERE status=1 "
                        + "AND quantity>0";
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
        ItemsDTO itemsTemp = null;

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
                if (txtPriceStart.isEmpty() && txtPriceEnd.isEmpty()) { // ko co Price
                    if (category.isEmpty()) { // co Name, ko co Category va Price
                        sqlString = "SELECT "
                                + "name, image, description, price, createDate, category, quantity "
                                + "FROM Items "
                                + "WHERE name Like ? "
                                + "And status=1 "
                                + "And quantity>0";
                        pst = cn.prepareStatement(sqlString);
                        pst.setString(1, "%" + name + "%");
                    } else {
                        if (name.isEmpty()) { // co Category, ko co Name va Price
                            sqlString
                                    = "DECLARE @PageNumber AS INT "
                                    + "DECLARE @RowsOfPage AS INT "
                                    + "SET @PageNumber=? "
                                    + "SET @RowsOfPage=10 "
                                    + "SELECT name, image, description, price, createDate, category, quantity "
                                    + "FROM Items "
                                    + "WHERE category=? "
                                    + "And status=1 "
                                    + "And quantity>0 "
                                    + "ORDER BY createDate "
                                    + "OFFSET (@PageNumber-1)*@RowsOfPage ROWS "
                                    + "FETCH NEXT @RowsOfPage ROWS ONLY";

                            pst = cn.prepareStatement(sqlString);
                            pst.setInt(1, pageNumber);
                            pst.setString(2, category);

                        } else { // co Name va Category, ko co Price
                            sqlString
                                    = "DECLARE @PageNumber AS INT "
                                    + "DECLARE @RowsOfPage AS INT "
                                    + "SET @PageNumber=? "
                                    + "SET @RowsOfPage=10 "
                                    + "SELECT name, image, description, price, createDate, category, quantity "
                                    + "FROM Items "
                                    + "WHERE name Like ? "
                                    + "And status=1 "
                                    + "And quantity>0 "
                                    + "And category=? "
                                    + "ORDER BY createDate "
                                    + "OFFSET (@PageNumber-1)*@RowsOfPage ROWS "
                                    + "FETCH NEXT @RowsOfPage ROWS ONLY";

                            pst = cn.prepareStatement(sqlString);
                            pst.setInt(1, pageNumber);
                            pst.setString(2, "%" + name + "%");
                            pst.setString(3, category);

                        }
                    }
                } else { // co Price
                    if (name.isEmpty()) { // co Price, ko co Name
                        if (category.isEmpty()) { // co Price, ko co Category va Name
                            sqlString
                                    = "DECLARE @PageNumber AS INT "
                                    + "DECLARE @RowsOfPage AS INT "
                                    + "SET @PageNumber=? "
                                    + "SET @RowsOfPage=10 "
                                    + "SELECT name, image, description, price, createDate, category, quantity "
                                    + "FROM Items "
                                    + "WHERE price BETWEEN ? And  ? "
                                    + "And status=1 "
                                    + "And quantity>0 "
                                    + "ORDER BY createDate "
                                    + "OFFSET (@PageNumber-1)*@RowsOfPage ROWS "
                                    + "FETCH NEXT @RowsOfPage ROWS ONLY";

                            pst = cn.prepareStatement(sqlString);
                            pst.setInt(1, pageNumber);
                            pst.setFloat(2, priceStart);
                            pst.setFloat(3, priceEnd);
                        } else { // co Price va Category, ko co Name
                            sqlString
                                    = "DECLARE @PageNumber AS INT "
                                    + "DECLARE @RowsOfPage AS INT "
                                    + "SET @PageNumber=? "
                                    + "SET @RowsOfPage=10 "
                                    + "SELECT name, image, description, price, createDate, category, quantity "
                                    + "FROM Items "
                                    + "WHERE category=? "
                                    + "And status=1 "
                                    + "And quantity>0 "
                                    + "And price BETWEEN ? And  ? "
                                    + "ORDER BY createDate "
                                    + "OFFSET (@PageNumber-1)*@RowsOfPage ROWS "
                                    + "FETCH NEXT @RowsOfPage ROWS ONLY";

                            pst = cn.prepareStatement(sqlString);
                            pst.setInt(1, pageNumber);
                            pst.setString(2, category);
                            pst.setFloat(3, priceStart);
                            pst.setFloat(4, priceEnd);
                        }
                    } else { // co Name, co Price
                        if (category.isEmpty()) { // co Price va Name, ko co Category
                            sqlString
                                    = "DECLARE @PageNumber AS INT "
                                    + "DECLARE @RowsOfPage AS INT "
                                    + "SET @PageNumber=? "
                                    + "SET @RowsOfPage=10 "
                                    + "SELECT name, image, description, price, createDate, category, quantity "
                                    + "FROM Items "
                                    + "WHERE name Like ? "
                                    + "And status=1 "
                                    + "And quantity>0 "
                                    + "price BETWEEN ? And  ? "
                                    + "ORDER BY createDate "
                                    + "OFFSET (@PageNumber-1)*@RowsOfPage ROWS "
                                    + "FETCH NEXT @RowsOfPage ROWS ONLY";

                            pst = cn.prepareStatement(sqlString);
                            pst.setInt(1, pageNumber);
                            pst.setString(2, "%" + name + "%");
                            pst.setFloat(3, priceStart);
                            pst.setFloat(4, priceEnd);
                        } else { // co Prce va Name va Category
                            sqlString
                                    = "DECLARE @PageNumber AS INT "
                                    + "DECLARE @RowsOfPage AS INT "
                                    + "SET @PageNumber=? "
                                    + "SET @RowsOfPage=10 "
                                    + "SELECT name, image, description, price, createDate, category, quantity "
                                    + "FROM Items "
                                    + "WHERE name Like ? "
                                    + "And category=? "
                                    + "And status=1 "
                                    + "And quantity>0 "
                                    + "And price BETWEEN ? And  ? "
                                    + "ORDER BY createDate "
                                    + "OFFSET (@PageNumber-1)*@RowsOfPage ROWS "
                                    + "FETCH NEXT @RowsOfPage ROWS ONLY";

                            pst = cn.prepareStatement(sqlString);
                            pst.setInt(1, pageNumber);
                            pst.setString(2, "%" + name + "%");
                            pst.setString(3, category);
                            pst.setFloat(4, priceStart);
                            pst.setFloat(5, priceEnd);
                        }
                    }
                }
                result = pst.executeQuery();

                while (result.next()) {
                    String nameResult = result.getString("name");
                    String img = result.getString("image");
                    String description = result.getString("description");
                    float price = result.getFloat("price");
                    String createDate = result.getString("createDate");
                    category = result.getString("category");
                    int quantity = result.getInt("quantity");

                    itemsTemp = new ItemsDTO(nameResult, img, description, price, createDate, category, true, quantity);
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

    public List<String> checkOutOfStorageItems(CartObject cart) {
        List<String> outOfStorageItemsList = new ArrayList<>();
        HashMap<ItemsDTO, Integer> cartItem = cart.getCartItem();

        Iterator<Map.Entry<ItemsDTO, Integer>> it = cartItem.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<ItemsDTO, Integer> itemTemp = (Map.Entry<ItemsDTO, Integer>) it.next();

            if (itemTemp.getValue() > itemTemp.getKey().getQuantity()) {
                outOfStorageItemsList.add(itemTemp.getKey().getName());
            }
        }

        return outOfStorageItemsList;
    }

    public void updateItemAfterCheckoutBill(CartObject cart)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;
        HashMap<ItemsDTO, Integer> cartItems = cart.getCartItem();

        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sqlString = "UPDATE Items "
                        + "SET quantity=? "
                        + "WHERE name=?";
                pst = cn.prepareStatement(sqlString);
                cn.setAutoCommit(false);

                Iterator<Map.Entry<ItemsDTO, Integer>> it = cartItems.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry<ItemsDTO, Integer> itemTemp = (Map.Entry<ItemsDTO, Integer>) it.next();

                    int quantity = itemTemp.getKey().getQuantity() - itemTemp.getValue();
                    String name = itemTemp.getKey().getName();

                    pst.setInt(1, quantity);
                    pst.setString(2, name);

                    pst.executeUpdate();
                }
                cn.commit();
                cn.setAutoCommit(true);
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

    public List<ItemsDTO> loadListItems(int pageNumber)
            throws NamingException, SQLException {

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        try {
            List<ItemsDTO> listItemName = new ArrayList<>();

            cn = DBHelper.makeConnection();

            if (cn != null) {
                String sqlString
                        = "DECLARE @PageNumber AS INT "
                        + "DECLARE @RowsOfPage AS INT "
                        + "SET @PageNumber=? "
                        + "SET @RowsOfPage=10 "
                        + "SELECT * "
                        + "FROM Items "
                        + "ORDER BY createDate "
                        + "OFFSET (@PageNumber-1)*@RowsOfPage ROWS "
                        + "FETCH NEXT @RowsOfPage ROWS ONLY";
                pst = cn.prepareStatement(sqlString);
                pst.setInt(1, pageNumber);

                result = pst.executeQuery();
                while (result.next()) {
                    String itemName = result.getString("name");
                    String image = result.getString("image");
                    String description = result.getString("description");
                    float price = result.getFloat("price");
                    Timestamp createDate = result.getTimestamp("createDate");
                    String category = result.getString("category");
                    boolean status = result.getBoolean("status");
                    int quantity = result.getInt("quantity");

                    ItemsDTO dto = new ItemsDTO(itemName, image, description, price, createDate.toString(), category, status, quantity);
                    listItemName.add(dto);
                }
                return listItemName;
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

    public List<ItemsDTO> loadListItems()
            throws NamingException, SQLException {

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        try {
            List<ItemsDTO> listItemName = new ArrayList<>();

            cn = DBHelper.makeConnection();

            if (cn != null) {
                String sqlString = "SELECT * FROM Items";
                pst = cn.prepareStatement(sqlString);
//                pst.setInt(1, pageNumber);

                result = pst.executeQuery();
                while (result.next()) {
                    String itemName = result.getString("name");
                    String image = result.getString("image");
                    String description = result.getString("description");
                    float price = result.getFloat("price");
                    Timestamp createDate = result.getTimestamp("createDate");
                    String category = result.getString("category");
                    boolean status = result.getBoolean("status");
                    int quantity = result.getInt("quantity");

                    ItemsDTO dto = new ItemsDTO(itemName, image, description, price, createDate.toString(), category, status, quantity);
                    listItemName.add(dto);
                }
                return listItemName;
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

    public boolean updateItem(String userUpdate, String oldItemName, String itemName, String image, String description, float price, String category, int quantity, boolean status)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                Date currentDate = new Date();

                long currentTime = currentDate.getTime();

                Timestamp lastUpdateDate = new Timestamp(currentTime);

                String sqlString = "UPDATE Items "
                        + "SET "
                        + "name=?, "
                        + "image=?, "
                        + "description=?, "
                        + "price=?, "
                        + "category=?, "
                        + "status=?, "
                        + "quantity=?, "
                        + "lastUpdateDate=?, "
                        + "lastUpdateUser=? "
                        + "WHERE name=?";
                pst = cn.prepareStatement(sqlString);
                pst.setString(1, itemName);
                pst.setString(2, image);
                pst.setString(3, description);
                pst.setFloat(4, price);
                pst.setString(5, category);
                pst.setBoolean(6, status);
                pst.setInt(7, quantity);
                pst.setTimestamp(8, lastUpdateDate);
                pst.setString(9, userUpdate);
                pst.setString(10, oldItemName);

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

    public boolean deleteItem(String itemName, String userUpdate)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                Date currentDate = new Date();

                long currentTime = currentDate.getTime();

                Timestamp lastUpdateDate = new Timestamp(currentTime);

                String sqlString = "UPDATE Items "
                        + "SET status=?, "
                        + "lastUpdateDate=?, "
                        + "lastUpdateUser=? "
                        + "WHERE name=?";

                pst = cn.prepareStatement(sqlString);
                pst.setBoolean(1, false);
                pst.setTimestamp(2, lastUpdateDate);
                pst.setString(3, userUpdate);
                pst.setString(4, itemName);

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

    public int getNumberOfPage()
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                String sqlString = "SELECT COUNT(name) "
                        + "FROM Items ";
                pst = cn.prepareStatement(sqlString);

                result = pst.executeQuery();

                if (result.next()) {
                    int numberOfPage = result.getInt(1);

                    if (numberOfPage < 10) {
                        return 1;
                    } else {
                        if (numberOfPage % 10 != 0) {
                            return (numberOfPage / 10) + 1;
                        } else {
                            return numberOfPage / 10;
                        }
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
        return -1;
    }

    public int getNumberOfPageSearch(String name, String txtPriceStart, String txtPriceEnd, String category)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;
        String sqlString = null;
//        ItemsDTO itemsTemp = null;

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
                if (txtPriceStart.isEmpty() && txtPriceEnd.isEmpty()) { // ko co Price
                    if (category.isEmpty()) { // co Name, ko co Category va Price
                        sqlString = "SELECT COUNT(name) "
                                + "FROM Items "
                                + "WHERE name=? "
                                + "And status=1 "
                                + "And quantity>0";
                        pst = cn.prepareStatement(sqlString);
                        pst.setString(1, name);
                    } else {
                        if (name.isEmpty()) { // co Category, ko co Name va Price
                            sqlString = "SELECT COUNT(name) "
                                    + "FROM Items "
                                    + "WHERE category=? "
                                    + "And status=1 "
                                    + "And quantity>0 ";

                            pst = cn.prepareStatement(sqlString);
//                            pst.setInt(1, pageNumber);
                            pst.setString(1, category);

                        } else { // co Name va Category, ko co Price
                            sqlString = "SELECT COUNT(name) "
                                    + "FROM Items "
                                    + "WHERE name=? "
                                    + "And status=1 "
                                    + "And quantity>0 "
                                    + "And category=? ";

                            pst = cn.prepareStatement(sqlString);
//                            pst.setInt(1, pageNumber);
                            pst.setString(1, name);
                            pst.setString(2, category);

                        }
                    }
                } else { // co Price
                    if (name.isEmpty()) { // co Price, ko co Name
                        if (category.isEmpty()) { // co Price, ko co Category va Name
                            sqlString = "SELECT COUNT(name) "
                                    + "FROM Items "
                                    + "WHERE price BETWEEN ? And  ? "
                                    + "And status=1 "
                                    + "And quantity>0 ";

                            pst = cn.prepareStatement(sqlString);
//                            pst.setInt(1, pageNumber);
                            pst.setFloat(1, priceStart);
                            pst.setFloat(2, priceEnd);
                        } else { // co Price va Category, ko co Name
                            sqlString = "SELECT COUNT(name) "
                                    + "FROM Items "
                                    + "WHERE category=? "
                                    + "And status=1 "
                                    + "And quantity>0 "
                                    + "And price BETWEEN ? And  ? ";

                            pst = cn.prepareStatement(sqlString);
//                            pst.setInt(1, pageNumber);
                            pst.setString(1, category);
                            pst.setFloat(2, priceStart);
                            pst.setFloat(3, priceEnd);
                        }
                    } else { // co Name, co Price
                        if (category.isEmpty()) { // co Price va Name, ko co Category
                            sqlString = "SELECT COUNT(name) "
                                    + "FROM Items "
                                    + "WHERE name=? "
                                    + "And status=1 "
                                    + "And quantity>0 "
                                    + "And price BETWEEN ? And  ? ";

                            pst = cn.prepareStatement(sqlString);
//                            pst.setInt(1, pageNumber);
                            pst.setString(1, name);
                            pst.setFloat(2, priceStart);
                            pst.setFloat(3, priceEnd);
                        } else { // co Prce va Name va Category
                            sqlString = "SELECT COUNT(name) "
                                    + "FROM Items "
                                    + "WHERE name=? "
                                    + "And category=? "
                                    + "And status=1 "
                                    + "And quantity>0 "
                                    + "And price BETWEEN ? And  ? ";

                            pst = cn.prepareStatement(sqlString);
//                            pst.setInt(1, pageNumber);
                            pst.setString(1, name);
                            pst.setString(2, category);
                            pst.setFloat(3, priceStart);
                            pst.setFloat(4, priceEnd);
                        }
                    }
                }
                result = pst.executeQuery();

                while (result.next()) {
                    int numberOfPage = result.getInt(1);

                    if (numberOfPage < 10) {
                        return 1;
                    } else {
                        if (numberOfPage % 10 != 0) {
                            return (numberOfPage / 10) + 1;
                        } else {
                            return numberOfPage / 10;
                        }
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
        return -1;
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

                String sqlString = "INSERT INTO Items"
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
