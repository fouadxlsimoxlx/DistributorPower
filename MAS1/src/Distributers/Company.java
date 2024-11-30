package Distributers;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;

public class Company extends Agent{
	private boolean stat=true;
	private int capacity;
	private int n_ON;
	private int n_OFF;
	private int currentHour = 0;
	private Random random = new Random();
	private int capacitynow = 0;
	private String rec;
	@Override
	protected void setup() {
		System.out.println("Agent " + getLocalName() + " started");
		if (getLocalName().equals("C1")) {
			Company("DL1");
		}else {Company("DL2");}
		
		super.setup();
	}
	
	
	public void updateCompany1(String value) {
        Frame frame = Frame.getInstance();
        frame.setCompany1Text(value);
    }
	public void updateCompany2(String value) {
        Frame frame = Frame.getInstance();
        frame.setCompany2Text(value);
    }
	
	public void Company(String DL) {
	    addBehaviour(new TickerBehaviour(this, 3000) { 
	        @Override
	        protected void onTick() {
	            // Only perform actions when the current simulation hour has changed
	            if (SimulationTime.getCurrentHour() > currentHour) {
	                if (SimulationTime.getCurrentHour() >= 8 && SimulationTime.getCurrentHour() <= 16) {
	                	System.out.println("");
	                	int randomNumber = random.nextInt(5) + 1;
	                    randomNumber = randomNumber * 100;
	                    int toreq = randomNumber - capacitynow;

	                    if (toreq < 0)
	                        System.out.println(getLocalName() + " returned " + toreq + "W to : " + DL + " AT : " + SimulationTime.getCurrentHour());
	                    if (toreq > 0)
	                        System.out.println(getLocalName() + " requested " + toreq + "W from : " + DL + " AT : " + SimulationTime.getCurrentHour());
	                    if (toreq > 0)
	                        System.out.println(getLocalName() + " didnt request anything " + toreq + "W from : " + DL + " AT : " + SimulationTime.getCurrentHour());
	                    
	                    if (toreq != 0) {
	                    request_electricity(DL, toreq);
	                    rec();
	                    }

	                    if (rec == "0" && toreq !=0) {
	                        System.out.println(getLocalName() + " didn't get electricity *******");
	                    } else {
	                    	System.out.println(""+rec+"ffffffffffffffffffffffffffffffffffffffffffffff");
	                        capacitynow = capacitynow + toreq;
	                        System.out.println(getLocalName() + " Capacity " + capacitynow);
	                    }
			
	    				if(getLocalName().equals("C1"))updateCompany1(""+capacitynow);
	    				if(getLocalName().equals("C2"))updateCompany2(""+capacitynow);
	                }else if (capacity>0) {	                	
	                	request_electricity(DL, -capacity);
	                	System.out.println(getLocalName() + " returned " + -capacity + "W to : " + DL + " AT : " + SimulationTime.getCurrentHour());
	                	capacity=capacity-capacity;
	                	if(getLocalName().equals("C1"))updateCompany1(""+capacitynow);
	    				if(getLocalName().equals("C2"))updateCompany2(""+capacitynow);
	                }
	              
	                
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
	                    rec=msg.getContent();
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
	
	private void request_electricity(String Agent_name,int Data) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(Agent_name, AID.ISLOCALNAME));
        msg.setContent(""+Data);
        send(msg);
        //System.out.println(getLocalName()+" sent Request to "+Agent_name+": " + msg.getContent());
    }
	
	
}
