package gui.state;

import gui.game.GameWindow;
import gui.game.MainPanel;
import gui.util.Observer;

public class Idle implements State {
	private Observer observer;
	
	public Idle(Observer observer) {
		this.observer = observer;
	}
	
	@Override
	public void handle() {
		MainPanel main = GameWindow.getInstance().getMainPanel();
		main.getStart().setEnabled(true);
		main.getAuto().setEnabled(true);
		main.getManual().setEnabled(true);
		if(main.isManual()) {
			main.waitForRoll();
			main.getRoll().setEnabled(true);
		}
		observer.update("Waiting for next move...");
		try { Thread.sleep(3000); } 
		catch (InterruptedException e) { }
		main.getStart().setEnabled(false);
		main.getAuto().setEnabled(false);
		main.getManual().setEnabled(false);
		main.getRoll().setEnabled(false);
	}

}
