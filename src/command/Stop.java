package command;
import components.Player;

public class Stop implements Command{
	private int k; //numero di turni per cui sostare (1 panchina, 3 locanda, 0 divieto di sosta)
	
	public Stop(int k) {
		this.k = k;
	}
	
	public void execute(Player p) {
		System.out.println("Il giocatore si deve fermare per "+k+" turni");
		p.stopFor(k);
	}
	
	public int getTurns() {
		return k;
	}
}
