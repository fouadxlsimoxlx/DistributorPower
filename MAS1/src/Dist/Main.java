package Dist;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {
    public static void main(String[] args) {
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.MAIN_PORT, "1099");
        profile.setParameter(Profile.GUI, "true");  // Disable GUI
        AgentContainer mainContainer = runtime.createMainContainer(profile);

        try {
            // Create a Central Distributor
            AgentController centralDistributor = mainContainer.createNewAgent("CentralDistributor",
                    "Dist.Distributor", new Object[]{"central", 1000});
            centralDistributor.start();

            // Create Local Distributors
            AgentController localDistributor1 = mainContainer.createNewAgent("LocalDistributor1",
                    "Dist.Distributor", new Object[]{"local", 500});
            localDistributor1.start();

            AgentController localDistributor2 = mainContainer.createNewAgent("LocalDistributor2",
                    "Dist.Distributor", new Object[]{"local", 400});
            localDistributor2.start();

            // Create House Agents
            AgentController house1 = mainContainer.createNewAgent("House1", "Dist.House", null);
            house1.start();

            AgentController house2 = mainContainer.createNewAgent("House2", "Dist.House", null);
            house2.start();

            // Create Company Agents
            AgentController company1 = mainContainer.createNewAgent("Company1", "Dist.Company", null);
            company1.start();

            AgentController company2 = mainContainer.createNewAgent("Company2", "Dist.Company", null);
            company2.start();

        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
