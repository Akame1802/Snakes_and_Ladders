package gui.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.*;
import components.Cell;

public class Configuration {
	private int nPlayers = -1, nDice = -1, nCards = -1, nRows = -1, nCols = -1;
	private boolean doubleSixReroll, oneDie, deck, noStop, defaultSet, specialC, stopC, rewardC, drawC;
	private List<String> names = null;
	private Map<Integer,Cell> cells = null;
	private List<Integer> occupiedCells = null;
	
	public void saveJson(File file) {
		String json = "{\n"
						+ "\t\"nPlayers\" : "+nPlayers+",\n"
						+ "\t\"nDice\" : "+nDice+",\n"
						+ "\t\"nCards\" : "+nCards+",\n"
		 				+ "\t\"nRows\" : "+nRows+",\n"
		 				+ "\t\"nCols\" : "+nCols+",\n"
		 				+ "\t\"doubleSixReroll\" : "+doubleSixReroll+",\n"
		 				+ "\t\"oneDie\" : "+oneDie+",\n"
		 				+ "\t\"deck\" : "+deck+",\n"
		 				+ "\t\"noStop\" : "+noStop+",\n"
		 				+ "\t\"defaultSet\" : "+defaultSet+",\n"
		 				+ "\t\"specialC\" : "+specialC+",\n"
		 				+ "\t\"stopC\" : "+stopC+",\n"
		 				+ "\t\"rewardC\" : "+rewardC+",\n"
		 				+ "\t\"drawC\" : "+drawC+",\n"
		 				+ "\t\"names\" : [ ";
		for(String name: names)
			json += "\""+name+"\", ";
		json = json.substring(0, json.length()-2);
		json += " ],\n"
				+ "\t\"cells\" : [ ";
		for(Integer i: cells.keySet()) {
			Cell c = cells.get(i);
			json += "{\n\t\t\t\"index\" : "+i+",\n"
					+ "\t\t\t\"cell\" : {\n"
					+ "\t\t\t\t\"effect\" : ";
			String[] className = c.getEffect().getClass().getName().split("\\.");
			json += "\""+className[className.length-1]+"\"";
			if(c.getEffect() instanceof Reward)
				json += ",\n\t\t\t\t\"springOrDice\" : "+((Reward)c.getEffect()).isSpringOrDice();
			else if(c.getEffect() instanceof Stop)
				json += ",\n\t\t\t\t\"k\" : "+((Stop)c.getEffect()).getTurns();
			else if(c.getEffect() instanceof StairSnake)
				json += ",\n\t\t\t\t\"stairOrSnake\" : "+((StairSnake)c.getEffect()).isLadder()
						+",\n\t\t\t\t\"toCell\" : "+((StairSnake)c.getEffect()).getToCell();
			json += "\n\t\t\t\t}\n\t\t  } , ";
		}
		json = json.substring(0, json.length()-2)
				+ "\n\t\t]\n}";
		
		 PrintWriter out = null;
		 try {
			out = new PrintWriter(file);
			out.print(json);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Salvataggio non riuscito!", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			if(out != null)
				out.close();
		}
	}

