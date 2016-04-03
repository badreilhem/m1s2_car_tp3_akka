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
		System.out.println("created \"" + name + "\" node");
		return system.actorOf(Props.create(Node.class, name));
	}

	public ActorRef addChild(ActorRef parent, String childName) {
		if (parent == null) {
			System.err.println("can't create child \"" + childName + "\", given parent is null");
			return null;
		}

		ActorRef child = createNode(childName);

		parent.tell(new AddChildMessage(child), parent);
		System.out.println("sent message to add child \"" + childName + "\"");
		
		return child;
	}

	public void waitForShutdown() throws IOException {		
		system.shutdown();
		System.out.println("closed \"" + system.name() + "\" system");
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ActorRef noeud1, noeud2, noeud3, noeud4, noeud5, noeud6;

		AKKAMain akkaSystem = new AKKAMain("systemTest");

		noeud1 = akkaSystem.createNode("noeud1");
		waitInput();
		noeud2 = akkaSystem.addChild(noeud1, "noeud2");
		waitInput();
		noeud3 = akkaSystem.addChild(noeud2, "noeud3");
		waitInput();
		noeud4 = akkaSystem.addChild(noeud2, "noeud4");
		waitInput();
		noeud5 = akkaSystem.addChild(noeud1, "noeud5");
		waitInput();
		noeud6 = akkaSystem.addChild(noeud5, "noeud6");

		waitInput();
		noeud1.tell(new GreetingsMessage("message from noeud 1"), ActorRef.noSender());

		waitInput();
		noeud2.tell(new GreetingsMessage("message from noeud 2"), ActorRef.noSender());

		waitInput();
		akkaSystem.waitForShutdown();
	}

	public static void waitInput() throws IOException {
		System.out.println(">>Press enter to continue");
		System.in.read();
	}
}
