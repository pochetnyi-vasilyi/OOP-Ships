package Service2;

import Service1.Ship;
import Service1.ShipGenerator;
import Service3.Unloading;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.*;

public class JsonCreator {

    private List<Ship> ships;

    public void runService1 (int number) {
        ShipGenerator ship = new ShipGenerator(number);
        ships = ship.getShips();
        try {
            write();
        }  catch (InputMismatchException e) {
            System.out.println("It must be a number!");
        }

        writeService1();
    }

    public void writeService1 () {
        JSONArray shipArray = new JSONArray();
        for (Ship ship : ships) {
            JSONObject schedule = new JSONObject();
            schedule.put("Name of ship", ship.getName());
            schedule.put("Arrival date", ship.getEstimatedBeginTime().toString());
            schedule.put("Departure date", ship.getEstimatedEndTime().toString());
            schedule.put("Type of cargo", ship.getNameType());
            schedule.put("Weight of cargo", ship.getCargoWeight());
            shipArray.add(schedule);
        }
        JSONObject out = new JSONObject();
        out.put("Ships", shipArray);
        try (FileWriter file = new FileWriter("src\\resource\\schedule.JSON")) {
            file.write(out.toString());
            System.out.println("Service 1 was written in JSON");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Ship> readService1 () {
        JSONParser parser = new JSONParser();
        try {
            Object schedule =  parser.parse(new FileReader("src\\resource\\schedule.JSON"));
            JSONArray solutions = new JSONArray();
            List<Ship> ships = new ArrayList<>();
            Iterator iterator = solutions.iterator();
            while (iterator.hasNext()) {
                JSONObject ship = new JSONObject();
                String name = (String) ship.get("Name of ship");
                Timestamp arrivalDate = Timestamp.valueOf((String) ship.get("Arrival date"));
                String type = (String) ship.get("Type of cargo");
                int number = (int) ship.get("Weight of cargo");
                ships.add(new Ship(name, arrivalDate, new Ship.Cargo(type, number)));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ships;
    }

    public void write () throws InputMismatchException {
        while (true) {
            System.out.println("Add new ship? (Y/N only)\n");
            Scanner in = new Scanner(System.in);
            String answer = in.next();

            while (!answer.equals("Y") && !answer.equals("N")) {
                System.out.println("Add new ship? (Y/N only)\n");
                answer = in.next();
            }
            if (answer.equals("N")) {
                return;
            }
            System.out.print("Enter name of ship: ");
            answer = in.next();
            while (answer.isEmpty())
            {
                System.out.print("Name must not be empty!");
                answer = in.next();
            }
            System.out.print("Enter type of cargo (B for bulk, L for liquid, C for container): ");
            String type = in.next();
            while (!type.equals("B") && !type.equals("L") && !type.equals("C")) {
                System.out.print("Enter type of cargo (B for bulk, L for liquid, C for container): ");
                type = in.next();
            }

            switch (type) {
                case "B" -> type = "bulk";
                case "L" -> type = "liquid";
                case "C" -> type = "container";
            }

            System.out.print("Enter weight of cargo: ");
            int number = in.nextInt();
            while (number < 1) {
                System.out.println("Weight must be more than 0!");
                number = in.nextInt();
            }

            System.out.print("Enter the day of begin: ");
            int day = in.nextInt();
            while ((day < 1) || (day > 30)) {
                System.out.println("Day must be in [1; 30] range!");
                day = in.nextInt();
            }
            System.out.print("Enter the hour of begin: ");
            int hour = in.nextInt();
            while ((hour < 0) || (hour > 23)) {
                System.out.println("Hour must be in [0; 23] range!");
                hour = in.nextInt();
            }
            System.out.print("Enter the minute of begin: ");
            int minute = in.nextInt();
            while ((minute < 0) || (minute > 60)) {
                System.out.println("Minute must be in [0; 59] range!");
                minute = in.nextInt();
            }
            int year = GregorianCalendar.getInstance().get(Calendar.YEAR);
            int month = GregorianCalendar.getInstance().get(Calendar.MONTH) + 1;
            String time = "" + year + "-" + month +"-" + day + " " + hour + ":" + minute + ":" + "00.000";
            Timestamp timestamp = Timestamp.valueOf(time);
            Ship ship = new Ship(answer, timestamp, new Ship.Cargo(type, number));
            ships.add(ship);
            System.out.println("Ship has been successfully added.");
        }
    }

    public void writeService3 (Unloading unloading) {
        List<Ship> ships = unloading.getList();
        JSONObject report = new JSONObject();
        report.put("Fine", unloading.getFine());
        report.put("Mean delay", unloading.getMeanDelay());
        report.put("Max delay", unloading.getMaxDelay());
        report.put("Number of container cranes", unloading.getNumberOfContainerCranes());
        report.put("Number of liquid cranes", unloading.getNumberOfLiquidCranes());
        report.put("Number of bulk cranes", unloading.getNumberOfBulkCranes());
        report.put("Number of ships", unloading.getNumberOfShips());
        report.put("Waiting time", unloading.getWaitTime());
        JSONArray shipsArray = new JSONArray();
        for (Ship ship : ships) {
            JSONObject json = new JSONObject();
            json.put("Name of ship", ship.getName());
            json.put("Estimated arrival date", ship.getEstimatedBeginTime().toString());
            json.put("Departure date", ship.getEstimatedEndTime().toString());
            json.put("Type of cargo", ship.getNameType());
            json.put("Weight of cargo", ship.getCargoWeight());
            json.put("Real arrival date", ship.getArrivalTime().toString());
            json.put("Begin of unloading time", ship.getRealBeginTime().toString());
            json.put("End of unloading time", ship.getRealEndTime().toString());
            json.put("Waiting time", ship.getWaitString());
            json.put("Fine", ship.getFine());
            shipsArray.add(json);
        }
        JSONObject out = new JSONObject();
        out.put("Report", report);
        out.put("Ships", shipsArray);
        try (FileWriter file = new FileWriter("src\\resource\\report.JSON")) {
            file.write(out.toString());
            System.out.println("Service 3 was written in JSON");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}