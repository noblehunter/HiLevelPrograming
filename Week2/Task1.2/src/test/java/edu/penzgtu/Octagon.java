package edu.penzgtu;

public class Octagon {
    private double innerRadius;
    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
    }

    public double calculateArea() {
        double sideLength = innerRadius + innerRadius / Math.sqrt(2);
        return 2 * (1 + Math.sqrt(2)) * sideLength * sideLength;
    }

    public double calculatePerimeter() {
        double sideLength = innerRadius + innerRadius / Math.sqrt(2);
        return 8 * sideLength;
    }

    public static void main(String[] args) {
        Octagon octagon = new Octagon();

        octagon.setInnerRadius(5.0);

        System.out.println("Зона октагона: " + octagon.calculateArea());
        System.out.println("Периметр октагона: " + octagon.calculatePerimeter());
    }
}
