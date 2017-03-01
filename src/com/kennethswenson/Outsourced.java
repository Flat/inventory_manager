package com.kennethswenson;

public class Outsourced extends Part {
    private String companyName;

    public Outsourced(String name, int partID, double price, int instock, int min, int max, String companyName) {
        super(name, partID, price, instock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
