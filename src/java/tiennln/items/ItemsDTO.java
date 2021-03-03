/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.items;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author ADMIN
 */
public class ItemsDTO implements Serializable {

    private String name;
    private String img;
    private String description;
    private float price;
    private String createDate;
    private String category;
    private boolean status;
    private int quantity;
    private String lastUpdateDate;
    private String lastUpdateUser;

    public ItemsDTO() {
    }

    public ItemsDTO(String name, String img, String description, float price, String createDate, String category, boolean status, int quantity) {
        this.name = name;
        this.img = img;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.category = category;
        this.status = status;
        this.quantity = quantity;
        this.lastUpdateDate = null;
        this.lastUpdateUser = null;
    }

    public ItemsDTO(String name, String img, String description, float price, String createDate, String category, boolean status, int quantity, String lastUpdateDate, String lastUpdateUser) {
        this.name = name;
        this.img = img;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.category = category;
        this.status = status;
        this.quantity = quantity;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return Float.parseFloat(df.format(price));
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

//    public static double roundAvoid(double value, int places) {
//        double scale = Math.pow(10, places);
//        return Math.round(value * scale) / scale;
//    }
//    @Override
//    public boolean equals(Object obj) {
//        return super.equals(((ItemsDTO) obj).getName());
//    }
    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the lastUpdateDate
     */
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param lastUpdateDate the lastUpdateDate to set
     */
    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * @return the lastUpdateUser
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * @param lastUpdateUser the lastUpdateUser to set
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
}
