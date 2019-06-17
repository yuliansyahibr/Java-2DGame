package game.model.object;

public class Player_Attack extends Attack{

	public Player_Attack(double x, double y, double dx, double dy) {
		super(x, y, dx, dy);
		
		this.width = 1;
		this.height = 8;
		
		img = Images.Attack1;
		
		playSound();
	}

	
	private void playSound() {
		AudioPlayer.play("attackA");
	}
	
}
