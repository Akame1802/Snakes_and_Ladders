package gui.state;

import gui.util.Observer;

public class Rolling implements State {
	private int val;
	private String playerName;
	private Observer observer;
	
	public Rolling(String playerName, int val, Observer observer) {
		this.playerName = playerName;
		this.val = val;
		this.observer = observer;
	}
	
	@Override
	public void handle() {
		String name = playerName.length()>25? playerName.substring(0, 25)+"..." : playerName;
		observer.update(name+" rolls "+val);
		try { Thread.sleep(1500); } 
		catch (InterruptedException e) { }
	}

}
