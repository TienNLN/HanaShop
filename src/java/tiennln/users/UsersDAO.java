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
public class UsersDAO implements Serializable {

    public UsersDTO checkLogin(String username, String password)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sqlString = "Select * From Users "
                        + "Where userID=? and password=?";
                pst = cn.prepareStatement(sqlString);
                pst.setString(1, username);
                pst.setString(2, password);

                result = pst.executeQuery();
                if (result.next()) {
                    String name = result.getString("name");
                    boolean isAdmin = result.getBoolean("isAdmin");
                    String email = result.getString("email");

                    UsersDTO dtoTemp = new UsersDTO(username, password, name, isAdmin, email);
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

    public UsersDTO checkLoginByGoogle(String email, String loginName)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                String sqlString = "Select * From Users "
                        + "Where email=?";
                pst = cn.prepareStatement(sqlString);
                pst.setString(1, email);

                result = pst.executeQuery();

                if (result.next()) { // this email is existing in DB
                    String username = result.getString("userID");
                    String password = result.getString("password");
                    String name = result.getString("name");
                    boolean isAdmin = result.getBoolean("isAdmin");

                    UsersDTO dto = new UsersDTO(username, password, name, isAdmin, email);
                    return dto;
                }

                // this email is completely new
                String userID = autoUserIDCreateForGoogleAccount();

                createAccount(userID, null, loginName, false, email);

                return new UsersDTO(userID, null, loginName, false, email);

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

    public String autoUserIDCreateForGoogleAccount()
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet result = null;

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                String sqlString = "Select userID From Users "
                        + "WHERE email is not null";
                pst = cn.prepareStatement(sqlString);

                result = pst.executeQuery();

                String lastUserID = null;
                while (result.next()) {
                    lastUserID = result.getString("userID");

                    String lastUserIDNumber = lastUserID.split("-")[1];

                    String newUserIDNumber = "ID-" + (Integer.parseInt(lastUserIDNumber) + 1);

                    return newUserIDNumber;
                }
                
                String newUserIDNumber = "ID-1";
                
                return newUserIDNumber;
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

    public boolean addNameToNewGoogleAccount(String email, String name)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                String sqlString = "UPDATE Users "
                        + "SET name=? "
                        + "WHERE email=?";
                pst = cn.prepareStatement(sqlString);
                pst.setString(1, name);
                pst.setString(2, email);

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

    public boolean createAccount(String userID, String password, String name, boolean isAdmin, String email)
            throws NamingException, SQLException {
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DBHelper.makeConnection();

            if (cn != null) {
                String sqlString = "INSERT INTO Users "
                        + "VALUES(?, ?, ?, ?, ?)";

                pst = cn.prepareStatement(sqlString);
                pst.setString(1, userID);
                pst.setString(2, password);
                pst.setString(3, name);
                pst.setBoolean(4, isAdmin);
                pst.setString(5, email);

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
