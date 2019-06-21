package game.controller.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.rmi.CORBA.Tie;

import game.controller.inputs.Keys;
import game.model.object.AudioPlayer;
import game.model.user.Scores;
import game.view.GamePanel;

public class MenuState extends GameState{
	
	// daftar option
	private String[] options = {
			"Start",
			"Help",
			"Quit"
	};
	// index option 
	private int currentChoice = 0;
	
	// font 
	private Color titleColor;
	private Font titleFont;
	private Font titleFont2;	
	private Font font1;
	private Font font2;
	
	// background
	private BufferedImage bg;
	private BufferedImage stars;
	private BufferedImage title;
	// panah
	private BufferedImage arrow;
	// help
	private BufferedImage help;
	// posisi bg
	private double bgX;
	private double starsX;
	
	// status help option
	private boolean _help;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		
		// set posisi awal bg
		bgX=0;
		starsX=0;
		
		try {
			// load floating head cursor
			arrow = ImageIO.read(getClass().getResourceAsStream("/arrow.png"));
			title = ImageIO.read(getClass().getResourceAsStream("/title.png"));
			bg = ImageIO.read(getClass().getResourceAsStream("/backgrounds/bg_menu.png"));
			stars = ImageIO.read(getClass().getResourceAsStream("/backgrounds/stars.png"));
			help = ImageIO.read(getClass().getResourceAsStream("/help.jpg"));
			
			//titles and fonts
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN,  28);
			titleFont2 = new Font("Times New Roman", Font.PLAIN,  20);
			
			font1 = new Font("Arial", Font.PLAIN, 14);
			font2 = new Font("Arial", Font.PLAIN, 10);
			
			// load sounds
			AudioPlayer.load("/sounds/menuoption.mp3", "menuopt");
			AudioPlayer.load("/sounds/menuselect.mp3", "menuselect");			
			AudioPlayer.load("/sounds/Frenchyboy_TitleScreen_Music.mp3", "bgm_menu");
			
			// loop bgm
			AudioPlayer.loop("bgm_menu", 600, AudioPlayer.getFrames("bgm_menu") - 2000);
			
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
		// gerak bg
		bgX -= 0.6;
		if(bgX <= -GamePanel.WIDTH) bgX=0;		
		starsX -= 0.25;
		if(starsX <= -GamePanel.WIDTH) starsX=0;
	}

	@Override
	public void draw(Graphics2D g2) {
		
		if(_help) {
			g2.drawImage(help, (int)0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
			return;
		}
		
		//draw background
		g2.setColor(Color.DARK_GRAY);
		g2.drawImage(bg, (int) bgX, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(bg, (int)bgX+GamePanel.WIDTH, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(stars, (int)starsX, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(stars, (int)starsX+GamePanel.WIDTH, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		
		//draw title
		g2.drawImage(title, 62, 96, 286, 31, null);
		
		// draw menu options
		g2.setFont(font1);
		g2.setColor(Color.WHITE);
		int xPos = GamePanel.WIDTH/2 - 26; 
		g2.drawString("Start", xPos, 165);
		g2.drawString("Help", xPos, 190);
		g2.drawString("Quit", xPos, 215);
		
		// draw arrow
		if(currentChoice == 0) {
			g2.drawImage(arrow, xPos-25, 154, null);			
		}else if (currentChoice == 1) {
			g2.drawImage(arrow, xPos-25, 179, null);
		}else if (currentChoice == 2) {
			g2.drawImage(arrow, xPos-25, 204, null);
		}
		
		//other
		g2.setFont(font2);
		g2.drawString("2019 Yuliansyah", 10, 292);
	}

	@Override
	public void handleInput() {
		
		//event-event input
		// spasi = pilih option
		// w = gerak opt ke atas
		// s - gerak opt ke bawah
		// play sfx menuopt jika pilihan berpindah
		if(_help) {
			// anykey = exit help
			if(Keys.anyKeyPress()) {
				_help=false;
			}			
		}else {
			if(Keys.isPressed(Keys.SPACE)) {
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
	}
	
	// pilih option
	private void select() {
		//play sfx
		AudioPlayer.play("menuselect");
		if (currentChoice == 0) {
			AudioPlayer.stop("bgm_menu");
			// load audio
			AudioPlayer.load("/Sounds/Long_Away_Home.mp3", "bgm_level1");
			gsm.setState(GameStateManager.LEVEL1);
		}else if (currentChoice == 1) {
			_help=true;
			AudioPlayer.play("menuselect");
		}else if (currentChoice == 2) {
			// exit game
			gsm.setRunning(false);
		}
	}
}
