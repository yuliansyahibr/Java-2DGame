package game.model.object.attack;

import game.model.object.Images;

// Kelas untuk serangan dari EnemyA
public class EnemyA_Attack extends Attack{
	
	public EnemyA_Attack(double x, double y, double dx, double dy) {
		super(x, y, dx, dy);
		//set image
		img = Images.EnemyA_attack;
		// set size
		this.width = 3;
		this.height = 12;
	}
	

}
