package gui.state;

import javax.swing.JPanel;

import gui.game.GameWindow;
import gui.game.PlayerLabel;
import gui.util.Observer;

public class Moving implements State {
	private String playerName;
	private int playerId, fromCell, toCell;
	private Observer observer;
	
	public Moving(String playerName, int playerId, int fromCell, int toCell, Observer observer) {
		this.playerName = playerName;
		this.playerId = playerId;
		this.fromCell = fromCell;
		this.toCell = toCell;
		this.observer = observer;
	}

	@Override
	public void handle() {
		JPanel cell = (JPanel)GameWindow.getInstance().getMainPanel().getBoardPan().getComponent(fromCell);
		((PlayerLabel)cell.getComponent(0)).removePlayer(playerId);
		cell = (JPanel)GameWindow.getInstance().getMainPanel().getBoardPan().getComponent(toCell);
		((PlayerLabel)cell.getComponent(0)).placePlayer(playerId);
		String name = playerName.length()>25? playerName.substring(0, 25)+"..." : playerName;
		observer.update(name+" moves to cell "+(toCell+1));
		try { Thread.sleep(1500); } 
		catch (InterruptedException e) { }
	}

}
