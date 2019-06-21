package game.model.object.enemy;

import game.model.object.Images;
import game.model.object.attack.EnemyA_Attack;


// kelas untuk EnemyB
public class EnemyB extends Enemy{

	public EnemyB(double x, double y) {		
		super(x, y);
		//set image and size
		img = Images.EnemyB;
		width = 61;
		height = 46;
		// set death animation
		explosionAnimation.setFrames(Images.Explosion, 4);
	}
	
	// create attack
	public void attack() {
		attacks.add(new EnemyA_Attack((int)x+8, (int)y+height-13, 0, -5));
		attacks.add(new EnemyA_Attack((int)x+width-22, (int)y+height-13, 0, -5));
	}
	
	// gambar ledakan
	@Override
	public void drawExplosion(java.awt.Graphics2D g2) {
		g2.drawImage(explosionAnimation.getImage(), (int)x-7, (int)y, 32, 32, null);
		g2.drawImage(explosionAnimation.getImage(), (int)x+15, (int)y+16, 32, 32, null);
		g2.drawImage(explosionAnimation.getImage(), (int)x+30, (int)y, 32, 32, null);
	}
	
}



