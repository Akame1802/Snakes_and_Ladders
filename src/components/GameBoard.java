package components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {
	private int rows, cols;
	private List<Cell> cells;
    private Map<Integer, Integer> playerPieces; //<id,posizione>
    private boolean oneDieForVictory;

    public GameBoard(int rows, int cols, List<Cell> cells, int nPlayers, boolean oneDieForVictory) {
    	this.rows = rows;
    	this.cols = cols;
    	this.cells = cells;
        this.playerPieces = new HashMap<Integer, Integer>();
        for(int i=0; i<nPlayers; i++) 
        	playerPieces.put(i,0);
        this.oneDieForVictory = oneDieForVictory;
    }
    
    public Cell movePawn(int id, int toCell) {
    	playerPieces.put(id,toCell);
    	return cells.get(toCell);
    }

	public boolean condOneDie(int id) {
		int pos = playerPieces.get(id);
		int size = rows*cols;
		return pos >= size-6 && oneDieForVictory;
	}
	
	public int getPlayerPos(int id) {
		return playerPieces.get(id);
	}
	
	public int getBoardSize() {
		return rows * cols;
	}
    
}
