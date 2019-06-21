package game.model.object;

import java.awt.Rectangle;
import java.util.ArrayList;

import game.model.object.attack.Attack;

// Kelas abstrak untuk obejc-objek yang ada di game
public abstract class GameEntity{
	
	protected int width, height;
	
	// posisi dan kecepatan bergerak
	protected double x, y, dx, dy;
	protected double deceleration;
	
	protected ArrayList<Attack> attacks;
	
	// status
	public boolean dead;
	
	public abstract void draw(java.awt.Graphics2D g2);	
	public abstract void update();
	
	protected abstract void moveY();	
	protected abstract void moveX();
	
	// set posisi baru objek
	protected void setPosition() {		
		x += dx; 
		y += dy;
	}
	
	// cek benturan antara objek game
	public boolean collision(GameEntity ge) {		
		Rectangle r1 = getRectangle();
		Rectangle r2 = ge.getRectangle();
		return r1.intersects(r2);
	}
	// get posisi dan ukran object dalam bentuk rectangle
	public Rectangle getRectangle() {
		return new Rectangle((int)this.x, (int)this.y, this.width, this.height);
	}
	// cek jika objek ge berada di dalam objek
	public boolean contains(GameEntity ge) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = ge.getRectangle();
		return r1.contains(r2);
	}
	
	// getters
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

}
