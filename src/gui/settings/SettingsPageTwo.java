package gui.settings;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import components.Cell;
import command.*;
import gui.game.GameWindow;
import gui.game.SnakesAndLaddersGui;

@SuppressWarnings("serial")
public class SettingsPageTwo extends JPanel {
	private SettingsWindow parent;
	private JButton back, apply, placeSnake, placeLadder, placeStop, placeReward, placeDraw;
	private JSpinner rows, cols;
	private JCheckBox defaultOpt, specialCells, stopC, rewardC, drawC;
	//la mappa tmpCells mi serve per salvare le celle fra un apply e l'altro
	private Map<Integer,Cell> cells = new HashMap<>(), tmpCells = new HashMap<>();
	private List<Integer> occupiedCells = new ArrayList<>(), tmpOccupiedCells = new ArrayList<>(); //indexes
	
	public SettingsPageTwo(SettingsWindow parent) {
		this.parent = parent;
		setPreferredSize(new Dimension(720,700));
		setLayout(null);
		
		//base game (rows, cols, snakes and ladders)
		JLabel l = new JLabel("Board configuration");
		l.setBounds(50,20, 250, 50);
		l.setFont(SnakesAndLaddersGui.fontText);
		add(l);
		
		//default options
		defaultOpt = new JCheckBox("Default board settings");
		defaultOpt.setBounds(45,70,250,50);
		defaultOpt.setFont(SnakesAndLaddersGui.fontText);
		defaultOpt.setSelected(true);
		defaultOpt.addActionListener(new DefaultListener());
		add(defaultOpt);
		
		l = new JLabel("Number of rows: ");
		l.setBounds(50,120, 150, 50);
		l.setFont(SnakesAndLaddersGui.fontText);
		add(l);
		//massimo 15 righe e colonne per una migliore visualizzazione della board
		SpinnerModel rowsModel = new SpinnerNumberModel(10, 4, 15, 1); 
        rows = new JSpinner(rowsModel);
        rows.setFont(SnakesAndLaddersGui.fontText);
        rows.setBounds(230, 130, 50, 30);
        rows.setEnabled(false);
        add(rows);
        l = new JLabel("Number of columns: ");
		l.setBounds(50,160, 150, 50);
		l.setFont(SnakesAndLaddersGui.fontText);
		add(l);
		SpinnerModel colsModel = new SpinnerNumberModel(10, 3, 15, 1);
        cols = new JSpinner(colsModel);
        cols.setFont(SnakesAndLaddersGui.fontText);
        cols.setBounds(230, 170, 50, 30);
        cols.setEnabled(false);
        add(cols);
        
        l = new JLabel("Set snakes");
        l.setBounds(400, 120, 100, 50);
		l.setFont(SnakesAndLaddersGui.fontText);
		add(l);
		placeSnake = new JButton("Place on board");
		placeSnake.setFont(SnakesAndLaddersGui.fontMenu);
		placeSnake.setBounds(500, 130, 150, 30);
		ActionListener placeLis = new PlaceListener();
		placeSnake.addActionListener(placeLis);
		add(placeSnake);
		
		l = new JLabel("Set ladders");
        l.setBounds(400, 160, 100, 50);
		l.setFont(SnakesAndLaddersGui.fontText);
		add(l);
		placeLadder = new JButton("Place on board");
		placeLadder.setFont(SnakesAndLaddersGui.fontMenu);
		placeLadder.setBounds(500, 170, 150, 30);
		placeLadder.addActionListener(placeLis);
		add(placeLadder);
        
		//special cells
		ActionListener cellsListener = new CellsListener();
		specialCells = new JCheckBox("Special cells");
		specialCells.setBounds(45,230,250,50);
		specialCells.setFont(SnakesAndLaddersGui.fontText);
		specialCells.addActionListener(cellsListener);
		add(specialCells);
		
		stopC = new JCheckBox("Stop cells");
		stopC.setBounds(45,290,200,50);
		stopC.setFont(SnakesAndLaddersGui.fontText);
		stopC.setEnabled(false);
		stopC.addActionListener(cellsListener);
		add(stopC);
		placeStop = new JButton("Place on board");
		placeStop.setFont(SnakesAndLaddersGui.fontMenu);
		placeStop.setBounds(270, 300, 150, 30);
		placeStop.setEnabled(false);
		placeStop.addActionListener(placeLis);
		add(placeStop);
		
		rewardC = new JCheckBox("Reward cells");
		rewardC.setBounds(45,340,200,50);
		rewardC.setFont(SnakesAndLaddersGui.fontText);
		rewardC.setEnabled(false);
		rewardC.addActionListener(cellsListener);
		add(rewardC);
		placeReward = new JButton("Place on board");
		placeReward.setFont(SnakesAndLaddersGui.fontMenu);
		placeReward.setBounds(270, 350, 150, 30);
		placeReward.setEnabled(false);
		placeReward.addActionListener(placeLis);
		add(placeReward);
		
		drawC = new JCheckBox("Draw card cells");
		drawC.setBounds(45,390,200,50);
		drawC.setFont(SnakesAndLaddersGui.fontText);
		drawC.setEnabled(false);
		drawC.addActionListener(cellsListener);
		add(drawC);
		placeDraw = new JButton("Place on board");
		placeDraw.setFont(SnakesAndLaddersGui.fontMenu);
		placeDraw.setBounds(270, 400, 150, 30);
		placeDraw.setEnabled(false);
		placeDraw.addActionListener(placeLis);
		add(placeDraw);
		
		
		//bottoni pie di pagina
        back = new JButton("Back");
        apply = new JButton("Apply");
        back.setFont(SnakesAndLaddersGui.fontMenu);
        apply.setFont(SnakesAndLaddersGui.fontMenu);
        back.setBounds(220, 550, 150, 50);
        apply.setBounds(410, 550, 150, 50);
        ActionListener buttonListener = new ButtonListener();
        back.addActionListener(buttonListener);
        apply.addActionListener(buttonListener);
        apply.addActionListener(GameWindow.getInstance().getApplyListener());
        add(back);
        add(apply);
	}
	
