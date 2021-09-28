package gui.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

@SuppressWarnings("serial")
//come sono rappresentati i giocatori sul tabellone
public class PlayerLabel extends JLabel {
	private final static Color[] COLORS = { Color.red, Color.blue, Color.black, Color.white, Color.orange, Color.magenta };
	private List<Integer> players = new ArrayList<>();
	
	public void placePlayer(int playerId) {
		players.add(playerId);
	}
	
	public void removePlayer(int playerId) {
		players.remove((Integer)playerId);
	}
	
	public void clear() {
		players.clear();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		//in base al numero di giocatori, cambio la loro posizione sulla cella in modo che tutti siano visibili
		if(players.size()==1) {
			g2d.setColor(COLORS[players.get(0)]);
			g2d.fill(new Ellipse2D.Float(20, 20, 10, 10));
		}
		
		else if(players.size()==2) {
			g2d.setColor(COLORS[players.get(0)]);
			g2d.fill(new Ellipse2D.Float(20, 10, 10, 10));

			g2d.setColor(COLORS[players.get(1)]);
			g2d.fill(new Ellipse2D.Float(20, 30, 10, 10));
		}
		
		else if(players.size()==3) {
			g2d.setColor(COLORS[players.get(0)]);
			g2d.fill(new Ellipse2D.Float(30, 10, 10, 10));

			g2d.setColor(COLORS[players.get(1)]);
			g2d.fill(new Ellipse2D.Float(20, 20, 10, 10));
			
			g2d.setColor(COLORS[players.get(2)]);
			g2d.fill(new Ellipse2D.Float(10, 30, 10, 10));
		}
		
		else if(players.size()==4) {
			g2d.setColor(COLORS[players.get(0)]);
			g2d.fill(new Ellipse2D.Float(10, 10, 10, 10));

			g2d.setColor(COLORS[players.get(1)]);
			g2d.fill(new Ellipse2D.Float(30, 10, 10, 10));
			
			g2d.setColor(COLORS[players.get(2)]);
			g2d.fill(new Ellipse2D.Float(10, 30, 10, 10));
			
			g2d.setColor(COLORS[players.get(3)]);
			g2d.fill(new Ellipse2D.Float(30, 30, 10, 10));
		}
		
		else if(players.size()==5) {
			g2d.setColor(COLORS[players.get(0)]);
			g2d.fill(new Ellipse2D.Float(10, 10, 10, 10));

			g2d.setColor(COLORS[players.get(1)]);
			g2d.fill(new Ellipse2D.Float(30, 10, 10, 10));
			
			g2d.setColor(COLORS[players.get(2)]);
			g2d.fill(new Ellipse2D.Float(10, 30, 10, 10));
			
			g2d.setColor(COLORS[players.get(3)]);
			g2d.fill(new Ellipse2D.Float(30, 30, 10, 10));
			
			g2d.setColor(COLORS[players.get(4)]);
			g2d.fill(new Ellipse2D.Float(20, 20, 10, 10));
		}
		
		else if(players.size()==6) {
			g2d.setColor(COLORS[players.get(0)]);
			g2d.fill(new Ellipse2D.Float(10, 5, 10, 10));

			g2d.setColor(COLORS[players.get(1)]);
			g2d.fill(new Ellipse2D.Float(10, 20, 10, 10));
			
			g2d.setColor(COLORS[players.get(2)]);
			g2d.fill(new Ellipse2D.Float(10, 35, 10, 10));
			
			g2d.setColor(COLORS[players.get(3)]);
			g2d.fill(new Ellipse2D.Float(30, 5, 10, 10));
			
			g2d.setColor(COLORS[players.get(4)]);
			g2d.fill(new Ellipse2D.Float(30, 20, 10, 10));
			
			g2d.setColor(COLORS[players.get(5)]);
			g2d.fill(new Ellipse2D.Float(30, 35, 10, 10));
		}
		
		g2d.dispose();
	}
}
