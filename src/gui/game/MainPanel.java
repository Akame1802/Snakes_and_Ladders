package gui.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import command.*;
import components.Card;
import components.Cell;
import components.GameEngine;
import components.Player;
import gui.settings.Configuration;
import gui.state.AutomaHandler;
import gui.state.Idle;
import gui.state.State;
import gui.util.IconPool;
import gui.util.ResourceLoader;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	private JPanel boardPan, laddersPan;
	private DeckPanel deckPanel;
	private JLabel currPlayer, pawn, infoMove, deckLabel;
	private JButton start, roll;
	private JRadioButton manual, auto;
	
	private Command[] cardEffects = { new Stop(1), new Stop(3), new Reward(true), new Reward(false), new Stop(0) };
	private Random rand = new Random();
	private Queue<State> automa;
	private AutomaHandler automaHandler;
	private boolean paused = false, diceRolled = true;
	
	public MainPanel() {
		setPreferredSize(new Dimension(1310,1020));
		setLayout(null);
		
		//pannello distinto per scale
		laddersPan = new JPanel();
		laddersPan.setBounds(30, 10, 860,760);
		laddersPan.setOpaque(false);
		laddersPan.setLayout(null);
		add(laddersPan);
		
		//game board panel
		boardPan = new JPanel();
		boardPan.setBounds(30, 10, 860,760);
		boardPan.setLayout(new GridBagLayout());
		add(boardPan);
		
		//side panel (player, dice)
		JPanel sidePan = new JPanel();
		sidePan.setBounds(910, 10, 365,935);
		sidePan.setLayout(null);
		sidePan.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//player
		JLabel l = new JLabel("Turn:");
		l.setFont(SnakesAndLaddersGui.fontMain);
		l.setBounds(160, 20, 100,50);
		sidePan.add(l);
		currPlayer = new JLabel("");
		currPlayer.setFont(new Font("Calibri", Font.BOLD, 24));
		currPlayer.setForeground(Color.white);
		currPlayer.setHorizontalAlignment(JLabel.CENTER);
		currPlayer.setVerticalAlignment(JLabel.CENTER);
		currPlayer.setBounds(15, 60, 335, 50);
		sidePan.add(currPlayer);
		pawn = new JLabel();
		pawn.setBounds(140, 110, 100,120);
		pawn.setIcon(IconPool.getInstance().get(IconPool.PAWN0));
		sidePan.add(pawn);
		
		//dice
		JLabel dice = new JLabel();
		dice.setBounds(120, 320, 120,120);
		Image pic = new ImageIcon(ResourceLoader.loadResource("dice.png")).getImage();
		dice.setIcon(new ImageIcon(pic.getScaledInstance(120,120, Image.SCALE_SMOOTH)));
		sidePan.add(dice);
		roll = new JButton("Roll");
		roll.setFont(SnakesAndLaddersGui.fontMenu);
        roll.setBounds(110, 455, 150, 50);
        roll.setEnabled(false);
        roll.addActionListener(new RollListener());
        sidePan.add(roll);
        
        //deck
        deckLabel = new JLabel("Deck");
		deckLabel.setFont(SnakesAndLaddersGui.fontMain);
		deckLabel.setBounds(160, 590, 100,50);
		sidePan.add(deckLabel);
		deckPanel = new DeckPanel();
		deckPanel.setBounds(80, 650, 220, 220);
		deckPanel.setBackground(new Color(0,0,0,0));
		sidePan.add(deckPanel);
		
		sidePan.setBackground(new Color(108,219,255));
		add(sidePan);
		
		//bottom panel
		JPanel bottomPan = new JPanel();
		bottomPan.setBounds(30,780,860,165);
		bottomPan.setLayout(null);
		bottomPan.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//info moves
        infoMove = new JLabel("Waiting for game to start");
        infoMove.setFont(SnakesAndLaddersGui.fontMain);
        infoMove.setHorizontalAlignment(JLabel.CENTER);
		infoMove.setBounds(180, 30, 480,50);
		infoMove.setBorder(BorderFactory.createLineBorder(Color.blue));
        bottomPan.add(infoMove);
        
        ActionListener btnListener = new ButtonListener();
        
        //auto/manual option
        ButtonGroup options = new ButtonGroup();
        auto = new JRadioButton("Auto Mode");
        auto.setFont(SnakesAndLaddersGui.fontMain);
		auto.setBounds(180, 100, 150,50);
		auto.setSelected(true);
		auto.setEnabled(false);
		auto.setContentAreaFilled(false);
		auto.setBorderPainted(false);
		auto.addActionListener(btnListener);
		bottomPan.add(auto);
        manual = new JRadioButton("Manual Mode");
        manual.setFont(SnakesAndLaddersGui.fontMain);
		manual.setBounds(380, 100, 150,50);
		manual.setEnabled(false);
		manual.setContentAreaFilled(false);
		manual.setBorderPainted(false);
		manual.addActionListener(btnListener);
		bottomPan.add(manual);
		options.add(auto);
		options.add(manual);
		
		//start/stop
		start = new JButton();
		start.setBounds(600, 90, 60,60);
		pic = new ImageIcon(ResourceLoader.loadResource("start.png")).getImage();
		start.setIcon(new ImageIcon(pic.getScaledInstance(60,60, Image.SCALE_SMOOTH)));
		start.setContentAreaFilled(false);
		start.setBorderPainted(false);
		start.setEnabled(false);
		start.addActionListener(btnListener);
		bottomPan.add(start);
		
		bottomPan.setBackground(new Color(108,219,255));
		add(bottomPan);
	}
	
	public JPanel getBoardPan() {
		return boardPan;
	}
	
	public JButton getStart() {
		return start;
	}
	
	public JButton getRoll() {
		return roll;
	}
	
	public JRadioButton getManual() {
		return manual;
	}
	
	public JRadioButton getAuto() {
		return auto;
	}
	
	public JLabel getInfoLabel() {
		return infoMove;
	}
	
	public JLabel getCurrPlayerLabel() {
		return currPlayer;
	}
	
	public JLabel getPawnLabel() {
		return pawn;
	}
	
	public DeckPanel getDeckPanel() {
		return deckPanel;
	}
	
	public void enableDeck(boolean enable) {
		deckPanel.setVisible(enable);
		deckLabel.setVisible(enable);
	}
	
	public void paintBoard() {
		Configuration config = GameWindow.getInstance().getConfiguration();
		boardPan.removeAll();
		laddersPan.removeAll();
		int r = config.getnRows(), c = config.getnCols();
		
		JPanel boardLadders = new JPanel();
		boardLadders.setOpaque(false);
		boardLadders.setLayout(null);
		boardLadders.setBounds(860/2-(50*c/2), 760/2-(50*r/2), 50*c, 50*r);
		laddersPan.add(boardLadders);
		
		Color[] colors = { new Color(152,222,219), new Color(249,240,162) };
		GridBagConstraints gbc = new GridBagConstraints();
		Map<Integer, Cell> cellMap = config.getCells();
		JPanel cell;
		JLabel l;
		for(int i=0; i<r*c; i++) {
			cell = new JPanel();
			cell.setBackground(colors[i%2]);
			cell.setPreferredSize(new Dimension(50,50));
			cell.setBorder(BorderFactory.createLineBorder(Color.black));
			cell.setLayout(null);
			//dettaglio estetico riguardante la rappresentazione delle label all'interno delle celle
			l = new JLabel(((i+1)<10? "  " : (i+1)<100? " " : "")+(i+1));
			l.setFont(SnakesAndLaddersGui.fontCell);
			l.setForeground(Color.blue);
			l.setBounds(0, 0, 50, 50);
			cell.add(l);
			gbc.gridx = (i/c)%2 == 0? i%c : c - 1 - i%c; 
			gbc.gridy = r - 1 - i/c;
			//in questo modo la riga zero non è la prima dall'alto, ma la prima dal basso
			
			//disegno la label corretta in base al tipo di effetto
			if(cellMap.containsKey(i)) {
				if(cellMap.get(i).getEffect() instanceof Reward) {
					l = new JLabel();
					l.setBounds(0, 0, 50,50);
					if(((Reward)cellMap.get(i).getEffect()).isSpringOrDice())
						l.setIcon(IconPool.getInstance().get(IconPool.DICE));
					else 
						l.setIcon(IconPool.getInstance().get(IconPool.SPRING));
					cell.add(l);
				}
				else if(cellMap.get(i).getEffect() instanceof Draw) {
					l = new JLabel();
					l.setBounds(0, 0, 50,50);
					l.setIcon(IconPool.getInstance().get(IconPool.CARD));
					cell.add(l);
				}
				else if(cellMap.get(i).getEffect() instanceof Stop) {
					l = new JLabel();
					l.setBounds(0, 0, 50,50);
					if(((Stop)cellMap.get(i).getEffect()).getTurns() == 1)
						l.setIcon(IconPool.getInstance().get(IconPool.BENCH));
					else 
						l.setIcon(IconPool.getInstance().get(IconPool.INN));
					cell.add(l);
				}
				else if(cellMap.get(i).getEffect() instanceof StairSnake) {
					StairSnake ss = (StairSnake)cellMap.get(i).getEffect();
					if(!ss.isLadder()) {//serpente
						l = new JLabel();
						l.setBounds(0, 0, 50,50);
						l.setIcon(IconPool.getInstance().get(IconPool.SNAKE_HEAD));
						cell.add(l);
						l = new JLabel();
						l.setBounds(0, 0, 50, 50);
						l.setIcon(IconPool.getInstance().get(IconPool.SNAKE_TAIL));
						((JPanel)boardPan.getComponent(ss.getToCell())).add(l);
						
						int i_f = (i/c)-1, j_f = i_f%2==0? c - 1 - i%c : i%c;
						int i_t = ss.getToCell()/c, j_t = i_t%2==0? ss.getToCell()%c : c - 1 - ss.getToCell()%c;
						paintSnake(i_f, j_f, i_t, j_t, c);
					}
					else {//scala
						l = new LadderLabel();
						int i_feet = i/c, j_feet = i_feet%2==0? i%c : c - 1 - i%c, 
							i_top = ss.getToCell()/c, j_top = i_top%2==0? ss.getToCell()%c : c - 1 - ss.getToCell()%c;
						
						int widthUnits = Math.abs(j_feet-j_top)+1;
						int heightUnits = Math.abs(i_feet-i_top)+1;
						//motivo: i componenti di java swing si sviluppano dall'alto verso il basso e da sinistra verso destra
						int x_start = j_feet < j_top? j_feet : j_top;
						int y_start = r - 1 - i_top;
						l.setBounds(x_start*50, y_start*50, widthUnits*50, heightUnits*50);
						((LadderLabel)l).setDirection(j_feet < j_top);
						boardLadders.add(l);
					}
				}
			}
			//aggiungo la label sulla quale disegnerò le pedine dei giocatori
			l = new PlayerLabel();
			l.setBounds(0, 0, 50,50);
			//aggiungo i giocatori sulla prima cella
			if(i == 0)
				for(int id=0; id<config.getnPlayers(); id++)
					((PlayerLabel)l).placePlayer(id);
			cell.add(l, 0);
			
			boardPan.add(cell, gbc);
			
			//label cella finale
			if(i == r*c-1) {
				l = new JLabel();
				l.setBounds(0, 0, 50,50);
				l.setIcon(IconPool.getInstance().get(IconPool.FINISH));
				cell.add(l);
			}
		}//for
		
		GameWindow.getInstance().getGameFrame().validate();
		GameWindow.getInstance().getGameFrame().repaint();
	}
	
	//i_f_p è l'indice di riga della cella puntata precedentemente
	//i_f è l'indice di riga della cella corrente
	private void paintSnake(int i_f, int j_f, int i_t, int j_t, int c) {
		int i_f_p = i_f+1;
		int j_f_p = j_f;
		paintSnakeImpl(i_f, j_f, i_f_p, j_f_p, i_t, j_t, c);
	}
	
	private void paintSnakeImpl(int i_f, int j_f, int i_f_p, int j_f_p, int i_t, int j_t, int c) {
		if(i_f == i_t)
			return; //sono arrivata alla coda
		
		IconPool ic = IconPool.getInstance();
		ImageIcon icon;
		boolean connector = true;
		JPanel cell = (JPanel)boardPan.getComponent(i_f*c + (i_f%2==0? j_f : c - 1 - j_f));
		JLabel l = new JLabel();
		l.setBounds(0, 0, 50, 50);
		if(i_f_p != i_f) { //il nuovo pezzo si trova sotto quello precedente
			if(j_f < j_t) { //il pezzo di serpente corrente è a sinistra della coda
				icon = ic.get(IconPool.SNAKE_CONNECTOR_UP_TO_RX);
				if(i_t <= i_f-2) {
					connector = rand.nextFloat()>0.5;
					icon = connector? icon : ic.get(IconPool.SNAKE_BODY_V);
				}
				l.setIcon(icon);
				cell.add(l);
				paintSnakeImpl(connector? i_f : i_f-1, connector? j_f+1 : j_f, i_f, j_f, i_t, j_t, c);
			}
			else if(j_f > j_t) { //il pezzo di serpente corrente è a destra della coda
				icon = ic.get(IconPool.SNAKE_CONNECTOR_UP_TO_LX);
				if(i_t <= i_f-2) {
					connector = rand.nextFloat()>0.5;
					icon = connector? icon : ic.get(IconPool.SNAKE_BODY_V);
				}
				l.setIcon(icon);
				cell.add(l);
				paintSnakeImpl(connector? i_f : i_f-1, connector? j_f-1 : j_f, i_f, j_f, i_t, j_t, c);
			}
			else { //j_f == j_t, il pezzo di serpente corrente è sopra la coda
				l.setIcon(ic.get(IconPool.SNAKE_BODY_V));
				cell.add(l);
				paintSnakeImpl(i_f-1, j_f, i_f, j_f, i_t, j_t, c);
			}
		}
		else { //i_f_p == i_f, il nuovo pezzo è sulla stessa riga di quello precedente 
			if(j_f_p < j_f) { //il pezzo precedente è a sinistra del pezzo corrente
				if(j_f == j_t) { //sono sulla stessa colonna della coda
					l.setIcon(ic.get(IconPool.SNAKE_CONNECTOR_LX_TO_DOWN));
					cell.add(l);
					paintSnakeImpl(i_f-1, j_f, i_f, j_f, i_t, j_t, c);
				}
				else { //altrimenti no
					if(i_f == i_t+1) {
						l.setIcon(ic.get(IconPool.SNAKE_BODY_H));
						cell.add(l);
						paintSnakeImpl(i_f, j_f+1, i_f, j_f, i_t, j_t, c);
					}
					else {
						connector = rand.nextFloat()>0.5;
						icon = connector? ic.get(IconPool.SNAKE_CONNECTOR_LX_TO_DOWN) : ic.get(IconPool.SNAKE_BODY_H);
						l.setIcon(icon);
						cell.add(l);
						paintSnakeImpl(connector? i_f-1 : i_f, connector? j_f : j_f+1, i_f, j_f, i_t, j_t, c);
					}
				}
			}
			else { //il pezzo precedente è a destra del pezzo corrente
				if(j_f == j_t) { //sono sulla stessa colonna della coda
					l.setIcon(ic.get(IconPool.SNAKE_CONNECTOR_RX_TO_DOWN));
					cell.add(l);
					paintSnakeImpl(i_f-1, j_f, i_f, j_f, i_t, j_t, c);
				}
				else {//altrimenti no
					if(i_f == i_t+1) {
						l.setIcon(ic.get(IconPool.SNAKE_BODY_H));
						cell.add(l);
						paintSnakeImpl(i_f, j_f-1, i_f, j_f, i_t, j_t, c);
					}
					else {
						connector = rand.nextFloat()>0.5;
						icon = connector? ic.get(IconPool.SNAKE_CONNECTOR_RX_TO_DOWN) : ic.get(IconPool.SNAKE_BODY_H);
						l.setIcon(icon);
						cell.add(l);
						paintSnakeImpl(connector? i_f-1 : i_f, connector? j_f : j_f-1, i_f, j_f, i_t, j_t, c);
					}
				}
			}
		}
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public boolean hasRolled() {
		return diceRolled;
	}
	
	public boolean isManual() {
		return manual.isSelected();
	}
	
	public void pause() {
		paused = true;
	}
	
	public void waitForRoll() {
		diceRolled = false;
	}
	
	public AutomaHandler getAutoma() {
		return automaHandler;
	}
	
	public void endGame() {
		Image pic = new ImageIcon(ResourceLoader.loadResource("start.png")).getImage();
		start.setIcon(new ImageIcon(pic.getScaledInstance(60,60, Image.SCALE_SMOOTH)));
		start.setEnabled(false);
		roll.setEnabled(false);
		auto.setEnabled(false);
		manual.setEnabled(false);
	}
	
	private class ButtonListener implements ActionListener {
		private ImageIcon pause, play;
		
		public ButtonListener() {
			super();
			Image pic = new ImageIcon(ResourceLoader.loadResource("pause.png")).getImage();
			pause = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
			pic = new ImageIcon(ResourceLoader.loadResource("start.png")).getImage();
			play = new ImageIcon(pic.getScaledInstance(60,60, Image.SCALE_SMOOTH));
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == start) {
				GameWindow.getInstance().getOpt().setEnabled(false);
				if(paused) {
					start.setIcon(pause);
					paused = false;
				}
				else {
					start.setIcon(play);
					paused = true;
					automaHandler.interrupt();
				}
				if(!GameWindow.getInstance().isGameStarted()) {
					GameWindow.getInstance().setStarted();
					Configuration config = GameWindow.getInstance().getConfiguration();

					//creo la lista di celle, cards e players da passare come parametro a init()
					List<Cell> cells = new ArrayList<>();
					for(int i=0; i<config.getnRows()*config.getnCols()-1; i++) {
						if(config.getCells().containsKey(i))
							cells.add(config.getCells().get(i));
						else
							cells.add(new Cell(new Empty()));
					}
					cells.add(new Cell(new EndGame()));

					LinkedList<Card> cards = new LinkedList<>();
					int max = config.getNoStop()? cardEffects.length : cardEffects.length-1;
					for(int i=0; i<max; i++)
						cards.add(new Card(cardEffects[i])); //mi assicuro che nel deck ci sia almeno una carta di ogni tipo
					for(int i=max; i<config.getnCards(); i++)
						cards.add(new Card(cardEffects[rand.nextInt(max)]));

					ArrayList<Player> players = new ArrayList<>();
					for(int i=0; i<config.getnPlayers(); i++)
						players.add(new Player(config.getNames().get(i), i));

					automa = new LinkedList<>();
					
					//è il main panel che si occupa dell'inizializzazione del Game Engine e dell'automa
					GameEngine.getInstance().init(config.getnPlayers(), config.getnDice(), config.getnRows(),
							config.getnCols(), config.getOneDie(), config.getDoubleSixReroll(),
							cells, cards, players, automa);

					automa.add(new Idle(GameWindow.getInstance().getObserver()));
					automaHandler = new AutomaHandler(automa, config.getnPlayers());
					automaHandler.start();
				}
			}
			if(e.getSource() == auto) {
				diceRolled = true;
				automaHandler.interrupt();
			}
			if(e.getSource() == manual) {
				diceRolled = false;
				automaHandler.interrupt();
			}
		}
	}

	private class RollListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			diceRolled = true;
			automaHandler.interrupt();
		}
	}
}
