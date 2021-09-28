package components;

import command.Command;
import command.Reward;
import command.Stop;

public class Card {
	private Command effect;
	
	public Card(Command effect) {
		this.effect = effect;
	}
	
	public void activate(Player p) {
		if(effect instanceof Reward)
			System.out.println("Il giocatore "+ p.getId()+ " ha pescato una carta PREMIO");
		else if (effect instanceof Stop) {
			int t = ((Stop) effect).getTurns();
			if(t>0)
				System.out.println("Il giocatore "+ p.getId()+ " ha pescato una carta SOSTA");
			else 
				System.out.println("Il giocatore "+ p.getId()+ " ha pescato una carta DIVIETO DI SOSTA");
		}
		effect.execute(p);
	}
	
	public Command getEffect() {
		return effect;
	}
}
