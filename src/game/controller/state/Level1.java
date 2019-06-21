package game.controller.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import game.controller.inputs.Keys;
import game.controller.inputs.Mouse;
import game.model.object.AudioPlayer;
import game.model.object.Player;
import game.model.object.enemy.EnemiesManager;
import game.view.GamePanel;

public class Level1 extends GameState{

	// player
	private Player player;
	
	// background image
	private double bgX, bgY;
	private BufferedImage bg;
	
	// enemies manager
	private EnemiesManager em;
	
	// ticks
	int ticks;
	
	//constructor
	public Level1(GameStateManager gsm) {
		super(gsm);
		
		// set ticks ke 100 agar munculnya musuh tidak terlalu lam
		ticks = 100;
		
		// background
		bgX = 0;
		bgY = 0;
		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/bg-level1.png"));			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// inisialisasi
		init();
	}
	
	@Override
	public void init() {
		
		// set enemyoffset 11, attack offset 2
		em = new EnemiesManager();		
		player = new Player();
		
		// inisialisasi enemies manager
		em.init(player);		
		// init player, set skor ke 0
		player.init(em, 0);
		
		// load sfx
		AudioPlayer.load("/Sounds/attackA.wav", "attackA");
		AudioPlayer.load("/Sounds/playerhit.wav", "hit");
		AudioPlayer.load("/Sounds/gameover.wav", "game_over");
		// play bgm
		AudioPlayer.loop("bgm_level1", 600, AudioPlayer.getFrames("bgm_level1") - 2000);
	}

	@Override
	public void update() {
		
		// tambah ticks
		ticks++;
		
		if(ticks%200 == 0) {
			// reset ticks
			ticks=0;
			// spawn enemies
			em.spawnEnemies();
		}
		
		// jika skor sudah memnuhi target, lanjut ke stage 2
		if(player.getScore() > 100) {
//			gsm.setState(GameStateManager.LEVEL2);
		}
		
		// update inputs
		handleInput();
		
		// jalankan background scr vertikal
		bgY -= -0.4;
		if(bgY > GamePanel.HEIGHT) bgY=0;

		// jika player mati
		// masuk state gameover
		if(player.dead) {
			//stop bgm
			AudioPlayer.stop("bgm_level1");
			// set gameover = true
			gsm.setGameOver(true);
			// mainkan sfx gameover
			AudioPlayer.play("game_over");
			// return, abaikan update 
			return;
		}
		
		//update player
		player.update();
		// update enemies
		em.updateEnemies(player);
		
	}

	@Override
	public void draw(Graphics2D g2) {
		
		//draw background
		drawBackground(g2);
		//draw musuh
		em.drawEnemies(g2);
		// draw player
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
		// input ntuk gerakan player
		player.setUp(Keys.keyState[Keys.W]);
		player.setDown(Keys.keyState[Keys.S]);		
		player.setLeft(Keys.keyState[Keys.A]);		
		player.setRight(Keys.keyState[Keys.D]);
		
		// input untuk serangan player
		if(Mouse.isClicked(Mouse.LEFT_CLICK)) { 
			player.attack();
		}
		// input untuk pause
		if(Keys.isPressed(Keys.SPACE)) {
			AudioPlayer.stop("bgm_level1");
			gsm.setPaused(true);
		}
		
	}

}