	@SuppressWarnings("unchecked")
	public boolean loadJson(File file) throws IOException {
		if(!file.getName().endsWith(".json"))
			return false;
		//Object Mapper mi consente di costruire una mappa a partire dal file JSON
		Map<String, Object> map = new ObjectMapper().readValue(file, Map.class);
		
		for(String key: map.keySet())
			try {
				if(!key.equals("cells")) {
					//Java Reflection mi permette di automatizzare il processo di valorizzazione degli attributi
					Field f = this.getClass().getDeclaredField(key);
					f.set(this, map.get(key));
				}
				else {
					cells = new HashMap<>();
					occupiedCells = new ArrayList<>();
					for(Map<String, Object> entry: (ArrayList<Map<String, Object>>)map.get(key)) {
						Map<String, Object> cell = (Map<String, Object>)entry.get("cell");
						
						String effect = (String)cell.get("effect");
						Command c = null;
						if(effect.equals("Draw"))
							c = new Draw();
						else if(effect.equals("Reward")) {
							if(!cell.containsKey("springOrDice")) return false;
							c = new Reward((Boolean)cell.get("springOrDice"));
						}
						else if(effect.equals("Stop")) {
							if(!cell.containsKey("k")) return false;
							c = new Stop((Integer)cell.get("k"));
						}
						else if(effect.equals("StairSnake")) {
							if(!cell.containsKey("stairOrSnake") || !cell.containsKey("toCell")) return false;
							c = new StairSnake((Boolean)cell.get("stairOrSnake"), (Integer)cell.get("toCell"));
							occupiedCells.add((Integer)cell.get("toCell"));
						} 
						this.cells.put((Integer)entry.get("index"), new Cell(c));
					}
				}
			} catch(Exception e) { //file malformato
				return false;
			}
		return validate();
	}
	
	private boolean validate() {
		boolean validIf = nPlayers>=2 && nDice>=1 && nDice<=2 && nCards>=5 && nRows>=4 && nRows<=15 && nCols>=3 && nCols<=15;
		if(nDice==1 && (doubleSixReroll || oneDie))
			return false;
		if(!deck && noStop)
			return false;
		if (defaultSet && !(nRows == 10 && nCols == 10))
			return false;
		if (!specialC && (stopC || rewardC || drawC))
			return false;
		if (names.size() != nPlayers)
			return false;
		return validIf;
	}
	
	public int getnPlayers() {
		return nPlayers;
	}
	
	public void setnPlayers(int nPlayers) {
		this.nPlayers = nPlayers;
	}

	public int getnDice() {
		return nDice;
	}

	public void setnDice(int nDice) {
		this.nDice = nDice;
	}

	public int getnCards() {
		return nCards;
	}

	public void setnCards(int nCards) {
		this.nCards = nCards;
	}

	public int getnRows() {
		return nRows;
	}

	public void setnRows(int nRows) {
		this.nRows = nRows;
	}

	public int getnCols() {
		return nCols;
	}

	public void setnCols(int nCols) {
		this.nCols = nCols;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public boolean getDoubleSixReroll() {
		return doubleSixReroll;
	}

	public void setDoubleSixReroll(boolean doubleSixReroll) {
		this.doubleSixReroll = doubleSixReroll;
	}

	public boolean getOneDie() {
		return oneDie;
	}

	public void setOneDie(boolean oneDie) {
		this.oneDie = oneDie;
	}

	public boolean getDeck() {
		return deck;
	}

	public void setDeck(boolean deck) {
		this.deck = deck;
	}

	public boolean getNoStop() {
		return noStop;
	}

	public void setNoStop(boolean noStop) {
		this.noStop = noStop;
	}

	public boolean getDefaultSet() {
		return defaultSet;
	}

	public void setDefaultSet(boolean defaultSet) {
		this.defaultSet = defaultSet;
	}

	public boolean getSpecialC() {
		return specialC;
	}

	public void setSpecialC(boolean specialC) {
		this.specialC = specialC;
	}

	public boolean getStopC() {
		return stopC;
	}

	public void setStopC(boolean stopC) {
		this.stopC = stopC;
	}

	public boolean getRewardC() {
		return rewardC;
	}

	public void setRewardC(boolean rewardC) {
		this.rewardC = rewardC;
	}

	public boolean getDrawC() {
		return drawC;
	}

	public void setDrawC(boolean drawC) {
		this.drawC = drawC;
	}

	public Map<Integer,Cell> getCells() {
		return cells;
	}

	public void setCells(Map<Integer,Cell> cells) {
		this.cells = cells;
	}
	public List<Integer> getOccupiedCells() {
		return occupiedCells;
	}

	public void setOccupiedCells(List<Integer> occupiedCells) {
		this.occupiedCells = occupiedCells;
	}
}
