package edu.penzgtu;

public class Octagon {
    // Обьявление приватного класса innerRadius
    private double innerRadius;
    //Объявление публичного метода setInnerRadius с параметром типа double.
    public void setInnerRadius(double innerRadius) {
        this.innerRadius = innerRadius;
    }
    // вычисление длины стороны октагона
    public double calculateArea() {
        double sideLength = innerRadius + innerRadius / Math.sqrt(2);
        return 2 * (1 + Math.sqrt(2)) * sideLength * sideLength;
    }
    //  вычисление периметра октагона.
    public double calculatePerimeter() {
        double sideLength = innerRadius + innerRadius / Math.sqrt(2);
        return 8 * sideLength;
    }
    // Задаем стороны октагона и его радиус
    public static void main(String[] args) {
        Octagon octagon = new Octagon();

        octagon.setInnerRadius(7.77);
    // Выыодим получившеся на экран
        System.out.println("Зона октагона: " + octagon.calculateArea());
        System.out.println("Периметр октагона: " + octagon.calculatePerimeter());
    }
}