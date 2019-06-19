package game.controller.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.controller.inputs.Keys;
import game.controller.inputs.Mouse;
import game.model.object.Attack;
import game.model.object.AudioPlayer;
import game.model.object.EnemiesManager;
import game.model.object.Player;
import game.view.GamePanel;

public class Level1 extends GameState{

	private Attack attack;
	private Player player;
	private ArrayList<Attack> pAttacks;
	
	private double bgX, bgY;
	private BufferedImage bg;
	
	private EnemiesManager em;
	int ticks;
	
	public Level1(GameStateManager gsm) {
		super(gsm);
		ticks = 100;
		bgX = 0;
		bgY = 0;
		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/bg-test4.png"));
			
			AudioPlayer.loop("bgm_level1", 600, AudioPlayer.getFrames("bgm_level1") - 2000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		init();
	}
	
	@Override
	public void init() {
		
		em = new EnemiesManager(11, 2);		
		player = new Player();
		
		em.init(player);		
		player.init(em, 0);
		
		AudioPlayer.load("/Sounds/attackA.wav", "attackA");
		AudioPlayer.load("/Sounds/playerhit.wav", "hit");
		AudioPlayer.load("/Sounds/gameover.wav", "game_over");
	}

	@Override
	public void update() {
		
		if(player.getScore() > 100) {
//			gsm.setState(GameStateManager.LEVEL2);
		}
		
		handleInput();
		
		bgY -= -0.5;
		if(bgY > GamePanel.HEIGHT) bgY=0;

		if(player.dead) {
			//stop bgm
			AudioPlayer.stop("bgm_level1");
			gsm.setGameOver(true);
			AudioPlayer.play("game_over");
			return;
		}
		
		player.update();
		em.updateEnemies(player);
		
	}

	@Override
	public void draw(Graphics2D g2) {
		
		//draw background
		drawBackground(g2);
		
		ticks++;
//		System.out.println(ticks);
		
		if(ticks%200 == 0) {
			em.spawnEnemies();
		}
		
		em.drawEnemies(g2);
				
		player.draw(g2);
	}
	
	private void drawBackground(Graphics2D g2) {
		//draw background
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g2.drawImage(bg, (int)bgX,(int) bgY, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(bg, (int)bgX, (int)bgY-GamePanel.HEIGHT, GamePanel.WIDTH, GamePanel.HEIGHT, null);
	}

	@Override
	public void handleInput() {
		
		player.setUp(Keys.keyState[Keys.W]);
		player.setDown(Keys.keyState[Keys.S]);
		
		player.setLeft(Keys.keyState[Keys.A]);		
		player.setRight(Keys.keyState[Keys.D]);
		
		if(Mouse.isClicked(Mouse.LEFT_CLICK)) { 
			player.attack();
		}
		
		if(Keys.isPressed(Keys.SPACE)) {
			AudioPlayer.stop("bgm_level1");
			gsm.setPaused(true);
		}
		
	}

}
