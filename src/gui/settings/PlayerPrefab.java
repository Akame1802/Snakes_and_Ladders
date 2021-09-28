package gui.settings;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.game.SnakesAndLaddersGui;

@SuppressWarnings("serial")
//premade panel che contiene Label + JTextField
public class PlayerPrefab extends JPanel {
	private JTextField inputName;
	
	public String getInputName() {
		return inputName.getText();
	}
	
	public void setInputName(String name) {
		inputName.setText(name);
	}
	
	public PlayerPrefab(int playerNum) {
		setPreferredSize(new Dimension(400, 50));
		
		JLabel playerLabel = new JLabel("Player "+playerNum);
		playerLabel.setFont(SnakesAndLaddersGui.fontText);
		playerLabel.setLocation(0,0);
		playerLabel.setPreferredSize(new Dimension(70, 50));
		inputName = new JTextField();
		inputName.setFont(SnakesAndLaddersGui.fontText);
		inputName.setLocation(50,0);
		inputName.setPreferredSize(new Dimension(280, 50));
		add(playerLabel);
		add(inputName);
	}
}
