package edu.penzgtu;

public class Person {
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) {
        Person person1 = new Person("Alice", 30);
        Person person2 = new Person();
        person2.setName("Bob");
        person2.setAge(25);

        System.out.println("Person 1: Name - " + person1.getName() + ", Age - " + person1.getAge());
        System.out.println("Person 2: Name - " + person2.getName() + ", Age - " + person2.getAge());
    }
}
