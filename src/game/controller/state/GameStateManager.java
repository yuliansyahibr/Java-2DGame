package game.controller.state;

import game.model.object.AudioPlayer;
import game.model.object.Player;
import game.model.user.Scores;
import game.view.GamePanel;

public class GameStateManager {
	
	// model table tpeace
	private Scores scores;
	
	// array dari states
	private GameState[] gameStates;
	
	// index dari state yang berjalan 
	private int currentState;
	
	
	// jumlah states dalam game
	public static final int NUMGAMESTATES = 3;
	
	// list states
	public static final int MENUSTATE = 0;
	public static final int LEVEL1 = 1;
	public static final int LEVEL2 = 2;
	
	// pause dan gameover terpisah agar waktu transisi ke state tidak lama
	// state pause
	private PauseState pauseState;
	private boolean paused;
	// state game over
	private boolean gameover;
	private GameOverState gameOverState;
	
	// status running
	// exit game = set running to false
	private boolean running;
	
	// bgm sekarang
	private String currentBgm;
	
	public GameStateManager() {
		
		// inisialisasi audio player
		AudioPlayer.init();
		
		// score
		scores = new Scores();
		
		// inisialisasi gamestates
		gameStates = new GameState[NUMGAMESTATES];
		
		// inisialisasi pause state
		pauseState = new PauseState(this);
		paused = false;
		
		// inisialisasi gameover state
		gameOverState = new GameOverState(this);
		gameover = false;
		
		// set running ke true
		running = true;
		
		// jalankan menu state
		currentState = MENUSTATE;
		loadState(currentState);
	}
	
	private void loadState(int state) {
		// inisialisasi state
		// set bgm
		AudioPlayer.stop(currentBgm);
		if(state == MENUSTATE) { 
			scores.update(Player.getUsername(), Player.getScore());
			currentBgm = "bgm_menu";
			gameStates[state] = new MenuState(this);
		}else if(state == LEVEL1) {
			currentBgm = "bgm_level1";
			gameStates[state] = new Level1(this);
		}
		else if(state == LEVEL2) gameStates[state] = new Level2(this);
		
	}
	
	// unload state
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	// ubah state
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	// set pause state
	public void setPaused(boolean b) {		
		paused = b;		
		// jika pause true, stop bgm
		// jika false, resume
		if(!paused )AudioPlayer.resume(currentBgm);
	}
	
	// game over
	public void setGameOver(boolean b) {
		gameover = b;
	}
	
	// set status running dari game
	public void setRunning(boolean b) {
		running = b;
		if(!running) AudioPlayer.stop(currentBgm);
	}
	
	// cek status dari running
	public boolean isRunning() {
		return running;
	}
	
	// update game
	// memanggil method-method update di masing2 state
	public void update() {
		
		// jika game over, interrupt & jalankan state game over
		if(gameover) {
			gameOverState.update();
			return;
		}
		
		// jika sedang pause, interrupr & jalankan state pause
		if(paused) {
			pauseState.update();
			return;
		}
		
		// jalankan update
		if (gameStates[currentState] != null) {
			gameStates[currentState].update();
		}
	}
	
	// menjalankan method-method yang ada di tiap state
	public void draw(java.awt.Graphics2D g2) {
		// jika game over, interrupt & jalankan state game over
		if(gameover) {
			gameOverState.draw(g2);
			return;
		}
		
		// jika sedang pause, interrupr & jalankan state pause
		if(paused) {
			pauseState.draw(g2);
			return;
		}
		
		// jalankan draw
		// jika null gambar layar hitam
		if (gameStates[currentState] != null) gameStates[currentState].draw(g2);
		else {
			g2.setColor(java.awt.Color.BLACK);
			g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
}
