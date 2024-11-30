package Dist;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Distributor extends Agent {
    private String type;
    private int capacity;

    @Override
    protected void setup() {
        // Setup the agent arguments
        Object[] args = getArguments();
        if (args != null && args.length >= 2) {
            type = (String) args[0];
            capacity = Integer.parseInt(args[1].toString());
        }

        log("Started as " + type + " distributor with initial capacity: " + capacity);
        addBehaviour(new DistributionBehaviour());
    }

    private class DistributionBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                String content = msg.getContent();
                ACLMessage reply = msg.createReply();

                if (content.contains("request-electricity")) {
                    // Parse the requested amount of electricity
                    int requestAmount = Integer.parseInt(content.split("-")[1]);
                    log("Received request for " + requestAmount + " units.");

                    // Check if enough capacity is available
                    if (requestAmount <= capacity) {
                        updateCapacity(-requestAmount);
                        reply.setContent("electricity-granted-" + requestAmount);
                        log("Granted " + requestAmount + " units. New capacity: " + capacity);
                    } else {
                        reply.setContent("electricity-denied");
                        log("Denied request. Insufficient capacity.");
                    }

                    // Send the reply to the requesting agent
                    send(reply);
                }
            } else {
                block();  // Wait for the next message
            }
        }
    }

    private void updateCapacity(int amount) {
        // Update the capacity of the distributor
        capacity += amount;
        if (capacity < 0) {
            log("Error: Capacity went below zero! Fixing capacity.");
            capacity = 0;  // Prevent negative capacity
        }
    }

    private void log(String message) {
        // Log messages to the console
        System.out.println(getLocalName() + ": " + message);
    }
}
