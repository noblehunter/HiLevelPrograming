package edu.penzgtu;

public class Car {
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

