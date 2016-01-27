import java.io.Serializable;

/**
 * A component for a GameObject.
 * <p> Ex: Texture, Sound, Script, Animation
 */
public abstract class Component implements Serializable {
	private static final long serialVersionUID = 2L;
	
	public abstract void tick(long timePassed);
}