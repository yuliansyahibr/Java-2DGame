package game.model.object;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


// load images saat aplikasi start
public class Images {
	
	// gambar biasa 
	public static BufferedImage Player = load("/player/player.png");
	public static BufferedImage EnemyA = load("/enemyA.png");
	public static BufferedImage EnemyB = load("/enemyB.png");
	public static BufferedImage EnemyA_attack = load("/enemyA_attack.png");
	public static BufferedImage Attack1 = load("/attack4.png");
	
	// sprite sheets
	public static BufferedImage[] Explosion = load("/spritesheets/sprite-explosion.png", 32);
	
	// method untuk me load gambar biasa
	public static BufferedImage load(String s) {
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
	
	// method untuk meload sprite sheet 1 baris
	public static BufferedImage[] load(String s, int width) {
		return load(s, width, width)[0];
	}
	
	// load sprite sheet n baris
	public static BufferedImage[][] load(String s, int width, int height) {
		
		BufferedImage[][] sprite;
		try {
			// load img
			BufferedImage img = ImageIO.read(Images.class.getResourceAsStream(s));
			// hitung jumlah kolom
			int col = img.getWidth()/width;
			// hitung jumlah baris
			int row = img.getHeight()/height;
			// instansiasi sprite
			sprite = new BufferedImage[row][col];
			// get sub image sesuai kolom dan baris lalu masukkan ke array sprtie
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
