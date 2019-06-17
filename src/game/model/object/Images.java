package game.model.object;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


// load images saat aplikasi start
public class Images {
	
	public static BufferedImage Player = load("/player.png");
	public static BufferedImage EnemyA = load("/enemyA.png");
	public static BufferedImage EnemyA_attack = load("/enemyA_attack.png");
	public static BufferedImage Attack1 = load("/attack4.png");
	
	
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
}
