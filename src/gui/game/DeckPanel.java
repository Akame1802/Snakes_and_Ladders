package gui.game;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.util.IconPool;
import gui.util.ResourceLoader;

@SuppressWarnings("serial")
public class DeckPanel extends JPanel {
	public static enum Cards { DICE, EMPTY, INN, SPRING, NOSTOP, BENCH }
	
	private JLabel deck, card;
	private ImageIcon deckIconNoCard, deckIconWithCard, noStop;
	
	public DeckPanel() {
		super();
		setLayout(null);
		
		deck = new JLabel();
		deck.setBounds(0, 0, 220,220);
		card = new JLabel();
		card.setBounds(80, 50, 60,60);
		add(card);
		add(deck);
		
		Image pic = new ImageIcon(ResourceLoader.loadResource("deckNoCard.png")).getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
		deckIconNoCard = new ImageIcon(pic);
		pic = new ImageIcon(ResourceLoader.loadResource("deckWithCard.png")).getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
		deckIconWithCard = new ImageIcon(pic);
		pic = new ImageIcon(ResourceLoader.loadResource("nostop.png")).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		noStop = new ImageIcon(pic);
		
		deck.setIcon(deckIconNoCard);
	}
	
	public void setCardIcon(Cards cardIcon) {
		if(cardIcon.ordinal() == 1)
			deck.setIcon(deckIconNoCard);
		else
			deck.setIcon(deckIconWithCard);
		
		switch(cardIcon.ordinal()) {
		case 0:
			card.setIcon(IconPool.getInstance().get(IconPool.DICE));
			break;
		case 1:
			card.setIcon(null);
			break;
		case 2:
			card.setIcon(IconPool.getInstance().get(IconPool.INN));
			break;
		case 3:
			card.setIcon(IconPool.getInstance().get(IconPool.SPRING));
			break;
		case 4:
			card.setIcon(noStop);
			break;
		case 5:
			card.setIcon(IconPool.getInstance().get(IconPool.BENCH));
			break;
		}
		
		GameWindow.getInstance().getGameFrame().validate();
		GameWindow.getInstance().getGameFrame().repaint();
	}
}
