package main;

import java.io.Serializable;

import akka.actor.ActorRef;

public class AddChildMessage implements Serializable {

	protected ActorRef child;

	public AddChildMessage(ActorRef child) {
		this.child = child;
	}

	public String toString() {
		return "Action : add child";
	}
}
