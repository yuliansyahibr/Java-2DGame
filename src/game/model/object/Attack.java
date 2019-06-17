package game.model.object;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import game.view.GamePanel;

public abstract class Attack extends GameEntity{
	
	
	protected boolean remove;
	protected BufferedImage img;
	
	public Attack(double x, double y, double dx, double dy) {
		
		this.x = x + 7;
		this.y = y;
		
		this.dx = dx;
		this.dy = dy;
		
//		AudioPlayer.play("attackA");
	}
	
	private void hit(GameEntity ge) {
		
	}
	

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(img, (int)x, (int)y, width, height, null);
	}

	@Override
	public void update() {
		moveY();
		
		if(y < 0 || y > GamePanel.HEIGHT) {
			remove=true;
		}
	}

	@Override
	public void moveY() {
		this.y = (int) (this.y - dy);	
	}

	@Override
	public void moveX() {
		
	}
	
	public void setRemove() {
		remove = true;
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	
}




