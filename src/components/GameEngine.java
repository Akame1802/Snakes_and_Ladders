package components;

import java.util.*;

import gui.game.GameWindow;
import gui.state.Drawing;
import gui.state.Ended;
import gui.state.Moving;
import gui.state.Rolling;
import gui.state.State;

public final class GameEngine {
	
	private static GameEngine instance = null;
	private GameEngine() {}
	public static GameEngine getInstance() {
		if(instance == null)
			instance = new GameEngine();
		return instance;
	}
	
	private GameBoard gameBoard;
	private Deck deck;
    private int nPlayers;
    private Player winner;
    private int diceNumber;
    private ArrayList<Player> players;
    private int lastThrow;
    private Random r = new Random();
    private Queue<State> automa;
	private boolean doubleSixReroll;
    
    
    public void init(int players, int dice, int rows, int cols, boolean oneDieForVictory, boolean doubleSixReroll,
    				List<Cell> cells, LinkedList<Card> cards, ArrayList<Player> p, Queue<State> automa) {
    	nPlayers = players;
    	diceNumber = dice;
    	gameBoard = new GameBoard(rows,cols,cells,players,oneDieForVictory);
    	deck = new Deck(cards);
    	this.players = p;
    	this.automa = automa;
    	this.doubleSixReroll = doubleSixReroll;
    	winner = null;
    }
    
    public void gameLoop() {
    	while(winner == null) {
    		int turn = 0;
    		while(turn < nPlayers && winner == null) {
        		System.out.println("Turno del giocatore "+turn);
    			players.get(turn).playTurn(true);
    			if(diceNumber>1 && lastThrow == 12) {
    				System.out.println("Il giocatore "+players.get(turn).getId()+" ha effettuato un doppio sei: rilancia i dadi");
    				players.get(turn).playTurn(true);
    			}
    			turn++;
    		}
    	}
    }
    
    public void oneGameLoop(int turn) { //dò la possibilità all'interfaccia grafica di rappresentare un solo turno di gioco
		if(winner!=null)
			return;
		else {
	    	System.out.println("Turno del giocatore "+turn);
			players.get(turn).playTurn(true);
			if(diceNumber>1 && lastThrow == 12 && doubleSixReroll) {
				System.out.println("Il giocatore "+players.get(turn).getId()+" ha effettuato un doppio sei: rilancia i dadi");
				players.get(turn).playTurn(true);
			}
			if(winner != null)
				automa.add(new Ended(winner, GameWindow.getInstance().getObserver()));
		}
    }

    public Cell move(int id, int toCell) {
    	automa.add(new Moving(players.get(id).getName(), id, gameBoard.getPlayerPos(id), toCell, GameWindow.getInstance().getObserver()));
		return gameBoard.movePawn(id,toCell);
    }
    
    public int rollDice(int id) {
    	lastThrow = 0;
    	int dice = diceNumber>1 && gameBoard.condOneDie(id) ? 1 : diceNumber;
    	for (int i = 0; i<dice; i++)
    		lastThrow += r.nextInt(6) + 1;
    	automa.add(new Rolling(players.get(id).getName(), lastThrow, GameWindow.getInstance().getObserver()));
    	return lastThrow;
    }
    
    public Card draw(int id) {
    	Card c = deck.draw();
    	automa.add(new Drawing(players.get(id).getName(), c, GameWindow.getInstance().getObserver()));
    	return c;
    }
    
    public void putCard(Card c) {
    	deck.putCard(c);
    }
    
    public void setWinner(Player p) {
    	winner = p;
    }
    
	public int getPlayerPos(int id) {
		return gameBoard.getPlayerPos(id);
	}
	
	public boolean isPlayerStopped(int id) {
		return players.get(id).isStopped();
	}
	
	public int boardSize() {
		return gameBoard.getBoardSize();
	}
	
	public boolean hasWinner() {
		return winner != null;
	}
}
