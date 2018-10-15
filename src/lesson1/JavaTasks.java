package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    // Трудоемкость O(n * log(n))
    // Ресурсоемкость O(n)
    static public void sortTimes(String inputName, String outputName) throws IOException, IllegalFormatException, ParseException {
        List<Date> timeList = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                Date date = formatter.parse(line);
                timeList.add(date);
            }
        }
        Collections.sort(timeList);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        for (Date time : timeList) {
            writer.write(formatter.format(time));
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
    // Трудоемкость O(n^2)
    // Ресурсоемкость O(n)
    static public void sortTemperatures(String inputName, String outputName) throws IOException, IllegalFormatException {
        List<Float> listOfTemperatures = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                listOfTemperatures.add(Float.parseFloat(line));
            }
        }
        listOfFloatsSort(listOfTemperatures);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        for (Float temperature : listOfTemperatures) {
            writer.write(Float.toString(temperature));
            writer.newLine();
        }
        writer.close();
    }

    private static int partitionForFloats(List<Float> elements, int min, int max) {
        Float x = elements.get(min + Sorts.random.nextInt(max - min + 1));
        int left = min, right = max;
        while (left <= right) {
            while (elements.get(left) < x) {
                left++;
            }
            while (elements.get(right) > x) {
                right--;
            }
            if (left <= right) {
                Float temp = elements.get(left);
                elements.set(left, elements.get(right));
                elements.set(right, temp);
                left++;
                right--;
            }
        }
        return right;
    }

    private static void listOfFloatsSort(List<Float> elements, int min, int max) {
        if (min < max) {
            int border = partitionForFloats(elements, min, max);
            listOfFloatsSort(elements, min, border);
            listOfFloatsSort(elements, border + 1, max);
        }
    }

    private static void listOfFloatsSort(List<Float> elements) {
        listOfFloatsSort(elements, 0, elements.size() - 1);
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
    // Трудоемкость O(n^2)
    // Ресурсоемкость O(n)
    static public void sortSequence(String inputName, String outputName) throws IOException, IllegalFormatException {
        List<Integer> sequence = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                sequence.add(Integer.parseInt(line));
            }
        }
        List<Integer> sequenceNotSorted = new ArrayList<Integer>(sequence);
        listOfIntegersSort(sequence);
        int previous = sequence.get(0);
        int mostCommon = sequence.get(0);
        int count = 1;
        int maxCount = 1;
        for (int i = 1; i < sequence.size(); i++) {
            if (sequence.get(i) == previous) {
                count++;
            } else {
                if (count > maxCount) {
                    mostCommon = sequence.get(i - 1);
                    maxCount = count;
                }
                previous = sequence.get(i);
                count = 1;
            }
        }
        if (count > maxCount) {
            mostCommon = sequence.get(sequence.size() - 1);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        for (Integer number: sequenceNotSorted) {
            if (number != mostCommon) {
                writer.write(Integer.toString(number));
                writer.newLine();
            }
        }
        for (int i = 0; i < max(count, maxCount); i++) {
            writer.write(Integer.toString(mostCommon));
            writer.newLine();
        }
        writer.close();
    }


    private static int partition(List<Integer> elements, int min, int max) {
        Integer x = elements.get(min + Sorts.random.nextInt(max - min + 1));
        int left = min, right = max;
        while (left <= right) {
            while (elements.get(left) < x) {
                left++;
            }
            while (elements.get(right) > x) {
                right--;
            }
            if (left <= right) {
                Integer temp = elements.get(left);
                elements.set(left, elements.get(right));
                elements.set(right, temp);
                left++;
                right--;
            }
        }
        return right;
    }

    private static void listOfIntegersSort(List<Integer> elements, int min, int max) {
        if (min < max) {
            int border = partition(elements, min, max);
            listOfIntegersSort(elements, min, border);
            listOfIntegersSort(elements, border + 1, max);
        }
    }

    private static void listOfIntegersSort(List<Integer> elements) {
        listOfIntegersSort(elements, 0, elements.size() - 1);
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