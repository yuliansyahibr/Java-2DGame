package game.controller.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.controller.inputs.Keys;
import game.model.object.Player;
import game.view.GamePanel;

public class GameOverState extends GameState {

	private int currentChoice = 0;
	private String[] options = {
			"Start",
			"Help",
			"Quit"
	};
	
	private Font font1;
	private Font font2;
	private Font fontScore;
	private Color fontColor;
	
	private Color bgColor;

	public GameOverState(GameStateManager gsm) {
		super(gsm);
		
		fontColor = Color.LIGHT_GRAY;
		font1 = new Font("Times New Roman", Font.PLAIN,  22);
		font2 = new Font("Arial", Font.PLAIN, 10);
		fontScore = new Font("Arial", Font.PLAIN,  10);
		
		bgColor = new Color(0.221f, 0.221f, 0.221f, 0.04f);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		handleInput();
	}

	@Override
	public void draw(Graphics2D g2) {
		
		g2.setColor(bgColor);
		g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		g2.setColor(Color.WHITE);
		g2.setFont(fontScore);
		g2.drawString("Score: "+Player.score, GamePanel.WIDTH-70, 20);
		
		g2.setColor(Color.WHITE);
		g2.setFont(font1);
		g2.drawString("G A M E  O V E R", 110, 130);
		
		g2.setFont(font2);		
		int xPos = GamePanel.WIDTH/2 - 68; 
		g2.drawString("Press space to continue. . .", xPos, 155);;
	}

	@Override
	public void handleInput() {
		if(Keys.isPressed(Keys.SPACE)) {
			gsm.setGameOver(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
}
