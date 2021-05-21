package Service1;

import java.sql.Timestamp;
import java.util.Calendar;


public class Ship {

    private String name;
    private Timestamp estimatedBeginTime;
    private Timestamp realBeginTime;
    private Timestamp estimatedEndTime;
    private Timestamp realEndTime;
    private Timestamp arrivalTime;
    private long waitTime;
    private long fine;
    private int delay;
    private final Cargo cargo;

    public static final int FINE_PER_HOUR = 100;

    public Ship (String name, Timestamp timeBegin, Cargo cargo) {
        this.name = name;
        this.cargo = cargo;
        this.estimatedBeginTime = timeBegin;
        this.estimatedEndTime = stay(timeBegin);
        this.fine = 0;
        this.waitTime = 0;
    }

    public String getName () {
        return name;
    }

    public void setName (String newName) {
        this.name = newName;
    }

    public Timestamp getEstimatedBeginTime () {
        return estimatedBeginTime;
    }

    public void setEstimatedBeginTime (Timestamp newBeginTime) {
        this.estimatedBeginTime = newBeginTime;
    }

    public Timestamp getRealBeginTime () {
        return realBeginTime;
    }

    public void setRealBeginTime (Timestamp newBeginTime) {
        this.realBeginTime = newBeginTime;
    }

    public Timestamp getEstimatedEndTime () {
        return estimatedEndTime;
    }

    public void setEstimatedEndTime (Timestamp newEndTime) {
        this.estimatedEndTime = newEndTime;
    }

    public Timestamp getRealEndTime () {
        return realEndTime;
    }

    public void setRealEndTime (Timestamp newRealEndTime) {
        this.realEndTime = newRealEndTime;
    }

    public Timestamp getArrivalTime () {
        return arrivalTime;
    }

    public void setArrivalTime (Timestamp newArrivalTime) {
        this.arrivalTime = newArrivalTime;
    }

    public long getWaitTime () {
        return waitTime;
    }

    public void setWaitTime (long newWaitTime) {
        this.waitTime = newWaitTime;
    }

    public long getFine () {
        return fine;
    }

    public void setFine () {
        long millis = estimatedEndTime.getTime() - realEndTime.getTime();
        if (millis < 0) {
            millis *= -1;
            fine = TimeFormater.getHours(millis) * FINE_PER_HOUR;
        }
    }

    public int getDelay () {
        return delay;
    }

    public void setDelay (int newDelay) {
        this.delay = newDelay;
    }

    public Timestamp stay (Timestamp ts) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ts.getTime());
        int timeInHours = cargo.type.getHour();
        calendar.add(Calendar.HOUR, cargo.weight * timeInHours);
        ts = new Timestamp(calendar.getTime().getTime());
        return ts;
    }

    public String toString () {
        return "Ship name: " + name + "\nType of cargo: " + cargo.name + "\nWeight of cargo: " + cargo.weight
                + "\nArrival time: " + estimatedBeginTime + "\nDeparture time: " + estimatedEndTime;
    }

    public String getUnloadingTime () {
        int timeInHours = cargo.type.getHour();
        int time = cargo.weight * timeInHours;
        int day = time / TimeFormater.HOURS_IN_DAY;
        int hour = time % TimeFormater.HOURS_IN_DAY;
        return String.format("%02d:%02d:00", day, hour);
    }

    public String getNameType () {
        return cargo.name;
    }

    public int getCargoWeight () {
        return cargo.weight;
    }

    public Cargo.cargoType getCargoType () {
        return cargo.type;
    }

    public String getWaitString () {
        long time = waitTime;
        int minute = TimeFormater.getMinute(time);
        int hour = TimeFormater.getHour(time);
        int day = TimeFormater.getDay(time);
        return String.format("%02d:%02d:%02d", day, hour, minute);
    }

    public static class Cargo {

        private final String name;
        private final int weight;
        private cargoType type;

        public Cargo (String newName, int newWeight) {
            this.name = newName;
            this.weight = newWeight;
            setType_(newName);
        }

        public enum cargoType {
            BULK(2),
            LIQUID(3),
            CONTAINER(1);

            private final int hour;

            cargoType(int i) {
                hour = i;
            }

            public int getHour () {
                return hour;
            }
        }

        public void setType_ (String typeName) {
            switch (typeName) {
                case "bulk" -> type = cargoType.BULK;
                case "liquid" -> type = cargoType.LIQUID;
                case "container" -> type = cargoType.CONTAINER;
            }
        }
    }
}
