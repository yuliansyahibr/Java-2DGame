package game.model.object.enemy;

import game.model.object.Images;
import game.model.object.attack.EnemyA_Attack;


// class untuk EnemyA
public class EnemyA extends Enemy{
	
	public EnemyA(double x, double y) {		
		super(x, y);
		// set image
		img = Images.EnemyA;
		// set size
		width = 20;
		height = 22;
		
		// set sprite untuk animasi ledakan dengan delay 4
		explosionAnimation.setFrames(Images.Explosion, 4);
	}
	// create attack
	public void attack() {
		attacks.add(new EnemyA_Attack((int)x, (int)y+height, 0, -5));
	}
	
}



