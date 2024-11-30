package Dist;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class House extends Agent {
    private boolean requestDenied = false;
    private int currentHour = 0;

    @Override
    protected void setup() {
        addBehaviour(new RequestElectricityBehaviour());
        addBehaviour(new HandleReplyBehaviour());
    }

    private class RequestElectricityBehaviour extends TickerBehaviour {
        public RequestElectricityBehaviour() {
            super(House.this, 1000);  // Request every second (1 second = 1 tick)
        }

        @Override
        protected void onTick() {
            if (!requestDenied && currentHour < 24) {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(getAID("LocalDistributor1"));  // Assuming LocalDistributor1 is the distributor
                msg.setContent("request-electricity-100");  // Requesting 100 units
                send(msg);
                log("Requested 100 units of electricity. Hour: " + currentHour);
            }
            currentHour++;
            if (currentHour == 24) {
                log("Simulation ended for the day.");
                stop();
            }
        }
    }

    private class HandleReplyBehaviour extends TickerBehaviour {
        public HandleReplyBehaviour() {
            super(House.this, 500);  // Check for reply every 500 ms
        }

        @Override
        protected void onTick() {
            ACLMessage reply = receive();
            if (reply != null) {
                String content = reply.getContent();
                if (content.contains("electricity-granted")) {
                    log("Electricity granted.");
                } else if (content.contains("electricity-denied")) {
                    log("Electricity request denied.");
                    requestDenied = true;  // Stop making requests if denied
                }
            } else {
                block();
            }
        }
    }

    private void log(String message) {
        // Log messages to the console
        System.out.println(getLocalName() + ": " + message);
    }
}
