package main.equipment;

import main.sensors.ClimatControl;

public class Conditioner {
    private int temperatureIncreaseID;
    private int temperatureDecreaseID;

    public Conditioner() { }

    public Conditioner(int temperatureIncreaseID, int temperatureDecreaseID) {
        this.temperatureIncreaseID = temperatureIncreaseID;
        this.temperatureDecreaseID = temperatureDecreaseID;
    }

    public int getTemperatureIncreaseID() {
        return temperatureIncreaseID;
    }

    public void setTemperatureIncreaseID(int temperatureIncreaseID) {
        this.temperatureIncreaseID = temperatureIncreaseID;
    }

    public int getTemperatureDecreaseID() {
        return temperatureDecreaseID;
    }

    public void setTemperatureDecreaseID(int temperatureDecreaseID) {
        this.temperatureDecreaseID = temperatureDecreaseID;
    }

    // Повысить температуру воздуха
    public ClimatControl increase(ClimatControl c, double increase) {
        c.setTemperature(c.getTemperature() + increase);
        if (c.getTemperature() > 50) {
            c.setTemperature(50);
        }
        return c;
    }

    // Понизить температуру воздуха
    public ClimatControl decrease(ClimatControl c, double decrease) {
        c.setTemperature(c.getTemperature() - decrease);
        if (c.getTemperature() < 0) {
            c.setTemperature(0);
        }
        return c;
    }
}
