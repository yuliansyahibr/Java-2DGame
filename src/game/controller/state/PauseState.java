package game.controller.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.controller.inputs.Keys;
import game.view.GamePanel;

public class PauseState extends GameState{
	
	// font
	private Font font1;
	private Font font2;
	// background
	private Color bgColor;

	public PauseState(GameStateManager gsm) {
		super(gsm);
		font1 = new Font("Times New Roman", Font.PLAIN,  28);
		font2 = new Font("Arial", Font.PLAIN, 11);
		
		bgColor = new Color(0.221f, 0.221f, 0.221f, 0.01f);
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
		// draw background
		g2.setColor(bgColor);
		g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		// draw string
		g2.setColor(Color.WHITE);
		g2.setFont(font1);
		g2.drawString("P A U S E D", 128, 130);		
		g2.setFont(font2);		
		int xPos = GamePanel.WIDTH/2 - 58; 
		g2.drawString("Press esc to exit game", xPos, 165);;
	}

	// inputan user
	@Override
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}else if (Keys.anyKeyPress()) {
			gsm.setPaused(false);
		}
	}

}
