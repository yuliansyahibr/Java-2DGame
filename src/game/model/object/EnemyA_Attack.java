package game.model.object;

public class EnemyA_Attack extends Attack{

	
	public EnemyA_Attack(double x, double y, double dx, double dy) {
		super(x, y, dx, dy);
		
		img = Images.EnemyA_attack;
		
		this.width = 3;
		this.height = 12;
	}
	

}
