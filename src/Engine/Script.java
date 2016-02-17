package Engine;

public class Script extends Component {
	private static final long serialVersionUID = 6L;

	@Override
	public void tick(long timePassed) {}
	
	/**
	 * Handles collisionEvent for its GameObject.
	 * @param event contains information of keyEvent.
	 */
	public void collisionEvent(GameObject other) {}
	
	/**
	 * Handles keyEvent for its GameObject.
	 * @param event contains information of keyEvent.
	 */
	public void keyEvent(EventKey event) {}

}
