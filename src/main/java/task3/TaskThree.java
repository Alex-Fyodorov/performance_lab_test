package task3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskThree {

    public static void main(String[] args) {

        String values = args[0];
        String tests = args[1];
        String report = args[2];

        JSONArray testsJsonArray = deSerialization(tests, "tests");
        JSONArray valuesJsonArray = deSerialization(values, "values");

        ObjectMapper mapper = new ObjectMapper();

        // Получение из объекта типа JSONArray массива объектов Note.
        List<Note> noteList = new ArrayList<>();
        for (Object object : testsJsonArray) {
            Note note = mapper.convertValue(object, Note.class);
            noteList.add(note);
        }

        // Получение из объекта типа JSONArray Map в которой ключи - значения id,
        // а значения - это данные, которые нужно вставить в поля value.
        Map<Long, String> valuesMap = new HashMap<>();
        for (Object object : valuesJsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            Long id = (Long) jsonObject.get("id");
            String value = (String) jsonObject.get("value");
            valuesMap.put(id, value);
        }

        toFillFields(noteList, valuesMap);
        writeToFile(mapper, report, noteList);
    }

    /**
     * Сериализация в Json и запись в файл.
     * @param mapper ObjectMapper.
     * @param file Адрес файла.
     * @param noteList Массив объектов, записываемых в файл.
     */
    private static void writeToFile(ObjectMapper mapper, String file, List<Note> noteList) {
        StringWriter stringWriter = new StringWriter();

        try {
            mapper.writeValue(stringWriter, noteList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject reportObject = new JSONObject();
        reportObject.put("report", stringWriter);

        try (Writer fileWriter = new FileWriter(file)) {
            fileWriter.write(reportObject.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * метод заполняет в объектах Note поля value согласно id.
     * @param noteList Массив объектов Note.
     * @param valuesMap Map в которой ключи - значения id, а значения - это данные,
     *                  которые нужно вставить в поля value.
     */
    private static void toFillFields(List<Note> noteList, Map<Long, String> valuesMap) {
        for (Note note : noteList) {
            if (valuesMap.containsKey(note.id)) {
                note.value = valuesMap.get(note.id);
            }
            if (note.values != null) {
                toFillFields(note.values, valuesMap);
            }
        }
    }

    /**
     * Получение массива объектов Json из файла.
     * @param fileAddress Адрес файла.
     * @param field Название поля, значение которого составляет искомый массив объектов.
     * @return JSONArray.
     */
    private static JSONArray deSerialization(String fileAddress, String field) {
        File file = new File(fileAddress);
        Object jsonObject;

        try (Reader reader = new FileReader(file)) {
            jsonObject = new JSONParser().parse(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONObject jo = (JSONObject) jsonObject;
        JSONArray jsonArray = (JSONArray) jo.get(field);
        return jsonArray;
    }
}