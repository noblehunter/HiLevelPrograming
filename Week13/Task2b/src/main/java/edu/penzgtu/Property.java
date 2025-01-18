package edu.penzgtu;

public class Property {
    public String id;
    public double price;
    public String groupingValue; // Теперь храним значение, по которому группируем

    public Property(String id, double price, String groupingValue) {
        this.id = id;
        this.price = price;
        this.groupingValue = groupingValue;
    }
}