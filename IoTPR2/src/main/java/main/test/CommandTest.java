package main.test;

import htmlflow.HtmlView;
import htmlflow.StaticHtml;
import main.CommandResponse;
import main.equipment.AirHumidifier;
import main.equipment.Conditioner;
import main.sensors.ClimatControl;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.xembly.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// Класс тестирования команд
public class CommandTest {
    public static int command_object_id = 0;
    public static int command_id = 0;
    public static AirHumidifier airHumidifier = new AirHumidifier(101, 102); // Система увлажнения воздуха
    public static Conditioner conditioner = new Conditioner(201, 202); // Система кондиционирования

    public static void main(String[] args)  throws Exception {

        // Инициализируем класс выполнения команд оборудованию
        CommandResponse commandResponse = new CommandResponse();
        ClimatControl climatControl = new ClimatControl(999999, 1);

        outputClimatControl(climatControl);

        // Загрузка и чтение json-файла с командой:
        readJsonExecuteCommand();
        // Передача команды на выполнение
        climatControl = execute("json", climatControl);

        // Загрузка и чтение xml-файла с командой:
        readXmlExecuteCommand();
        // Передача команды на выполнение
        climatControl = execute("xml", climatControl);

        // Загрузка и чтение html-файла с командой:
        readHtmlExecuteCommand();
        // Передача команды на выполнение
        climatControl = execute("html", climatControl);
    }

    public static void outputClimatControl(ClimatControl climatControl) {
        // Обработаем климат-контроль оранжереи
        System.out.println("Данные датчика климат-контроля оранжереи: ");
        System.out.println("Идентификатор: " + climatControl.getId());
        System.out.println("Статус датчика: " + climatControl.getStatus());
        System.out.println("Температура воздуха: " + climatControl.getTemperature());
        System.out.println("Влажность воздуха: " + climatControl.getAir_humidity());
        System.out.println();
    }

    public static ClimatControl execute(String responseType, ClimatControl climatControl) throws Exception {
        if (command_object_id == climatControl.getId()) {
            if (command_id == 101 || command_id == 102 || command_id == 201 || command_id == 202) {
                System.out.println("Результат выполнения команды:");
                climatControl = executeCommand(command_id, climatControl);
                switch (responseType) {
                    case "xml":
                        getXMLCommandResponse(command_id, command_object_id, 1);
                        outputClimatControl(climatControl);
                        break;
                    case "html":
                        getHTMLCommandResponse(command_id, command_object_id, 1);
                        outputClimatControl(climatControl);
                        break;
                    case "json":
                        getJSONCommandsResponse(command_id, command_object_id, 1);
                        outputClimatControl(climatControl);
                        break;
                    default:
                        System.out.println("Неизвестная ошибка!");
                        break;
                }
            } else {
                System.out.println("Некорректный идентификатор комманды!");
            }
        } else {
            System.out.println("Некорректный идентификатор устройства!");
        }
        System.out.println();
        return climatControl;
    }

    public static ClimatControl executeCommand(int command_id, ClimatControl climatControl) {
        switch (command_id) {
            case 101:
                climatControl = airHumidifier.increase(climatControl, 5);
                break;
            case 102:
                climatControl = airHumidifier.decrease(climatControl,5);
                break;
            case 201:
                climatControl = conditioner.increase(climatControl,5);
                break;
            case 202:
                climatControl = conditioner.decrease(climatControl,5);
                break;
            default:
                break;
        }
        return climatControl;
    }

    // Метод чтения json-команды
    public static void readJsonExecuteCommand() throws Exception {
        File file = new File("test\\toExecute.json");
        System.out.println("Чтение JSON-файла команды");
        System.out.println("Содержание JSON-файла с командой: ");
        Scanner jsonReader = new Scanner(file);
        while (jsonReader.hasNextLine()) {
            String data = jsonReader.nextLine();
            System.out.println(data);
            System.out.println();
        }
        jsonReader.close();
        String jsonContent = new String(Files.readAllBytes(Paths.get(file.getPath())));
        JSONObject jsonData = new JSONObject(jsonContent);
        System.out.println("Обработанные данные из JSON-файла:");
        System.out.println("Для устройства с идентификатором: " + jsonData.get("id"));
        command_object_id = Integer.parseInt(jsonData.get("id").toString());
        System.out.println("Выполни команду с кодом: " + jsonData.get("command"));
        command_id = Integer.parseInt(jsonData.get("command").toString());
        System.out.println();
    }

