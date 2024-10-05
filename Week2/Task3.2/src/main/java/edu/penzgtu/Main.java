package edu.penzgtu;
// Объявление пакета и класса Engine.
class Engine {
    public int power;

    public Engine(int power) {
        this.power = power;
    }
}
// Объявление класса Passenger
class Passenger {
    public String name;

    public Passenger(String name) {
        this.name = name;
    }
}
// // Объявление класса Car
class Car {
    public String model;
    public Engine engine;
    public Passenger passenger1;
    public Passenger passenger2;

    public Car(String model, Engine engine) {
        this.model = model;
        this.engine = engine;
    }
// Метод addPassenger добавляет пассажира в машину
    public void addPassenger(Passenger passenger) {
        if (passenger1 == null) {
            passenger1 = passenger;
        } else if (passenger2 == null) {
            passenger2 = passenger;
        } else {
            System.out.println("Нет больше посадочных мест");
        }
    }
//Метод eliminatePassenger удаляет пассажира из машины.
    public void eliminatePassenger(Passenger passenger) {
        if (passenger1 == passenger) {
            passenger1 = null;
        } else if (passenger2 == passenger) {
            passenger2 = null;
        } else {
            System.out.println("Пассажир не в машине.");
        }
    }
// Метод displayInfo выводит информацию о модели машины, мощности двигателя и пассажирах в машине.
    public void displayInfo() {
        System.out.println("Модель машины: " + model);
        System.out.println("Мощьность двигателя: " + engine.power);
        System.out.println("Пассажир 1: " + (passenger1 != null ? passenger1.name : "Empty"));
        System.out.println("Пассажир 2: " + (passenger2 != null ? passenger2.name : "Empty"));
    }
}
// Основной класс мейн задествубщий остальные классы
public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine(200);
        Car car = new Car("Лада", engine);
// Создание объектов passenger1 и passenger2 типа Passenger с именами "Аня" и "Витя" соответственно.
        Passenger passenger1 = new Passenger("Аня");
        Passenger passenger2 = new Passenger("Витя");
// Добавление пассажиров passenger1 и passenger2 в машину car с помощью метода addPassenger.
        car.addPassenger(passenger1);
        car.addPassenger(passenger2);
// Вывод информации о машине с помощью метода displayInfo.
        car.displayInfo();
// Удаление пассажира passenger1 из машины с помощью метода removePassenger.
        car.eliminatePassenger(passenger1);
// Вывод информации о машине с помощью метода displayInfo после удаления пассажира
        car.displayInfo();
    }
}