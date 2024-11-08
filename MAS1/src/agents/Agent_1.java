package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class Agent_1 extends Agent {

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");
        
        Send_Data();
        
        // Add behavior to receive the response from Agent_2
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage reply = receive();
                if (reply != null) {
                    System.out.println("Reply received from " + reply.getSender().getLocalName() + ": " + reply.getContent());
                } else {
                    block();
                }
            }
        });

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                doWait(2000); 
                // doDelete();
            }
        });
    }

    private void Send_Data() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        msg.addReceiver(new AID("Agent_2", AID.ISLOCALNAME));
        msg.setContent("oussama benladen");
        send(msg);

        System.out.println("Message sent to Agent_2: " + msg.getContent());
    }
}
