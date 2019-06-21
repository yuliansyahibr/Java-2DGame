/*
Saya Yuliansyah Ibrahim mengerjakan evaluasi TMD dalam
mata kuliah Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak
melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
*/


package game.main;

import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

import game.view.ScoresWindow;


public class Main {
	
	public static void main(String[] args) {
		
//		Game.runGame();
		JFrame.setDefaultLookAndFeelDecorated(true);
//		new ScoresWindow();
		
		// run aplikasi dengan look and feel nimbus
		try {			
			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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
