package edu.penzgtu;

class Engine {
    private int power;

    public Engine(int power) {
        this.power = power;
    }
}

class Passenger {
    private String name;

    public Passenger(String name) {
        this.name = name;
    }
}

class Car {
    private String model;
    private Engine engine;
    private Passenger passenger1;
    private Passenger passenger2;

    public Car(String model, Engine engine) {
        this.model = model;
        this.engine = engine;
    }

    public void addPassenger(Passenger passenger) {
        if (passenger1 == null) {
            passenger1 = passenger;
        } else if (passenger2 == null) {
            passenger2 = passenger;
        } else {
            System.out.println("Car is full. Cannot add more passengers.");
        }
    }

    public void removePassenger(Passenger passenger) {
        if (passenger1 == passenger) {
            passenger1 = null;
        } else if (passenger2 == passenger) {
            passenger2 = null;
        } else {
            System.out.println("Passenger not found in the car.");
        }
    }

    public void displayInfo() {
        System.out.println("Car model: " + model);
        System.out.println("Engine power: " + engine.power);
        System.out.println("Passenger 1: " + (passenger1 != null ? passenger1.name : "Empty"));
        System.out.println("Passenger 2: " + (passenger2 != null ? passenger2.name : "Empty"));
    }
}

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine(200);
        Car car = new Car("Toyota", engine);

        Passenger passenger1 = new Passenger("Alice");
        Passenger passenger2 = new Passenger("Bob");

        car.addPassenger(passenger1);
        car.addPassenger(passenger2);

        car.displayInfo();

        car.removePassenger(passenger1);

        car.displayInfo();
    }
}

