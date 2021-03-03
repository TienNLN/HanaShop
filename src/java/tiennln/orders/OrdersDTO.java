/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.orders;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class OrdersDTO implements Serializable {

    private String orderID;
    private String userID;
    private String buyDateTime;
    private float totalAmount;
    private boolean isPaid;
    private String userFullName;
    private String userAddress;
    private String userPhoneNumber;

    public OrdersDTO() {
    }

    public OrdersDTO(String orderID, String userID, String buyDateTime, float totalAmount, boolean isPaid, String userFullName, String userAddress, String userPhoneNumber) {
        this.orderID = orderID;
        this.userID = userID;
        this.buyDateTime = buyDateTime;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
        this.userFullName = userFullName;
        this.userAddress = userAddress;
        this.userPhoneNumber = userPhoneNumber;
    }

    /**
     * @return the orderID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the buyDateTime
     */
    public String getBuyDateTime() {
        return buyDateTime;
    }

    /**
     * @param buyDateTime the buyDateTime to set
     */
    public void setBuyDateTime(String buyDateTime) {
        this.buyDateTime = buyDateTime;
    }

    /**
     * @return the totalAmount
     */
    public float getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the userFullName
     */
    public String getUserFullName() {
        return userFullName;
    }

    /**
     * @param userFullName the userFullName to set
     */
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    /**
     * @return the userAddress
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * @param userAddress the userAddress to set
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * @return the userPhoneNumber
     */
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    /**
     * @param userPhoneNumber the userPhoneNumber to set
     */
    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    /**
     * @return the isPaid
     */
    public boolean isIsPaid() {
        return isPaid;
    }

    /**
     * @param isPaid the isPaid to set
     */
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

}
