import java.util.ArrayList;

public class GameEngine {
	
	private int fpsLock;
	private ArrayList<GameObject> gameObjects;
	
	private static GameEngine thisInstance;
	public static GameEngine getInstance() {
		if (thisInstance == null)
			thisInstance = new GameEngine();
		return thisInstance;
	}
	
	public GameEngine() {
		//Constructor
	}
	
	/**
	 * Initiates all subsystems and starts the Game Loop.
	 * <p>Initiates InputManager.
	 * Creates a window and starts the Game Loop.
	 * 
	 * @see Window graphic display.
	 */
	public void initiate() {
		Window.getInstance(); //Creates window
		//InputManager input = InputManager.getInstance();
		
		boolean running = true;
		long currentTime = System.currentTimeMillis(), timePassed;
		
		//Gameloop
		while (running) {
			timePassed = System.currentTimeMillis()-currentTime;
			currentTime = 0;
			if (fpsLock > 0 && timePassed < fpsLock/1000) {
				try {
					Thread.sleep((fpsLock/1000)-timePassed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setFps(int number) {
		if (number < 0)
			throw new IllegalArgumentException("Fps cannot be lower than 0!");
		fpsLock = number;
	}
	
	public int addGameObject(GameObject go) {
		gameObjects.add(go);
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
}
