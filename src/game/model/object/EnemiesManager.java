package game.model.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import game.view.GamePanel;


public class EnemiesManager {
	
	private ArrayList<Enemy> enemies;
	Random rand;
	int enemyOffset;
	int attackOffset;
	int n;
	
	public static boolean SPAWN_STATUS;
	private Animation animation;
	private BufferedImage[] sprite;
	
	private boolean EXPLODED;
	
	public EnemiesManager(int enemyOffset, int attackOffset) {
		enemies = new ArrayList<Enemy>();
		rand = new Random();
		this.enemyOffset = enemyOffset;
		this.attackOffset = attackOffset;
		
//		BufferedImage image = Images.Explosion;
//		sprite = new BufferedImage[5]; 
//		for (int i = 0; i < 5; i++) {
//			sprite[i] = image.getSubimage(i*32, 0, 32, 32);
//		}
//		
//		animation = new Animation();
//		animation.setFrames(sprite, 3);
		
		AudioPlayer.load("/Sounds/explosion3.mp3", "explosion");
	}
	
	public void init(Player player) {
		Enemy.setPlayer(player);
//		Enemy.setExplosionAnimation(Images.Explosion);
	}
	
	public void spawnEnemies() {
		
//		int z = rand.nextInt(enemyOffset)+1;

		int col = 8;
		int space = GamePanel.WIDTH/col;
		
			int e = rand.nextInt(255)+1;
		
			for (int i = 0; i < 8; i++) {
				int x = rand.nextInt(20)+10;
				int y = rand.nextInt(50)+10;
				if(e%2 == 1) {
					enemies.add(new EnemyB(i*space+x, -20-y));
		//				System.out.println(i*20*2);
				}
				
				e /= 2;
			}
//		}
	}
	
	public void drawEnemies(Graphics2D g2) {
		
		for (Enemy enemy : enemies) {
			enemy.draw(g2);
		}
	}
	
	public void updateEnemies(Player p) {
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			
			int r = rand.nextInt(100)+30;
			
			if((int)enemy.y%r == 0) enemy.attack();
			
			enemy.update();
			
			if(enemy.shouldRemove()) {
				enemies.remove(i);
				i--;
			}
			
			if(p.collision(enemy)) p.health-=1;	
		}
	}
	
	public boolean scanPlayerAttack(Attack attack) {

//		System.out.println("--"+attack.x);
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
//			System.out.println(enemy.x);
			if(attack.collision(enemy)) {
				AudioPlayer.play("hit");
//				enemies.remove(i);
				enemy.setDead(true);
				AudioPlayer.play("explosion");
//				EXPLODED = true;
				attack.setRemove(true);
				i--;
				return true;
			}
		}
		return false;
	}
	
	
}



