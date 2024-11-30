package Distributers;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;

public class Home extends Agent {
    private boolean stat = true;
    private double capacity;
    private int n_ON;
    private int n_OFF;
    private Random random = new Random();
    private int capacitynow = 0;
    private String rec;
    private int currentHour=0;

    @Override
    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started");
        if (getLocalName().equals("H1")) {
            home("DL1");
        } else {
            home("DL2");
        }
        super.setup();
    }

    public void updateHome1(String value) {
        Frame frame = Frame.getInstance();
        frame.setHome1Text(value);
    }

    public void updateHome2(String value) {
        Frame frame = Frame.getInstance();
        frame.setHome2Text(value);
    }

    public void updatetime(String value) {
        Frame frame = Frame.getInstance();
        frame.setTimeText(value);
    }

    /*public void calc() {
        Frame frame = Frame.getInstance();
        frame.calc();
    }*/

    public void home(String DL) {
        // Create a TickerBehaviour that checks simulation time to trigger actions
        addBehaviour(new TickerBehaviour(this, 3000) { // This is a placeholder for 1 second real-time intervals
            @Override
            protected void onTick() {
                // Only perform actions when the current hour in simulation time has changed
                if (SimulationTime.getCurrentHour() > currentHour) {
                    System.out.println("");
                    int randomNumber = random.nextInt(5) + 1;
                    randomNumber = randomNumber * 100;
                    int toreq = randomNumber - capacitynow;

                    if (toreq < 0)
                        System.out.println(getLocalName() + " returned " + toreq + "W to : " + DL + " AT : " + SimulationTime.getCurrentHour());
                    if (toreq > 0)
                        System.out.println(getLocalName() + " requested " + toreq + "W from : " + DL + " AT : " + SimulationTime.getCurrentHour());
                    if (toreq == 0)
                        System.out.println(getLocalName() + " didnt request anything " + toreq + "W from : " + DL + " AT : " + SimulationTime.getCurrentHour());
                    
                    if (toreq != 0) {
                    request_electricity(DL, toreq);
                    rec();
                    }

                    if (rec == "0" && toreq !=0) {
                        System.out.println(getLocalName() + " didn't get electricity *******");
                    } else {
                    	//System.out.println(""+rec+"ffffffffffffffffffffffffffffffffffffffffffffff");
                        capacitynow = capacitynow + toreq;
                        System.out.println(getLocalName() + " Capacity " + capacitynow+ " received from ");
                    }

                    // Update display with new capacity
                    if (getLocalName().equals("H1")) updateHome1("" + capacitynow);
                    if (getLocalName().equals("H2")) updateHome2("" + capacitynow);
                    //if (getLocalName().equals("H1")) updatetime("" + SimulationTime.getCurrentHour());

                    currentHour = SimulationTime.getCurrentHour();
                }
            }
        });
    }

    private void rec() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    try {
                        rec = msg.getContent();
                        System.out.println("//////////////"+msg.getSender().getLocalName());
                        myAgent.removeBehaviour(this);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid message content: " + msg.getContent());
                    }
                } else {
                    block(); // Wait for a new message
                }
            }
        });
    }

    private void request_electricity(String Agent_name, int Data) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(Agent_name, AID.ISLOCALNAME));
        msg.setContent("" + Data);
        send(msg);
    }
}
