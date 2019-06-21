package game.model.object;

import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


// memakai library external agar bisa meload mp3
public class AudioPlayer {

	// kumpulan -key, value- yang berisi audio dan nama audio tsb
	private static HashMap<String, Clip> clips;
	// jarak antara lagu
	private static int gap;
	// status mute
	private static boolean mute = false;
	
	// inisialisasi
	public static void init() {
		clips = new HashMap<String, Clip>();
		gap = 0;
	}
	
	// load audio
	public static void load(String s, String n) {
		// return jika audio sudah diload sebelumnya
		if(clips.get(n) != null) return;
		
		// load audio dari folder resource
		Clip clip;
		try {
			// get audio
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
			// Get a clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(dais);
			// masukkan clip ke hashmap
			clips.put(n, clip);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// play audio dengan frame position 0
	public static void play(String s) {
		play(s, 0);
	}
	
	// play audio dengan frame position tertentu 
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
	
	// resume audio, abaikan jika sedang mute
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
	
	// get panjang frame dari audio 
	public static int getFrames(String s) { return clips.get(s).getFrameLength(); }
	

}
