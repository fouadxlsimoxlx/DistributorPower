package Dist;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class Company extends Agent {
    private int currentHour = 0;

    @Override
    protected void setup() {
        addBehaviour(new RequestElectricityBehaviour());
        addBehaviour(new HandleReplyBehaviour());
    }

    private class RequestElectricityBehaviour extends TickerBehaviour {
        public RequestElectricityBehaviour() {
            super(Company.this, 1000);  // Request every second
        }

        @Override
        protected void onTick() {
            if (currentHour >= 8 && currentHour <= 16) {  // Company works from 8 AM to 4 PM
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(getAID("LocalDistributor1"));  // Assuming LocalDistributor1 is the distributor
                msg.setContent("request-electricity-150");  // Requesting 150 units
                send(msg);
                log("Requested 150 units of electricity. Hour: " + currentHour);
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
            super(Company.this, 500);  // Check for reply every 500 ms
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
