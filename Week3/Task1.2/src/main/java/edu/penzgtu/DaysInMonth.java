package edu.penzgtu;

public class DaysInMonth {
    // Публичный класс с данными вискостного года
    public static void main(String[] args) {
        int year = 2012; // Задайте год
        int month = 2;   // Задайте месяц (1 - январь, 2 - февраль и т.д.)
        // Информация для вывода
        int days = getDaysInMonth(month, year);

        if (days != -1) {
            System.out.println("Количество дней во " + month + " месяце, " + year + " года" + ": " + days);
        } else {
            System.out.println("Неверный номер месяца: " + month);
        }
    }

    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 1: // Январь
            case 3: // Март
            case 5: // Май
            case 7: // Июль
            case 8: // Август
            case 10: // Октябрь
            case 12: // Декабрь
                return 31;

            case 4: // Апрель
            case 6: // Июнь
            case 9: // Сентябрь
            case 11: // Ноябрь
                return 30;

            case 2: // Февраль
                if (isLeapYear(year)) {
                    return 29; // Високосный год
                } else {
                    return 28; // Обычный год
                }

            default:
                return -1; // Неверный номер месяца
        }
    }

    public static boolean isLeapYear(int year) {
        // Проверка на високосный год
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
