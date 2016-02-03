import java.util.ArrayList;

public class GameEngine {
	
	private int fpsLock;
	boolean running;
	private ArrayList<GameObject> gameObjects;
	
	private static GameEngine thisInstance;
	public static GameEngine getInstance() {
		if (thisInstance == null)
			thisInstance = new GameEngine();
		return thisInstance;
	}
	
	private GameEngine() {
		//Constructor
		gameObjects = new ArrayList<GameObject>();
	}
	
	/**
	 * Initiates all subsystems and starts the Game Loop.
	 * <p>Initiates InputManager.
	 * Creates a window and starts the Game Loop.
	 * 
	 * @see Window graphic display.
	 */
	public void initiate() {
		Renderer ren = Renderer.getInstance(); //Starts drawing graphic components
		//InputManager input = InputManager.getInstance();

		running = true;
		long currentTime = System.currentTimeMillis(), timePassed;
		
		//Gameloop
		while (running) {
			//Calculate timePassed
			timePassed = System.currentTimeMillis()-currentTime;
			currentTime = 0;
			//Sleep thread if needed
			if (fpsLock > 0 && timePassed < fpsLock/1000) {
				try {
					Thread.sleep((fpsLock/1000)-timePassed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//Update InputManager
			InputManager.getInstance().update();
			//Update GameObjects
			for (GameObject go : gameObjects)
				go.tick(timePassed);
			//Updates screen with updated graphics
			ren.draw();
		}
	}
	
	public void setFps(int number) {
		if (number < 0)
			throw new IllegalArgumentException("Fps cannot be lower than 0!");
		fpsLock = number;
	}
	
	public int addGameObject(GameObject go) {
		gameObjects.add(go); //Add to list
		for (Component comp : go.getComponents()) { //Add to renderer
			if (comp instanceof ImageComponent)
				Renderer.getInstance().add((ImageComponent)comp);
		}
		return gameObjects.size()-1; //GameObject ID
	}
	
	public void removeGameObject(int goID) { //Remove GameObject by ID
		gameObjects.remove(goID);
	}
	
	public void removeGameObject(GameObject go) { //Remove GameObject by reference
		gameObjects.remove(go);
	}
	
	public ArrayList<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	public void addKeyEvent(GameObject object, EventKey event) {
	}
	
	public void terminate() {
		running = false;
		Window.getInstance().restoreWindow();
	}
}
