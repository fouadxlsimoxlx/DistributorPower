package Distributers;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Main {

    public static void main(String[] args) {
    	Frame frame = Frame.getInstance();
        frame.setVisible(true);
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.MAIN_PORT, "1099");
        //profile.setParameter(Profile.GUI, "true");  // Enable JADE GUI

        ContainerController mainContainer = runtime.createMainContainer(profile);
        try {
            // Create and start the sniffer agent
            //AgentController sniffer = mainContainer.createNewAgent("Sniffer", "jade.tools.sniffer.Sniffer", null);
            //sniffer.start();

            // Create and start the other agents
            AgentController DP = mainContainer.createNewAgent("DP", "Distributers.Distributer", null);
            AgentController DL1 = mainContainer.createNewAgent("DL1", "Distributers.Distributer", null);
            AgentController DL2 = mainContainer.createNewAgent("DL2", "Distributers.Distributer", null);
            AgentController H1 = mainContainer.createNewAgent("H1", "Distributers.Home", null);
            AgentController H2 = mainContainer.createNewAgent("H2", "Distributers.Home", null);
            AgentController C1 = mainContainer.createNewAgent("C1", "Distributers.Company", null);
            AgentController C2 = mainContainer.createNewAgent("C2", "Distributers.Company", null);

            DP.start();
            DL1.start();
            DL2.start();
            H1.start();
            H2.start();
            C1.start();
            C2.start();

            // Automatically set up the Sniffer to monitor all agents
            //sniffer.putO2AObject("DP,DL1,DL2,H1,H2,C1,C2", AgentController.ASYNC); // Add all agent names to monitor

        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        runSimulation();
    }
    private static void runSimulation() {
        while (SimulationTime.getCurrentHour() < 24) {
            // Sleep or wait for the next simulation tick (e.g., 1 second in real time per 1 minute in simulation time)
            try {
                Thread.sleep(3000);  // This simulates the passage of 1 minute in real time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Increment global simulation time by 1 hour per tick (this will affect all agents)
            Frame frame = Frame.getInstance();
            
            SimulationTime.incrementTime();
            System.out.println("::-------------- Simulation Time: " + SimulationTime.getCurrentHour() + " hours --------------::");
            frame.calc();
            frame.setTimeText(""+SimulationTime.getCurrentHour());
        }

        // End the simulation after 24 hours
        System.out.println("Simulation completed!");
        System.exit(0);
    }
}
