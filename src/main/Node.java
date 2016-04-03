package main;

import java.util.LinkedList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Node extends UntypedActor {

	protected List<ActorRef> children;
	protected String who;

	public Node(String who) {
		this.who = who;
		this.children = new LinkedList<ActorRef>();
	}

	public Node(ActorRef parent, List<ActorRef> childs, String who) {
		this.children = childs;
	}

	public void sendMessageToChildren(GreetingsMessage message) {
		for (ActorRef child : children) {
			child.forward(message, getContext());
		}
	}

	@Override
	public void onReceive(Object message) throws InterruptedException {
		if (message instanceof GreetingsMessage) {
			GreetingsMessage greetingsMessage = (GreetingsMessage) message;
			if (!greetingsMessage.hasAlreadyVisited(getSelf())) {
				System.out.println(this.who + " received \"" + greetingsMessage
						+ "\"");
				((GreetingsMessage) message).addToVisited(getSelf());
				sendMessageToChildren(greetingsMessage);
			}
			return;
		}

		if (message instanceof AddChildMessage) {
			AddChildMessage addChildMessage = (AddChildMessage) message;
			System.out.println(who + " received \"" + addChildMessage + "\"");
			children.add(addChildMessage.child);
			return;
		}

		unhandled(message);
	}
}
