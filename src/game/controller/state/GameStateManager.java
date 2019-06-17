package game.controller.state;

import game.model.object.AudioPlayer;
import game.view.GamePanel;
import game.view.ScoresWindow;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 4;
	
	public static final int MENUSTATE = 0;
	public static final int LEVEL1 = 1;
	public static final int LEVEL2 = 2;
	public static final int PAUSE = 3;
//	public static final boo GAMEOVER = 3;
	
	private boolean gameover;
	private GameOverState gameOverState;
	private boolean running;
	private int score;
	
	public GameStateManager() {
		
		AudioPlayer.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
		gameOverState = new GameOverState(this);
		gameover = false;
		
		running = true;
		
		currentState = MENUSTATE;
//		currentState = LEVEL1;
		loadState(currentState);
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE) gameStates[state] = new MenuState(this);
		else if(state == LEVEL1) gameStates[state] = new Level1(this);
		else if(state == LEVEL2) gameStates[state] = new Level2(this);
		
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public void setPaused(boolean b) {
		paused = b;
	}
	
	public void setGameOver(boolean b) {
		gameover = b;
	}
	
	public void setRunning(boolean b) {
		running = b;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void update() {
		
		if(gameover) {
			gameOverState.update();
			return;
		}
		if(paused) {
			pauseState.update();
			return;
		}
		
		if (gameStates[currentState] != null) {
			gameStates[currentState].update();
		}
	}
	
	public void draw(java.awt.Graphics2D g2) {
		
		if(gameover) {
			gameOverState.draw(g2);
			return;
		}
		if(paused) {
			pauseState.draw(g2);
			return;
		}
		
		if (gameStates[currentState] != null) gameStates[currentState].draw(g2);
		else {
//			g2.setColor(java.awt.Color.BLACK);
//			g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
}
