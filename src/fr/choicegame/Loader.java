package fr.choicegame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class Loader {
	
	HashMap<String,BufferedImage> resources = new HashMap<String,BufferedImage>();
		
	/**
	 * load all pictures resources contained in the res folder
	 * files contained in another folder under /res/ WON'T be loaded
	 *  
	 * @return a boolean indicating whether or not the import was successful.
	 */
	public boolean load(){
		
		File folder = new File("src/fr/choicegame/res");
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
			  //ajouter vérification type image
			  
			  try{
				  resources.put(listOfFiles[i].getName(), ImageIO.read(listOfFiles[i]));
				  System.out.println("File " + listOfFiles[i].getName() + " loaded");
				  
			  }catch(IOException e){
				  //return false;
			  }
		  }
	    }
		
		return true;
	}
	
	
	
	public HashMap<String,BufferedImage> getGameAssets(){
		return this.resources;
	}
	
	/**
	 * get the asset corresponding to name
	 * @param name the name of the resource
	 * @return the image if existing, null if not.
	 */
	public BufferedImage getGameAsset(String name){		
		return this.resources.get(name);
	}
	
}
