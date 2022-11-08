/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.users;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class AccountDTO implements Serializable {

    private int id;
    private String email;
    private String password;
    private String fullname;
    private String phone;
    private boolean status;
    private boolean role;

    public AccountDTO() {
    }

    public AccountDTO(int id, String email, String fullname, String phone, boolean status, boolean role) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.status = status;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }
    
    
}
