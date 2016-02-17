package Engine;
import javax.sound.sampled.Clip;

public class Sound extends Component {
	private static final long serialVersionUID = 3L;
	
	private Resource<Clip> audio;
	private String name;
	
	public Sound(Resource<Clip> clip, String name) {
		audio = clip;
		this.name = name;
	}
	
	@Override
	public void tick(long timePassed) {
		// TODO Auto-generated method stub
		
	}
	
	public void play() {
		audio.getData().start();
	}
	
	public void stop() {
		audio.getData().stop();
	}
	
	public void loop(){
		audio.getData().loop(Clip.LOOP_CONTINUOUSLY);
	}

	public Resource<Clip> getResource() {
		return audio;
	}
	
	public String getName(){
		return name;
	}
}
