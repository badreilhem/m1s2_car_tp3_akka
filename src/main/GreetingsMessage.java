package main;

import java.io.Serializable;

public class GreetingsMessage implements Serializable {
	public String msg;
	
	public GreetingsMessage(String msg) {
		this.msg = msg;
	}
	
	public String toString() {
		return msg;
	}
}
