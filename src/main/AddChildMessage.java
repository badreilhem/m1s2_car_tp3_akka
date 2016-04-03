package main;

import java.io.Serializable;

import akka.actor.ActorRef;

/**
 * Message asking a Node to add another Node as a child
 * @author Badreddine & Cojez
 *
 */
public class AddChildMessage implements Serializable {

	//FIELDS
	protected ActorRef child;

	//METHODS
	
	/**
	 * Constructor of the AddChildMessage class
	 * @param child the Node we want the receiving Node to add as a child 
	 */
	public AddChildMessage(ActorRef child) {
		this.child = child;
	}

	/**
	 * Returns a String representing the object
	 * @return a String representing the object
	 */
	public String toString() {
		return "Action : add child";
	}
}
