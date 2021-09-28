package components;

import java.util.LinkedList;
import command.Command;
import command.Stop;

public class Player {
	private String name;
	private int id;
	private GameEngine engine;
	private int stopFor; //il giocatore si ferma per k turni
	private int lastThrow; //memorizza il valore dell'ultimo lancio di dadi
	private LinkedList<Card> cards; //riferimento alle carte pescate dal giocatore
	
	public Player(String name, int id) {
	    this.name = name;
	    this.id = id;
	    this.cards = new LinkedList<Card>();
	    this.engine = GameEngine.getInstance();
	}
	
	public String getName() {
	    return name;
	}
	
	public int getId() {
	    return id;
	}
	
	public void playTurn(boolean throwDice) { //false-> effetto "molla" applicato, altrimenti si applica effetto "dadi"		
		if(canPlayTurn()) {
			if(throwDice) {
				int val = engine.rollDice(id);
				System.out.println("Lancio dei dadi: "+val);
				lastThrow = val;
				int toCell = manipulate(val);
				System.out.println("Il giocatore "+id+ " si sposta sulla casella "+(toCell+1));
				Cell c = engine.move(id, toCell);
				c.over(this);
			}
			else {
				int toCell = manipulate(lastThrow);
				Cell c = engine.move(id, toCell);
				System.out.println("Il giocatore "+id+ " si sposta sulla casella "+(toCell+1));
				c.over(this);
			}
		}
	}
	
	private boolean canPlayTurn() {
		if(stopFor>0) {
			if(!cards.isEmpty()) {
				Card c = cards.removeFirst();
				System.out.println("Il giocatore "+id+" gioca la carta DIVIETO DI SOSTA");
				c.activate(this);
			}
			else {
				stopFor--;
				return false;
			}
		}
		return true;
	}
	
	private int manipulate(int val) {
		int currCell = engine.getPlayerPos(id);
		int newCell = currCell + val;
		if (newCell > engine.boardSize()-1) {
			int residues = newCell - engine.boardSize() + 1;
			newCell = engine.boardSize() - 1 - residues;
		}
		return newCell;
	}

	public void stopFor(int turns) {
		stopFor = turns;
	}
	
	public boolean isStopped() {
		return stopFor > 0;
	}
	
	public void addCard(Card c) {
		Command effect = c.getEffect();
		if(effect instanceof Stop && ((Stop)effect).getTurns()==0)
			cards.add(c);
		else {
			c.activate(this);
			engine.putCard(c);
		}
	}
	
	
}
