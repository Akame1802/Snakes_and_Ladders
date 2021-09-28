package command;

import components.GameEngine;
import components.Player;

public class EndGame implements Command{
	private GameEngine engine = GameEngine.getInstance();
	
	public void execute(Player p) {
		System.out.println("Il giocatore "+p.getId()+" ha vinto. Partita terminata.");
		engine.setWinner(p);
	}
}
