import java.io.Serializable;

/**
 * A component for a GameObject.
 * <p> Ex: Texture, Sound, Script, Animation
 */
public class Component implements Serializable {
	private static final long serialVersionUID = 2L;
	private GameObject gameObject;
	
	protected Component(){} //Should not be created with new
	public void setGameObject(GameObject go) { gameObject = go; }
	public void tick(long timePassed){}
	protected GameObject getParent() { return gameObject; }
}