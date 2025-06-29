package ru.netology;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        try {
            // Задача 1.1. Сохраняем исходные данные в CSV
            String csvFileName = "data.csv";
            List<String[]> employeesData = new ArrayList<>();
            employeesData.add(new String[]{"1", "John", "Smith", "USA", "25"});
            employeesData.add(new String[]{"2", "Ivan", "Petrov", "RU", "23"});

            try (FileWriter writer = new FileWriter(csvFileName)) {
                for (String[] emp : employeesData) {
                    writer.write(String.join(",", emp));
                    writer.write("\n");
                }
            }

            // Задача 1.2. Чтение из CSV
            String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
            List<Employee> staff = parseCSV(columnMapping, csvFileName);
            staff.forEach(System.out::println);

            // Задача 1.3. Преобразуем CSV-список в JSON и записываем
            String json = JsonUtil.listToJson(staff);
            String jsonFileName = "data.json";
            FileUtil.writeString(json, jsonFileName);
            System.out.println("CSV → JSON записан в файл: " + jsonFileName);


            // Задача 2.1. Чтение из XML
           List<Employee> list = XmlParser.parseXML("data.xml");

            // Задача 2.2. Преобразуем XML-список в JSON
            String xmlJson = JsonUtil.listToJson(list);

            // Задача 2.3. Запись XML → JSON в файл
            String outputJsonFile = "data2.json";
            FileUtil.writeString(xmlJson, outputJsonFile);


            System.out.println("Запись завершена. JSON сохранён в " + outputJsonFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Задача 3.1: Чтение JSON из файла
        String json = FileUtil.readString("new_data.json");

        // Задача 3.2: Преобразование JSON в список>>> XML → JSON
        List<Employee> list = FileUtil.jsonToList(json);

        // Задача 3.3: Вывод в консоль
        for (Employee emp : list) {
            System.out.println(emp);
        }
    }

    // Вспомогательный метод для чтения CSV
    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            return csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



}