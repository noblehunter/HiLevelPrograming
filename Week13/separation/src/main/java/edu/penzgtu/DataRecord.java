package edu.penzgtu;

public class DataRecord {
    private final String city;
    private final int year;
    private final double price;

    public DataRecord(String city, int year, double price) {
        this.city = city;
        this.year = year;
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }
}
