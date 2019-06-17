package game.model.object;

import java.awt.Graphics2D;
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
	
	public EnemiesManager(int enemyOffset, int attackOffset) {
		enemies = new ArrayList<Enemy>();
		rand = new Random();
		this.enemyOffset = enemyOffset;
		this.attackOffset = attackOffset;
		
		AudioPlayer.load("/Sounds/explosion.wav", "explosion");
	}
	
	public void init(Player player) {
		Enemy.setPlayer(player);
	}
	
	public void spawnEnemies() {
		
		int z = rand.nextInt(enemyOffset)+1;
//		System.out.println(z);
		
//		if(z%2 == 0) {
//			System.out.println(z);
		int col = 8;
		int space = GamePanel.WIDTH/col;
		
			int e = rand.nextInt(255)+1;
//			System.out.println("--"+e);
			int sisa = e;
		
			for (int i = 0; i < 8; i++) {
				int x = rand.nextInt(20)+10;
				int y = rand.nextInt(50)+10;
				if(sisa%2 == 1) {
					enemies.add(new Enemy(i*space+x, -20-y));
		//				System.out.println(i*20*2);
				}
				
				sisa /= 2;
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
			
			int r = rand.nextInt(70)+20;
			
			if((int)enemy.y%50 == 0) enemy.attack();
			
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
				enemies.remove(i);
				AudioPlayer.play("explosion");
				attack.setRemove();
				i--;
				return true;
			}
		}
		return false;
	}
	
	
}



