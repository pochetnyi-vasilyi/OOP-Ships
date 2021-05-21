package Service3;

import Service1.Ship;
import Service1.TimeFormater;

import java.util.ArrayList;
import java.util.List;

public class Unloading {

    private List<Ship> bulkList;
    private List<Ship> liquidList;
    private List<Ship> containerList;
    private int bulkCraneCounter = 0;
    private int liquidCraneCounter = 0;
    private int containerCraneCounter = 0;
    private int allShips = 0;
    private long time = 0;
    private long fine;
    private int maxDelay = 0;
    private int completeDelay = 0;

    public Unloading (List<Ship> ships) throws InterruptedException {
        Seaport port = new Seaport(ships);
        bulkList = port.getBulk();
        liquidList = port.getLiquid();
        containerList = port.getContainer();
        bulkList = working("Bulk", Ship.Cargo.cargoType.BULK, bulkList);
        liquidList = working("Liquid", Ship.Cargo.cargoType.LIQUID, liquidList);
        containerList = working("Container", Ship.Cargo.cargoType.CONTAINER, containerList);
        allShips += bulkList.size() + liquidList.size() + containerList.size();
    }

    public int getNumberOfBulkCranes () {
        return bulkCraneCounter;
    }

    public int getNumberOfLiquidCranes () {
        return liquidCraneCounter;
    }

    public int getNumberOfContainerCranes () {
        return containerCraneCounter;
    }

    public int getNumberOfShips () {
        return allShips;
    }

    public String getWaitTime () {
        List<Ship> ships = getList();
        for (Ship ship : ships) {
            time += ship.getWaitTime();
        }
        time /= ships.size();
        int minute = TimeFormater.getMinute(time);
        int hour = TimeFormater.getHour(time);
        int day = TimeFormater.getDay(time);
        return String.format("%02d:%02d:%02d", day, hour, minute);
    }

    public long getFine () {
        return fine;
    }

    public int getMaxDelay () {
        List<Ship> ships = getList();
        for (Ship ship : ships) {
            int currentDelay = ship.getDelay();
            if (currentDelay > maxDelay) {
                maxDelay = currentDelay;
            }
        }
        return maxDelay;
    }

    public int getMeanDelay () {
        List<Ship> ships = getList();
        for (Ship ship : ships) {
            completeDelay += ship.getDelay();
        }
        int meanDelay = completeDelay / ships.size();
        return meanDelay;
    }

    private void setNumberOfCargo (Ship.Cargo.cargoType type, int number) {
        switch (type) {
            case LIQUID -> liquidCraneCounter = number;
            case BULK -> bulkCraneCounter = number;
            case CONTAINER -> containerCraneCounter = number;
        }
    }

    public List<Ship> working (String name, Ship.Cargo.cargoType kind, List<Ship> ships) throws InterruptedException {
        long fine = 0;
        List<Ship> shipArrayList = new ArrayList<>();
        Worker worker = null;
        System.out.println(name);
        for (int i = 1; i < ships.size(); i++) {
            worker = new Worker(i, ships, kind);
            System.out.println("Crane " + i + " Fine " + worker.getFine());
            if ((i != 1) && (fine < worker.getFine())) {
                this.fine += worker.getFine();
                setNumberOfCargo(kind, i - 1);
                return shipArrayList;
            }
            fine = worker.getFine();
            shipArrayList = worker.getShips();
        }
        setNumberOfCargo(kind, ships.size());
        return worker.getShips();
    }

    public List<Ship> getList () {
        List<Ship> all = new ArrayList<>();
        all.addAll(0, bulkList);
        all.addAll(bulkList.size(), liquidList);
        all.addAll(liquidList.size(), containerList);
        return all;
    }

    public void report () {
        List<Ship> ships = getList();
        for (Ship ship : ships) {
            System.out.println(System.lineSeparator() + "Name of ship: " + ship.getName() + System.lineSeparator()
                    + "Arrival time: " + ship.getArrivalTime() + System.lineSeparator() + "Waiting time: "
                    + ship.getWaitString() + System.lineSeparator() + "Real begin time: " + ship.getRealBeginTime()
                    + System.lineSeparator() + "Unloading time: " + ship.getUnloadingTime());
        }
        System.out.println(System.lineSeparator() + System.lineSeparator() + "REPORT:" + System.lineSeparator()
                + "Number of ships: " + ships.size() + System.lineSeparator() + "Mean delay: " + getMeanDelay()
                + System.lineSeparator() + "Max delay: " + getMaxDelay() + System.lineSeparator() + "Number of loose cranes: "
                + bulkCraneCounter + System.lineSeparator() + "Number of liquid cranes: " + liquidCraneCounter
                + System.lineSeparator() + "Number of container cranes: " + containerCraneCounter + System.lineSeparator()
                + "Fine: " + fine + System.lineSeparator() + "Average waiting time: " + getWaitTime());
    }
}
