import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private GraphicsDevice vc;
	//DisplayModes
	private static final DisplayMode displayModes[] = {
		new DisplayMode(1920,1080,32,0),
		new DisplayMode(1920,1080,24,0),
		new DisplayMode(1920,1080,16,0),
		new DisplayMode(1680,1050,32,0),
		new DisplayMode(1680,1050,24,0),
		new DisplayMode(1680,1050,16,0),
		new DisplayMode(1280,720,32,0),
		new DisplayMode(1280,720,24,0),
		new DisplayMode(1280,720,16,0),
		new DisplayMode(800,600,32,0),
		new DisplayMode(800,600,24,0),
		new DisplayMode(800,600,16,0),
		new DisplayMode(640,480,32,0),
		new DisplayMode(640,480,24,0),
		new DisplayMode(640,480,16,0)
	};
	
	private static Window thisInstance;
	public static Window getInstance() {
		if (thisInstance == null)
			thisInstance = new Window();
		return thisInstance;
	}

	//Currently only goes Fullscreen
	private Window() { //Constructor
		super("GameEngine");
		GraphicsEnvironment evn = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = evn.getDefaultScreenDevice();

		//JFrame settings
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.GRAY);
		setForeground(Color.WHITE);
		setFont(new Font("Arial", Font.PLAIN, 24));
		setVisible(false);
		setFullScreen( findFirstCompatibleDisplayMode(displayModes) ); //Set view mode
		addKeyListener(InputManager.getInstance());
		addMouseListener(InputManager.getInstance());
		addMouseMotionListener(InputManager.getInstance());
	}

	//DisplayMode
	public DisplayMode[] getCompatibleDisplayModes() {
		return vc.getDisplayModes();
	}

	public DisplayMode findFirstCompatibleDisplayMode(DisplayMode[] modes) {
		DisplayMode goodModes[] = getCompatibleDisplayModes();
		for (DisplayMode mode : modes) {
			for (DisplayMode goodMode : goodModes) {
				if (displayModesMatch(mode, goodMode)) {
					return mode;
				}
			}
		}
		return null; //Didn't find a compatible DisplayMode
	}

	public boolean displayModesMatch(DisplayMode mode, DisplayMode goodMode) {
		//Resolution
		if (mode.getWidth() != goodMode.getWidth() || mode.getHeight() != goodMode.getHeight())
			return false;

		//BitDepth
		if (mode.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && goodMode.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && mode.getBitDepth() != goodMode.getBitDepth())
			return false;

		//Refresh rate
		if (mode.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && goodMode.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && mode.getRefreshRate() != goodMode.getRefreshRate())
			return false;

		return true;
	}

	public void setFullScreen(DisplayMode dm) {
		if (dm != null && vc.isFullScreenSupported()) { //Checking if fullscreen and display change is supported
			//Attempting to set this JFrame to Fullscreen
			try {
				//Alter JFrame settings appropriate for Fullscreen
				//setUndecorated(true);
				setResizable(false);
				setIgnoreRepaint(true);
				//vc.setFullScreenWindow(this);
				setSize(1000,1000);
				if (vc.isDisplayChangeSupported()) {
					try {
						vc.setDisplayMode(dm);
					} catch(Exception e){}
				}
				setVisible(true);
			} catch(Exception e) {
				System.out.println("Failed to set Fullscreen: " + e.getMessage());
				restoreWindow();
			}
			createBufferStrategy(2);
		}
	}

	public void restoreWindow() {
		dispose();
		vc.setFullScreenWindow(null);
	}
	
	//Graphics
	public Graphics2D getGraphics() {
		return (Graphics2D)getBufferStrategy().getDrawGraphics();
	}

	public void update() {
		BufferStrategy s = getBufferStrategy();
		if (!s.contentsLost())
			s.show();
	}

	public BufferedImage createCompatibleImage(int w, int h, int t) {
		return getGraphicsConfiguration().createCompatibleImage(w, h, t);
	}

	public VolatileImage createCompatibleVolatileImage(int w, int h, int t) {
		return getGraphicsConfiguration().createCompatibleVolatileImage(w, h, t);
	}
}
