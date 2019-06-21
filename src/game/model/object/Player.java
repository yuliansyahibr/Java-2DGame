package game.model.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.model.object.attack.Attack;
import game.model.object.attack.Player_Attack;
import game.model.object.enemy.EnemiesManager;
import game.view.GamePanel;

public class Player extends GameEntity{
	
	// state dari gerakan player
	private boolean up, down, left, right;
	
	// kecepatan gerak player
	public double v;
	
	// penglambatan, berfungi untuk memuluskan gerakan 
	public double deceleration;
	// hp player
	public double health;
	
	// score dan username
	private static int score=0;
	private static String username;
	
	// font untuk score
	private Font fontScore;
	
	private ArrayList<Player_Attack> attacks;
	
	private EnemiesManager enemyManager;
	
	// gambar player
	private BufferedImage img;
	// health bar
	private BufferedImage hp;
	private BufferedImage hp_bar;
	
	public Player() {
		
		// set down dan up ke false
		up = down = false;
		
		// inisialisasi player
		health = 15;
		dx = 0;
		dy = 0;
		v=2.6;
		deceleration = 0.4;
		
		// koordinat spawn player
		x = GamePanel.WIDTH/2 -20;
		y = GamePanel.HEIGHT - 40;
		// ukuran player
		width = 20;
		height = 22;
		
		attacks = new ArrayList<Player_Attack>();		
		fontScore = new Font("Arial", Font.PLAIN,  12);		
		
		// load image dari class Images
		img = Images.Player;
		// load images dari direktori
		try {
			hp = ImageIO.read(Player.class.getResourceAsStream("/player/health.png"));
			hp_bar = ImageIO.read(Player.class.getResourceAsStream("/player/health_bar.png"));			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// inisialisasi
	public void init(EnemiesManager em, int skor) {
		this.enemyManager = em;		
		score = skor;
	}
	
	public void draw(java.awt.Graphics2D g2) {		
		
		// gambar skor
		g2.setColor(Color.WHITE);
		g2.setFont(fontScore);
		g2.drawString(String.format("%05d", score), GamePanel.WIDTH-54, 20);		
		
		// gambar attack
		for (Attack attack : attacks) {
			attack.draw(g2);
		}
		
		// gambar player
		g2.drawImage(img, (int)x, (int)y, width, height, null);
		
		
		// draw health bar
		g2.drawImage(hp_bar, 7, GamePanel.HEIGHT-28, 112, 18, null);
		g2.drawImage(hp, 24, GamePanel.HEIGHT-22, (int)(6*health), 8, null);
//		for (int i = 0; i < health; i++) {
//			g2.drawImage(hp, 24+(i*6), GamePanel.HEIGHT-22, 6, 8, null);
//		}
	}
	
	public void update() {

		// jika hp habis, set dead = true
		if(health <=0 ) {
			dead = true;
			return;
		}
		
		// gerak vertikal
		moveY();
		// gerak horizaontal
		moveX();
		// cek collision antara player dan dinding
		wallCollision();
		// set posisi baru player
		setPosition();
		// perlambat kecepatan gerakan
		decelerate();		
		
		// update attack
		for (int i = 0; i < attacks.size(); i++) {
			Attack attack = attacks.get(i);
			attack.update();
			// cek tembakan
			if(enemyManager.scanPlayerAttack(attack)) addScore();
			// jika kena, hapus tembakan
			if(attack.shouldRemove()) {
				attacks.remove(i);
				i--;
			}
		}
	}
		
	// status gerak ke atas
	public void setUp(boolean state) {
		up = state;
	}
	// status gerak ke bawah
	public void setDown(boolean state) {
		down = state;
	}
	// status gerak ke kiri
	public void setLeft(boolean state) {
		left = state;
	}
	// status gerak ke kanan
	public void setRight(boolean state) {
		right = state;
	}
	// create attack
	public void attack() {
		attacks.add(new Player_Attack((int)x, (int)y, 0, 5));
	}
	
	// gerak vertikal player  
	public void moveY() {		
		if(up) {
			dy = -v;
		}
		else if(down) {
			dy  = v; 		
		}		
		
	}
	// gerak horizontal player
	public void moveX() {		
		if(left) {
			dx = -v;
		}
		else if(right) {
			dx  = v; 		
		}
	}
	
	// memperlambat kecepatan gerak player
	// kecepanan player dikurangi secara perlahan sampai menjadi 0
	private void decelerate() {
		if(dx > 0) {
			dx -=  deceleration;
			if(dx < 0) dx = 0;
		}else if(dx < 0) {
			dx +=  deceleration;
			if(dx > 0) dx = 0;
		}		
		if(dy > 0) {
			dy -= deceleration;
			if(dy < 0) dy = 0; 
		}else if(dy < 0) {
			dy += deceleration;
			if(dy > 0) dy = 0;
		}
	}
	
	// periksa tabrakan dengan dinding
	private void wallCollision() {		
		if(x+dx <= 0 || x+dx+width >= GamePanel.WIDTH) dx = 0;		
		if(y+dy <= 0 || y+dy+height >= GamePanel.HEIGHT) dy = 0;		
	}
	// add score player
	public void addScore() {
		score+=5;
	}
	// terkena sereangan
	public void getHit() {
		health--;
	}
	// set username
	public static void setUsername(String username){
		Player.username = username;
	}
	// get username
	public static String getUsername() {
		return username;
	}
	// get score
	public static int getScore() {
		return score;
	}
}









