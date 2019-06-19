package game.main;

import javax.swing.UnsupportedLookAndFeelException;

import game.view.Game;
import game.view.ScoresWindow;

public class Main {
	
	public static void main(String[] args) {
//		Game.runGame();
//		new ScoresWindow();
		
		try {			
			javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ScoresWindow();
			}
		});
	}
}
