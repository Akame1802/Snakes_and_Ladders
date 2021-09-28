package gui.state;

import components.Player;
import gui.util.Observer;

public class Ended implements State {
	private Player winner;
	private Observer observer;
	
	public Ended(Player p, Observer observer) {
		winner = p;
		this.observer = observer;
	}
	
	@Override
	public void handle() {
		String name = winner.getName().length()>25? winner.getName().substring(0, 25)+"..." : winner.getName();
		observer.update(name+" wins!");
	}

}
