package gui.state;

import java.util.Queue;

import components.GameEngine;
import gui.game.GameWindow;
import gui.game.MainPanel;
import gui.util.IconPool;

public class AutomaHandler extends Thread {
	private Queue<gui.state.State> automa;
	private int nPlayers, turn;

	public AutomaHandler(Queue<gui.state.State> automa, int nPlayers) {
		this.automa = automa;
		this.nPlayers = nPlayers;
		turn = 0;
	}
	
	public void run() {
		MainPanel mainPanel = GameWindow.getInstance().getMainPanel();
		while(GameWindow.getInstance().isGameStarted()) {
			if(GameEngine.getInstance().hasWinner())
				break;
			//gestione coda degli stati a partire da idle
			gui.state.State idle = automa.poll();
			if(!GameEngine.getInstance().isPlayerStopped(turn))
				idle.handle();
			if(!mainPanel.isPaused() && mainPanel.hasRolled() && GameWindow.getInstance().isGameStarted()) {
				if(!GameEngine.getInstance().isPlayerStopped(turn)) {
					mainPanel.getCurrPlayerLabel().setText(GameWindow.getInstance().getConfiguration().getNames().get(turn));
					mainPanel.getPawnLabel().setIcon(IconPool.getInstance().get(IconPool.PAWN0+turn));
				}
				GameEngine.getInstance().oneGameLoop(turn);
				turn = (turn+1)%nPlayers;
				while(!automa.isEmpty())
					automa.poll().handle();
				automa.add(idle);
			}
			else
				automa.add(idle);
		}
		if(GameWindow.getInstance().isGameStarted())
			mainPanel.endGame();
	}

}
