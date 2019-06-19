package game.model.object;

public class EnemyB extends Enemy{

	
	public EnemyB(double x, double y) {
		
		super(x, y);
		
		img = Images.EnemyB;
		width = 61;
		height = 46;
		
		explosionAnimation.setFrames(Images.Explosion, 3);
	}
	
	public void attack() {
		attacks.add(new EnemyA_Attack((int)x, (int)y+height, 0, -5));
	}
	
	@Override
	public void drawExplosion(java.awt.Graphics2D g2) {
		g2.drawImage(explosionAnimation.getImage(), (int)x-10, (int)y, 32, 32, null);
		g2.drawImage(explosionAnimation.getImage(), (int)x, (int)y+15, 32, 32, null);
		g2.drawImage(explosionAnimation.getImage(), (int)x+10, (int)y, 32, 32, null);
	}
	
}



