package game.controller.inputs;

import java.awt.event.MouseEvent;


// class untuk input dari mouse
public class Mouse {
	
	// jumlah event mouse yang dipakai
	public static final int N = 2;
	
	// state dari mouse
	public static boolean mouseState[] = new boolean[N];
	public static boolean prevMouseState[] = new boolean[N];
	
	// button mouse
	public static int LEFT_CLICK = 0;
	public static int RIGHT_CLICK = 1;
	
	// set state
	public static void set(int i, boolean b) {
		if(i == MouseEvent.BUTTON1) mouseState[LEFT_CLICK] = b;
		else if(i == MouseEvent.BUTTON3) mouseState[RIGHT_CLICK] = b;
	}
	// update state
	public static void update() {
		for (int i = 0; i < N; i++) {
			prevMouseState[i] = mouseState[i];
		}
	}
	
	// get status input
	public static boolean isClicked(int i) {
		return mouseState[i] && !prevMouseState[i];
	}
	
}
