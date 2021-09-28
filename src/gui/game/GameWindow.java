package gui.game;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.FloatControl;
import javax.swing.*;

import gui.settings.Configuration;
import gui.settings.SettingsWindow;
import gui.util.IconPool;
import gui.util.Observer;
import gui.util.ResourceLoader;

public class GameWindow{
	private static GameWindow instance;
	private GameWindow() { }
	public static GameWindow getInstance() {
		if(instance == null)
			instance = new GameWindow();
		return instance;
	}
	
	private Observer observer;
	private JFrame gameFrame;
	private MainPanel main;
	private String title = "Snakes and Ladders";
	private JMenuItem config, exit, save, saveAs, load, setOpt, rules;
	private ActionListener applyListener = new ApplyListener();

	private SettingsWindow settingsWindow;
	private File savedConfig;
	private Configuration configuration;
	
	private boolean gameStarted;
	
	public void init() {
		UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 16));
		UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 12));
		gameFrame = new JFrame();
		gameFrame.setTitle(title);
		gameFrame.setSize(1310,1030);
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ActionListener lis = new ActionLis();
		
		//menubar
        JMenuBar menuBar = new JMenuBar();
        gameFrame.setJMenuBar(menuBar);
        
        //new game
        JMenu newGame = new JMenu("New Game");
        newGame.setFont(SnakesAndLaddersGui.fontMenu);
		menuBar.add(newGame);
		//voci
		config = new JMenuItem("Configure");
		config.setFont(SnakesAndLaddersGui.fontMenu);
		config.addActionListener(lis);
		newGame.add(config);
		load = new JMenuItem("Load");
		load.setFont(SnakesAndLaddersGui.fontMenu);
		load.addActionListener(lis);
		newGame.add(load);
		newGame.addSeparator();
		exit = new JMenuItem("Exit");
		exit.setFont(SnakesAndLaddersGui.fontMenu);
		exit.addActionListener(lis);
		newGame.add(exit);
		
		//save configuration
		JMenu saveConfig = new JMenu("Save Configuration");
		saveConfig.setFont(SnakesAndLaddersGui.fontMenu);
		menuBar.add(saveConfig);
		//voci
		save = new JMenuItem("Save");
		save.setFont(SnakesAndLaddersGui.fontMenu);
		save.addActionListener(lis);
		save.setEnabled(false);
		saveConfig.add(save);
		saveAs = new JMenuItem("Save As");
		saveAs.setFont(SnakesAndLaddersGui.fontMenu);
		saveAs.addActionListener(lis);
		saveAs.setEnabled(false);
		saveConfig.add(saveAs);
		
		
		//Settings
		JMenu settings = new JMenu("Settings");
		settings.setFont(SnakesAndLaddersGui.fontMenu);
		menuBar.add(settings);
		//voci
		setOpt = new JMenuItem("Set options");
		setOpt.setFont(SnakesAndLaddersGui.fontMenu);
		setOpt.addActionListener(lis);
		setOpt.setEnabled(false);
		settings.add(setOpt);
		JMenuItem music = new JMenuItem("Stop music");
		music.setFont(SnakesAndLaddersGui.fontMenu);
		music.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(music.getText().charAt(0) == 'S') {
					music.setText("Play music");
					FloatControl control = (FloatControl) SnakesAndLaddersGui.ost.getControl(FloatControl.Type.MASTER_GAIN);
					control.setValue(control.getMinimum());
				}
				else {
					music.setText("Stop music");
					FloatControl control = (FloatControl) SnakesAndLaddersGui.ost.getControl(FloatControl.Type.MASTER_GAIN);
					control.setValue(control.getMaximum());
				}
			}
		});
		settings.add(music);
        
        //help
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setFont(SnakesAndLaddersGui.fontMenu);
		menuBar.add(helpMenu);
		//voci
		rules = new JMenuItem("Rules");
		rules.setFont(SnakesAndLaddersGui.fontMenu);
		rules.addActionListener(lis);
		helpMenu.add(rules);
		
		main = new MainPanel();
		gameFrame.setContentPane(main);
		observer = new Observer(main.getInfoLabel());
		gameFrame.validate();
		gameFrame.repaint();
		
		gameFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (exitConsent())
					System.exit(0);
			}
		});
	}
	
	public ActionListener getApplyListener() {
		return applyListener;
	}
	
	public Observer getObserver() {
		return observer;
	}
	
	public JFrame getGameFrame() {
		return gameFrame;
	}
	
	public MainPanel getMainPanel() {
		return main;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}
	
	public JMenuItem getOpt() {
		return setOpt;
	}
	
	public boolean isGameStarted() {
		return gameStarted;
	}
	
	public void setStarted() {
		gameStarted = true;
	}
	
 	private class ActionLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == rules) {
				JOptionPane.showMessageDialog(null, "Snakes and Ladders è un gioco da tavolo tradizionale nato in Inghilterra. "
						+"Si tratta di un semplicissimo gioco di percorso simile al gioco dell'oca. \n Come nel gioco dell'oca, l'esito "
						+"di una partita è completamente determinato dal lancio dei dadi. Un giocatore che arriva in una casella posta "
						+"ai \n piedi di una scala viene spostato alla casella in cima alla scala; viceversa, un giocatore che arriva in una "
						+"casella con la bocca di un serpente \n retrocede fino alla coda. Il giocatore può scegliere di rendere il gioco "
						+"più interessante inserendo delle caselle speciali."
						+"\n CASELLE DI SOSTA: \n PANCHINA -> il giocatore rimane fermo un turno. \n LOCANDA -> il giocatore rimane fermo tre turni."
						+"\n CASELLE PREMIO: \n DADI -> il giocatore può lanciare nuovamente i dadi ed avanzare ulteriormente. "
						+"\n MOLLA -> il giocatore avanza dello stesso numero di caselle indicato dal precedente lancio di dadi."
						+"\n Il giocatore può scegliere di utilizzare anche un mazzo di CARTE aventi gli stessi effetti delle caselle speciali. "
						+"\n In aggiunta, è possibile aggiungere anche la carta DIVIETO DI SOSTA che consente al giocatore di annullare l'effetto SOSTA "
						+"di una casella o di una carta. \n ","About", JOptionPane.INFORMATION_MESSAGE);
			} // about
			
			else if (e.getSource() == config) {
				boolean opened = false;
				if(settingsWindow == null || !settingsWindow.hasApplied()) {
					configuration = new Configuration();
					settingsWindow = new SettingsWindow();
					settingsWindow.getSettingsFrame().setVisible(true);
					opened = true;
				}
				else if(settingsWindow.hasApplied()) {
					int option = JOptionPane.showConfirmDialog(null,
							"Creando una nuova partita si perderanno tutti i dati non salvati! \nNon sarà possibile continuare la partita corrente. \nContinuare?", "Warning", JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION) {
						savedConfig = null;
						gameFrame.setTitle(title);
						save.setEnabled(false);
						saveAs.setEnabled(false);
						configuration = new Configuration();
						settingsWindow = new SettingsWindow();
						settingsWindow.getSettingsFrame().setVisible(true);
						opened = true;
					}
				}
				if(opened) {
					settingsWindow.getSettingsFrame().setAlwaysOnTop(true);
					gameFrame.setFocusableWindowState(false);
					gameFrame.setEnabled(false);
				}
			}
			
			else if(e.getSource() ==  load) {
				JFileChooser chooser=new JFileChooser();
				try{
					if( chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION ){
						if( !chooser.getSelectedFile().exists() )
							JOptionPane.showMessageDialog(null,"File inesistente!"); 
						else{	
							boolean checkFile = false;
							try{
								configuration = new Configuration();
								checkFile = configuration.loadJson(chooser.getSelectedFile());
							}catch(IOException ioe){
  							   JOptionPane.showMessageDialog(null,"Fallimento apertura!");
  							}
							if(checkFile) {
								savedConfig = chooser.getSelectedFile();
								gameFrame.setTitle(title+" - "+savedConfig.getName());
								if(main.getAutoma() != null) {
									gameStarted = false;
									main.getAutoma().interrupt();
								}
								newGameEnable();
								main.paintBoard();
								settingsWindow = new SettingsWindow();
								settingsWindow.setApplied();
								settingsWindow.getPageOne().getFromConfiguration();
							}
							else
								JOptionPane.showMessageDialog(null,"File selezionato non valido.", "Error", JOptionPane.ERROR_MESSAGE); 
						}
					}
					else JOptionPane.showMessageDialog(null,"Caricamento del file non effettuato.");
	  			   }catch( Exception exc ){
	  				   exc.printStackTrace();
	  			   }
			}//load
			
			else if(e.getSource() == exit) {
				if (exitConsent())
					System.exit(0);
			}
			
			else if(e.getSource() == save) {
					JFileChooser chooser = new JFileChooser();
					if( savedConfig!=null ){
						int ans=JOptionPane.showConfirmDialog(null,"Sovrascrivere "+savedConfig.getAbsolutePath()+" ?");
						if( ans==JOptionPane.YES_OPTION) {
							configuration.saveJson(savedConfig);
							JOptionPane.showMessageDialog(null, "Salvataggio effettuato");
						}
						else JOptionPane.showMessageDialog(null,"Nessun salvataggio effettuato.");
						return;
					}
					else if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						savedConfig = chooser.getSelectedFile();
						if(!savedConfig.getName().endsWith(".json"))
							savedConfig = new File(savedConfig.getAbsolutePath()+".json");
						configuration.saveJson(savedConfig);
						gameFrame.setTitle(title+" - "+savedConfig.getName());
						JOptionPane.showMessageDialog(null, "Salvataggio effettuato.");
					}
					else JOptionPane.showMessageDialog(null,"Nessun salvataggio effettuato.");
			}//save
			
			else if(e.getSource() == saveAs) {
				JFileChooser chooser = new JFileChooser();
				if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					savedConfig = chooser.getSelectedFile();
					if(!savedConfig.getName().endsWith(".json"))
						savedConfig = new File(savedConfig.getAbsolutePath()+".json");
					configuration.saveJson(savedConfig);
					gameFrame.setTitle(title+" - "+savedConfig.getName());
					JOptionPane.showMessageDialog(null, "Salvataggio effettuato.");
				}
				else JOptionPane.showMessageDialog(null,"Nessun salvataggio effettuato.");
			}//saveAs
			
			else if (e.getSource() == setOpt) {
				settingsWindow.getSettingsFrame().setAlwaysOnTop(true);
				gameFrame.setFocusableWindowState(false);
				gameFrame.setEnabled(false);
				settingsWindow.getSettingsFrame().setContentPane(settingsWindow.getPageOne());
				settingsWindow.getSettingsFrame().setVisible(true);
			}
		}
	}
	
	private class ApplyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			newGameEnable();
			settingsWindow.getSettingsFrame().setAlwaysOnTop(false);
			gameFrame.setFocusableWindowState(true);
			gameFrame.setEnabled(true);
			gameFrame.requestFocus();
		}
	}
	
	private void newGameEnable() {
		if(gameStarted) {
			Image pic = new ImageIcon(ResourceLoader.loadResource("start.png")).getImage();
			main.getStart().setIcon(new ImageIcon(pic.getScaledInstance(60,60, Image.SCALE_SMOOTH)));
		}
		gameStarted = false;
		main.enableDeck(configuration.getDeck());
		Image pic = new ImageIcon(ResourceLoader.loadResource("start.png")).getImage();
		main.getStart().setIcon(new ImageIcon(pic.getScaledInstance(60,60, Image.SCALE_SMOOTH)));
		main.getStart().setEnabled(true);
		main.getRoll().setEnabled(false);
		main.getManual().setEnabled(true);
		main.getAuto().setEnabled(true);
		main.getAuto().setSelected(true);
		main.getCurrPlayerLabel().setText("");
		main.getPawnLabel().setIcon(IconPool.getInstance().get(IconPool.PAWN0));
		main.getInfoLabel().setText("Waiting for game to start");
		main.pause();
		save.setEnabled(true);
		saveAs.setEnabled(true);
		setOpt.setEnabled(true);
	}
	
	private boolean exitConsent() {
		int option = JOptionPane.showConfirmDialog(null,
				"Uscendo si perderanno tutti i dati non salvati! \nContinuare?", "Warning", JOptionPane.YES_NO_OPTION);
		return option == JOptionPane.YES_OPTION;
	}// consensoUscita
}
