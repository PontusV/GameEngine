import java.io.Serializable;

/**
 * A component for a GameObject.
 * <p> Ex: Texture, Sound, Script, Animation
 */
public class Component implements Serializable {
	private static final long serialVersionUID = 2L;
	private GameObject gameObject;
	
	public void setGameObject(GameObject go) { gameObject = go; }
	public void tick(long timePassed){}
	public Resource<?> getResource() { return null; }
	protected Component(){} //Should not be created with new
	protected GameObject getParent() { return gameObject; }
}