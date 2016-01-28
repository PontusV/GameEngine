import java.awt.Image;

public class ImageComponent extends Component {
	private static final long serialVersionUID = 4L;
	protected int x, y; //Offset from parent location
	private Image image;
	
	protected ImageComponent(int x, int y) { //image = null; Used by Animation
		this.x = x;
		this.y = y;
	}
	
	public ImageComponent(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void tick(long timePassed) {
		// TODO Auto-generated method stub
		
	}

	public Image getImage() {
		return image;
	}
	
	public int getX() { return getParent().getX()+x; }
	public int getY() { return getParent().getY()+y; }

}