	public Map<Integer, Cell> getCells() {
		return cells;
	}
	
	public Map<Integer, Cell> getTmpCells() {
		return tmpCells;
	}
	
	public JCheckBox getDefaultOpt() {
		return defaultOpt;
	}
	
	public JSpinner getRows() {
		return rows;
	}
	
	public JSpinner getCols() {
		return cols;
	}
	
	public JCheckBox getSpecialCells() {
		return specialCells;
	}
	
	public JCheckBox getStopC() {
		return stopC;
	}
	
	public JCheckBox getRewardC() {
		return rewardC;
	}
	
	public JCheckBox getDrawC() {
		return drawC;
	}
	
	public void setCells(Map<Integer,Cell> c) {
		cells = c;
	}
	
	public void setOccupiedCells(List<Integer> oc) {
		occupiedCells = oc;
	}
	
	public JButton getPlaceStop() {
		return placeStop;
	}
	
	public JButton getPlaceReward() {
		return placeReward;
	}
	
	public JButton getPlaceDraw() {
		return placeDraw;
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == back) {
				parent.getSettingsFrame().setContentPane(parent.getPageOne());
				parent.getSettingsFrame().validate();
				parent.getSettingsFrame().repaint();
			}
			else { //apply
				SettingsPageOne page = parent.getPageOne();
				//controllo se l'utente ha inserito correttamente i nomi dei giocatori
				boolean pass = true;
				for(int i=0; i<page.getPlayerPan().getComponents().length; i++) 
					if(((PlayerPrefab)page.getPlayerPan().getComponent(i)).getInputName().trim().isEmpty()) {
						pass = false;
						JOptionPane.showMessageDialog(parent.getSettingsFrame(), "Non hai inserito i nomi di tutti i giocatori!",
								"Warning!", JOptionPane.WARNING_MESSAGE);
						parent.getSettingsFrame().setAlwaysOnTop(true);
						GameWindow.getInstance().getGameFrame().setFocusableWindowState(false);
						GameWindow.getInstance().getGameFrame().setEnabled(false);
						parent.getSettingsFrame().requestFocus();
						break;
					}
				if(pass) {
					parent.getSettingsFrame().setVisible(false);
					parent.setApplied();
					int rVal = (Integer)rows.getValue(), cVal = (Integer)cols.getValue();
					Iterator<Integer> it = tmpCells.keySet().iterator();
					while(it.hasNext()) {
						int i = it.next();
						Cell cell = tmpCells.get(i);
						if(i >= rVal*cVal-1) {
							it.remove();
							if(cell.getEffect() instanceof StairSnake) {
								int toCell = ((StairSnake)cell.getEffect()).getToCell();
								tmpOccupiedCells.remove(new Integer(toCell));
							}
						} else {
							StairSnake c = cell.getEffect() instanceof StairSnake? (StairSnake)cell.getEffect() : null;
							//così evito le situazioni in cui, per una modifica successiva delle dimensioni della board,
							//la testa del serpente si trova esattamente una riga sopra la coda (la situazione in sè non è
							//sbagliata, ma la evito per una questione di rappresentazione grafica)
							if(c != null && !c.isLadder() && c.getToCell()/cVal == i/cVal-1) {
								it.remove();
								tmpOccupiedCells.remove(new Integer(c.getToCell()));
							} 
							//se la cima della scala si ritrova sulla cella finale o fuori dalla board, viene rimossa
							else if(c != null && c.isLadder() && c.getToCell() >= rVal*cVal-1) {
								it.remove();
								tmpOccupiedCells.remove(new Integer(c.getToCell()));
							}
						}
					}
					it = cells.keySet().iterator();
					while(it.hasNext()) {
						int i = it.next();
						Cell cell = cells.get(i);
						if(i >= rVal*cVal-1) {
							it.remove();
							if(cell.getEffect() instanceof StairSnake) {
								int toCell = ((StairSnake)cell.getEffect()).getToCell();
								occupiedCells.remove(new Integer(toCell));
							}
						} else {
							StairSnake c = cell.getEffect() instanceof StairSnake? (StairSnake)cell.getEffect() : null;
							if(c != null && !c.isLadder() && c.getToCell()/cVal == i/cVal-1) {
								it.remove();
								occupiedCells.remove(new Integer(c.getToCell()));
							} 
							else if(c != null && c.isLadder() && c.getToCell() >= rVal*cVal-1) {
								it.remove();
								occupiedCells.remove(new Integer(c.getToCell()));
							}
						}
					}
					cells.putAll(tmpCells);
					occupiedCells.addAll(tmpOccupiedCells);
					tmpCells.clear();
					tmpOccupiedCells.clear();
					
					Configuration config = GameWindow.getInstance().getConfiguration();
					
					//setting attributes of Configuration class
					//attributes from page two
					config.setDefaultSet(defaultOpt.isSelected());
					config.setnRows((Integer)rows.getValue());
					config.setnCols((Integer)cols.getValue());
					config.setSpecialC(specialCells.isSelected());
					config.setRewardC(rewardC.isSelected());
					config.setStopC(stopC.isSelected());
					config.setDrawC(drawC.isSelected());
					config.setCells(cells);
					config.setOccupiedCells(occupiedCells);
					
					//attributes from page one
					config.setnPlayers(page.getnPlayers());
					config.setnDice(page.getnDice());
					config.setnCards(page.getnCards());
					config.setDoubleSixReroll(page.getDoubleSix().isSelected());
					config.setOneDie(page.getOneDie().isSelected());
					config.setDeck(page.getDeck().isSelected());
					config.setNoStop(page.getNoStop().isSelected());
					ArrayList<String> names = new ArrayList<>(page.getnPlayers());
					for (Component c : page.getPlayerPan().getComponents()) {
					    String text = ((PlayerPrefab)c).getInputName();
					    names.add(text);
					}
					config.setNames(names);
					GameWindow.getInstance().getMainPanel().enableDeck(config.getDeck());
					GameWindow.getInstance().getMainPanel().paintBoard();
				}
			}//apply
		}
	}//ButtonListener
	
	private class DefaultListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			rows.setEnabled(!defaultOpt.isSelected());
			cols.setEnabled(!defaultOpt.isSelected());
			if(defaultOpt.isSelected()) {
				rows.setValue(10);
				cols.setValue(10);
			}
		}
	}
	
	private class CellsListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source == specialCells) 
				if(specialCells.isSelected()) {
					stopC.setEnabled(true);
					rewardC.setEnabled(true);
					if(parent.getPageOne().getDeck().isSelected()) //se il deck non è presente, non deve esistere la cella che fa pescare le carte
						drawC.setEnabled(true);
				}
				else {
					stopC.setEnabled(false);
					rewardC.setEnabled(false);
					drawC.setEnabled(false);
					stopC.setSelected(false);
					rewardC.setSelected(false);
					drawC.setSelected(false);
					placeStop.setEnabled(false);
					placeReward.setEnabled(false);
					placeDraw.setEnabled(false);
					//utilizzo l'array keys per ovviare alla concurrentModificationException nel momento in cui più celle speciali sono
					//rimosse, pertanto non sarebbe possibile utilizzare un for each e rimuovere un elemento al suo interno
					Integer[] keys = tmpCells.keySet().toArray(new Integer[] {});
					for(int i=0; i<keys.length; i++)
						if(tmpCells.get(keys[i]).getEffect() instanceof Stop || tmpCells.get(keys[i]).getEffect() instanceof Reward ||
							tmpCells.get(keys[i]).getEffect() instanceof Draw)
							tmpCells.remove(keys[i]);
					keys = cells.keySet().toArray(new Integer[] {});
					for(int i=0; i<keys.length; i++)
						if(cells.get(keys[i]).getEffect() instanceof Stop || cells.get(keys[i]).getEffect() instanceof Reward ||
								cells.get(keys[i]).getEffect() instanceof Draw)
							cells.remove(keys[i]);
				}
			if(source == stopC) {
				placeStop.setEnabled(stopC.isSelected());
				if(!stopC.isSelected()) {
					Integer[] keys = tmpCells.keySet().toArray(new Integer[] {});
					for(int i=0; i<keys.length; i++)
						if(tmpCells.get(keys[i]).getEffect() instanceof Stop)
							tmpCells.remove(keys[i]);
					keys = cells.keySet().toArray(new Integer[] {});
					for(int i=0; i<keys.length; i++)
						if(cells.get(keys[i]).getEffect() instanceof Stop)
							cells.remove(keys[i]);
				}
			}
			if(source == rewardC) {
				placeReward.setEnabled(rewardC.isSelected());
				if(!rewardC.isSelected()) {
					Integer[] keys = tmpCells.keySet().toArray(new Integer[] {});
					for(int i=0; i<keys.length; i++)
						if(tmpCells.get(keys[i]).getEffect() instanceof Reward)
							tmpCells.remove(keys[i]);
					keys = cells.keySet().toArray(new Integer[] {});
					for(int i=0; i<keys.length; i++)
						if(cells.get(keys[i]).getEffect() instanceof Reward)
							cells.remove(keys[i]);
				}
			}
			if(source == drawC) {
				placeDraw.setEnabled(drawC.isSelected());
				if(!drawC.isSelected()) {
					Integer[] keys = tmpCells.keySet().toArray(new Integer[] {});
					for(int i=0; i<keys.length; i++)
						if(tmpCells.get(keys[i]).getEffect() instanceof Draw)
							tmpCells.remove(keys[i]);
					keys = cells.keySet().toArray(new Integer[] {});
					for(int i=0; i<keys.length; i++)
						if(cells.get(keys[i]).getEffect() instanceof Draw)
							cells.remove(keys[i]);
				}
			}
		}
	}
	
	private class PlaceListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(source == placeSnake) {
				parent.getSettingsFrame().setAlwaysOnTop(false);
				int nCells = (Integer) rows.getValue() * (Integer) cols.getValue();
				int from = -1;
				int headRow = -1;
				while(from == -1) {
					String head= JOptionPane.showInputDialog(null, "In quale cella vuoi inserire la testa del serpente?"); //numero cella, non indice
					if(head==null) return;
					try {
						from = Integer.parseInt(head)-1;
						headRow = from/(Integer)cols.getValue();
						if(from<=0 || from >= nCells-1) {
							JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
							from = -1;
						}
						else if(cells.get(from)!=null || occupiedCells.contains(from) ||
								tmpCells.get(from)!=null || tmpOccupiedCells.contains(from)) {
							JOptionPane.showMessageDialog(null, "La cella è già occupata! Inserisci un altro valore.", "Error", JOptionPane.ERROR_MESSAGE);
							from = -1;
						}
						else if(headRow < 2) {
							JOptionPane.showMessageDialog(null, "La testa del serpente si dovrebbe trovare almeno alla terza riga.", "Error", JOptionPane.ERROR_MESSAGE);
							from = -1;
						}
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
						from = -1;
					}
				}//controllo from
				
				int to = -1;
				while(to == -1) {
					String tail= JOptionPane.showInputDialog(null, "In quale cella vuoi inserire la coda del serpente?"); //numero cella, non indice
					if(tail==null) return;
					try {
						to = Integer.parseInt(tail)-1;
						int tailRow = to/(Integer)cols.getValue();
						if(to<=0 || to >= nCells-1) {
							JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
							to = -1;
						}
						else if(cells.get(to)!=null || occupiedCells.contains(to) ||
								tmpCells.get(to)!=null || tmpOccupiedCells.contains(to)) {
							JOptionPane.showMessageDialog(null, "La cella è già occupata! Inserisci un altro valore.", "Error", JOptionPane.ERROR_MESSAGE);
							to = -1;
						}
						else if(tailRow >= headRow-1) {
							JOptionPane.showMessageDialog(null, "La coda del serpente deve stare almeno due righe più in basso della testa!", "Error", JOptionPane.ERROR_MESSAGE);
							to = -1;
						}
							
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
						to = -1;
					}
				}//controllo to
				
				tmpCells.put(from, new Cell(new StairSnake(false, to)));
				tmpOccupiedCells.add(to); //indice cella board
				parent.getSettingsFrame().setAlwaysOnTop(true);
			}//placeSnake
			
			if(source == placeLadder) {
				parent.getSettingsFrame().setAlwaysOnTop(false);
				int nCells = (Integer) rows.getValue() * (Integer) cols.getValue();
				int from = -1;
				int footRow = -1;
				while(from == -1) {
					String foot= JOptionPane.showInputDialog(null, "In quale cella vuoi inserire i piedi della scala?"); //numero cella, non indice
					if(foot==null) return;
					try {
						from = Integer.parseInt(foot)-1;
						footRow = from/(Integer)cols.getValue();
						if(from<=0 || from >= nCells-1) {
							JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
							from = -1;
						}
						else if(cells.get(from)!=null || occupiedCells.contains(from) ||
								tmpCells.get(from)!=null || tmpOccupiedCells.contains(from)) {
							JOptionPane.showMessageDialog(null, "La cella è già occupata! Inserisci un altro valore.", "Error", JOptionPane.ERROR_MESSAGE);
							from = -1;
						}
						else if(footRow == nCells-1) {
							JOptionPane.showMessageDialog(null, "I piedi della scala si dovrebbero trovare almeno alla penultima riga.", "Error", JOptionPane.ERROR_MESSAGE);
							from = -1;
						}
							
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
						from = -1;
					}
				}//controllo from
				
				int to = -1;
				while(to == -1) {
					String top= JOptionPane.showInputDialog(null, "In quale cella vuoi inserire la cima della scala?"); //numero cella, non indice
					if(top==null) return;
					try {
						to = Integer.parseInt(top)-1;
						int topRow = to/(Integer)cols.getValue();
						if(to<=0 || to >= nCells-1) {
							JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
							to = -1;
						}
						else if(cells.get(to)!=null || occupiedCells.contains(to) ||
								tmpCells.get(to)!=null || tmpOccupiedCells.contains(to)) {
							JOptionPane.showMessageDialog(null, "La cella è già occupata! Inserisci un altro valore.", "Error", JOptionPane.ERROR_MESSAGE);
							to = -1;
						}
						else if(topRow <= footRow) {
							JOptionPane.showMessageDialog(null, "La cima della scala deve stare più in alto dei piedi!", "Error", JOptionPane.ERROR_MESSAGE);
							to = -1;
						}
							
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
						to = -1;
					}
				}//controllo to
				
				
				tmpCells.put(from, new Cell(new StairSnake(true, to)));
				tmpOccupiedCells.add(to); //indice cella board
				parent.getSettingsFrame().setAlwaysOnTop(true);
			}//placeLadder
			
			if(source == placeStop) {
				parent.getSettingsFrame().setAlwaysOnTop(false);
				int nCells = (Integer) rows.getValue() * (Integer) cols.getValue();
				int index = -1;
				while(index == -1) {
					String cell= JOptionPane.showInputDialog(null, "In quale cella vuoi inserire la casella di sosta?");
					if(cell==null) return;
					try {
						index = Integer.parseInt(cell)-1;
						if(index<=0 || index >= nCells-1) {
							JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
							index = -1;
						}
						else if(cells.get(index)!=null || occupiedCells.contains(index) ||
								tmpCells.get(index)!=null || tmpOccupiedCells.contains(index)) {
							JOptionPane.showMessageDialog(null, "La cella è già occupata! Inserisci un altro valore.", "Error", JOptionPane.ERROR_MESSAGE);
							index = -1;
						}
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
						index = -1;
					}
				}//controllo se la cella è valida e può essere posizionata
				
				int n = -1;
				while(n == -1) {
					String turn= JOptionPane.showInputDialog(null, "Quanti turni deve durare la sosta?");
					if(turn == null) return;
					try {
						n = Integer.parseInt(turn);
						if(n!=1 && n!=3) {
							JOptionPane.showMessageDialog(null, "Le soste possono durare solo uno o tre turni!", "Error", JOptionPane.ERROR_MESSAGE);
							n = -1;
						}
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
						n = -1;
					}
				}//controllo numero turni
				tmpCells.put(index, new Cell(new Stop(n)));
				
				parent.getSettingsFrame().setAlwaysOnTop(true);
			}//placeStop
			
			if(source == placeReward) {
				parent.getSettingsFrame().setAlwaysOnTop(false);
				int nCells = (Integer) rows.getValue() * (Integer) cols.getValue();
				int index = -1;
				while(index == -1) {
					String cell= JOptionPane.showInputDialog(null, "In quale cella vuoi inserire la casella premio?");
					if(cell==null) return;
					try {
						index = Integer.parseInt(cell)-1;
						if(index<=0 || index >= nCells-1) {
							JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
							index = -1;
						}
						else if(cells.get(index)!=null || occupiedCells.contains(index) ||
								tmpCells.get(index)!=null || tmpOccupiedCells.contains(index)) {
							JOptionPane.showMessageDialog(null, "La cella è già occupata! Inserisci un altro valore.", "Error", JOptionPane.ERROR_MESSAGE);
							index = -1;
						}
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
						index = -1;
					}
				}//controllo se la cella è valida e può essere posizionata
				
				boolean choice = false; //true: dadi, false: molla
				String springOrDice = "";
				while(springOrDice.isEmpty()) {
					springOrDice = JOptionPane.showInputDialog(null, "Molla o dadi?");
					if (springOrDice == null) return;
					if(!springOrDice.toLowerCase().equals("molla") && !springOrDice.toLowerCase().equals("dadi")) {
						JOptionPane.showMessageDialog(null, "Inserire una stringa corretta!", "Error", JOptionPane.ERROR_MESSAGE);
						springOrDice = "";
						continue;
					}
					if(springOrDice.toLowerCase().equals("dadi")) 
						choice = !choice;
					else if(index >= nCells-12) { //accorgimento adottato per evitare il "bug molla"
						JOptionPane.showMessageDialog(null, "Non puoi mettere una molla qui!", "Warning", JOptionPane.WARNING_MESSAGE);
						springOrDice = "";
					}
				}
				tmpCells.put(index, new Cell(new Reward(choice)));
				parent.getSettingsFrame().setAlwaysOnTop(true);
			}//placeReward
			
			if(source == placeDraw) {
				parent.getSettingsFrame().setAlwaysOnTop(false);
				int nCells = (Integer) rows.getValue() * (Integer) cols.getValue();
				int index = -1;
				while(index == -1) {
					String cell= JOptionPane.showInputDialog(null, "In quale cella vuoi inserire la casella per pescare una carta?");
					if(cell==null) return;
					try {
						index = Integer.parseInt(cell)-1;
						if(index<=0 || index >= nCells-1) {
							JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
							index = -1;
						}
						else if(cells.get(index)!=null || occupiedCells.contains(index) ||
								tmpCells.get(index)!=null || tmpOccupiedCells.contains(index)) {
							JOptionPane.showMessageDialog(null, "La cella è già occupata! Inserisci un altro valore.", "Error", JOptionPane.ERROR_MESSAGE);
							index = -1;
						}
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "Valore inserito non valido!", "Error", JOptionPane.ERROR_MESSAGE);
						index = -1;
					}
				}//controllo se la cella è valida e può essere posizionata
				tmpCells.put(index, new Cell(new Draw()));
				parent.getSettingsFrame().setAlwaysOnTop(true);
			}//placeDraw
		}
	}
}
