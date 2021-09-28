package gui.state;

import command.Command;
import command.Reward;
import command.Stop;
import components.Card;
import gui.game.DeckPanel;
import gui.game.GameWindow;
import gui.util.Observer;

public class Drawing implements State{
	private String playerName;
	private DeckPanel.Cards card;
	private Observer observer;
	
	public Drawing(String playerName, Card card, Observer observer) {
		this.playerName = playerName;
		this.observer = observer;
		Command effect = card.getEffect();
		if(effect instanceof Reward) {
			if(((Reward)effect).isSpringOrDice())
				this.card = DeckPanel.Cards.DICE;
			else
				this.card = DeckPanel.Cards.SPRING;
		}
		else {
			if(((Stop)effect).getTurns() == 1)
				this.card = DeckPanel.Cards.BENCH;
			else if(((Stop)effect).getTurns() == 3)
				this.card = DeckPanel.Cards.INN;
			else
				this.card = DeckPanel.Cards.NOSTOP;
		}
	}
	
	@Override
	public void handle() {
		GameWindow.getInstance().getMainPanel().getDeckPanel().setCardIcon(card);
		String name = playerName.length()>25? playerName.substring(0, 25)+"..." : playerName;
		observer.update(name+" draws a card");
		try { Thread.sleep(1500); } 
		catch (InterruptedException e) { }
		GameWindow.getInstance().getMainPanel().getDeckPanel().setCardIcon(DeckPanel.Cards.EMPTY);
	}

}
