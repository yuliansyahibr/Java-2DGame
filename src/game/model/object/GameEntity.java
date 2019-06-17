package game.model.object;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class GameEntity {
	
//	protected boolean up, down, left, right;
	
	public int width, height;
	
	public double x, y, dx, dy;
	public double deceleration;
	
	protected ArrayList<Attack> attacks;
	
	public abstract void draw(java.awt.Graphics2D g2);
	
	public abstract void update();
	
	public boolean dead;
	
	public boolean collision(GameEntity ge) {
		
		Rectangle r1 = getRectangle();
		Rectangle r2 = ge.getRectangle();
		return r1.intersects(r2);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int)this.x, (int)this.y, this.width, this.height);
	}
	
	public boolean contains(GameEntity ge) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = ge.getRectangle();
		return r1.contains(r2);
	}
	
//	public void attack() {
//		attacks.add(new Attack((int)x, (int)y, 5, attackColor));
//	}
	
	public abstract void moveY();
	
	public abstract void moveX();
	
	protected void setPosition() {		
		x += dx; 
		y += dy;
	}

}
