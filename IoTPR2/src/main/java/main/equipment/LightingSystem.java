package main.equipment;

import main.sensors.Segment;

public class LightingSystem {
    private int increaseLightingID;
    private int decreaseLightingID;

    public LightingSystem(int increaseLightingID, int decreaseLightingID) {
        this.increaseLightingID = increaseLightingID;
        this.decreaseLightingID = decreaseLightingID;
    }

    public int getIncreaseLightingID() {
        return increaseLightingID;
    }

    public void setIncreaseLightingID(int increaseLightingID) {
        this.increaseLightingID = increaseLightingID;
    }

    public int getDecreaseLightingID() {
        return decreaseLightingID;
    }

    public void setDecreaseLightingID(int decreaseLightingID) {
        this.decreaseLightingID = decreaseLightingID;
    }

    // Повысить освешенность сегмента
    Segment increase(Segment s, double increase) {
        s.setSegment_lighting(s.getSegment_lighting() + increase);
        if (s.getSegment_lighting() > 100) {
            s.setSegment_lighting(100);
        }
        return s;
    }

    // Понизить освещенность сегмента
    Segment decrease(Segment s, double decrease) {
        s.setSegment_lighting(s.getSegment_lighting() + decrease);
        if (s.getSegment_lighting() < 0) {
            s.setSegment_lighting(0);
        }
        return s;
    }
}