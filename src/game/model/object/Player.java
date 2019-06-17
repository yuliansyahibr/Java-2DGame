package game.model.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.view.GamePanel;

public class Player extends GameEntity{
	
	private boolean up, down, left, right;
	private boolean wallX, wallY;
	
//	public int width, height;
	
	public double v;
	public double deceleration;
	public int health;
	public static int score=0;
	
	private Font fontScore;
	
//	private Attack attack;
	
	private ArrayList<Player_Attack> attacks;
	
	private EnemiesManager enemyManager;
	
	private BufferedImage img;
	
	public Player() {
		
		up = down = false;
		
		health = 10;
		dx = 0;
		dy = 0;
		v=2.6;
		deceleration = 0.4;
		
		x = GamePanel.WIDTH/2 -20;
		y = GamePanel.HEIGHT - 40;
		width = 20;
		height = 22;
		
		attacks = new ArrayList<Player_Attack>();
		
		fontScore = new Font("Arial", Font.PLAIN,  10);
		
		img = Images.Player;
//		try {
//			img = ImageIO.read(Player.class.getResourceAsStream("/player.png"));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
	
	public void init(EnemiesManager em, int skor) {
		this.enemyManager = em;
		
		score = skor;
	}
	
	public void draw(java.awt.Graphics2D g2) {
		
		g2.setColor(Color.WHITE);
		g2.setFont(fontScore);
		g2.drawString("Score: "+score, GamePanel.WIDTH-70, 20);
		
//		if(dead) {
////			g2.setColor(Color.DARK_GRAY);
//			g2.setFont(new Font("Times New Roman", Font.PLAIN,  20));
//			g2.drawString("G A M E  O V E R", 132, 130);
//		}
		
		for (Attack attack : attacks) {
			attack.draw(g2);
		}
		
		g2.setColor(Color.BLUE);
//		g2.fillOval(0, 0, 10, 20);
//		g2.fillRect((int)x, (int)y, this.width, this.height);
		g2.drawImage(img, (int)x, (int)y, width, height, null);
		
		g2.setColor(Color.decode("#780303"));
		g2.fillRect(5, GamePanel.HEIGHT-25, 8*10, 10);
		g2.setColor(Color.decode("#FFF25C"));
		g2.fillRect(5, GamePanel.HEIGHT-25, 8*health, 10);
		
		
		
	}
	
	public void update() {
//		if(up) y -= dy;
//		else if(down) y += dy;
//		
//		if(left) x -= dx;
//		else if(right) x += dx;
		
		if(health <=0 ) {
			dead = true;
			return;
		}
		
		
		moveY();
		moveX();
		wallCollision();
		setPosition();
		decelerate();		
		
		for (int i = 0; i < attacks.size(); i++) {
			Attack attack = attacks.get(i);
			attack.update();
			if(enemyManager.scanPlayerAttack(attack)) addScore();
			if(attack.shouldRemove()) {
				attacks.remove(i);
				i--;
			}
		}
		
//		for (Attack attack : attacks) {
//			attack.update();
//			enemyManager.playerAttack(attack);
//			if(attack.shouldRemove()) {
//				attacks.remove(attack);
//			}
//		}
		
//		if(attack != null) {
//			attack.move();
//		}
	}
	
	
	public void getHit() {
		health--;
	}
	
	public void addScore() {
		score+=10;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setUp(boolean state) {
		up = state;
	}
	
	public void setDown(boolean state) {
		down = state;
	}
	
	public void setLeft(boolean state) {
		left = state;
	}
	
	public void setRight(boolean state) {
		right = state;
	}
	
	public void attack() {
		attacks.add(new Player_Attack((int)x, (int)y, 0, 5));
	}
	
	public void moveY() {		
		if(up) {
			dy = -v;
		}
		else if(down) {
			dy  = v; 		
		}		
		
	}
	
	public void moveX() {		
		if(left) {
			dx = -v;
		}
		else if(right) {
			dx  = v; 		
		}
	}
	
//	private void setPosition() {		
//		x += dx; 
//		y += dy;
//	}
	
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
	
	private void wallCollision() {
		
		if(x+dx <= 0 || x+dx+width >= GamePanel.WIDTH) dx = 0;
		
		if(y+dy <= 0 || y+dy+height >= GamePanel.HEIGHT) dy = 0;
		
		
	}
}









