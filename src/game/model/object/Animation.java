package game.model.object;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] sprite;
	
	private int currentFrame;
	private int numFrames;
	
	private int ticks;
	private int delay;
	
	private boolean played;
	
	public Animation() {
		
	}
	
	public void setFrames(BufferedImage[] img, int delay) {
		sprite = img;
		currentFrame = 0;
		ticks = 0;
		numFrames = sprite.length;
		this.delay = delay;
	}
	
	public void setFrame(int frameIndex) {
		currentFrame = frameIndex;
	}
	
	public void update() {
//		if(delay == -1) return;
		
		ticks++;
		played = false;
		if(ticks == delay) {
			currentFrame++;
			ticks=0;
		}
		if(currentFrame == numFrames) {
			currentFrame = 0;
			played=true;
		}
	}
	
	public BufferedImage getImage() {
		return sprite[currentFrame];
	}
	
	public boolean hasPlayed() {
		return played;
	}
	
	
}
