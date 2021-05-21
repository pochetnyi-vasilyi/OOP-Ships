package Service3;

import Service1.Ship;

import java.sql.Timestamp;
import java.util.*;

import static Service1.TimeFormater.getMinutesInWeek;

public class Seaport {
    private final List<Ship> bulk;
    private final List<Ship> liquid;
    private final List<Ship> container;

    private static final int MAX_UNLOADING_DELAY_TIME = 1440;

    public Seaport (List<Ship> ships) {
        bulk = new ArrayList<>();
        liquid = new ArrayList<>();
        container = new ArrayList<>();
        read(ships);
        bulk.sort(Comparator.comparing(Ship::getArrivalTime));
        liquid.sort(Comparator.comparing(Ship::getArrivalTime));
        container.sort(Comparator.comparing(Ship::getArrivalTime));
    }

    public List<Ship> getBulk () {
        return bulk;
    }

    public List<Ship> getContainer () {
        return container;
    }

    public List<Ship> getLiquid () {
        return liquid;
    }

    private void read (List<Ship> ships) {
        for (Ship ship : ships) {
            randomTime(ship);
            addToQueue(ship);
        }
    }

    private void addToQueue (Ship ship) {
        switch (ship.getCargoType()) {
            case BULK -> bulk.add(ship);
            case CONTAINER -> container.add(ship);
            case LIQUID -> liquid.add(ship);
        }
    }

    private void randomTime (Ship ship) {
        Calendar calendar = Calendar.getInstance();
        Timestamp ts = ship.getEstimatedBeginTime();
        calendar.setTimeInMillis(ts.getTime());
        int minutes = (int) (Math.random() * (getMinutesInWeek() * 2 + 1)) - getMinutesInWeek();
        calendar.add(Calendar.MINUTE, minutes);
        ts = new Timestamp(calendar.getTime().getTime());
        ship.setArrivalTime(ts);
        ship.setRealBeginTime(ts);
        ship.setDelay((int) (Math.random() * MAX_UNLOADING_DELAY_TIME));
    }
}
