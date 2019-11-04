package com.cgaxtr.tfg.data.model;

public class Device {
    private String name;
    private String macAddress;
    private boolean bonded;

    public Device(String name, String macAddress, boolean bonded) {
        this.name = name;
        this.macAddress = macAddress;
        this.bonded = bonded;
    }

    public Device(String name, String macAddress) {
        this.name = name;
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public boolean getBonded() {
        return bonded;
    }

    public void setBonded(boolean bonded) {
        this.bonded = bonded;
    }
}
