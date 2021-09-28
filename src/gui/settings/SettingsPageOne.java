package gui.settings;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import command.Draw;
import components.Cell;
import gui.game.GameWindow;
import gui.game.SnakesAndLaddersGui;

@SuppressWarnings("serial")
public class SettingsPageOne extends JPanel {
	private SettingsWindow parent;
	
	private JPanel playerPan;
	private JSpinner playerSpinner, diceSpinner, cardSpinner;
	private int currentVal = 2;
	private JScrollPane sp;
	private JCheckBox doubleSix, oneDie, deck, noStop;
	private JButton back, nextPage;
	
	
	public SettingsPageOne(SettingsWindow parent) {
		this.parent = parent;
		setPreferredSize(new Dimension(720,700));
		setLayout(null);

		//player panel
		JLabel l = new JLabel("Number of players: ",JLabel.LEFT);
        l.setBounds(50,20, 250,50);
        l.setFont(SnakesAndLaddersGui.fontText);
        add(l);
        //model(currValue, minValue, maxValue, step)
        SpinnerModel model = new SpinnerNumberModel(currentVal, 2, 6, 1);
        playerSpinner = new JSpinner(model);
        playerSpinner.setFont(SnakesAndLaddersGui.fontText);
        playerSpinner.setBounds(230, 30, 50, 30);
        playerSpinner.addChangeListener(new PlayerSpinnerListener());
        add(playerSpinner);
		playerPan = new JPanel();
		PlayerPrefab player1 = new PlayerPrefab(1), player2 = new PlayerPrefab(2);
		playerPan.add(player1);
		playerPan.add(player2);
		//lo spinner serve sia a settare il numero di giocatori che a manipolare le dimensioni del playerPanel
		playerPan.setPreferredSize(new Dimension(400, 60*(Integer)playerSpinner.getValue()));
		sp = new JScrollPane(playerPan);
		sp.setBounds(50,70,400,200);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(sp);
        
        //Dice settings
        l = new JLabel("Number of Dice: ", JLabel.LEFT);
        l.setBounds(50,280, 250,50);
        l.setFont(SnakesAndLaddersGui.fontText);
        add(l);
        SpinnerModel mod = new SpinnerNumberModel(2, 1, 2, 1);
        diceSpinner = new JSpinner(mod);
        diceSpinner.setFont(SnakesAndLaddersGui.fontText);
        diceSpinner.setBounds(230, 290, 50, 30);
        diceSpinner.addChangeListener(new DiceSpinnerListener());
        add(diceSpinner);
        
        //Dice options
        doubleSix = new JCheckBox("Double Six Reroll");
        oneDie = new JCheckBox("One die for victory");
        doubleSix.setBounds(45, 320, 150, 50);
        doubleSix.setFont(SnakesAndLaddersGui.fontText);
        oneDie.setFont(SnakesAndLaddersGui.fontText);
        oneDie.setBounds(230, 320, 160, 50);
        add(doubleSix);
        add(oneDie);
        
        //Deck section
        deck = new JCheckBox("Deck");
        deck.setBounds(45, 370, 150, 50);
        deck.setFont(SnakesAndLaddersGui.fontText);
        deck.addActionListener(new DeckListener());
        add(deck);
        
        l = new JLabel("Number of cards: ", JLabel.LEFT);
        l.setBounds(70,410, 250,50);
        l.setFont(SnakesAndLaddersGui.fontText);
        add(l);
        SpinnerModel m = new SpinnerNumberModel(10, 5, Integer.MAX_VALUE, 1);
        cardSpinner = new JSpinner(m);
        cardSpinner.setEnabled(false);
        cardSpinner.setFont(SnakesAndLaddersGui.fontText);
        cardSpinner.setBounds(230, 420, 50, 30);
        add(cardSpinner);
        
        noStop = new JCheckBox("Add noStop card");
        noStop.setEnabled(false);
        noStop.setBounds(65, 450, 150, 50);
        noStop.setFont(SnakesAndLaddersGui.fontText);
        add(noStop);
        
        //bottoni pie di pagina
        back = new JButton("Back");
        nextPage = new JButton("Next Page");
        back.setFont(SnakesAndLaddersGui.fontMenu);
        nextPage.setFont(SnakesAndLaddersGui.fontMenu);
        back.setBounds(220, 550, 150, 50);
        nextPage.setBounds(410, 550, 150, 50);
        ActionListener buttonListener = new ButtonListener();
        back.addActionListener(buttonListener);
        nextPage.addActionListener(buttonListener);
        add(back);
        add(nextPage);
	}
	
	public int getnPlayers() {
		return (Integer)playerSpinner.getValue();
	}
	
	public int getnDice() {
		return (Integer)diceSpinner.getValue();
	}
	
	public int getnCards() {
		return (Integer)cardSpinner.getValue();
	}
	
	public JCheckBox getDoubleSix() {
		return doubleSix;
	}
	
	public JCheckBox getOneDie() {
		return oneDie;
	}
	
	public JCheckBox getDeck() {
		return deck;
	}
	
	public JCheckBox getNoStop() {
		return noStop;
	}
	
	public JPanel getPlayerPan() {
		return playerPan;
	}
	
	private class PlayerSpinnerListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			if((Integer)playerSpinner.getValue() > currentVal) {
				PlayerPrefab player = new PlayerPrefab(currentVal+1);
				playerPan.add(player);
			}
			else 
				playerPan.remove(currentVal-1);

