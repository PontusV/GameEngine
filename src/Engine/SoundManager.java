package Engine;
import java.util.ArrayList;


public class SoundManager {
	private static SoundManager thisInstance;
	public static SoundManager getInstance() {
		if (thisInstance == null)
			thisInstance = new SoundManager();
		return thisInstance;
	}
	
	//The maximum number of sounds that can be playing at the same time
	private final int MAX_SOUNDS_PLAYING = 3;
	
	//Contains the sounds that are currently playing
	private ArrayList<Sound> soundsPlaying;
	
	private ArrayList<Sound> sounds;
	
	private SoundManager() {
		sounds = new ArrayList<Sound>();
		soundsPlaying = new ArrayList<Sound>();
	}
	
	public void addSound(Sound sound){
		if(!sounds.contains(sound)){
			sounds.add(sound);
		}
	}

	
	public void playSound(String name){
		
		//The sounds that are not currently playing are removed from the list of sounds playing
		for(Sound s : sounds){
			if(!s.getResource().getData().isRunning()){
				soundsPlaying.remove(s);
			}
		}
		
		//Plays the sound if the maximum is not already met
		if(soundsPlaying.size() < MAX_SOUNDS_PLAYING){
		for(Sound s : sounds){
			if(s.getName() == name){
				soundsPlaying.add(s);
				s.play();
			}
		}
		}
	}
	
	public void loop(String name){
		for(Sound s : sounds){
			if(s.getName() == name){
				s.loop();
			}
		}
	}
	
	//Stops the sound and removes it from the list of sounds playing
	public void stopSound(String name){
		for(Sound s : sounds){
			if(s.getName() == name && s.getResource().getData().isRunning()){
				soundsPlaying.remove(s);
				s.stop();
			}
		}
	}
	
	public void stopAllSounds(){
		for(Sound s : sounds){
			if(s.getResource().getData().isRunning()){
				s.stop();	
			}
		}
	}
}
