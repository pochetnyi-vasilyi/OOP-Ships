package Main;

import Service2.JsonCreator;
import Service3.Unloading;

public class Main {
    private static final int NUMBER_OF_SHIPS = 100;
    public static void main(String[] args) throws InterruptedException {
        JsonCreator jsonCreator = new JsonCreator();
        jsonCreator.runService1(NUMBER_OF_SHIPS);
        Unloading unloading = new Unloading(jsonCreator.readService1());
        jsonCreator.writeService3(unloading);
        unloading.report();
    }
}