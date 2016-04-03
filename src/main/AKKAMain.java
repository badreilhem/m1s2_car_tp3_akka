package main;

import java.io.IOException;

import scala.io.StdIn;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class AKKAMain {
	protected ActorSystem system;

	public AKKAMain(String systemName) {
		super();
		system = ActorSystem.create(systemName);
		System.out.println("created \"" + system.name() + "\" system");
	}

	public ActorRef createNode(String name) {
		System.out.println("created \"" + name + "\" node in system \"" + system.name() + "\"");
		return system.actorOf(Props.create(Node.class, name), name);
	}
	
	public static void addChild(ActorRef parent, ActorRef child) {
		if (parent == null) {
			System.err.println("can't add child, given parent is null");
		}
		if (child == null) {
			System.err.println("can't add child, given child is null");
		}

		parent.tell(new AddChildMessage(child), parent);
		System.out.println("sent message to add child");
	}

	public void shutdown(){		
		system.shutdown();
		System.out.println("closed \"" + system.name() + "\" system");
	}

	/**= 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ActorRef noeud1, noeud2, noeud3, noeud4, noeud5, noeud6;
		AKKAMain system1, system2;
		
		//creates two systems
		system1= new AKKAMain("system1");
		system2= new AKKAMain("system2");
		waitInput();

		//creates the node 1 in system1
		noeud1 = system1.createNode("noeud1");
		waitInput();

		//creates the node 2 in system2
		//adds the node 2 as a child of node 1
		noeud2 = system2.createNode("noeud2");
		addChild(noeud1, noeud2);
		waitInput();
		
		//creates the node 3 in system1
		//adds the node 3 as a child of node 2
		noeud3 = system1.createNode("noeud3");
		addChild(noeud2, noeud3);
		waitInput();

		//creates the node 4 in system2
		//adds the node 4 as a child of node 2
		noeud4 = system2.createNode("noeud4");
		addChild(noeud2, noeud4);
		waitInput();

		//creates the node 5 in system2
		//adds the node 5 as a child of node 1
		noeud5 = system2.createNode("noeud5");
		addChild(noeud1, noeud5);
		waitInput();

		//creates the node 6 in system1
		//adds the node 6 as a child of node 5
		noeud6 = system1.createNode("noeud6");
		addChild(noeud5, noeud6);
		waitInput();

		//sends a message to the node 1
		//all the nodes should receive this message
		noeud1.tell(new GreetingsMessage("message from noeud 1"), ActorRef.noSender());
		waitInput();

		//sends a message to the node 2
		//nodes 2, 3 and 4 should receive this message
		noeud2.tell(new GreetingsMessage("message from noeud 2"), ActorRef.noSender());
		waitInput();

		//shuts down the two systems
		system1.shutdown();
		system2.shutdown();
	}

	public static void waitInput() throws IOException {
		System.out.println(">>Press enter to continue");
		System.in.read();
	}
}
