package task1;

import java.util.ArrayList;
import java.util.List;

public class TaskOne {

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        List<Integer> result = doCycle(n, m);
        printResult(result);
    }

    /**
     * Вычисление позиций, по которым проходит цикл, длинной m по круговому циклу, длинной n.
     * @param n Число n.
     * @param m Число m.
     * @return Массив чисел, составляющих начальные позиции цикла m.
     */
    public static List<Integer> doCycle(int n, int m) {
        List<Integer> result = new ArrayList<>();
        int value = 1;
        do {
            result.add(value);
            value += m - 1;
            value = value % n;
            if (value == 0) value = n;
        } while (value != 1);
        return result;
    }

    /**
     * Печать результата.
     * @param result Массив чисел, составляющих результат.
     */
    public static void printResult(List<Integer> result) {
        for (Integer number : result) {
            System.out.printf("%d ", number);
        }
    }
}