			currentVal = (Integer)playerSpinner.getValue();
			playerPan.setPreferredSize(new Dimension(400, 60*(Integer)playerSpinner.getValue()));
			playerPan.validate();
			playerPan.repaint();
			sp.validate();
		}
	}
	
	private class DiceSpinnerListener implements ChangeListener{
		public void stateChanged(ChangeEvent e) {
			if((Integer)diceSpinner.getValue() == 1) {
				oneDie.setEnabled(false);
				doubleSix.setEnabled(false);
				oneDie.setSelected(false);
				doubleSix.setSelected(false);
			}
			else {
				oneDie.setEnabled(true);
				doubleSix.setEnabled(true);
			}
		}
	}
	
	private class DeckListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			cardSpinner.setEnabled(deck.isSelected());
			if(!deck.isSelected())
				noStop.setSelected(deck.isSelected());
			
			if(parent.getPageTwo().getSpecialCells().isSelected()) {
				if(!deck.isSelected()) {
					parent.getPageTwo().getDrawC().setSelected(deck.isSelected());
					parent.getPageTwo().getPlaceDraw().setEnabled(deck.isSelected());
					Map<Integer, Cell> map = parent.getPageTwo().getTmpCells();
					Integer[] keys = map.keySet().toArray(new Integer[] {});
					for(int i=0; i<keys.length; i++)
						if(map.get(keys[i]).getEffect() instanceof Draw)
							map.remove(keys[i]);
					map = parent.getPageTwo().getCells();
					keys = map.keySet().toArray(new Integer[] {});
					for(int i=0; i<keys.length; i++)
						if(map.get(keys[i]).getEffect() instanceof Draw)
							map.remove(keys[i]);
				}
				parent.getPageTwo().getDrawC().setEnabled(deck.isSelected());
			}
			noStop.setEnabled(deck.isSelected());
		}
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == back) {
				parent.getSettingsFrame().setVisible(false);
				parent.getSettingsFrame().setAlwaysOnTop(false);
				GameWindow.getInstance().getGameFrame().setFocusableWindowState(true);
				GameWindow.getInstance().getGameFrame().setEnabled(true);
				GameWindow.getInstance().getGameFrame().requestFocus();
				
				if(parent.hasApplied()) {
					getFromConfiguration();
				}
			}
			else {
				parent.getSettingsFrame().setContentPane(parent.getPageTwo());
				parent.getSettingsFrame().validate();
				parent.getSettingsFrame().repaint();
			}
		}
	}
	
	public void getFromConfiguration() {
		SettingsPageTwo page = parent.getPageTwo();
		Configuration config = GameWindow.getInstance().getConfiguration();
		
		//setting attributes from Configuration class
		//attributes from page two
		page.getDefaultOpt().setSelected(config.getDefaultSet());
		page.getRows().setValue(config.getnRows());
		page.getRows().setEnabled(!config.getDefaultSet());
		page.getCols().setValue(config.getnCols());
		page.getCols().setEnabled(!config.getDefaultSet());
		page.getSpecialCells().setSelected(config.getSpecialC());
		page.getStopC().setSelected(config.getStopC());
		page.getStopC().setEnabled(config.getSpecialC());
		page.getPlaceStop().setEnabled(config.getStopC());
		page.getRewardC().setSelected(config.getRewardC());
		page.getRewardC().setEnabled(config.getSpecialC());
		page.getPlaceReward().setEnabled(config.getRewardC());
		page.getDrawC().setSelected(config.getDrawC());
		page.getDrawC().setEnabled(config.getSpecialC());
		page.getPlaceDraw().setEnabled(config.getDrawC());
		page.setCells(config.getCells());
		page.setOccupiedCells(config.getOccupiedCells());
		
		//attributes from page one
		playerSpinner.setValue(config.getnPlayers());
		currentVal = config.getnPlayers();
		diceSpinner.setValue(config.getnDice());
		cardSpinner.setValue(config.getnCards());
		cardSpinner.setEnabled(config.getDeck());
		noStop.setEnabled(config.getDeck());
		doubleSix.setSelected(config.getDoubleSixReroll());
		oneDie.setSelected(config.getOneDie());
		deck.setSelected(config.getDeck());
		noStop.setSelected(config.getNoStop());
		if(playerPan.getComponents().length == config.getNames().size())
			for(int i=0; i<config.getNames().size(); i++) 
				((PlayerPrefab)playerPan.getComponent(i)).setInputName(config.getNames().get(i));
		else if(playerPan.getComponents().length < config.getNames().size()) {
			int i = 0;
			for(; i<playerPan.getComponents().length; i++)
				((PlayerPrefab)playerPan.getComponent(i)).setInputName(config.getNames().get(i));
			for(; i<config.getNames().size(); i++) {
				PlayerPrefab player = new PlayerPrefab(i+1);
				player.setInputName(config.getNames().get(i));
				playerPan.add(player);
			}
		}
		else { //playerPan.getComponents().length > config.getNames().size()
			int i = 0;
			for(; i<config.getNames().size(); i++)
				((PlayerPrefab)playerPan.getComponent(i)).setInputName(config.getNames().get(i));
			int previousLength = playerPan.getComponents().length;
			for(; i<previousLength; i++) //finchè non arrivo all'ultimo component, rimuovo all'indietro
				playerPan.remove(playerPan.getComponents().length-1);
		}
	}
	
}
