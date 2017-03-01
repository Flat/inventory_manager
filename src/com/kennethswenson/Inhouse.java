package com.kennethswenson;

public class Inhouse extends Part {
    private int machineID;

    public Inhouse(String name, int partID, double price, int instock, int min, int max, int machineID) {
        super(name, partID, price, instock, min, max);
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
