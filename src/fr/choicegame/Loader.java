package fr.choicegame;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class Loader {
	
	HashMap<String,BufferedImage> imagesResources = new HashMap<String,BufferedImage>();
	HashMap<String,String> textResources = new HashMap<String,String>();
	
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
				  imagesResources.put(listOfFiles[i].getName(), ImageIO.read(listOfFiles[i]));
				  System.out.println("File " + listOfFiles[i].getName() + " loaded");
				  
			  }catch(IOException e){
				  //return false;
			  }
		  }
	    }
		
		return true;
	}
	
	public static BufferedImage loadImageAsset(String path){
		try{
			return ImageIO.read(new File(path));
		}catch(IOException e){return null;}
	}
	
	public static String loadTextAsset(String pathToSource){
		try
		  {
		      File f = new File (pathToSource);
		      FileReader fr = new FileReader (f);
		      BufferedReader br = new BufferedReader (fr);
		      String content = "";
		   
		      try
		      {
		          String line = br.readLine();  
		          while (line != null)
		          {
		        	  content = content + line + "\n";
		              line = br.readLine();
		          }
		          br.close();
		          fr.close();
		          return content;
		      }
		      catch (IOException exception)
		      {
		          System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		      }
		  }
		  catch (FileNotFoundException exception)
		  {
		      System.out.println ("Le fichier n'a pas été trouvé");
		  }
		return null;
	}
	
	public HashMap<String, String> getGameFiles(){
		return this.textResources;
	}
	
	public String getGameFile(String name){
		return this.textResources.get(name);
	}
	
	public HashMap<String,BufferedImage> getGameAssets(){
		return this.imagesResources;
	}
	
	/**
	 * get the asset corresponding to name
	 * @param name the name of the resource
	 * @return the image 
	 * if existing, null if not.
	 */
	public BufferedImage getGameAsset(String name){		
		return this.imagesResources.get(name);
	}
	
}
