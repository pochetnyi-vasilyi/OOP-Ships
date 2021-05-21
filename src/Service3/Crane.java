package Service3;

import Service1.*;

import java.sql.Timestamp;
import java.util.Calendar;

class Crane {

    private final int powerPerHour;

    public Crane (Ship.Cargo.cargoType type) {
        powerPerHour = type.getHour();
    }

    public void unloading (Ship ship) {
        Timestamp ts = ship.getRealBeginTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ts.getTime());
        int weight = ship.getCargoWeight();
        while (weight != 0) {
            calendar.add(Calendar.HOUR, powerPerHour);
            weight--;
        }
        int minuteRand = ship.getDelay();
        calendar.add(Calendar.MINUTE, minuteRand);
        ts = new Timestamp(calendar.getTime().getTime());
        ship.setRealEndTime(ts);
    }

}
