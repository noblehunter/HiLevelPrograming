package edu.penzgtu;
// Обьявление класса Student
public class Student {
    private String name;
    private String birthDate;
    private String group;
    private int studentId;
    private double averageScore;
    // Конструктор класса Student
    public Student(String name, String birthDate, String group, int studentId, double averageScore) {
        this.name = name;
        this.birthDate = birthDate;
        this.group = group;
        this.studentId = studentId;
        this.averageScore = averageScore;
    }

    // Получение имени студента
    public String getName() {
        return name;
    }
    // Установка нового имени студента
    public void setName(String name) {
        this.name = name;
    }
    // Получение года рождения студента
    public String getBirthDate() {
        return birthDate;
    }
    // Установка года рождения студента
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    // Получение группы студента
    public String getGroup() {
        return group;
    }
// Установка группы студента
    public void setGroup(String group) {
        this.group = group;
    }
// Получение индификатора студента
    public int getStudentId() {
        return studentId;
    }
// Устанока индификатора студента
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
// Получение средней остценки студента
    public double getAverageScore() {
        return averageScore;
    }
// Установка средней отценки студента
    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    // Метод для вычисления возраста студента
    public int getAge() {
        // Assuming birth date is in format "yyyy-mm-dd"
        String[] parts = birthDate.split("-");
        int birthYear = Integer.parseInt(parts[0]);
        int currentYear = java.time.Year.now().getValue();
        return currentYear - birthYear;
    }

    // Метод предоставления всей информации о студенте
    public String toString() {
        return "Студент Имя:='" + name + "', Дата рождения='" + birthDate + "', группа='" + group +
                "', Индификатор студента=" + studentId + ", Средняя отценка=" + averageScore + "}";
    }

    // Метод для проверки, является ли студент отличником
    public boolean isExcellentStudent() {
        return averageScore >= 4.8;
    }
    // Основной метод программы
    public static void main(String[] args) {
        Student student1 = new Student("Алиса", "2000-05-15", "A1", 101, 4.9);
        Student student2 = new Student("Михаил", "1999-11-30", "B2", 102, 4.5);

        System.out.println(student1.toString());
        System.out.println("Лет " + student1.getName() + ": " + student1.getAge() + " лет");
        System.out.println("Является ли " + student1.getName() + " студентом? " + student1.isExcellentStudent());

        System.out.println(student2.toString());
        System.out.println("Лет " + student2.getName() + ": " + student2.getAge() + " лет");
        System.out.println("Является ли " + student2.getName() + " студентом? " + student2.isExcellentStudent());
    }
}
