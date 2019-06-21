package game.controller.inputs;

import java.awt.event.KeyEvent;


// Class untuk masukan keyboard
public class Keys {
	
	// jumlah key
	public static final int NUM_KEYS = 11;
	// state
	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];
	// daftar key
	public static int W = 0;
	public static int A = 1;
	public static int S = 2;
	public static int D = 3;
	public static int ENTER = 4;
	public static int SPACE = 5;
	public static int ESCAPE = 10;
	
	// set state
	public static void keySet(int i, boolean b) {
		if(i == KeyEvent.VK_W) keyState[W] = b;
		else if(i == KeyEvent.VK_A) keyState[A] = b;
		else if(i == KeyEvent.VK_S) keyState[S] = b;
		else if(i == KeyEvent.VK_D) keyState[D] = b;
		else if(i == KeyEvent.VK_ENTER) keyState[ENTER] = b;
		else if(i == KeyEvent.VK_SPACE) keyState[SPACE] = b;
		else if(i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = b;
	}
	
	//update state
	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}
	// get state
	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}
	// any key
	public static boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if(isPressed(i)) return true;
		}
		return false;
	}
}
