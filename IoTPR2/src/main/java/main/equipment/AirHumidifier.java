package main.equipment;

import main.sensors.ClimatControl;

public class AirHumidifier {
    private int humidityIncreaseID;
    private int humidityDecreaseID;

    public AirHumidifier(int humidityIncreaseID, int humidityDecreaseID) {
        this.humidityIncreaseID = humidityIncreaseID;
        this.humidityDecreaseID = humidityDecreaseID;
    }

    public int getHumidityIncreaseID() {
        return humidityIncreaseID;
    }

    public void setHumidityIncreaseID(int humidityIncreaseID) {
        this.humidityIncreaseID = humidityIncreaseID;
    }

    public int getHumidityDecreaseID() {
        return humidityDecreaseID;
    }

    public void setHumidityDecreaseID(int humidityDecreaseID) {
        this.humidityDecreaseID = humidityDecreaseID;
    }

    // Повысить влажность воздуха
    public ClimatControl increase(ClimatControl c, double increase) {
        c.setAir_humidity(c.getAir_humidity() + increase);
        if (c.getAir_humidity() > 100) {
            c.setAir_humidity(100);
        }
        return c;
    }

    // Понизить влажность воздуха
    public ClimatControl decrease(ClimatControl c, double decrease) {
        c.setAir_humidity(c.getAir_humidity() - decrease);
        if (c.getAir_humidity() < 0) {
            c.setAir_humidity(0);
        }
        return c;
    }
}