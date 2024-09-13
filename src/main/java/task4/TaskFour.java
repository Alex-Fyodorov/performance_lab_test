package task4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TaskFour {

    public static void main(String[] args) {

        String digits = args[0];
        List<Integer> digitsList = readDigits(digits);
        Map<Integer, Integer> numsAndQuantity = toFillMap(digitsList);
        System.out.println(solution(numsAndQuantity));
    }

    /**
     * Метод получает массив чисел из файла.
     * @param fileAddress Адрес файла.
     * @return List<Integer>.
     */
    private static List<Integer> readDigits(String fileAddress) {
        List<Integer> digitsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileAddress))) {
            String string;
            while ((string = reader.readLine()) != null) {
                String[] stringArr = string.split(" ");
                for (String str : stringArr) {
                    digitsList.add(Integer.parseInt(str));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return digitsList;
    }

    /**
     * Метод изменяет объект Map, сводя все числа к одной позиции по самому выгодному алгоритму,
     * при этом считает какое количество шагов на это потребовалось.
     * @param numsAndQuantity объект Map, в котором ключ - это само число,
     *                        а значение - количество этих чисел в массиве.
     * @return Количество шагов.
     */
    public static Integer solution(Map<Integer, Integer> numsAndQuantity) {
        int steps = 0;
        Set<Integer> keys = numsAndQuantity.keySet();
        int minKey = keys.stream().min(Integer::compareTo).get();
        int maxKey = keys.stream().max(Integer::compareTo).get();

        while (minKey != maxKey) {
            if (numsAndQuantity.get(minKey) <= numsAndQuantity.get(maxKey)) {
                steps += numsAndQuantity.get(minKey);
                changeMap(numsAndQuantity, minKey, true);
                minKey++;
            } else {
                steps += numsAndQuantity.get(maxKey);
                changeMap(numsAndQuantity, maxKey, false);
                maxKey--;
            }
        }
        return steps;
    }

    /**
     * Метод изменяет объект Map, перемещая числа в ту или иную сторону.
     * @param numsAndQuantity объект Map, в котором ключ - это само число,
     *                        а значение - количество этих чисел в массиве.
     * @param key Позиция, которую необходимо переместить.
     * @param increase true, если число необходимо увеличить, false, если уменьшить.
     * @return Map<Integer, Integer>
     */
    public static Map<Integer, Integer> changeMap(Map<Integer, Integer> numsAndQuantity,
                                                  int key, boolean increase) {
        int keyTwo = 0;
        if (increase) {
            keyTwo = key + 1;
        } else {
            keyTwo = key - 1;
        }
        int valueTwo = 0;
        if (numsAndQuantity.containsKey(keyTwo)) {
            valueTwo = numsAndQuantity.get(keyTwo);
        }
        numsAndQuantity.put(keyTwo, valueTwo + numsAndQuantity.get(key));
        numsAndQuantity.remove(key);
        return numsAndQuantity;
    }

    /**
     * Получение из массива чисел объекта Map, в котором ключ - это само число,
     * а значение - количество этих чисел в массиве.
     * @param digitsList Массив чисел.
     * @return Map<Integer, Integer>
     */
    public static Map<Integer, Integer> toFillMap(List<Integer> digitsList) {
        Map<Integer, Integer> numsAndQuantity = new HashMap<>();
        for (Integer num : digitsList) {
            int numValue = 0;
            if (numsAndQuantity.containsKey(num)) {
                numValue = numsAndQuantity.get(num);
            }
            numsAndQuantity.put(num, numValue + 1);
        }
        return numsAndQuantity;
    }
}
