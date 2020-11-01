package main.equipment;

import main.sensors.Segment;

public class IrrigationSystem {
    private int increaseSoilHumidityID;
    private int increaseSoilNitrogenousID;

    public IrrigationSystem(int increaseSoilHumidityID, int increaseSoilNitrogenousID) {
        this.increaseSoilHumidityID = increaseSoilHumidityID;
        this.increaseSoilNitrogenousID = increaseSoilNitrogenousID;
    }

    public int getIncreaseSoilHumidityID() {
        return increaseSoilHumidityID;
    }

    public void setIncreaseSoilHumidityID(int increaseSoilHumidityID) {
        this.increaseSoilHumidityID = increaseSoilHumidityID;
    }

    public int getIncreaseSoilNitrogenousID() {
        return increaseSoilNitrogenousID;
    }

    public void setIncreaseSoilNitrogenousID(int increaseSoilNitrogenousID) {
        this.increaseSoilNitrogenousID = increaseSoilNitrogenousID;
    }

    // Повысить влажность почвы
    Segment increaseSoilHumidity(Segment s, double increase) {
        s.setSoil_humidity(s.getSoil_humidity() + increase);
        if (s.getSoil_humidity() > 100) {
            s.setSoil_humidity(100);
        }
        return s;
    }

    // Повысить азотистость почвы
    Segment increaseSoilNitrogenous(Segment s, double increase) {
        s.setSoil_nitrogenous(s.getSoil_nitrogenous() + increase);
        s.setSoil_humidity(s.getSoil_humidity() + increase);
        if (s.getSoil_nitrogenous() > 100) {
            s.setSoil_nitrogenous(100);
        }
        if (s.getSoil_humidity() > 100) {
            s.setSoil_humidity(100);
        }
        return s;
    }
}