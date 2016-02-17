package Engine;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads and saves data.
 */
public class Loader {
	
	private final String resourceFolder = "resources/";
	
	private Map<String, Resource<BufferedImage>> images = new HashMap<String, Resource<BufferedImage>>();
	private Map<String, Resource<Clip>> sounds = new HashMap<String, Resource<Clip>>();
	
	private static Loader thisInstance;
	public static Loader getInstance() {
		if (thisInstance == null)
			thisInstance = new Loader();
		return thisInstance;
	}
	
	private Loader(){
	}
	
	public Resource<?> load(String name) {
		try {
			String mime = Files.probeContentType(Paths.get(name));
			if (mime.contains("image/"))
				return getImage(name);
			else if (mime.contains("audio/"))
				return getSound(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Resource<BufferedImage> getImage(String name) { //png, x-png, jpeg, bmp, gif
		Resource<BufferedImage> resource = images.get(name);
        if(resource != null){
        	return resource;
        }
		try {
			String mime = Files.probeContentType(Paths.get(name));
	        boolean compatible = false;
	        for (String type : ImageIO.getReaderMIMETypes()) { //Loops through all compatible Image MIMEs
	        	if (mime.equals(type))
	        		compatible = true;
	        }
	        if (!compatible) { //If image isnt compatible
	        	System.out.println("Image is not compatible! " + name);
	        	return null;
	        }
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		//Image was found and is compatible
        try{
        	File sourceimage = new File(resourceFolder + name);
        	BufferedImage image = ImageIO.read(sourceimage);
        	resource = new Resource<BufferedImage>(image, name);
        	images.put(name, resource);
        	return resource;
        } catch(IOException e){
        	System.out.println("Could not load image " + name + ": " + e.getMessage());
        }
        return null;
    }
	
	public Resource<Clip> getSound(String name) { //AudioInputStream for Wav
		Resource<Clip> resource = sounds.get(name);
		if(resource != null){ //Is sound already loaded?
        	return resource;
        }
		File file = new File(resourceFolder + name);
		try {
			if (Files.probeContentType(file.toPath()).equals("audio/wav")) {
				AudioInputStream audioStream = null;
				try {
					audioStream = AudioSystem.getAudioInputStream(file);
					Clip clip = AudioSystem.getClip();
					clip.open(audioStream);
					resource = new Resource<Clip>(clip, name);
					sounds.put(name, resource); //Stores sound in hashmap
					return resource; //Successfully loaded sound
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Used to reload an existing GameObject. Reloads all components.
	 * 
	 * @param go GameObject to be loaded
	 * @see GameObject
	 * @see Component
	 */
	public void loadGameObject(GameObject go) {
		for (Component c : go.getComponents()) {
			if (c.getResource() != null) //Reloadable?
				c.getResource().reload();
		}
	}
}
