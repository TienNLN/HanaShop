/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennln.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import tiennln.items.PlantDTO;

/**
 *
 * @author ADMIN
 */
public class CartObject implements Serializable {

    private HashMap<PlantDTO, Integer> cartItem = null;

    private int size = -1;
    
//    private float totalMoney;

    public HashMap<PlantDTO, Integer> getCartItem() {
        return cartItem;
    }

    public int getSize() {
        return sizeOfCart();
    }

    public int sizeOfCart() {
        if (cartItem != null) {
            return cartItem.size();
        }
        return -1;
    }

    public void addItemIntoCart(PlantDTO item) {
        if (this.cartItem == null) {
            this.cartItem = new HashMap<>();
        }

        int quantity = 1;

        Iterator<Map.Entry<PlantDTO, Integer>> it = cartItem.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<PlantDTO, Integer> itemTemp = (Map.Entry<PlantDTO, Integer>) it.next();

            if (item.getName().equals(itemTemp.getKey().getName())) {
                quantity = itemTemp.getValue() + 1;
                itemTemp.setValue(quantity);
                return;
            }
        }
        cartItem.put(item, quantity);
    }

    public void deleteItemFromCart(String name) {
        if (this.cartItem == null) {
            this.cartItem = new HashMap<>();
        }

        Iterator<Map.Entry<PlantDTO, Integer>> it = cartItem.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<PlantDTO, Integer> itemTemp = (Map.Entry<PlantDTO, Integer>) it.next();

            if (itemTemp.getKey().getName().equals(name)) {
                cartItem.remove(itemTemp.getKey());
                if (cartItem.isEmpty()) {
                    cartItem = null;
                }
                return;
            }
        }
    }

    public void editQuantity(String name, int quantity) {
        if (this.cartItem == null) {
            this.cartItem = new HashMap<>();
        }

        Iterator<Map.Entry<PlantDTO, Integer>> it = cartItem.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<PlantDTO, Integer> itemTemp = (Map.Entry<PlantDTO, Integer>) it.next();

            if (itemTemp.getKey().getName().equals(name)) {
                itemTemp.setValue(quantity);

                if (itemTemp.getValue() < 1) {
                    cartItem.remove(itemTemp.getKey());
                    if (cartItem.isEmpty()) {
                        cartItem = null;
                    }
                }
                return;
            }
        }
    }

    public float getTotalMoney() {
        Iterator<Map.Entry<PlantDTO, Integer>> it = cartItem.entrySet().iterator();
        float total = -1;
        while (it.hasNext()) {
            if (total == -1) {
                total = 0;
            }
            HashMap.Entry<PlantDTO, Integer> itemTemp = (Map.Entry<PlantDTO, Integer>) it.next();
            total += (itemTemp.getKey().getPrice() * itemTemp.getValue());
        }
        return total;
    }
}
