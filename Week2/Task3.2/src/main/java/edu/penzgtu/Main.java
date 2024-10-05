package edu.penzgtu;

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