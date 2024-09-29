package edu.penzgtu;

public class App { public static void main(String[] args) {
    System.out.println("Вывести минимальное значение Integer: " + Integer.MIN_VALUE);
    System.out.println("Вывести максимальное значение: Integer: " + Integer.MAX_VALUE);
    // Integer: минимальное значение -2^31, максимальное значение 2^31-1, потому что int состоит из 32 бит, один из которых используется для хранения знака
    System.out.println("Вывести плоское минимальное значение Float: " + Float.MIN_VALUE);
    System.out.println("Вывести плоское максимальное значение Float: " + Float.MAX_VALUE);
    // Float: минимальное положительное значение очень мало, максимальное значение достаточно большое, потому что float представляет собой числа с плавающей точкой одинарной точности.
    System.out.println("Вывести длинное минимальное значение Long: " + Long.MIN_VALUE);
    System.out.println("Вывести длинное максимальное значение Long: " + Long.MAX_VALUE);
    // Long: минимальное значение -2^63, максимальное значение 2^63-1, так как long состоит из 64 бит и используется для хранения больших целых чисел.
    System.out.println("Вывести дублированное минимальное значение Double:" + Double.MIN_VALUE);
    System.out.println("Вывести дублираванное максимальное значение Double: " + Double.MAX_VALUE);
    // Double: минимальное положительное значение очень маленькое, максимальное значение очень большое, так как double представляет собой числа с плавающей точкой двойной точности.
    System.out.println("Вывести минимальное значение в байтах Byte: " + Byte.MIN_VALUE);
    System.out.println("Вывести максимальное значение в байтах Byte: " + Byte.MAX_VALUE);
    // Byte: минимальное значение -128, максимальное значение 127, так как byte состоит из 8 бит, один из которых используется для хранения знака.
    System.out.println("Вывести длинное минимальное значение Short: " + Short.MIN_VALUE);
    System.out.println("Вывести длинное максимальное значениe Short: " + Short.MAX_VALUE);
    // Short: минимальное значение -32768, максимальное значение 32767, так как short состоит из 16 бит.
    System.out.println("Вывести минимальное символьное значениe Character: " + (int) Character.MIN_VALUE);
    System.out.println("Вывести максимальное символьное значениe Character: " + (int) Character.MAX_VALUE);
    // Character: минимальное значение 0, максимальное значение 65535, так как char представляет собой беззнаковое целое число
}
}