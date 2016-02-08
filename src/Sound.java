import javax.sound.sampled.Clip;

public class Sound extends Component {
	private static final long serialVersionUID = 3L;
	
	private Resource<Clip> audio;
	
	public Sound(Resource<Clip> clip) {
		audio = clip;
	}
	
	@Override
	public void tick(long timePassed) {
		// TODO Auto-generated method stub
		
	}
	
	public void play() {
		audio.getData().start();
	}
	
}