    // Метод чтения xml-команды
    public static void readXmlExecuteCommand() throws Exception {
        File file = new File("test\\toExecute.xml");
        System.out.println("Чтение XML-файла команды");
        System.out.println("Содержание XML-файла с командой: ");
        Scanner xmlReader = new Scanner(file);
        while (xmlReader.hasNextLine()) {
            String data = xmlReader.nextLine();
            System.out.println(data);
            System.out.println();
        }
        xmlReader.close();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        System.out.println("Обработанные данные из XML-файла:");
        System.out.println("Для устройства с идентификатором: " + doc.getElementsByTagName("id").item(0).getTextContent());
        command_object_id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
        System.out.println("Выполни команду с кодом: " + doc.getElementsByTagName("command").item(0).getTextContent());
        command_id = Integer.parseInt(doc.getElementsByTagName("command").item(0).getTextContent());
        System.out.println("Команда передана на выполнение");
        System.out.println();
    }

    public static void readHtmlExecuteCommand() throws Exception {
        System.out.println("Содержание HTML-файла с командой: ");
        System.out.println();
        org.jsoup.nodes.Document htmlFile = Jsoup.parse(new File("test\\toExecute.html"), "ISO-8859-1");
        System.out.println();
        System.out.println("Обработанные данные из HTML-файла:");
        System.out.println("Для устройства с идентификатором " + htmlFile.getElementById("id").text());
        command_object_id = Integer.parseInt(htmlFile.getElementById("id").text());
        System.out.println("Выполни команду с кодом: " + htmlFile.getElementById("command").text());
        command_id = Integer.parseInt(htmlFile.getElementById("command").text());
        System.out.println();
    }

    public static void getJSONCommandsResponse(int commandID, int command_object_id, int command_result) throws IOException {
        System.out.println("Запись результата выполнения команды в JSON-файл");
        JSONObject json = new JSONObject();
        json.put("commandID", commandID);
        json.put("command_object_id", command_object_id);
        json.put("command_result", command_result);
        File myJson = new File("test\\command_" + commandID + "_to_" + command_object_id + ".json");
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

    public static void getXMLCommandResponse(int commandID, int command_object_id, int command_result) throws IOException, ImpossibleModificationException, ParserConfigurationException, SAXException {
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
        File myXml = new File("test\\command_" + commandID + "_to_" + command_object_id + ".xml");
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
        Document doc = dBuilder.parse(new File("test\\command_" + commandID + "_to_" + command_object_id + ".xml"));
        System.out.println("Обработанные данные из XML-файла:");
        System.out.println("commandID: " + doc.getElementsByTagName("commandID").item(0).getTextContent());
        System.out.println("command_object_id: " + doc.getElementsByTagName("command_object_id").item(0).getTextContent());
        System.out.println("command_result: " + doc.getElementsByTagName("command_result").item(0).getTextContent());
        System.out.println();
    }

     static void getHTMLCommandResponse(int commandID, int command_object_id, int command_result) throws IOException {
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
        view.setPrintStream(new PrintStream(new FileOutputStream("test\\command_" + commandID + "_to_" + command_object_id + ".html"))).write();
        org.jsoup.nodes.Document htmlFile = Jsoup.parse(new File("test\\command_" + commandID + "_to_" + command_object_id + ".html"), "ISO-8859-1");
        System.out.println();
        System.out.println("Обработанные данные из HTML-файла:");
        System.out.println("commandID: " + htmlFile.getElementById("commandID").text());
        System.out.println("command_object_id: " + htmlFile.getElementById("command_object_id").text());
        System.out.println("command_result: " + htmlFile.getElementById("command_result").text());
        System.out.println();
    }
}
