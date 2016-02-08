import java.io.Serializable;

/**
 * Represents a resource used by Components. Used for loading resources to components.
 * @see Component
 * @see Sound
 * @see ImageComponent
 */
public class Resource<T> implements Serializable {
	private static final long serialVersionUID = 99L;
	
	private String adress; //Saved for reloading
	private transient T data; //Shouldn't be saved
	
	public Resource(T data, String adress) {
		this.data = data;
		this.adress = adress;
	}
	
	public String getAdress() {
		return adress;
	}
	
	public T getData() {
		return data;
	}
	
	public void reload() {
		data = (T)Loader.getInstance().load(adress).getData();
	}
}
