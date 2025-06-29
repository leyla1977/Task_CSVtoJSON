package ru.netology;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    public static List<Employee> parseXML(String fileName) {
        List<Employee> list = new ArrayList<>();
        try {
            File xmlFile = new File(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList employees = doc.getElementsByTagName("employee");

            for (int i = 0; i < employees.getLength(); i++) {
                Node node = employees.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;
                Element e = (Element) node;

                // Извлекаем по очереди каждое поле и сразу же печатаем
                String idText = e.getElementsByTagName("id").item(0).getTextContent().trim();
                System.out.println("  idText = [" + idText + "]");
                long id = Long.parseLong(idText);
                System.out.println("  parsed id = " + id);

                String firstNameText = e.getElementsByTagName("firstName").item(0).getTextContent().trim();
                System.out.println("  firstNameText = [" + firstNameText + "]");

                String lastNameText = e.getElementsByTagName("lastName").item(0).getTextContent().trim();
                System.out.println("  lastNameText = [" + lastNameText + "]");

                String countryText = e.getElementsByTagName("country").item(0).getTextContent().trim();
                System.out.println("  countryText = [" + countryText + "]");

                String ageText = e.getElementsByTagName("age").item(0).getTextContent().trim();
                System.out.println("  ageText = [" + ageText + "]");
                int age = Integer.parseInt(ageText);
                System.out.println("  parsed age = " + age);

                // Теперь создаём Employee и вызываем сеттеры
                Employee emp = new Employee();
                emp.setId(id);
                emp.setFirstName(firstNameText);
                emp.setLastName(lastNameText);
                emp.setCountry(countryText);
                emp.setAge(age);

                System.out.println("  CREATED Employee: " + emp);
                list.add(emp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}