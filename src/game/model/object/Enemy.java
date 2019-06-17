package game.model.object;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.view.GamePanel;

public class Enemy extends GameEntity{
	
	public static BufferedImage img = Images.EnemyA;
	
	private static Player player;
	
	private boolean remove;
	
	public Enemy(double x, double y) {
		
		remove = false;
		dx = 0;
		dy = 0.7;
		
		
		this.x = x;
		this.y = y;
		width = 20;
		height = 22;
		
		attacks = new ArrayList<Attack>();
	}
	
	public static void setPlayer(Player p) {
		player = p;
	}
	
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
		g2.drawImage(img, (int)x, (int)y, width, height, null);
		
		for (Attack attack : attacks) {
			attack.draw(g2);
		}
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
	
	public boolean shouldRemove() {
		return remove;
	}
	
}



