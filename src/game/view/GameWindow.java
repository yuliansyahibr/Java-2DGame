package game.view;

import javax.swing.JFrame;


// JFrame yang menumpung panel game 
public class GameWindow {
	
	public GameWindow() {
		JFrame window = new JFrame("");
		// add panel game
		window.getContentPane().add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
}
