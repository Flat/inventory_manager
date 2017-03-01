package com.kennethswenson;

public abstract class Part {
    private String name;
    private int partID;
    private double price;
    private int instock;
    private int min;
    private int max;

    public Part(String name, int partID, double price, int instock, int min, int max) {
        this.name = name;
        this.partID = partID;
        this.price = price;
        this.instock = instock;
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
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
