package main;

import main.equipment.AirHumidifier;
import main.equipment.Conditioner;
import main.equipment.IrrigationSystem;
import main.equipment.LightingSystem;
import main.sensors.ClimatControl;
import main.sensors.Segment;
import org.xembly.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class Application {

    public static void main(String[] args) throws IOException, ImpossibleModificationException, ParserConfigurationException, SAXException {
        // Инициализируем класс выполнения команд оборудованию
        CommandResponse commandResponse = new CommandResponse();
        // Инициализируем симуляцию оборудования оранжереи
        AirHumidifier airHumidifier = new AirHumidifier(101, 102); // Система увлажнения воздуха
        Conditioner conditioner = new Conditioner(201, 202); // Система кондиционирования
        IrrigationSystem irrigationSystem = new IrrigationSystem(301, 302); // Система ирригации
        LightingSystem lightingSystem = new LightingSystem(401, 402); // Система освещения сегментов

        // Инициализируем климат-контроль оранжереи
        Integer id = Math.abs(new Random().nextInt());
        ClimatControl climatControl = new ClimatControl(id, 1);
        // Инициализируем сегменты оранжереи
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            segments.add(new Segment(Math.abs(new Random().nextInt()), 1));
        }

        // Обработаем климат-контроль оранжереи
        System.out.println("Данные датчика климат-контроля оранжереи");
        System.out.println("Идентификатор: " + climatControl.getId());
        System.out.println("Статус датчика: " + climatControl.getStatus());
        System.out.println("Температура воздуха: " + climatControl.getTemperature());
        System.out.println("Влажность воздуха: " + climatControl.getAir_humidity());
        System.out.println();
        climatControl.climatControlToJson();
        climatControl.climatControlToXML();
        climatControl.climatControlToHTML();

        // Обработаем датчик состояния сегмента оранжереи
        System.out.println("Данные датчика сегмента оранжереи");
        System.out.println("Идентификатор: " + segments.get(0).getId());
        System.out.println("Статус датчика: " + segments.get(0).getStatus());
        System.out.println("Влажность почвы: " + segments.get(0).getSoil_humidity());
        System.out.println("Освещенность: " + segments.get(0).getSegment_lighting());
        System.out.println("Насыщенность азотом: " + segments.get(0).getSoil_nitrogenous());
        System.out.println("Засоленность: " + segments.get(0).getSoil_salinity());
        System.out.println();
        segments.get(0).segmentToJSON();
        segments.get(0).segmentToXML();
        segments.get(0).segmentToHTML();

        // Приведем пример использования команды обордуованию оранжереи
        climatControl = airHumidifier.increase(climatControl, 10);
        int result;
        if (climatControl != null) {
            result = 1;
        } else {
            result = 0;
        }
        System.out.println("Данные о выполнении команды оборудованию оранжереи: ");
        System.out.println("Идентификатор команды: " + airHumidifier.getHumidityIncreaseID());
        System.out.println("Идентификатор объекта команды: " + climatControl.getId());
        System.out.println("Результат выполнения команды: " + result);
        System.out.println();
        commandResponse.getJSONCommandsResponse(airHumidifier.getHumidityIncreaseID(), climatControl.getId(), result);
        commandResponse.getXMLCommandResponse(airHumidifier.getHumidityIncreaseID(), climatControl.getId(), result);
        commandResponse.getHTMLCommandResponse(airHumidifier.getHumidityIncreaseID(), climatControl.getId(), result);
    }
}
