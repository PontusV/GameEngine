
public class SoundEngine {
	private static SoundEngine thisInstance;
	public static SoundEngine getInstance() {
		if (thisInstance == null)
			thisInstance = new SoundEngine();
		return thisInstance;
	}
	
	private SoundEngine() {
	}
	
	//Event-loop
	public void update() {
		
	}
}
