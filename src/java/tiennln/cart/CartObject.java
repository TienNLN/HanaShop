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
import tiennln.items.ItemsDTO;

/**
 *
 * @author ADMIN
 */
public class CartObject implements Serializable {

    private HashMap<ItemsDTO, Integer> cartItem = null;

    private int size = -1;
    
//    private float totalMoney;

    public HashMap<ItemsDTO, Integer> getCartItem() {
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

    public void addItemIntoCart(ItemsDTO item) {
        if (this.cartItem == null) {
            this.cartItem = new HashMap<>();
        }

        int quantity = 1;

        Iterator<Map.Entry<ItemsDTO, Integer>> it = cartItem.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<ItemsDTO, Integer> itemTemp = (Map.Entry<ItemsDTO, Integer>) it.next();

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

        Iterator<Map.Entry<ItemsDTO, Integer>> it = cartItem.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<ItemsDTO, Integer> itemTemp = (Map.Entry<ItemsDTO, Integer>) it.next();

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

        Iterator<Map.Entry<ItemsDTO, Integer>> it = cartItem.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<ItemsDTO, Integer> itemTemp = (Map.Entry<ItemsDTO, Integer>) it.next();

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
        Iterator<Map.Entry<ItemsDTO, Integer>> it = cartItem.entrySet().iterator();
        float total = -1;
        while (it.hasNext()) {
            if (total == -1) {
                total = 0;
            }
            HashMap.Entry<ItemsDTO, Integer> itemTemp = (Map.Entry<ItemsDTO, Integer>) it.next();
            total += (itemTemp.getKey().getPrice() * itemTemp.getValue());
        }
        return total;
    }
}
