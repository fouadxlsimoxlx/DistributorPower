package Dist;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {
    public static void main(String[] args) {
        // Create JADE runtime instance
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.GUI, "true");

        // Create the main container
        AgentContainer mainContainer = runtime.createMainContainer(profile);

        try {
            // Create Central Distributor agent
            AgentController centralDistributor = mainContainer.createNewAgent("Central", "agents.Distributor", new Object[]{"central"});
            centralDistributor.start();

            // Create Local Distributor agents
            AgentController local1 = mainContainer.createNewAgent("Local1", "agents.Distributor", new Object[]{"local"});
            local1.start();

            AgentController local2 = mainContainer.createNewAgent("Local2", "agents.Distributor", new Object[]{"local"});
            local2.start();

            // Create Home and Company agents under Local1
            AgentController home1 = mainContainer.createNewAgent("Home1", "agents.Home", new Object[]{"Local1"});
            home1.start();

            AgentController company1 = mainContainer.createNewAgent("Company1", "agents.Company", new Object[]{"Local1"});
            company1.start();

            // Create Home and Company agents under Local2
            AgentController home2 = mainContainer.createNewAgent("Home2", "agents.Home", new Object[]{"Local2"});
            home2.start();

            AgentController company2 = mainContainer.createNewAgent("Company2", "agents.Company", new Object[]{"Local2"});
            company2.start();

        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
