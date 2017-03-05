package com.kennethswenson;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Product {
    private ArrayList<Part> parts = new ArrayList<>();
    private int productID;
    private String name;
    private double price;
    private int instock;
    private int min;
    private int max;

    public Product(int productID, String name, double price, int instock, int min, int max) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.instock = instock;
        this.min = min;
        this.max = max;
    }

    public void addPart(Part part){
        if (!parts.isEmpty()){
            if(parts.contains(part)){
                return;
            }
        }
        parts.add(part);
    }
    public boolean removePart(int partID){
        if (parts.isEmpty()) {
            return false;
        } else {
            for (Part part: parts) {
                if(part.getPartID() == partID){
                    parts.remove(part);
                    return true;
                }
            }
            return false;
        }
    }
    public Part lookupPart(int partID){
        if (parts.isEmpty()) {
            return null;
        } else {
            for (Part part: parts) {
                if(part.getPartID() == partID){
                    return part;
                }
            }
            return null;
        }
    }

    public void updatePart(int partID, Part newPart){
        if (!parts.isEmpty()) {
            for (Part part: parts) {
                if(part.getPartID() == partID){
                    parts.set(parts.indexOf(part), newPart);
                }
            }
        }
    }

    public ObservableList<Part> getParts(){
        return FXCollections.observableArrayList(parts);
    }

    public boolean hasParts(){
        return !parts.isEmpty();
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInstock() {
        return instock;
    }

    public void setInstock(int instock) {
        this.instock = instock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
