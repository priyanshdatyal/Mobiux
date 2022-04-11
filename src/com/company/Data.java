package com.company;

import java.util.ArrayList;
import java.util.List;

public class Data {
    static List date = new ArrayList();
    static List suk = new ArrayList();
    static List unitPrice = new ArrayList();
    static List quantity = new ArrayList();
    static List total = new ArrayList();

    Data(){}

    public static void addDate(Object o){
        date.add(o);
    }
    public static void addSUK(Object o){
        suk.add(o);
    }
    public static void addUnitPrice(Object o){
        unitPrice.add(o);
    }
    public static void addQuantity(Object o){
        quantity.add(o);
    }
    public static void addTotal(Object o){
        total.add(o);
    }


    public static Object getDate(int pos){
        return date.get(pos);
    }

    public static Object getSUK(int pos){
        return suk.get(pos);
    }

    public static Object getUnitPrice(int pos){
        return unitPrice.get(pos);
    }

    public static Object getQuantity(int pos){
        return quantity.get(pos);
    }

    public static Object getTotal(int pos){
        return total.get(pos);
    }

}
