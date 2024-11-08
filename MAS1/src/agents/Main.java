package agents;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Main {

    public static void main(String[] args) {
    	
    	//sniffer
    	//platform jade
    
        // Get the JADE runtime instance
        Runtime rt = Runtime.instance();

        // Create the profile for the main container
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");  // Default host (localhost)
        p.setParameter(Profile.MAIN_PORT, "1099");
       // p.setParameter(Profile.GUI,"ok");// Default port

        // Create a main container
        ContainerController mainContainer = rt.createMainContainer(p);

        try {
            // Deploy Agent_1
            AgentController agent1 = mainContainer.createNewAgent("Agent_1", Agent_1.class.getName(), null);
            agent1.start();

            // Deploy Agent_2
            AgentController agent2 = mainContainer.createNewAgent("Agent_2", Agent_2.class.getName(), null);
            agent2.start();
            
            

        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        
    }
}
