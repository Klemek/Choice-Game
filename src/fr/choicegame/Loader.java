package fr.choicegame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Loader {
	
	public BufferedImage [] resources;
	public String [] resourcesNames;
	
	
	
	/**
	 * load all pictures resources contained in the res folder
	 * files contained in another folder under /res/ WON'T be loaded
	 *  
	 * @return a boolean indicating whether or not the import was successful.
	 */
	public boolean load(){
		
		File folder = new File("src/fr/choicegame/res");
		File[] listOfFiles = folder.listFiles();
		
		resourcesNames = new String [listOfFiles.length];
		resources = new BufferedImage [listOfFiles.length];
		
		int j=0;
		
		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
			  //ajouter vérification type image
			  
			  try{
				  resourcesNames[j] = listOfFiles[i].getName();
				  resources[j] = ImageIO.read(listOfFiles[i]);//on peut aussi passer par this.getClass().getResource, à voir comment le .jar se comportera 
				  System.out.println("File " + listOfFiles[i].getName() + " loaded");
				  j++;
			  }catch(IOException e){
				  //return false;
			  }
		  }
	    }
		
		return true;
	}
	
	
	
	public BufferedImage [] getGameAssets(){
		return this.resources;
	}
	
	/**
	 * get the asset corresponding to name
	 * @param name the name of the resource
	 * @return the image if existing, null if not.
	 */
	public BufferedImage getGameAsset(String name){
		BufferedImage swap = null;
		for (int i = 0; i < this.resources.length; i++){
			if (this.resourcesNames[i].equals(name)){
				swap = this.resources[i];
				break;
			}
		}
		
		return swap;
	}
	
}
