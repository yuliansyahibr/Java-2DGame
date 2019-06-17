package game.controller.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.rmi.CORBA.Tie;

import game.controller.inputs.Keys;
import game.model.object.AudioPlayer;
import game.view.GamePanel;

public class MenuState extends GameState{

	private BufferedImage head;
	
	private int currentChoice = 0;
	private String[] options = {
			"Start",
			"Help",
			"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	private Font titleFont2;
	
	private Font font1;
	private Font font2;
	
	private BufferedImage bg;
	private BufferedImage stars;
	
	private double bgX;
	private double starsX;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		
		bgX=0;
		starsX=0;
		
		try {
			// load floating head cursor
			head = ImageIO.read(getClass().getResourceAsStream("/HUD/arrow.png"));
			bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/bg_menu.png"));
			stars = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/stars.png"));
			
			//titles and fonts
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN,  28);
			titleFont2 = new Font("Times New Roman", Font.PLAIN,  20);
			
			font1 = new Font("Arial", Font.PLAIN, 14);
			font2 = new Font("Arial", Font.PLAIN, 10);
			
			// load sounds
			AudioPlayer.load("/Sounds/menuoption.mp3", "menuopt");
			AudioPlayer.load("/Sounds/menuselect.mp3", "menuselect");
//			AudioPlayer.load("/Sounds/playerhit.wav", "hit");
			
			AudioPlayer.load("/Sounds/bgm_menu.mp3", "bgm_menu");
			
			AudioPlayer.loop("bgm_menu", 600, AudioPlayer.getFrames("bgm_menu") - 2200);
//			AudioPlayer.loop("attack1", 0, AudioPlayer.getFrames("attack1")-10);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
		
		//check keys
		handleInput();
		
		bgX -= 0.6;

		if(bgX <= -GamePanel.WIDTH) bgX=0;
		
		starsX -= 0.25;
		if(starsX <= -GamePanel.WIDTH) starsX=0;
	}

	@Override
	public void draw(Graphics2D g2) {
		
		//draw background
		g2.setColor(Color.DARK_GRAY);
		g2.drawImage(bg, (int) bgX, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(bg, (int)bgX+GamePanel.WIDTH, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(stars, (int)starsX, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(stars, (int)starsX+GamePanel.WIDTH, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		
		//draw title
		g2.setColor(titleColor);
		g2.setFont(titleFont);
		g2.drawString("P E A C E ", 62, 112);
		g2.setFont(titleFont2);
		g2.drawString("", 180, 112);
		g2.setFont(titleFont);
		g2.drawString("K E E P E R", 206, 112);
		
		// draw menu options
		g2.setFont(font1);
		g2.setColor(Color.WHITE);
		int xPos = GamePanel.WIDTH/2 - 26; 
		g2.drawString("Start", xPos, 165);
		g2.drawString("Help", xPos, 190);
		g2.drawString("Quit", xPos, 215);
		
		// draw head
		if(currentChoice == 0) {
			g2.drawImage(head, xPos-25, 154, null);			
		}else if (currentChoice == 1) {
			g2.drawImage(head, xPos-25, 179, null);
		}else if (currentChoice == 2) {
			g2.drawImage(head, xPos-25, 204, null);
		}
		
		//other
		g2.setFont(font2);
		g2.drawString("2019 Yuliansyah", 10, 292);
	}

	@Override
	public void handleInput() {
		
		if(Keys.isPressed(Keys.SPACE)) {
			//System.out.println("e");
			select();
		}
		
		if (Keys.isPressed(Keys.W)) {
			if (currentChoice > 0) {
				currentChoice--;
				AudioPlayer.play("menuopt");
			}
		}
		if (Keys.isPressed(Keys.S)) {
			if (currentChoice < options.length - 1) {
				currentChoice++;
				AudioPlayer.play("menuopt");
			}
		}
	}
	
	
	private void select() {
		if (currentChoice == 0) {
			AudioPlayer.stop("bgm_menu");
			AudioPlayer.play("menuselect");
			gsm.setState(GameStateManager.LEVEL1);
		}else if (currentChoice == 1) {
			
			AudioPlayer.play("menuselect");
		}else if (currentChoice == 2) {
//			System.exit(0);
			AudioPlayer.stop("bgm_menu");
			gsm.setRunning(false);
		}
	}
}
