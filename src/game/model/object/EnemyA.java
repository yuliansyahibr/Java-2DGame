package game.model.object;

public class EnemyA extends Enemy{
	
	
	public EnemyA(double x, double y) {
		
		super(x, y);
		
		img = Images.EnemyA;
				
		width = 20;
		height = 22;
		
		explosionAnimation.setFrames(Images.Explosion, 3);
	}
	
	public void attack() {
		attacks.add(new EnemyA_Attack((int)x, (int)y+height, 0, -5));
	}
	
}



