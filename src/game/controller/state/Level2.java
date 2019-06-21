package game.controller.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.controller.inputs.Keys;
import game.controller.inputs.Mouse;
import game.model.object.Player;
import game.model.object.attack.Attack;
import game.model.object.enemy.EnemiesManager;
import game.view.GamePanel;

public class Level2 extends GameState{

	private Attack attack;
	private Player player;
	private ArrayList<Attack> pAttacks;
	
	private double bgX, bgY;
	private BufferedImage bg;
	
	private EnemiesManager em;
	int ticks;
	
	public Level2(GameStateManager gsm) {
		super(gsm);
		ticks = 100;
		bgX = 0;
		bgY = 0;
		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/bg6.png"));

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		init();
	}
	
	@Override
	public void init() {
		
		em = new EnemiesManager();		
		player = new Player();
		
		em.init(player);		
		player.init(em, Player.getScore());
	}

	@Override
	public void update() {
		
		handleInput();
		
		bgY -= -0.6;
		if(bgY > GamePanel.HEIGHT) bgY=0;

		if(player.dead) {
			gsm.setGameOver(true);
			return;
		}
		
		player.update();
		em.updateEnemies(player);
		
	}

	@Override
	public void draw(Graphics2D g2) {
		
		//draw background
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g2.drawImage(bg, (int)bgX,(int) bgY, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g2.drawImage(bg, (int)bgX, (int)bgY-GamePanel.HEIGHT, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		
		ticks++;
//		System.out.println(ticks);
		
		if(ticks%200 == 0) {
			em.spawnEnemies();
		}
		
		em.drawEnemies(g2);
				
		player.draw(g2);
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
			gsm.setPaused(true);;
		}
		
	}

}
