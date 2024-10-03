package edu.penzgtu;

public class Student {
    private String name;
    private String birthDate;
    private String group;
    private int studentId;
    private double averageScore;
    // Constructor
    public Student(String name, String birthDate, String group, int studentId, double averageScore) {
        this.name = name;
        this.birthDate = birthDate;
        this.group = group;
        this.studentId = studentId;
        this.averageScore = averageScore;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    // Method to calculate age
    public int getAge() {
        // Assuming birth date is in format "yyyy-mm-dd"
        String[] parts = birthDate.split("-");
        int birthYear = Integer.parseInt(parts[0]);
        int currentYear = java.time.Year.now().getValue();
        return currentYear - birthYear;
    }

    // toString method
    public String toString() {
        return "Студент Имя:='" + name + "', Дата рождения='" + birthDate + "', группа='" + group +
                "', Индификатор студента=" + studentId + ", Средняя отценка=" + averageScore + "}";
    }

    // Method to check if student is excellent
    public boolean isExcellentStudent() {
        return averageScore >= 4.8;
    }

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
