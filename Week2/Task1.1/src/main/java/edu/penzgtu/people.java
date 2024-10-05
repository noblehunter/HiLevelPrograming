package edu.penzgtu;
    // Создаем основной класс
public class people {
    // Делаем имя и возраст приватными
    private String name;
    private int age;
    // Задаем публичный класс
    public people() {
    }
    // Задаем показатели имя и возраст
    public people(String name, int age) {
        this.name = name;
        this.age = age;
    }
    // Получаем заданое имя
    public String getName() {
        return name;
    }
    // Устанавливаем имя
    public void setName(String name) {
        this.name = name;
    }
    // Получаем возраст
    public int getAge() {
        return age;
    }
    // Устанавливаем возраст
    public void setAge(int age) {
        this.age = age;
    }
    // Создаем два обьекта
    public static void main(String[] args) {
        people people1 = new people("Алиса", 30);
        people people2 = new people();
        people2.setName("Борис");
        people2.setAge(25);
    // Присваиваем обьектам имена
        System.out.println("1 субьект: Имя - " + people1.getName() + ", Лет - " + people1.getAge());
        System.out.println("2 субьект: Имя - " + people2.getName() + ", Лет - " + people2.getAge());
    }
}