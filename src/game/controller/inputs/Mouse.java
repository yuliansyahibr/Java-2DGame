package game.controller.inputs;

import java.awt.event.MouseEvent;

public class Mouse {
	
	public static final int N = 6;
	
	public static boolean mouseState[] = new boolean[N];
	public static boolean prevMouseState[] = new boolean[N];
	
	public static int LEFT_CLICK = 0;
	public static int RIGHT_CLICK = 1;
	public static int DOWN = 2;
	
	
	public static void set(int i, boolean b) {
		if(i == MouseEvent.BUTTON1) mouseState[LEFT_CLICK] = b;
		else if(i == MouseEvent.BUTTON3) mouseState[RIGHT_CLICK] = b;
		
	}
	
	public static void update() {
		for (int i = 0; i < N; i++) {
			prevMouseState[i] = mouseState[i];
		}
	}
	
	public static boolean isClicked(int i) {
//		System.out.print(mouseState[i]);
//		System.out.println(!prevMouseState[i]);
		return mouseState[i] && !prevMouseState[i];
	}
	
	public static boolean anyKeyPress() {
		for (int i = 0; i < N; i++) {
			if(mouseState[i]) return true;
		}
		return false;
	}
}
