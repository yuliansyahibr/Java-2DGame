package game.model.object;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


// load images saat aplikasi start
public class Images {
	
	public static BufferedImage Player = load("/player.png");
	public static BufferedImage EnemyA = load("/enemyA.png");
	public static BufferedImage EnemyB = load("/enemy2.png");
	public static BufferedImage EnemyA_attack = load("/enemyA_attack.png");
	public static BufferedImage Attack1 = load("/attack4.png");
	
	public static BufferedImage[] Explosion = load("/spritesheets/sprite-explosion.png", 32);
	
	
	public static BufferedImage load(String s) {
//		BufferedImage ret;
		try {
			BufferedImage img = ImageIO.read(Images.class.getResourceAsStream(s));
			
			return img;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}
	
	public static BufferedImage[] load(String s, int width) {
		return load(s, width, width)[0];
	}
	
	public static BufferedImage[][] load(String s, int width, int height) {
//		BufferedImage ret;
		BufferedImage[][] sprite;
		try {
			BufferedImage img = ImageIO.read(Images.class.getResourceAsStream(s));
			int col = img.getWidth()/width;
			int row = img.getHeight()/height;
			sprite = new BufferedImage[row][col];			
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					sprite[i][j] = img.getSubimage(j*width, i*height, width, height);
				}
			}			
			return sprite;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}
}
