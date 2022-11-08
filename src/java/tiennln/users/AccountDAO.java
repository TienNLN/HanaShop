/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.users;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tiennln.util.DBHelper;

/**
 *
 * @author ADMIN
 */
public class AccountDAO implements Serializable {

    public AccountDTO checkLogin(String email, String password)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sqlString = "Select * From Account "
                        + "Where email=? and password=?";
                pst = cn.prepareStatement(sqlString);
                pst.setString(1, email);
                pst.setString(2, password);

                result = pst.executeQuery();
                if (result.next()) {
                    int id = result.getInt("id");
                    String fullname = result.getString("name");
                    String phone = result.getString("phone");
                    boolean status = result.getBoolean("status");
                    boolean isAdmin = result.getBoolean("role");

                    AccountDTO dtoTemp = new AccountDTO(id, email, fullname, phone, status, isAdmin);
                    return dtoTemp;
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
        return null;
    }

    public boolean createAccount(String email, String password, String fullname, String phone, boolean role, boolean status)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                String sqlString = "INSERT INTO Account(email, password, fullname, phone, status, role) "
                        + "VALUES(?, ?, ?, ?, ?, ?)";

                pst = cn.prepareStatement(sqlString);
                pst.setString(1, email);
                pst.setString(2, password);
                pst.setString(3, fullname);
                pst.setString(4, phone);
                pst.setBoolean(5, status);
                pst.setBoolean(6, role);

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
