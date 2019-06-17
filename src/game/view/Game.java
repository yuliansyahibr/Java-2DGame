package game.view;

import javax.swing.JFrame;

public class Game {
	
	public static void runGame() {
		JFrame window = new JFrame("TMD");
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
}
