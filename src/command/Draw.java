package command;

import components.GameEngine;
import components.Player;

public class Draw implements Command {
	private GameEngine engine = GameEngine.getInstance();
	
	public void execute(Player p) {
		System.out.println("Il giocatore "+p.getId()+" è finito sulla casella PESCA UNA CARTA: pesca una carta");
		p.addCard(engine.draw(p.getId()));
	}
}
