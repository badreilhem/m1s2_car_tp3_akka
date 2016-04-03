package main;

import java.util.LinkedList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * A Node containing a name and children, capable of processing a message
 * @author Badreddine & Cojez
 *
 */
public class Node extends UntypedActor {

	//FIELDS
	protected List<ActorRef> children;
	protected String name;

	//METHODS
	
	/**
	 * Constructor of the Node class
	 * @param who the name of the Node
	 */
	public Node(String name) {
		this.name = name;
		this.children = new LinkedList<ActorRef>();
	}

	/**
	 * Transfers a message to all the children of the node
	 * @param message the message to be transfered
	 */
	public void sendMessageToChildren(GreetingsMessage message) {
		for (ActorRef child : children) {
			child.forward(message, getContext());
		}
	}

	/**
	 * Processes the received message :
	 * - adds a child when the message is an instance of AddChildMessage
	 * - prints the message if it is an instance of GreetingsMessage
	 */
	@Override
	public void onReceive(Object message) throws InterruptedException {
		if (message instanceof GreetingsMessage) {
			GreetingsMessage greetingsMessage = (GreetingsMessage) message;
			if (!greetingsMessage.hasAlreadyVisited(getSelf())) {
				System.out.println(this.name + " received \"" + greetingsMessage
						+ "\"");
				((GreetingsMessage) message).addToVisited(getSelf());
				sendMessageToChildren(greetingsMessage);
			}
			return;
		}

		if (message instanceof AddChildMessage) {
			AddChildMessage addChildMessage = (AddChildMessage) message;
			System.out.println(name + " received \"" + addChildMessage + "\"");
			children.add(addChildMessage.child);
			return;
		}

		unhandled(message);
	}
}
