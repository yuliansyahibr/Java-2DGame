package game.model.object.enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import game.model.object.Animation;
import game.model.object.AudioPlayer;
import game.model.object.Player;
import game.model.object.attack.Attack;
import game.view.GamePanel;


// Class untuk 
public class EnemiesManager {
	
	private ArrayList<Enemy> enemies;
	Random rand;
	int enemyOffset;
	int attackOffset;
	int n;
	
	
	public EnemiesManager() {
		// inisialisasi
		enemies = new ArrayList<Enemy>();
		rand = new Random();
		this.enemyOffset = enemyOffset;
		this.attackOffset = attackOffset;
		// load sfx
		AudioPlayer.load("/sounds/explosion3.mp3", "explosion");
	}
	
	// set player
	public void init(Player player) {
		Enemy.setPlayer(player);
	}
	
	// spawn enemies
	public void spawnEnemies() {
		
		// batas maksimal enemy dalam 1 row
		int col = 8;
		// jarak antar musuh
		int space = GamePanel.WIDTH/col;
		
			int e = rand.nextInt(255)+1;
		
			for (int i = 0; i < 8; i++) {
				int x = rand.nextInt(20)+10;
				int y = rand.nextInt(50)+10;
				if(e%2 == 1) {
					// random jenis enemy
					int e2 = rand.nextInt(10)+1;
					if(e2%3 == 0) enemies.add(new EnemyB(i*space+x, -20-y));
					else enemies.add(new EnemyA(i*space+x, -20-y));
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
	
	// update enemies
	public void updateEnemies(Player p) {
		
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			// angka acak untuk serangan
			int r = rand.nextInt(80)+30;
			if((int)enemy.getY()%r == 0) enemy.attack();
			
			// cek tabrakan antara musuh dan player 
			if(p.collision(enemy)) p.health-=1;	
			
			// update musuh
			enemy.update();
			// cek jika musuh harus dihapus dari array
			if(enemy.shouldRemove()) {
				enemies.remove(i);
				i--;
			}
		}
	}
	
	// periksa serangan player
	public boolean scanPlayerAttack(Attack attack) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			// jika serangan mengenai musuh
			if(attack.collision(enemy)) {
				// set status musuh
				enemy.setDead(true);
				// play sfx
				AudioPlayer.play("hit");
				AudioPlayer.play("explosion");
				// hapus attack
				attack.setRemove(true);
				i--;
				return true;
			}
		}
		return false;
	}
	
	
}



