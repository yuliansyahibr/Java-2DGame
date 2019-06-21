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
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;

import game.controller.inputs.Keys;
import game.controller.inputs.Mouse;
import game.controller.state.GameStateManager;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseInputListener{
	
	// size dari game panel
	// dikalikan dengan nilai scale
	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int SCALE = 2;
	
	// game thread
	private Thread thread;
	private static boolean running;
	// frame per second game
	private int FPS = 60;
	// konversi fps ke dalam satuan milisecond
	private long targetTime = 1000 / FPS;
	
	// image dan graphic
	private BufferedImage image;
	private Graphics2D g2;
	
	// game state manager
	private GameStateManager gsm;
	
	// skor terbaru user
	private int newScore;

	public GamePanel() {
		super();
		//set size panel
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void addNotify() {
		// inisialisasi thread dan listener
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			thread.start();
		}
	}

	private void init() {
		// inisialisasi graphic		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) image.getGraphics();

		//set running to true
		running = true;

		//inisialisasi gamestatemanager
		gsm = new GameStateManager();

	}

	public void run() {
		
		// run thread
		init();
		long start;
		long elapsed;
		long wait;
		while (running) {
			// jika user keluar game
			// dapatkan skor terbaru
			// set running ke false agar loop berhenti
			if(!gsm.isRunning()) {
				newScore= game.model.object.Player.getScore();
				running=false;
			}
			
			// dapatkan waktu dalam satuan nanoseconds
			// untuk menghitung elapsed time
			start = System.nanoTime();
			// update game
			update();
			// gambar
			draw();
			// tampilkan hasil gambar di panel
			drawToScreen();

			// hitung waktu yang dibutuhkan untuk memroses 3 fungsi di atas
			elapsed = System.nanoTime() - start;

			// hitung waktu yang diperlukan untuk sleep
			wait = targetTime - elapsed / 1000000;
			
			// jika waktu tunggu negatif
			if (wait < 0) {
				wait = 5;
			}
			// make thread sleep
			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// buka kembali window skor, lalu dispose window game 
		if(running == false) {
			JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
			frame.setVisible(false);
			new ScoresWindow();
			frame.dispose();
		}
	}

	private void update() {
		// update game
		gsm.update();
		
		// update inputs
		Keys.update();
		Mouse.update();
	}

	private void draw() {
		// gambar dengan grapahic yang didapatkan dari buffered image
		gsm.draw(g2);
	}

	private void drawToScreen() {
		// dapatkan graphic dari panel, lalu gambar
		Graphics2D g2 = (Graphics2D)getGraphics();
		g2.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	// event keyboard ditekan
	// set status di class Keys ke true
	@Override
	public void keyPressed(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), true);
	
	}	
	// set status di class Keys ke false
	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keySet(e.getKeyCode(), false);
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	// event mouse
	// set status di class Mouse ke true
	@Override
	public void mousePressed(MouseEvent e) {
		Mouse.set(e.getButton(), true);
	}
	// set status di class Mouse ke false
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
