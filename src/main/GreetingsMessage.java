package main;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import akka.actor.ActorRef;

public class GreetingsMessage implements Serializable {
	public String msg;
	public List<ActorRef> visitedActors;
	
	public GreetingsMessage(String msg) {
		this.msg = msg;
		this.visitedActors = new LinkedList<ActorRef>();
	}
	
	public String toString() {
		return msg;
	}

	public boolean addToVisited(ActorRef actor) {
		if(!visitedActors.contains(actor))
			return visitedActors.add(actor);
		return false;
	}
	
	public boolean hasAlreadyVisited(ActorRef actor) {
		return visitedActors.contains(actor);
	}
}
