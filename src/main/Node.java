package main;

import akka.actor.UntypedActor;
import akka.actor.ActorRef;
import java.util.List;

public class Node extends UntypedActor {
	
	List<ActorRef> childs;
	
	public Node(List<ActorRef> childs){
		this.childs = childs;		
	}

	public void sendMessageToChilds(Object message){
		for(ActorRef child : childs){
			
		}
	}
	
	public void onReceive(Object arg0) throws Exception {
			
	}

}
