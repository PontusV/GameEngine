import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation extends ImageComponent {
	private static final long serialVersionUID = 5L;
	
	private int index;
	private long currentDuration;
	private ArrayList<Frame> frames;
	
	public Animation(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void tick(long timePassed) {
		// TODO Auto-generated method stub
		if (currentDuration <= 0) {
			index++;
			if (index >= frames.size())
				index = 0;
			currentDuration = frames.get(index).duration;
			x = frames.get(index).x;
			y = frames.get(index).y;
		}
	}

	@Override
	public BufferedImage getImage() {
		if (!frames.isEmpty())
			return frames.get(index).img;
		return null;
	}
	
	public void addFrame(BufferedImage img, int x, int y, long duration) {
		if (frames.isEmpty()) {
			index = 0;
			currentDuration = duration;
			this.x = x;
			this.y = y;
		}
		frames.add(new Frame(img,x,y,duration));
	}
	
	private static class Frame {
		BufferedImage img;
		int x,y;
		long duration;
		
		public Frame(BufferedImage img, int x, int y, long duration) {
			this.img = img;
			this.x = x;
			this.y = y;
			this.duration = duration;
		}
	}

}
