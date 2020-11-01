package main.sensors;

import htmlflow.HtmlView;
import htmlflow.StaticHtml;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class Segment {
    private int id;
    private int status;
    private double soil_humidity;
    private double segment_lighting;
    private double soil_nitrogenous;
    private double soil_salinity;

    public Segment(int id, int status) {
        this.id = id;
        this.status = status;
        this.soil_humidity = 0 + new Random().nextDouble() * 100;
        this.segment_lighting = 0 + new Random().nextDouble() * 100;
        this.soil_nitrogenous = 0 + new Random().nextDouble() * 100;
        this.soil_salinity = 0 + new Random().nextDouble() * 5;
    }

    public Segment(int id, int status, double soil_humidity, double segment_lighting, double soil_nitrogenous, double soil_salinity) {
        this.id = id;
        this.status = status;
        this.soil_humidity = soil_humidity;
        this.segment_lighting = segment_lighting;
        this.soil_nitrogenous = soil_nitrogenous;
        this.soil_salinity = soil_salinity;
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

    public double getSoil_humidity() {
        return soil_humidity;
    }

    public void setSoil_humidity(double soil_humidity) {
        this.soil_humidity = soil_humidity;
    }

    public double getSegment_lighting() {
        return segment_lighting;
    }

    public void setSegment_lighting(double segment_lighting) {
        this.segment_lighting = segment_lighting;
    }

    public double getSoil_nitrogenous() {
        return soil_nitrogenous;
    }

    public void setSoil_nitrogenous(double soil_nitrogenous) {
        this.soil_nitrogenous = soil_nitrogenous;
    }

    public double getSoil_salinity() {
        return soil_salinity;
    }

    public void setSoil_salinity(double soil_salinity) {
        this.soil_salinity = soil_salinity;
    }

    public void segmentToJSON() throws IOException {
        System.out.println("Запись показаний в JSON-файл");
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("status", this.status);
        json.put("soil_humidity", this.soil_humidity);
        json.put("segment_lighting", this.segment_lighting);
        json.put("soil_nitrogenous", this.soil_nitrogenous);
        json.put("soil_salinity", this.soil_salinity);
        File myJson = new File("segment" + id + ".json");
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
        System.out.println("soil_humidity: " + jsonData.get("soil_humidity"));
        System.out.println("segment_lighting: " + jsonData.get("segment_lighting"));
        System.out.println("soil_nitrogenous: " + jsonData.get("soil_nitrogenous"));
        System.out.println("soil_salinity: " + jsonData.get("soil_salinity"));
        System.out.println();
    }

    public void segmentToXML() throws IOException, ImpossibleModificationException, ParserConfigurationException, SAXException {
        System.out.println("Запись показаний в XML-файл");
        String xml = new Xembler(
                new Directives()
                        .add("root")
                        .add("id").set(id)
                        .up()
                        .add("status").set(status)
                        .up()
                        .add("soil_humidity").set(soil_humidity)
                        .up()
                        .add("segment_lighting").set(segment_lighting)
                        .up()
                        .add("soil_nitrogenous").set(soil_nitrogenous)
                        .up()
                        .add("soil_salinity").set(soil_salinity)
        ).xml();
        File myXml = new File("segment" + id + ".xml");
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
        Document doc = dBuilder.parse(new File("segment" + id + ".xml"));
        System.out.println("Обработанные данные из XML-файла:");
        System.out.println("id: " + doc.getElementsByTagName("id").item(0).getTextContent());
        System.out.println("status: " + doc.getElementsByTagName("status").item(0).getTextContent());
        System.out.println("soil_humidity: " + doc.getElementsByTagName("soil_humidity").item(0).getTextContent());
        System.out.println("segment_lighting: " + doc.getElementsByTagName("segment_lighting").item(0).getTextContent());
        System.out.println("soil_nitrogenous: " + doc.getElementsByTagName("soil_nitrogenous").item(0).getTextContent());
        System.out.println("soil_salinity: " + doc.getElementsByTagName("soil_salinity").item(0).getTextContent());
        System.out.println();
    }

    public void segmentToHTML() throws IOException {
        System.out.println("Запись показаний в HTML-файл");
        HtmlView view = StaticHtml.view(v -> v
                .html()
                .body()
                .p().attrId("id").text(id).__()
                .p().attrId("status").text(status).__()
                .p().attrId("soil_humidity").text(soil_humidity).__()
                .p().attrId("segment_lighting").text(segment_lighting).__()
                .p().attrId("soil_nitrogenous").text(soil_nitrogenous).__()
                .p().attrId("soil_salinity").text(soil_salinity).__()
                .__()
                .__());
        System.out.println("Запись прошла успешно");
        System.out.println();
        System.out.println("Содержание HTML-файла: ");
        view.setPrintStream(System.out).write();
        System.out.println();
        view.setPrintStream(new PrintStream(new FileOutputStream("segment" + id + ".html"))).write();
        org.jsoup.nodes.Document htmlFile = Jsoup.parse(new File("segment" + id + ".html"), "ISO-8859-1");
        System.out.println();
        System.out.println("Обработанные данные из HTML-файла:");
        System.out.println("id: " + htmlFile.getElementById("id").text());
        System.out.println("status: " + htmlFile.getElementById("status").text());
        System.out.println("soil_humidity: " + htmlFile.getElementById("soil_humidity").text());
        System.out.println("segment_lighting: " + htmlFile.getElementById("segment_lighting").text());
        System.out.println("soil_nitrogenous: " + htmlFile.getElementById("soil_nitrogenous").text());
        System.out.println("soil_salinity: " + htmlFile.getElementById("soil_salinity").text());
        System.out.println();
    }
}
