package edu.penzgtu;

public class Author {
        private String name;
        private int birthYear;

        public Author(String name, int birthYear) {
            this.name = name;
            this.birthYear = birthYear;
        }
        // Метод для получения имени автора
        public String getName() {
            return name;
        }
        // Метод для установки нового имени автора
        public void setName(String name) {
            this.name = name;
        }
        // Метод для получения года рождения автора
        public int getBirthYear() {
            return birthYear;
        }
        // Метод для установки нового года рождения автора
        public void setBirthYear(int birthYear) {
            this.birthYear = birthYear;
        }
}
