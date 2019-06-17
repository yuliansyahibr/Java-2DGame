package game.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import game.controller.inputs.Keys;
import game.controller.inputs.Mouse;
import game.controller.state.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseInputListener{
	
	// dimensions
//	public static final int WIDTH = 320;
//	public static final int HEIGHT = 240;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int SCALE = 2;
	
	// game thread
	private Thread thread;
	private static boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// image
	private BufferedImage image;
	private Graphics2D g2;
	
	// game state manager
	private GameStateManager gsm;
	
	private int newScore;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			thread.start();
		}
	}

	private void init() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) image.getGraphics();

		running = true;

		gsm = new GameStateManager();

	}

	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		while (running) {
			
			if(!gsm.isRunning()) {
				newScore= game.model.object.Player.score;
				running=false;
			}
			
			start = System.nanoTime();

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			if (wait < 0) {
				wait = 5;
			}

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(running == false) {
			JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
			frame.setVisible(false);
			new ScoresWindow(newScore);
			frame.dispose();
		}
	}

	private void update() {
		gsm.update();
		Keys.update();
		Mouse.update();
	}

	private void draw() {
		gsm.draw(g2);
	}

	private void drawToScreen() {
		Graphics2D g2 = (Graphics2D)getGraphics();
		g2.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Mouse.set(e.getButton(), true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Mouse.set(e.getButton(), false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
