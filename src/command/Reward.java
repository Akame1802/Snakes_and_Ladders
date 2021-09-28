package command;

import components.Player;

public class Reward implements Command{
	//questo booleano serve a gestire il turno di un giocatore nel caso sia applicata la variante con le caselle premio:
	//spring: non è necessario rilanciare i dadi, ma si avanza del numero di caselle indicato dal lancio precedente
	//dice: si rilanciano i dadi e si avanza ulteriormente
	private boolean springOrDice;
	
	public Reward(boolean springOrDice) {
		this.springOrDice = springOrDice;
	}
	
	public void execute(Player p) {
		if(springOrDice)
			System.out.println("Il giocatore "+p.getId()+" riceve il premio DADI: tira di nuovo i dadi");
		else 
			System.out.println("Il giocatore " +p.getId()+ " riceve il premio MOLLA: avanza dello stesso numero di caselle indicato dal precedente tiro di dadi");
		p.playTurn(springOrDice);
	}
	
	public boolean isSpringOrDice() {
		return springOrDice;
	}
}
