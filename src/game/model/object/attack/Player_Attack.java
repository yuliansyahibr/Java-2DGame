package game.model.object.attack;

import game.model.object.AudioPlayer;
import game.model.object.Images;

public class Player_Attack extends Attack{

	public Player_Attack(double x, double y, double dx, double dy) {
		super(x, y, dx, dy);
		// size
		this.width = 1;
		this.height = 8;
		// set image
		img = Images.Attack1;
		// putas sfx
		playSound();
	}

	
	private void playSound() {
		AudioPlayer.play("attackA");
	}
	
}
