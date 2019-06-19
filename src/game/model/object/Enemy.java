package game.model.object;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.view.GamePanel;

public class Enemy extends GameEntity{
	
	protected static BufferedImage img;
	
	protected static Player player;
	
	protected boolean dead;
	protected boolean remove;
	
	protected Animation explosionAnimation;
	
	public Enemy(double x, double y) {
		
		dead = false;
		remove = false;
		dx = 0; 
		dy = 0.7;		
		this.x = x;
		this.y = y;
		
		attacks = new ArrayList<Attack>();
		
		explosionAnimation = new Animation();
	}
	
	public static void setPlayer(Player p) {
		player = p;
	}
	
//	public static void setExplosionAnimation(BufferedImage image) {
//		BufferedImage[] sprite = new BufferedImage[5]; 
//		for (int i = 0; i < 5; i++) {
//			sprite[i] = image.getSubimage(i*32, 0, 32, 32);
//		}
//		explosionAnimation.setFrames(sprite, 3);
//	}
	
	@Override
	public void moveY() {
	}

	@Override
	public void moveX() {		
	}

	@Override
	public void draw(java.awt.Graphics2D g2) {
		
		g2.setColor(Color.BLUE);
//		g2.fillRect((int)x, (int)y, this.width, this.height);
		if(isDead()) drawExplosion(g2);
		else g2.drawImage(img, (int)x, (int)y, width, height, null);
		
		for (Attack attack : attacks) {
			attack.draw(g2);
		}
	}
	
	public void drawExplosion(java.awt.Graphics2D g2) {
		g2.drawImage(explosionAnimation.getImage(), (int)x-5, (int)y, 32, 32, null);
	}

	@Override
	public void update() {
		
		moveY();
		moveX();
		setPosition();	
		
		if(y > GamePanel.HEIGHT) { 
			remove = true;			
			return;
		}
		
		if(isDead()) {
			explosionAnimation.update();
			if(explosionAnimation.hasPlayed()) {
				setRemove(true);
//				System.out.println("e");
				return;
			}
		}
		
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



