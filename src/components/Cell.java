package components;
import command.Command;

public class Cell {
	private Command effect;
	
	public Cell(Command effect) {
		this.effect = effect;
	}
	
	public void over(Player p) {
		effect.execute(p);
	}
	
	public Command getEffect() {
		return effect;
	}
}
