package com.example.civil_engineer_m_system;

public class user {
    private String item;
    private double quantity,price;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public user(String item, double quantity, double price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }
}
