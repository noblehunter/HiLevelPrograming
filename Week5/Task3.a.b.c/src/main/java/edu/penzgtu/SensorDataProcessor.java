import java.util.Scanner;

class SensorIdFormatException extends Exception {
    public SensorIdFormatException(String message) {
        super(message);
    }
}

        // Исключение для некорректного значения температуры
class TemperatureValueException extends Exception {
    public TemperatureValueException(String message) {
        super(message);
    }
}

// Исключение для превышения максимального количества датчиков
class MaxSensorsExceededException extends Exception {
    public MaxSensorsExceededException(String message) {
        super(message);
    }
}

public class SensorDataProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите пакет показаний: ");
        String input = scanner.nextLine();

// Максимальное количество уникальных датчиков
        final int MAX_SENSORS = 100;
        String[] sensorIds = new String[MAX_SENSORS];
        int[] temperatureSums = new int[MAX_SENSORS];
        int[] temperatureCounts = new int[MAX_SENSORS];
        int uniqueSensorCount = 0;

        // Разделение входной строки на показания
        String[] readings = input.split("@");

        // Обработка показаний
        for (String reading : readings) {
            try {
                if (reading.length() < 4) continue; // Игнорируем некорректные данные
                    String sensorId = reading.substring(0, 2); // Двузначный ID датчика

                // Проверка формата идентификатора датчика
                if (!sensorId.matches("\\d{2}")) {
                    throw new SensorIdFormatException("Некорректный формат идентификатора: " + sensorId);
                    }

                int temperature = Integer.parseInt(reading.substring(2)); // Температура
                // Проверка диапазона температуры
                    if (temperature < -50 || temperature > 50) {
                        throw new TemperatureValueException("Температура вне допустимого диапазона: " + temperature);
                    }

                    // Проверка, существует ли уже этот датчик
                    int index = findSensorIndex(sensorIds, sensorId, uniqueSensorCount);
                    if (index == -1) {
                        // Новый датчик
                        if (uniqueSensorCount < MAX_SENSORS) {
                            sensorIds[uniqueSensorCount] = sensorId;
                            temperatureSums[uniqueSensorCount] = temperature;
                            temperatureCounts[uniqueSensorCount] = 1;
                            uniqueSensorCount++;
                        } else {
                            throw new MaxSensorsExceededException("Превышено максимальное количество уникальных датчиков.");
                        }
                    } else {
                        // Обновление данных существующего датчика
                        temperatureSums[index] += temperature;
                        temperatureCounts[index]++;
                    }
                } catch (SensorIdFormatException | TemperatureValueException | MaxSensorsExceededException e) {
                    System.err.println(e.getMessage());
                } catch (NumberFormatException e) {
                    System.err.println("Некорректное значение температуры.");
                }
            }

            // Вычисление средней температуры
            double[] averages = new double[uniqueSensorCount];
        for (int i = 0; i < uniqueSensorCount; i++) {
                averages[i] = (double) temperatureSums[i] / temperatureCounts[i];
            }

            // Запрос на сортировку
        System.out.print("Введите поле для сортировки (1 - по ID, 2 - по средней температуре): ");
            int sortOption = scanner.nextInt();

            // Сортировка
        for (int i = 0; i < uniqueSensorCount - 1; i++) {
                for (int j = 0; j < uniqueSensorCount - 1 - i; j++) {
                    boolean swap = false;
                    if (sortOption == 1) {
                        // Сортировка по ID
                        if (sensorIds[j].compareTo(sensorIds[j + 1]) > 0) {
                            swap = true;
                    }
                } else if (sortOption == 2) {
                    // Сортировка по средней температуре
                        if (averages[j] > averages[j + 1]) {
                            swap = true;
                            }
                }
                if (swap) {
                // Обмен значениями
                    String tempId = sensorIds[j];
                    sensorIds[j] = sensorIds[j + 1];
                    sensorIds[j + 1] = tempId;

                    double tempAvg = averages[j];
                    averages[j] = averages[j + 1];
                    averages[j + 1] = tempAvg;
                }
            }
        }

        // Вывод результатов
        for (int i = 0; i < uniqueSensorCount; i++) {
            System.out.printf("%s %.1f%n", sensorIds[i], averages[i]);
        }

        scanner.close();
    }

    // Метод для поиска индекса датчика в массиве
    private static int findSensorIndex(String[] sensorIds, String sensorId, int uniqueSensorCount) {
        for (int i = 0; i < uniqueSensorCount; i++) {
            if (sensorIds[i].equals(sensorId)) {
                return i;
            }
        }
        return -1; // Датчик не найден
    }
}



