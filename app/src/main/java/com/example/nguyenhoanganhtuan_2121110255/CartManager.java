package com.example.nguyenhoanganhtuan_2121110255;

import java.util.ArrayList;

public class CartManager {
    private static ArrayList<Product> cartList = new ArrayList<>();

    public static void addToCart(Product product) {
        cartList.add(product);
    }

    public static ArrayList<Product> getCart() {
        return cartList;
    }

    public static void clearCart() {
        cartList.clear();
    }

    public static void removeFromCart(Product product) {
        cartList.remove(product);
    }

    public static int getTotalPrice() {
        int total = 0;
        for (Product p : cartList) {
            total += p.getPrice();
        }
        return total;
    }
}
