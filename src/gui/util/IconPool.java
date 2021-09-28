package gui.util;

import java.awt.Image;

import javax.swing.ImageIcon;

public class IconPool {
	private static IconPool instance;
	public static IconPool getInstance() {
		if(instance == null)
			instance = new IconPool();
		return instance;
	}
	private IconPool() {
		Image pic;
		
		pic = new ImageIcon(getClass().getResource("dice.png")).getImage(); 
		icons[DICE] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("molla.png")).getImage();
		icons[SPRING] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("card.png")).getImage();
		icons[CARD] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("panchina.png")).getImage();
		icons[BENCH] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("locanda.png")).getImage();
		icons[INN] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("finish.png")).getImage();
		icons[FINISH] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("pawn_p0.png")).getImage();
		icons[PAWN0] = new ImageIcon(pic.getScaledInstance(80,100, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("pawn_p1.png")).getImage();
		icons[PAWN1] = new ImageIcon(pic.getScaledInstance(80,100, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("pawn_p2.png")).getImage();
		icons[PAWN2] = new ImageIcon(pic.getScaledInstance(80,100, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("pawn_p3.png")).getImage();
		icons[PAWN3] = new ImageIcon(pic.getScaledInstance(80,100, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("pawn_p4.png")).getImage();
		icons[PAWN4] = new ImageIcon(pic.getScaledInstance(80,100, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("pawn_p5.png")).getImage();
		icons[PAWN5] = new ImageIcon(pic.getScaledInstance(80,100, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("snake_head.png")).getImage();
		icons[SNAKE_HEAD] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("snake_tail.png")).getImage();
		icons[SNAKE_TAIL] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("snake_horizontal.png")).getImage();
		icons[SNAKE_BODY_H] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("snake_vertical.png")).getImage();
		icons[SNAKE_BODY_V] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("snake_connector_up_to_rx.png")).getImage();
		icons[SNAKE_CONNECTOR_UP_TO_RX] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("snake_connector_up_to_lx.png")).getImage();
		icons[SNAKE_CONNECTOR_UP_TO_LX] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("snake_connector_rx_to_down.png")).getImage();
		icons[SNAKE_CONNECTOR_RX_TO_DOWN] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
		pic = new ImageIcon(getClass().getResource("snake_connector_lx_to_down.png")).getImage();
		icons[SNAKE_CONNECTOR_LX_TO_DOWN] = new ImageIcon(pic.getScaledInstance(50,50, Image.SCALE_SMOOTH));
	}
	
	//uso interi statici per una comodità di scrittura 
	public static int DICE = 0,
					  SPRING = 1,
					  CARD = 2,
					  BENCH = 3,
					  INN = 4,
					  FINISH = 5,
					  PAWN0 = 6,
					  PAWN1 = 7,
					  PAWN2 = 8,
					  PAWN3 = 9,
					  PAWN4 = 10,
					  PAWN5 = 11,
					  SNAKE_HEAD = 12,
					  SNAKE_TAIL = 13,
					  SNAKE_BODY_H = 14,
					  SNAKE_BODY_V = 15,
					  SNAKE_CONNECTOR_UP_TO_RX = 16,
					  SNAKE_CONNECTOR_UP_TO_LX = 17,
					  SNAKE_CONNECTOR_RX_TO_DOWN = 18,
					  SNAKE_CONNECTOR_LX_TO_DOWN = 19;
					  
	
	private final int size = 20;
	private ImageIcon[] icons = new ImageIcon[size];
	
	public ImageIcon get(int reusableIndex) {
		if(reusableIndex < 0 || reusableIndex >= size)
			throw new IllegalArgumentException();
		return icons[reusableIndex];
	}
}
