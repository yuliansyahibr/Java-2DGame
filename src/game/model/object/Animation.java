package game.model.object;

import java.awt.image.BufferedImage;


// kelas untuk melakukan animasi dari sprite sheet
public class Animation {
	
	// array yang berisi dari frame-frame dari sprite
	private BufferedImage[] sprite;
	// index frame yang sedang dipakai 
	private int currentFrame;
	// jumlah frame yang ada
	private int numFrames;
	// ticks
	private int ticks;
	// delay animasi
	private int delay;
	// status apakh animasi sudah selesai 
	private boolean played;
	
	public Animation() {
		
	}
	
	// set sprite
	public void setFrames(BufferedImage[] img, int delay) {
		// inisialisasi
		sprite = img;
		currentFrame = 0;
		ticks = 0;
		numFrames = sprite.length;
		this.delay = delay;
	}
	
	// set frame
	public void setFrame(int frameIndex) {
		currentFrame = frameIndex;
	}
	
	// update aniimasi
	public void update() {
		ticks++;
		played = false;
		// jika delay terpenuhi
		if(ticks == delay) {
			currentFrame++;
			ticks=0;
		}
		// jika animasi selesai dimainkan
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
