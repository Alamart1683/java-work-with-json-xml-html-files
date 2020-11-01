package main;

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
import java.util.Scanner;

public class CommandResponse {
    public void getJSONCommandsResponse(int commandID, int command_object_id, int command_result) throws IOException {
        System.out.println("Запись результата выполнения команды в JSON-файл");
        JSONObject json = new JSONObject();
        json.put("commandID", commandID);
        json.put("command_object_id", command_object_id);
        json.put("command_result", command_result);
        File myJson = new File("command_" + commandID + "_to_" + command_object_id + ".json");
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
        System.out.println("commandID: " + jsonData.get("commandID"));
        System.out.println("command_object_id: " + jsonData.get("command_object_id"));
        System.out.println("command_result: " + jsonData.get("command_result"));
        System.out.println();
    }

    public void getXMLCommandResponse(int commandID, int command_object_id, int command_result) throws IOException, ImpossibleModificationException, ParserConfigurationException, SAXException {
        System.out.println("Запись результата выполнения команды в XML-файл");
        String xml = new Xembler(
                new Directives()
                        .add("root")
                        .add("commandID").set(commandID)
                        .up()
                        .add("command_object_id").set(command_object_id)
                        .up()
                        .add("command_result").set(command_result)
        ).xml();
        File myXml = new File("command_" + commandID + "_to_" + command_object_id + ".xml");
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
        Document doc = dBuilder.parse(new File("command_" + commandID + "_to_" + command_object_id + ".xml"));
        System.out.println("Обработанные данные из XML-файла:");
        System.out.println("commandID: " + doc.getElementsByTagName("commandID").item(0).getTextContent());
        System.out.println("command_object_id: " + doc.getElementsByTagName("command_object_id").item(0).getTextContent());
        System.out.println("command_result: " + doc.getElementsByTagName("command_result").item(0).getTextContent());
        System.out.println();
    }

    public void getHTMLCommandResponse(int commandID, int command_object_id, int command_result) throws IOException {
        System.out.println("Запись результата выполнения команды в HTML-файл");
        HtmlView view = StaticHtml.view(v -> v
                .html()
                .body()
                .p().attrId("commandID").text(commandID).__()
                .p().attrId("command_object_id").text(command_object_id).__()
                .p().attrId("command_result").text(command_result).__()
                .__()
                .__());
        System.out.println("Запись прошла успешно");
        System.out.println();
        System.out.println("Содержание HTML-файла: ");
        view.setPrintStream(System.out).write();
        System.out.println();
        view.setPrintStream(new PrintStream(new FileOutputStream("command_" + commandID + "_to_" + command_object_id + ".html"))).write();
        org.jsoup.nodes.Document htmlFile = Jsoup.parse(new File("command_" + commandID + "_to_" + command_object_id + ".html"), "ISO-8859-1");
        System.out.println();
        System.out.println("Обработанные данные из HTML-файла:");
        System.out.println("commandID: " + htmlFile.getElementById("commandID").text());
        System.out.println("command_object_id: " + htmlFile.getElementById("command_object_id").text());
        System.out.println("command_result: " + htmlFile.getElementById("command_result").text());
        System.out.println();
    }
}
