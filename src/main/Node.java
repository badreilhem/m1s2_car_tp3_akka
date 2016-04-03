package main;

import java.util.LinkedList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Node extends UntypedActor {

	protected List<ActorRef> children;
	protected ActorRef parent;
	protected String who;

	public Node(String who) {
		this.who = who;
		this.parent = ActorRef.noSender();
		this.children = new LinkedList<ActorRef>();
	}

	public Node(ActorRef parent, List<ActorRef> childs, String who) {
		this.parent = parent;
		this.children = childs;
	}

	public void sendMessageToChildren(GreetingsMessage message) {
		for (ActorRef child : children) {
			child.forward(message, getContext());
		}
	}

	public void sendMessageToParent(GreetingsMessage message) {
		if (parent != null)
			parent.tell(message, getSelf());
	}

	@Override
	public void onReceive(Object message) throws InterruptedException {
		if (message instanceof GreetingsMessage) {
			GreetingsMessage greetingsMessage = (GreetingsMessage) message;
			System.out.println(this.who + " received \"" + greetingsMessage + "\"");
			sendMessageToChildren(greetingsMessage);
			// sendMessageToParent(message);
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
