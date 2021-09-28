package command;

import components.GameEngine;
import components.Player;

public class StairSnake implements Command {
	private GameEngine engine = GameEngine.getInstance();
	boolean isLadder;
	private int toCell;
	
	public StairSnake(boolean isLadder, int cell) { //true-> stair, false-> snake
		this.isLadder = isLadder;
		toCell = cell;
	}
	
	public void execute(Player p) {
		System.out.println("Il giocatore raggiunge la cella "+toCell);
		engine.move(p.getId(), toCell);
	}
	
	public int getToCell() {
		return toCell;
	}
	
	public boolean isLadder() {
		return isLadder;
	}
}
