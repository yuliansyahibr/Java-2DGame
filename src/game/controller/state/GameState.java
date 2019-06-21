package game.controller.state;


// class abstract untuk gamestates
public abstract class GameState {
	
	// manager
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}	
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g2);
	public abstract void handleInput();
}
