package Dist;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class House extends Agent {
    private String localDistributor;

    protected void setup() {
        Object[] args = getArguments();
        localDistributor = (args != null && args.length > 0) ? (String) args[0] : "Local1";

        System.out.println(getLocalName() + " is under " + localDistributor);
        addBehaviour(new SendRequestBehaviour());
    }

    private class SendRequestBehaviour extends OneShotBehaviour {
        @Override
        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setContent("Request from " + getLocalName());
            msg.addReceiver(getAID(localDistributor));
            send(msg);
        }
    }
}
