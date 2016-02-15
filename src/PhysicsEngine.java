import java.awt.image.BufferedImage;
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
	/**
	 * Checks if any of the pixels from the graphical representation of both gameobjects collide.
	 * <p> Retrieves all of their ImageComponents and calculates the area their colliding in.
	 * Then checks if any of the pixels within the designated area is in the same location.
	 * 
	 * @see GameObject 
	 * @see ImageComponent
	 * @param go
	 * @param other
	 * @return true if the gameObjects are colliding.
	 */
	public boolean isColliding(GameObject go, GameObject other) { //Pixel collision
		//Get current graphical representation of both objects
		int x,y,x2,y2;
		int boxLeft, boxUp, boxRight, boxDown;
		int xOffset, yOffset, alpha, alpha2;
		BufferedImage img,img2;
		for(ImageComponent image : go.getImageComponents()) {
			for (ImageComponent image2 : other.getImageComponents()) {
				x = go.getX() + image.getX();
				y = go.getY() + image.getY();
				x2 = other.getX() + image2.getX();
				y2 = other.getY() + image2.getY();
				
				//Offset = lowest y and x
				
				//Calculates colliding area
				if (x > x2) {
					boxLeft = x;
					xOffset = x2;
				} else {
					boxLeft = x2;
					xOffset = x;
				}
				if (y > y2) {
					boxUp = y;
					yOffset = y2;
				} else {
					boxUp = y2;
					yOffset = y;
				}
				if (x+image.getWidth() < x2+image2.getWidth()) boxRight = x+image.getWidth(); else boxRight = x2+image2.getWidth();
				if (y+image.getHeight() < y2+image2.getHeight()) boxDown = y+image.getHeight(); else boxDown = y2+image2.getHeight();
				
				if (boxRight-boxLeft < 0 || boxDown-boxUp < 0) //If box height or width is negative the imageComponents arent colliding
					return false;
				
				//Loop through all pixels in colliding area
				img = image.getResource().getData();
				img2 = image2.getResource().getData();
				for(int xLoc = boxLeft; xLoc < boxRight; xLoc++) {
					for (int yLoc = boxUp; yLoc < boxDown; yLoc++) { //Coordinates should not be out of bounds because only colliding area is looped
						alpha = (img.getRGB(xLoc - xOffset - (x - xOffset), yLoc - yOffset - (y - yOffset))>>24) & 0xff;
						alpha2 = (img2.getRGB(xLoc - xOffset - (x2 - xOffset), yLoc - yOffset - (y2 - yOffset))>>24) & 0xff;
						if (alpha != 0 && alpha2 != 0) //Not transparent : Collision detected
							return true;
					}
				}
			}
		}
		return false;
	}
	
}
