import java.util.ArrayList;


public class PhysicsEngine {

	private static PhysicsEngine thisInstance;
	public static PhysicsEngine getInstance() {
		if (thisInstance == null)
			thisInstance = new PhysicsEngine();
		return thisInstance;
	}
	
	private final int GRAVITATIONAL_CONSTANT = 10;
	private final int TERMINAL_VELOCITY = 200;
	
	public void resolveCollision(int x, int y, int velocity){
		
	}
	
	public void updateObjects(ArrayList<GameObject> objects, long lastTime){
		for(GameObject go : objects){
			if(go.isFalling()){
				updateFall(go, lastTime);
			}
		}
	}

	private void updateFall(GameObject go, long lastTime) {
		long currentTime = System.currentTimeMillis();
		
//		go.setVerticalSpeed(go.getVerticalSpeed() + GRAVITATIONAL_CONSTANT);
//        if (go.getVerticalSpeed() > TERMINAL_VELOCITY)
//        {
//            go.setVerticalSpeed(TERMINAL_VELOCITY);
//        }
//        go.setY((int) ((go.getY() + go.getVerticalSpeed()) * (currentTime - lastTime)));
		
	}
	
	
}
