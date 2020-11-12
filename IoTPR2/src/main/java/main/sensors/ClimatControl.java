package main.sensors;

import htmlflow.*;
import org.json.*;
import org.jsoup.*;
import org.w3c.dom.*;
import org.xembly.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ClimatControl {
    private int id;
    private int status;
    private double temperature;
    private double air_humidity;

    public ClimatControl(int id, int status) {
        this.id = id;
        this.status = status;
        double leftTemperatureLimit = 10;
        double rightTemperatureLimit = 40;
        this.temperature = leftTemperatureLimit + new Random().nextDouble() * (rightTemperatureLimit - leftTemperatureLimit);
        this.air_humidity = 0 + new Random().nextDouble() * 5;
    }

    public ClimatControl(int id, int status, double temperature, double air_humidity) {
        this.id = id;
        this.status = status;
        this.temperature = temperature;
        this.air_humidity = air_humidity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getAir_humidity() {
        return air_humidity;
    }

    public void setAir_humidity(double air_humidity) {
        this.air_humidity = air_humidity;
    }

    public void climatControlToJson() throws IOException {
        System.out.println("Запись показаний в JSON-файл");
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("status", this.status);
        json.put("air_temperature", this.temperature);
        json.put("air_humidity", this.air_humidity);
        File myJson = new File("climatControl.json");
        if (!myJson.exists()) {
            myJson.createNewFile();
        }
        try (FileWriter jsonWriter = new FileWriter(myJson, false)) {
            jsonWriter.write(String.valueOf(json));
            jsonWriter.flush();
            System.out.println("Запись прошла успешно");
            System.out.println();
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Чтение JSON-файла");
        System.out.println("Содержание JSON-файла: ");
        Scanner jsonReader = new Scanner(myJson);
        while (jsonReader.hasNextLine()) {
            String data = jsonReader.nextLine();
            System.out.println(data);
            System.out.println();
        }
        jsonReader.close();
        String jsonContent = new String(Files.readAllBytes(Paths.get(myJson.getPath())));
        JSONObject jsonData = new JSONObject(jsonContent);
        System.out.println("Обработанные данные из JSON-файла:");
        System.out.println("id: " + jsonData.get("id"));
        System.out.println("status: " + jsonData.get("status"));
        System.out.println("air_temperature: " + jsonData.get("air_temperature"));
        System.out.println("air_humidity: " + jsonData.get("air_humidity"));
        System.out.println();
    }

    public void climatControlToXML() throws IOException, ImpossibleModificationException, ParserConfigurationException, SAXException {
        System.out.println("Запись показаний в XML-файл");
        String xml = new Xembler(
                new Directives()
                        .add("root")
                        .add("id").set(id)
                        .up()
                        .add("status").set(status)
                        .up()
                        .add("air_temperature").set(temperature)
                        .up()
                        .add("air_humidity").set(air_humidity)
        ).xml();
        File myXml = new File("climatControl.xml");
        if (!myXml.exists()) {
            myXml.createNewFile();
        }
        try (FileWriter xmlWriter = new FileWriter(myXml, false)) {
            xmlWriter.write(String.valueOf(xml));
            xmlWriter.flush();
            System.out.println("Запись прошла успешно");
            System.out.println();
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Чтение XML-файла");
        System.out.println("Содержание XML-файла: ");
        Scanner xmlReader = new Scanner(myXml);
        while (xmlReader.hasNextLine()) {
            String data = xmlReader.nextLine();
            System.out.println(data);
            System.out.println();
        }
        xmlReader.close();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File("climatControl.xml"));
        System.out.println("Обработанные данные из XML-файла:");
        System.out.println("id: " + doc.getElementsByTagName("id").item(0).getTextContent());
        System.out.println("status: " + doc.getElementsByTagName("status").item(0).getTextContent());
        System.out.println("air_temperature: " + doc.getElementsByTagName("air_temperature").item(0).getTextContent());
        System.out.println("air_humidity: " + doc.getElementsByTagName("air_humidity").item(0).getTextContent());
        System.out.println();
    }

    public void climatControlToHTML() throws IOException {
        System.out.println("Запись показаний в HTML-файл");
        HtmlView view = StaticHtml.view(v -> v
                .html()
                .body()
                .p().attrId("id").text(id).__()
                .p().attrId("status").text(status).__()
                .p().attrId("air_temperature").text(temperature).__()
                .p().attrId("air_humidity").text(air_humidity).__()
                .__()
                .__());
        System.out.println("Запись прошла успешно");
        System.out.println();
        System.out.println("Содержание HTML-файла: ");
        view.setPrintStream(System.out).write();
        System.out.println();
        view.setPrintStream(new PrintStream(new FileOutputStream("climatControl.html"))).write();
        org.jsoup.nodes.Document htmlFile = Jsoup.parse(new File("climatControl.html"), "ISO-8859-1");
        System.out.println();
        System.out.println("Обработанные данные из HTML-файла:");
        System.out.println("id: " + htmlFile.getElementById("id").text());
        System.out.println("status: " + htmlFile.getElementById("status").text());
        System.out.println("air_temperature: " + htmlFile.getElementById("air_temperature").text());
        System.out.println("air_humidity: " + htmlFile.getElementById("air_humidity").text());
        System.out.println();
    }
}