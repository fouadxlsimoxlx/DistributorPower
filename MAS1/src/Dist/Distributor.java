package Dist;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Distributor extends Agent {
    private String role;
    private boolean Status;
    private int nb_site_on;
    private int nb_site_off;
    

    protected void setup() {
        Object[] args = getArguments();
        role = (args != null && args.length > 0) ? (String) args[0] : "local";

        System.out.println(getLocalName() + ": Role is " + role);

        if (role.equals("central")) {
            addBehaviour(new CentralBehaviour());
        } else {
            addBehaviour(new LocalBehaviour());
        }
    }

    // Central distributor behaviour
    private class CentralBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                System.out.println(getLocalName() + " received: " + msg.getContent() + " from " + msg.getSender().getLocalName());
            } else {
                block();
            }
        }
    }

    // Local distributor behaviour
    private class LocalBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                System.out.println(getLocalName() + " received: " + msg.getContent() + " from " + msg.getSender().getLocalName());

                // Respond to Home or Company agents
                ACLMessage reply = msg.createReply();
                reply.setContent("Acknowledged by " + getLocalName());
                send(reply);
            } else {
                block();
            }
        }
    }
}
