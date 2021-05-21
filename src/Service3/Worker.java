package Service3;

import Service1.Ship;
import Service1.TimeFormater;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Worker {
    private final CountDownLatch latch;
    private final ConcurrentLinkedQueue<Ship> ships = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Ship> shipsUnloaded = new ConcurrentLinkedQueue<>();
    private final Ship.Cargo.cargoType type;
    private long fine;

    public static final int CRANE_PRICE = 30000;

    public Worker (int number, List<Ship> ships, Ship.Cargo.cargoType type) throws InterruptedException {
        this.ships.addAll(ships);
        this.type = type;
        fine = CRANE_PRICE * number;
        ExecutorService executorService = Executors.newFixedThreadPool(number);
        latch = new CountDownLatch(ships.size());
        for (int i = 0; i < number; i++) {
            executorService.submit(unload());
        }
        latch.await();
        executorService.shutdown();
    }

    public List<Ship> getShips () {
        int size = shipsUnloaded.size();
        return Arrays.asList(shipsUnloaded.toArray(new Ship[size]));
    }

    public long getFine () {
        return fine;
    }

    public Runnable unload () {
        Crane crane = new Crane(type);
        Runnable task = () -> {
            Ship first = null;
            Ship last;
            while (!ships.isEmpty()) {
                last = ships.poll();
                if (last != null) {
                    if (first != null) {
                        makeQueue(first, last);
                    }
                    crane.unloading(last);
                    last.setFine();
                    synchronized (Worker.class)
                    {
                        fine += last.getFine();
                    }
                    first = last;
                    shipsUnloaded.add(last);
                    latch.countDown();
                }
            }
        };
        return task;
    }

    public synchronized void makeQueue (Ship ship1, Ship ship2) {
        Timestamp arrivalTime = ship2.getArrivalTime();
        long millis = arrivalTime.getTime() - ship1.getRealEndTime().getTime();
        if (millis < 0) {
            millis *= -1;

            int minute = TimeFormater.getMinute(millis);
            int hour = TimeFormater.getHour(millis);
            int day = TimeFormater.getDay(millis);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(arrivalTime.getTime());
            calendar.add(Calendar.MINUTE, minute);
            calendar.add(Calendar.HOUR, hour);
            calendar.add(Calendar.DAY_OF_YEAR, day);
            arrivalTime = new Timestamp(calendar.getTime().getTime());
            ship2.setWaitTime(millis);
        }
        ship2.setRealBeginTime(arrivalTime);
    }
}
