package gui.util;

import javax.swing.JLabel;

import gui.game.GameWindow;

public class Observer {
	private JLabel info;
	
	public Observer(JLabel info) {
		this.info = info;
	}
	
	public void update(String text) {
		info.setText(text);
		GameWindow.getInstance().getGameFrame().validate();
		GameWindow.getInstance().getGameFrame().repaint();
	}
}
