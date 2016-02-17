package Engine;
import java.awt.image.BufferedImage;


public class PhysicsEngine {

	private static PhysicsEngine thisInstance;
	public static PhysicsEngine getInstance() {
		if (thisInstance == null)
			thisInstance = new PhysicsEngine();
		return thisInstance;
	}

	public static final int GRAVITATIONAL_CONSTANT = 500;
	public static final int TERMINAL_VELOCITY = 5000;

	/**
	 * Calculates movement and collision for all GameObjects.
	 * 
	 * @see GameEngine
	 * @see GameObject
	 */
	public void update() {
		//Collision
		GameObject[] objects = new GameObject[GameEngine.getInstance().getGameObjects().size()];
		for (int i=0;i<objects.length;i++)
			objects[i] = GameEngine.getInstance().getGameObjects().get(i);
		GameObject object,other;
		for (int i=0;i<objects.length-1;i++) { //Loops through all GameObjects
			for (int ii=i+1;ii<objects.length;ii++) {
				//Check for collision with objects further back in the list
				object = objects[i];
				other = objects[ii];
				if (isColliding(object, other)) {
					object.collision(other);
				}
			}
		}
	}
	
	/**
	 * Boxcollision.
	 * @param go
	 * @param other
	 * @return
	 */
	public boolean isBoxColliding(GameObject go, GameObject other) { //Pixel collision
		//Get current graphical representation of both objects
		int x,y,x2,y2;
		int boxLeft, boxUp, boxRight, boxDown;
		for(ImageComponent image : go.getImageComponents()) {
			for (ImageComponent image2 : other.getImageComponents()) {
				x = image.getX();
				y = image.getY();
				x2 = image2.getX();
				y2 = image2.getY();
				if (x > x2) {
					boxLeft = x;
				} else {
					boxLeft = x2;
				}
				if (y > y2) {
					boxUp = y;
				} else {
					boxUp = y2;
				}
				if (x+image.getWidth() < x2+image2.getWidth()) boxRight = x+image.getWidth(); else boxRight = x2+image2.getWidth();
				if (y+image.getHeight() < y2+image2.getHeight()) boxDown = y+image.getHeight(); else boxDown = y2+image2.getHeight();

				if (boxRight-boxLeft < 0 || boxDown-boxUp < 0) //If box height or width is negative the imageComponents arent colliding
					return false;
				else
					return true;
			}
		}
		return false;
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
		int xOffset, yOffset;
		BufferedImage img,img2;
		for(ImageComponent image : go.getImageComponents()) {
			for (ImageComponent image2 : other.getImageComponents()) {
				x = image.getX();
				y = image.getY();
				x2 = image2.getX();
				y2 = image2.getY();
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
				int pixelX,pixelY,pixelX2, pixelY2;
				for(int xLoc = boxLeft; xLoc < boxRight; xLoc++) {
					for (int yLoc = boxUp; yLoc < boxDown; yLoc++) { //Coordinates should not be out of bounds because only colliding area is looped
						pixelX = xLoc - xOffset - (x - xOffset);
						pixelY = yLoc - yOffset - (y - yOffset);
						pixelX2 = xLoc - xOffset - (x2 - xOffset);
						pixelY2 = yLoc - yOffset - (y2 - yOffset);
						if (pixelCollision(img,img2,pixelX,pixelY,pixelX2,pixelY2)) { //Not transparent : Collision detected
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Retrieves alpha from pixels at the given location from the given images.
	 * <p>If both pixels have alpha over 0 there is a collision.
	 * 
	 * @param img
	 * @param img2
	 * @param pixelX
	 * @param pixelY
	 * @param pixelX2
	 * @param pixelY2
	 * @return
	 */
	private boolean pixelCollision(BufferedImage img, BufferedImage img2, int pixelX, int pixelY, int pixelX2, int pixelY2) {
		return ((img.getRGB(pixelX, pixelY)>>24) & 0xff) != 0 && ((img2.getRGB(pixelX2, pixelY2)>>24) & 0xff) != 0;
	}

}
