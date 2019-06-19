package game.model.object;

import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {

	private static HashMap<String, Clip> clips;
	private static int gap;
	private static boolean mute = false;
	
	public static void init() {
		clips = new HashMap<String, Clip>();
		gap = 0;
	}
	
	public static void load(String s, String n) {
		if(clips.get(n) != null) return;
		Clip clip;
		try {			
			AudioInputStream ais =
				AudioSystem.getAudioInputStream(
					AudioPlayer.class.getResourceAsStream(s)
				);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			clips.put(n, clip);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void play(String s) {
		play(s, 0);
	}
	
	public static void play(String s, int i) {
		//jika audio sedang di-mute, batalkan play
		if(mute) return;
		
		// get audio clip return jika null 
		Clip c = clips.get(s);
		if(c == null) return;
		
		// jika audio sedang running, stop
		if(c.isRunning()) c.stop();
		
		// set posisi frame audio
		c.setFramePosition(i);
		
		// start audio sampai selesai atau sampai method stop() dipanggil
		while(!c.isRunning()) c.start();
	}
	
	
	// stop audio
	public static void stop(String s) {
		
		// stop audio jika sedang running
		if(clips.get(s) == null) return;
		if(clips.get(s).isRunning()) clips.get(s).stop();
	}
	
	
	public static void resume(String s) {
		if(mute) return;
		if(clips.get(s).isRunning()) return;
		clips.get(s).start();
	}
	
	// loop audio 
	public static void loop(String s, int start, int end) {
		loop(s, gap, start, end);
	}
	public static void loop(String s, int frame, int start, int end) {
		stop(s);
		if(mute) return;
		clips.get(s).setLoopPoints(start, end);
		clips.get(s).setFramePosition(frame);
		clips.get(s).loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static int getFrames(String s) { return clips.get(s).getFrameLength(); }
	

}
