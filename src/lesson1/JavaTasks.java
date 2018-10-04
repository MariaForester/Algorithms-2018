package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

import static java.lang.Math.max;

@SuppressWarnings("unused")

public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     * <p>
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */

    static public void sortTimes(String inputName, String outputName) throws IOException, IllegalFormatException {
        List<Integer> timeList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                String[] lineComponents = line.split(":");
                timeList.add(Integer.parseInt(lineComponents[0]) * 3600 + Integer.parseInt(lineComponents[1]) * 60 +
                        Integer.parseInt(lineComponents[2]));
            }
        }
        int[] timesArray = timeList.stream().mapToInt(i -> i).toArray();
        Sorts.mergeSort(timesArray);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        for (int i = 0; i < timesArray.length; i++) {
            String hours = timesArray[i] / 3600 < 10 ? "0" + Integer.toString(timesArray[i] / 3600) :
                    Integer.toString(timesArray[i] / 3600);
            String minutes = (timesArray[i] / 60) % 60 < 10 ? "0" + Integer.toString((timesArray[i] / 60) % 60) :
                    Integer.toString((timesArray[i] / 60) % 60);
            String seconds = timesArray[i] % 60 < 10 ? "0" + Integer.toString(timesArray[i] % 60) :
                    Integer.toString(timesArray[i] % 60);
            writer.write(hours + ":" + minutes + ":" + seconds);
            writer.newLine();
        }
        writer.close();
    }


    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException, IllegalFormatException {
        List<Double> listOfTemperatures = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                listOfTemperatures.add(Double.parseDouble(line));
            }
        }
        Collections.sort(listOfTemperatures);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        for (int i = 0; i < listOfTemperatures.size(); i++) {
            writer.write(Double.toString(listOfTemperatures.get(i)));
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException, IllegalFormatException {
        List<Integer> timeList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                timeList.add(Integer.parseInt(line));
            }
        }
        int[] timesArray = timeList.stream().mapToInt(i -> i).toArray();
        int[] timesArrayNotSorted = timesArray.clone();
        Arrays.sort(timesArray);
        int previous = timesArray[0];
        int mostCommon = timesArray[0];
        int count = 1;
        int maxCount = 1;
        for (int i = 1; i < timesArray.length; i++) {
            if (timesArray[i] == previous) {
                count++;
            } else {
                if (count > maxCount) {
                    mostCommon = timesArray[i - 1];
                    maxCount = count;
                }
                previous = timesArray[i];
                count = 1;
            }
        }
        if (count > maxCount) {
            mostCommon = timesArray[timesArray.length - 1];
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        for (int i = 0; i < timesArrayNotSorted.length; i++) {
            if (timesArrayNotSorted[i] != mostCommon) {
                writer.write(Integer.toString(timesArrayNotSorted[i]));
                writer.newLine();
            }
        }
        for (int i = 0; i < max(count, maxCount); i++) {
            writer.write(Integer.toString(mostCommon));
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
