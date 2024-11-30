package Distributers;

import javax.swing.RepaintManager;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import javafx.scene.paint.Stop;

public class Distributer extends Agent{
	private boolean stat=true;
	private int capacity;
	private int n_ON;
	private int n_OFF;
	private int caprequested = 0;
	private String rec;
	int x=0;
	@Override
	protected void setup() {
		System.out.println("Agent " + getLocalName() + " started.");
		if (getLocalName().equals("DP")){
			initPrincipal();
			Principal();
		}else {
			initLocal();
			Local();
		}
		super.setup();
	}
	
	
	
	
	public void Principal() {		
		addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
            	ACLMessage msg = receive();
                if (msg != null) {
                	caprequested = Integer.parseInt(msg.getContent());
                	if (caprequested<0) {
                		// return electricity
                		capacity=capacity-caprequested;
                		System.out.println(getLocalName()+" returned "+ caprequested +" from "+msg.getSender().getLocalName()+" and my capacity now is ++++: "+ capacity);
                	}else if (caprequested<=capacity) {
                		//yb3th capacity ll home/company
                		capacity=capacity-caprequested;
                		System.out.println(getLocalName()+" received request of"+ caprequested +" from "+msg.getSender().getLocalName()+" and sent it back and my capacity is ----:"+ capacity);
                		Send_Data(msg.getSender().getLocalName(), ""+caprequested);
                		
                	}else if (caprequested>capacity) {
                		//request more data from other DL
                		if (msg.getSender().getLocalName().equals("DL1")) {
                		    int deficit = caprequested - capacity; // Calculate the deficit
                		    System.out.println(getLocalName() + " received a request from DL1. Forwarding deficit of " + deficit + " to DL2.************");

                		    // Send deficit request to DL2
                		    Send_Data("DL2",""+deficit);

                		    // Wait for the response from DL2
                		    ACLMessage responseFromDL2 = blockingReceive();
                		    int resp =Integer.parseInt(responseFromDL2.getContent());
                		    if (responseFromDL2 != null && resp>0) {
                		        System.out.println(getLocalName() + " received a response from "+responseFromDL2.getSender().getLocalName()+": " + responseFromDL2.getContent());
                		        capacity=capacity+resp;
                		        // Forward the received capacity back to DL1
                		        Send_Data("DL1", ""+caprequested);
                		        capacity=capacity-caprequested;
                		        System.out.println(getLocalName() + " forwarded "+caprequested+"to DL1 and my capacity : "+capacity);
                		    } else {
                		    	Send_Data("DL1", "0");
                		        System.out.println(getLocalName() + " did not receive a response from DL2 within the timeout.");
                		    }
                		}
                		if (msg.getSender().getLocalName().equals("DL2")) {
                		    int deficit = caprequested - capacity; // Calculate the deficit
                		    System.out.println(getLocalName() + " received a request from DL2. Forwarding deficit of " + deficit + " to DL1.***************");

                		    // Send deficit request to DL1
                		    Send_Data("DL1", String.valueOf(deficit));

                		    // Wait for the response from DL1
                		    ACLMessage responseFromDL1 = blockingReceive();
                		    int resp =Integer.parseInt(responseFromDL1.getContent());
                		    if (responseFromDL1 != null && resp > 0) {
                		        System.out.println(getLocalName() + " received a response from DL1: " + responseFromDL1.getContent());
                		        capacity=0;
                		        // Forward the response to DL2
                		        Send_Data("DL2",""+ caprequested);
                		        System.out.println(getLocalName() + " forwarded the response to DL2.");
                		    } else {
                		    	Send_Data("DL2", "0");
                		        System.out.println(getLocalName() + " did not receive a response from DL1 within the timeout.");
                		    }
                		}
    		
                	}
                	updateDP(""+capacity);
                }  
               
            }
        });
	}
	
	public void Local() {
		
		addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {	
                ACLMessage msg = receive();
                if (msg != null) {
                	caprequested = Integer.parseInt(msg.getContent());
                	
                	
                	
                	if (caprequested<0) {
                		// yrj3 W ll DL
                		capacity=capacity-caprequested;
                		System.out.println(getLocalName()+" returned "+ caprequested +" from "+msg.getSender().getLocalName()+" and my capacity now is : "+ capacity);
                	}else if (caprequested<=capacity) {
                		//yb3th capacity ll home/company
                		capacity=capacity-caprequested;
                		System.out.println(getLocalName()+" received request of "+ caprequested +" from "+msg.getSender().getLocalName()+" and sent back and my capacity now is : "+ capacity);
                		Send_Data(msg.getSender().getLocalName(), ""+caprequested);
                		
                	}else if (caprequested>capacity && !msg.getSender().getLocalName().equals("DP")) {
                		//request more data from DP
                		Send_Data("DP", ""+(caprequested-capacity));
                		System.out.println(getLocalName()+" sent request to DP "+ (caprequested-capacity));
                		ACLMessage responseFromDP = blockingReceive();
                		int resp =Integer.parseInt(responseFromDP.getContent());
             		    if (responseFromDP != null && resp>0 ) {
             		    	System.out.println(getLocalName()+" my capacity before receiving Data from "+responseFromDP.getSender().getLocalName()+" :"+capacity+"..........................");
             		       capacity=capacity+resp;
             		       System.out.println(getLocalName() + " received a response from DP: " + responseFromDP.getContent());  
             		       
             		       Send_Data(msg.getSender().getLocalName(), ""+caprequested);
             		       capacity=capacity-caprequested;
             		       System.out.println(getLocalName() + " forwarded "+caprequested+"to  ++++++++"+msg.getSender().getLocalName() +" and my capacity : "+capacity);
             		    } else {
             		    	Send_Data(msg.getSender().getLocalName(), ""+"0");
             		        System.out.println(getLocalName() + " did not receive a response from DP within the timeout.");
             		    }
                	}
                	if(getLocalName().equals("DL1")) {updateDL1(""+capacity);}
                    if(getLocalName().equals("DL2")) {updateDL2(""+capacity);}
                } 
                
              
            }
        });
		
	}
	
	public void initPrincipal(){
		capacity=1700;
		int dl1 = 100;
		int dl2 = 300;
		beginingcap(""+capacity);
		//int captosend = capacity/2;
		
		Send_Data("DL1",""+dl1);
		capacity=capacity-dl1;
    	Send_Data("DL2",""+dl2);
    	capacity=capacity-dl2;
    	System.out.println(getLocalName()+" and my capacity is :"+capacity);
    	updateDP(""+capacity);
    	
	}
	
	public void initLocal() {
	    capacity = 0;

	    // Use OneShotBehaviour because initialization happens once
	    addBehaviour(new OneShotBehaviour() {
	        @Override
	        public void action() {
	            System.out.println(getLocalName() + " is waiting for the initial capacity...");

	            // Wait for a message indefinitely
	            ACLMessage init = blockingReceive(); 
	            
	            if (init != null) {
	                try {
	                    capacity += Integer.parseInt(init.getContent());
	                    System.out.println(getLocalName() + " and my capacity is:" + capacity);
	                } catch (NumberFormatException e) {
	                    System.err.println("Invalid message content: " + init.getContent());
	                }
	                if(getLocalName().equals("DL1")) {updateDL1(""+capacity);}
                    if(getLocalName().equals("DL2")) {updateDL2(""+capacity);}
	            } else {
	                System.err.println(getLocalName() + " did not receive the initialization message.");
	            }
	            //System.out.println("+++++++++++++++++++++++++++");
	        }
	    });
	}

	private void Send_Data(String Agent_name,String Data) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(Agent_name, AID.ISLOCALNAME));
        msg.setContent(Data);
        send(msg);
        //System.out.println(getLocalName()+" sent to "+Agent_name+": " + msg.getContent()+ " and my capacity is :"+capacity);
    }
	
	public void updateDP(String value) {
        Frame frame = Frame.getInstance();
        frame.setDPCapacityText(value);
    }
	public void updateDL1(String value) {
        Frame frame = Frame.getInstance();
        frame.setDL1CapacityText(value);
    }
	public void updateDL2(String value) {
        Frame frame = Frame.getInstance();
        frame.setDL2CapacityText(value);
    }
	public void beginingcap(String value) {
        Frame frame = Frame.getInstance();
        frame.setbeginingcapacity(value);
    }

	
}
