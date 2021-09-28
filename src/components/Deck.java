package components;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Deck {
	private LinkedList<Card> cards;
	private Random r = new Random();
	
	public Deck(LinkedList<Card> deck) {
		cards = deck;
		shuffle();
	}
	
	public void shuffle() {
		Card[] toShuffle = cards.toArray(new Card[] {});
		for(int i=0; i<(toShuffle.length*toShuffle.length); i++) {
			int rand1 = r.nextInt(toShuffle.length);
			int rand2 = r.nextInt(toShuffle.length);
			Card tmp = toShuffle[rand1];
			toShuffle[rand1] = toShuffle[rand2];
			toShuffle[rand2] = tmp;
		}
		cards = new LinkedList<>(Arrays.asList(toShuffle));
	}
	
	public void putCard(Card c) {
		cards.addLast(c);
	}
	
	public Card draw() {
		return cards.removeFirst();
	}
}
