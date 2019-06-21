package game.model.object.enemy;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.model.object.Animation;
import game.model.object.GameEntity;
import game.model.object.Player;
import game.model.object.attack.Attack;
import game.model.object.attack.EnemyA_Attack;
import game.view.GamePanel;

// parent class untuk musuh musuh yang ada
public class Enemy extends GameEntity{
	
	// gamabr musuh
	protected BufferedImage img;
	// player
	protected static Player player;
	
	// status dari musuh
	protected boolean dead;
	protected boolean remove;
	
	// animasi meledak
	protected Animation explosionAnimation;
	
	public Enemy(double x, double y) {
		// inisialisasi
		dx = 0; 
		dy = 0.7;		
		this.x = x;
		this.y = y;
		init();
	}
	
	public Enemy(double x, double y, double dx, double dy) {
		// inisialisasi
		this.dx = dx; 
		this.dy = dy;		
		this.x = x;
		this.y = y;
		init();
	}
	protected void init(){
		dead = false;
		remove = false;
		attacks = new ArrayList<Attack>();
		explosionAnimation = new Animation();
	}
	
	// set player
	public static void setPlayer(Player p) {
		player = p;
	}
		
	// kosong karena kecepatan musuh konstan
	@Override
	protected void moveY() {
	}
	@Override
	protected void moveX() {		
	}

	@Override
	public void draw(java.awt.Graphics2D g2) {
		
		// jika musuh mati, gambar animasi ledakan
		// else, gambar musuh sperti biasa 
		if(isDead()) drawExplosion(g2);
		else g2.drawImage(img, (int)x, (int)y, width, height, null);
		for (Attack attack : attacks) {
			attack.draw(g2);
		}
	}
	
	// gambar animasi meledak
	protected void drawExplosion(java.awt.Graphics2D g2) {
		g2.drawImage(explosionAnimation.getImage(), (int)x-5, (int)y, 32, 32, null);
	}

	@Override
	public void update() {
		
		moveY();
		moveX();
		setPosition();
		// jika melebihi batas bawah panel
		// set remove true untuk dihapus
		if(y > GamePanel.HEIGHT) { 
			remove = true;			
			return;
		}
		
		// jika mati
		if(isDead()) {
			// update sprite ledakan
			explosionAnimation.update();
			// jika animasi ledakan selesai
			if(explosionAnimation.hasPlayed()) {
				setRemove(true);
				return;
			}
		}
		
		// check serangan musuh bila mengenai player
		for (int i = 0; i < attacks.size(); i++) {
			Attack attack = attacks.get(i);
			attack.update();
			if(player.collision(attack)) {
				player.getHit();
				attacks.remove(i);
				i--;
			}
		}
	}
	
	public void attack() {
		attacks.add(new EnemyA_Attack((int)x, (int)y+height, 0, -5));
	}
	public void setRemove(boolean b) {
		remove = b;
	}
	public boolean shouldRemove() {
		return remove;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public boolean isDead() {
		return dead;
	}
	
}



