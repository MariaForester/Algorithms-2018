package lesson6;

import kotlin.NotImplementedError;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        // Трудоемкость T = O(n^2)
        // Ресурсоемкость R = O(n)
        if (list.size() == 0 || list.size() == 1) {
            return list;
        }
        int[] lengths = new int[list.size()];
        Arrays.fill(lengths, 1);
        int[] previousIndices = new int[list.size()];
        Arrays.fill(previousIndices, -1);
        int maxLengthIndices = 0;
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i) && lengths[j] + 1 > lengths[i]) {
                    lengths[i] = lengths[j] + 1;
                    previousIndices[i] = j;
                    if (lengths[maxLengthIndices] < lengths[i]) {
                        maxLengthIndices = i;
                    }
                }
            }
        }
        int count = lengths[maxLengthIndices];
        int[] result = new int[count];
        int i = maxLengthIndices;
        while (i != -1) {
            result[--count] = list.get(i);
            i = previousIndices[i];
        }
        return Arrays.stream(result).boxed().collect(Collectors.toList());
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    // Трудоемкость T = O(n*m)
    // Ресурсоемкость R = O(n*m)

    public static int shortestPathOnField(String inputName) throws IOException {
        List<String> field = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                field.add(line.replaceAll(" ", ""));
            }
        }
        int matrixLength = field.get(0).length(), matrixHeight = field.size();
        int[][] sums = new int[matrixHeight][matrixLength];
        sums[0][0] = field.get(0).charAt(0) - 48;
        for (int i = 1; i < matrixLength; i++) {
            sums[0][i] = field.get(0).charAt(i) - 48 + sums[0][i - 1];
        }
        for (int i = 1; i < matrixHeight; i++) {
            sums[i][0] = field.get(i).charAt(0) - 48 + sums[i - 1][0];
        }
        for (int i = 1; i < matrixLength; i++) {
            for (int j = 1; j < matrixHeight; j++) {
                int minPreviousSum = Math.min(Math.min(sums[j - 1][i], sums[j][i - 1]), sums[j -1][i -1]);
                sums[j][i] = field.get(j).charAt(i) - 48 + minPreviousSum;
            }
        }
        return sums[matrixHeight - 1][matrixLength - 1];
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
