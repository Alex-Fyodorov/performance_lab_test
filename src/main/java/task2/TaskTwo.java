package task2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskTwo {

    public static void main(String[] args) {
        String circle = args[0];
        String points = args[1];

        List<String> circleList = readCoordinates(circle);
        List<String> pointsList = readCoordinates(points);

        List<Double> circleCoordinates = findCoordinates(circleList.get(0));
        Double radius = findCoordinates(circleList.get(1)).get(0);

        for (Integer i : pointsCheck(circleCoordinates, radius, pointsList)) {
            System.out.println(i);
        }
    }

    /**
     * Метод вычисляет положение точки относительно окружности.
     * @param circleCoordinates Массив с координатами центра окружности.
     * @param radius Радиус окружности.
     * @param pointsList Массив строк с координатами точек.
     * @return Массив с числами, указывающими на положение точки, где
     * ○ 0 - точка лежит на окружности,
     * ○ 1 - точка внутри,
     * ○ 2 - точка снаружи.
     */
    private static List<Integer> pointsCheck(List<Double> circleCoordinates, Double radius,
                                             List<String> pointsList) {
        List<Integer> result = new ArrayList<>();
        for (String string : pointsList) {
            List<Double> pointCoord = findCoordinates(string);
            Double distance = Math.sqrt(Math.pow(pointCoord.get(0) - circleCoordinates.get(0), 2) +
                    Math.pow(pointCoord.get(1) - circleCoordinates.get(1), 2));
            if (Math.abs(distance - radius) < 0.000001) {
                result.add(0);
            } else if (distance < radius) {
                result.add(1);
            } else {
                result.add(2);
            }
        }
        return result;
    }

    /**
     * Метод получает числовое выражение координат из строки.
     * @param coordString Строка с координатами.
     * @return List<Double>.
     */
    private static List<Double> findCoordinates(String coordString) {
        List<Double> coordinates = new ArrayList<>();
        String[] stringArr = coordString.split(" ");
        for (String string : stringArr) {
            coordinates.add(Double.parseDouble(string));
        }
        return coordinates;
    }

    /**
     * Метод читает поступающий файл и выдаёт массив строк из этого файла.
     * @param fileAddress Строка с адресом файла.
     * @return Массив строк с координатами.
     */
    private static List<String> readCoordinates(String fileAddress) {
        List<String> stringList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileAddress))) {
            String string;
            while ((string = reader.readLine()) != null) {
                stringList.add(string);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringList;
    }
}
