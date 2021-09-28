package gui.settings;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import gui.game.GameWindow;
import gui.game.SnakesAndLaddersGui;

public class SettingsWindow {
	private JFrame settingsFrame;
	private String title = "Settings";
	private JPanel settingsPageOne, settingsPageTwo;
	private JMenuItem about;
	private boolean applied;
	
	public SettingsWindow() {
		applied = false;
		settingsFrame = new JFrame();
		settingsFrame.setTitle(title);
		settingsFrame.setSize(720,700);
		settingsFrame.setResizable(false);
		settingsFrame.setLocationRelativeTo(null);
		settingsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ActionListener lis = new ActionLis();
        
        //menubar
        JMenuBar menuBar = new JMenuBar();
        settingsFrame.setJMenuBar(menuBar);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(SnakesAndLaddersGui.fontMenu);
		menuBar.add(helpMenu);
		about = new JMenuItem("About");
		about.setFont(SnakesAndLaddersGui.fontMenu);
		about.addActionListener(lis);
		helpMenu.add(about);
        
		settingsFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				settingsFrame.dispose();
				GameWindow.getInstance().getGameFrame().setFocusableWindowState(true);
				GameWindow.getInstance().getGameFrame().setEnabled(true);
				GameWindow.getInstance().getGameFrame().requestFocus();
			}
		});
		
		//mantengo i riferimenti alle pagine settings 1 e 2 così non perdo i settaggi effettuati dopo aver cliccato su back (se sono su page 2)
		//o next page (se sono su page 1); in questo modo si evitano anche gli sprechi di memoria in quanto a me serve solo un'istanza per ciascuna pagina
		settingsPageOne = new SettingsPageOne(this);
		settingsPageTwo = new SettingsPageTwo(this);
		settingsFrame.setContentPane(settingsPageOne);
		settingsFrame.validate();
		settingsFrame.repaint();
	}
	
	public SettingsPageOne getPageOne() {
		return (SettingsPageOne)settingsPageOne;
	}
	
	public SettingsPageTwo getPageTwo() {
		return (SettingsPageTwo)settingsPageTwo;
	}
	
	public JFrame getSettingsFrame() {
		return settingsFrame;
	}
	
	public void setApplied() {
		applied = true;
	}
	
	public boolean hasApplied() {
		return applied;
	}
	
	private class ActionLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == about) {
				UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
				UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 12));
				JOptionPane.showMessageDialog(null, "Snakes and Ladders settings: configura la partita in base alle tue preferenze! "
						+ "\n Double Six Reroll: se usi due dadi ed effettui un doppio sei in un unico lancio, hai la possibilità di lanciare nuovamente i dadi. "
						+ "\n One die for victory: se usi due dadi, dalla casella N-6 dovrai utilizzare un unico dado. "
						+ "\n Il deck contiene le carte PREMIO e SOSTA, ma puoi scegliere anche di includere la carta noStop (DIVIETO DI SOSTA) per "
						+"\n non perdere turni! Per impostare la posizione di celle speciali, serpenti e scale, basta cliccare sul bottone PLACE ON BOARD "
						+"\n ed indicare le celle su cui posizionare gli elementi."
						,"About", JOptionPane.INFORMATION_MESSAGE);
			} // about
		}
	}
}
