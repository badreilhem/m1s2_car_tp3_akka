package main;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import akka.actor.ActorRef;

/**
 * Message containing a String to be printed and memorizing which node has been visited
 * @author Baddredine & Cojez
 *
 */
@SuppressWarnings("serial")
public class GreetingsMessage implements Serializable {
	
	//FIELDS
	
	public String message;
	public List<ActorRef> visitedActors;
	
	//METHODS
	
	/**
	 * Constructor for the GreetingsMessage class
	 * @param msg the message to be printed
	 */
	public GreetingsMessage(String message) {
		this.message = message;
		this.visitedActors = new LinkedList<ActorRef>();
	}
	
	/**
	 * Returns a String representing the object
	 * @return a String representing the object
	 */
	public String toString() {
		return message;
	}

	/**
	 * Adds an ActorRef to the list of visited ActorRef (if it has not already been visited)
	 * @param actor the actor to add
	 * @return true if the actor has been successfully added, false otherwise
	 */
	public boolean addToVisited(ActorRef actor) {
		if(!visitedActors.contains(actor))
			return visitedActors.add(actor);
		return false;
	}
	
	/**
	 * Checks if an ActorRef has already been visited
	 * @param actor the actor to be checked
	 * @return true if the actor has already been visited, false otherwise
	 */
	public boolean hasAlreadyVisited(ActorRef actor) {
		return visitedActors.contains(actor);
	}
}
