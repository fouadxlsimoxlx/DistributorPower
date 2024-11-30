package Distributers;

public class SimulationTime {
    private static int currentHour = 0;  // Shared simulation time

    public static int getCurrentHour() {
        return currentHour;
    }

    public static void incrementTime() {
        currentHour++;
        /*if (currentHour >= 24) {
            currentHour = 0;  // Reset after 24 hours
        }*/
    }
}
