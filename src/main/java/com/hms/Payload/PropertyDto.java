package com.hms.Payload;

public class PropertyDto {

    private String name;
    private int noOfGuests;
    private int noOfBedrooms;
    private int noOfBathrooms;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfGuests() {
        return noOfGuests;
    }

    public void setNoOfGuests(int noOfGuests) {
        this.noOfGuests = noOfGuests;
    }

    public int getNoOfBedrooms() {
        return noOfBedrooms;
    }

    public void setNoOfBedrooms(int noOfBedrooms) {
        this.noOfBedrooms = noOfBedrooms;
    }

    public int getNoOfBathrooms() {
        return noOfBathrooms;
    }

    public void setNoOfBathrooms(int noOfBathrooms) {
        this.noOfBathrooms = noOfBathrooms;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
