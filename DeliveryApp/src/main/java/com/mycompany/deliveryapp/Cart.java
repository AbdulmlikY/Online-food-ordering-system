package com.mycompany.deliveryapp;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static List<String> items = new ArrayList<>();

    public static void addItem(String item) {
        items.add(item);
    }

    public static List<String> getItems() {
        return new ArrayList<>(items); 
    }

    public static void clear() {
        items.clear();
    }

    public static boolean isEmpty() {
        return items.isEmpty();
    }
}