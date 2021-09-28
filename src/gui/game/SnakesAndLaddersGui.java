package gui.game;

import java.awt.Font;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import gui.util.ResourceLoader;

public class SnakesAndLaddersGui {
	public static Font fontText = new Font("Calibri", Font.ITALIC, 18), 
					   fontMain = new Font("Calibri", Font.ITALIC, 20), 
					   fontMenu = new Font("Calibri", Font.PLAIN, 18),
					   fontCell = new Font("Calibri", Font.BOLD, 18);
	public static Clip ost;
	
	public static void main(String[] args) {
		GameWindow.getInstance().init();
		try {
			ost = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(ResourceLoader.loadResource("clip.wav"));
			ost.open(ais);
			ost.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) { e.printStackTrace(); }
	}
}
