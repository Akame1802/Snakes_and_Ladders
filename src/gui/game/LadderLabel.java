package gui.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class LadderLabel extends JLabel {
	private final Color ladderColor = new Color(145, 69, 29);
	private final BasicStroke ladderStroke = new BasicStroke(3);
	private final BasicStroke obliqueLadderStroke = new BasicStroke(1);
	private final int ladderWidth = 30;
	private final int distanceBetweenSteps = 20;
	private boolean direction; //true: direzione nord-est, false: direzione: nord-ovest
	
	public void setDirection(boolean d) {
		this.direction = d;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(ladderColor);
		g2d.setStroke(ladderStroke);
		Rectangle rect = getBounds();
		int numRungs;
		
		if(rect.width == 50) { //è messa in verticale
			g2d.drawLine(10, 5, 10, rect.height-5);
			g2d.drawLine(40, 5, 40, rect.height-5);
			numRungs = rect.height/25;
			for(int i=0; i<numRungs; i++) {
				float adj = i%2==0? -0.5f : 0.5f;
				g2d.drawLine(10, (int)(12.5+i*25+adj), 40, (int)(12.5+i*25+adj));
			}
		}
		else { //obliquo verso destra o verso sinistra
			double x0 = direction? 10 : rect.width-10, y0 = rect.height-10;
			double x1 = direction? rect.width-10 : 10, y1 = 10;
			drawLadderBetweenPoints(g2d, new Point2D.Double(x0, y0), new Point2D.Double(x1, y1));
		}
		
		g2d.dispose();
	}
	
	private void drawLadderBetweenPoints(Graphics2D g2d, Point2D p0, Point2D p1) {
	        double dx = p1.getX() - p0.getX();
	        double dy = p1.getY() - p0.getY();
	        double distance = p1.distance(p0);

	        double dirX = dx / distance;
	        double dirY = dy / distance;

	        double offsetX = dirY * ladderWidth * 0.5;
	        double offsetY = -dirX * ladderWidth * 0.5;

	        Line2D lineR = new Line2D.Double(
	            p0.getX() + offsetX, 
	            p0.getY() + offsetY,
	            p1.getX() + offsetX,
	            p1.getY() + offsetY);

	        Line2D lineL = new Line2D.Double(
	            p0.getX() - offsetX, 
	            p0.getY() - offsetY,
	            p1.getX() - offsetX,
	            p1.getY() - offsetY);

	        drawBar(g2d, lineL);
	        drawBar(g2d, lineR);

	        int numSteps = (int)(distance / distanceBetweenSteps);
	        for (int i=0; i<numSteps; i++)
	        {
	            double stepOffsetX = (i+1) * distanceBetweenSteps;
	            double stepOffsetY = (i+1) * distanceBetweenSteps;

	            Line2D step = new Line2D.Double(
	                p0.getX() + stepOffsetX * dirX - offsetX, 
	                p0.getY() + stepOffsetY * dirY - offsetY,
	                p0.getX() + stepOffsetX * dirX + offsetX,
	                p0.getY() + stepOffsetY * dirY + offsetY);
	            drawBar(g2d, step);
	        }
	    }

	private void drawBar(Graphics2D g2d, Line2D line) {
		Shape bar = obliqueLadderStroke.createStrokedShape(line);
		g2d.fill(bar);
		g2d.draw(bar);
	}
}
