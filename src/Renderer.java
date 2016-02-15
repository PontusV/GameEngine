import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Renderer {
	private static Renderer thisInstance;
	public static Renderer getInstance() {
		if (thisInstance == null)
			thisInstance = new Renderer();
		return thisInstance;
	}
	
	private ArrayList<ImageComponent> images;
	
	private Renderer() {
		Window.getInstance(); //Creates window
		images = new ArrayList<ImageComponent>();
	}
	
	public void draw() {
		Graphics2D windowGraph = Window.getInstance().getGraphics(); //Retrieves window graphic
		
		windowGraph.setColor( new Color(255,255,255,255) );
		windowGraph.fillRect(0,0,Window.getInstance().getWidth(), Window.getInstance().getHeight());
		
		for (ImageComponent image : images) { //Retrieves game graphics and draws it on the window graphic
			windowGraph.drawImage(image.getImage(), image.getX(), image.getY(), null);
		}
		Window.getInstance().update();
	}
	
	public void add(ImageComponent image) {
		images.add(image);
	}
	
	public void clear() {
		images.clear();
	}
}
