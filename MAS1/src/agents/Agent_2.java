package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;

public class Agent_2 extends Agent {

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started and waiting for messages.");

        // Add a cyclic behavior to continuously listen for incoming messages
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    System.out.println("Message received from " + msg.getSender().getLocalName() + ": " + msg.getContent());
                    
                    // Send a reply back to Agent_1
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("fmldsjfmlsqjfmlsqjfmsqjf: " + msg.getContent());
                    send(reply);
                    
                    System.out.println("Reply sent to " + msg.getSender().getLocalName() + ": " + reply.getContent());
                } else {
                    block();
                }
            }
        });
    }
}
