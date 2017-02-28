package fr.choicegame;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Loader {

	private static final ArrayList<String> IMAGE_EXT = new ArrayList<>(
			Arrays.asList(new String[] { "png", "jpg", "gif" }));
	private static final ArrayList<String> TEXT_EXT = new ArrayList<>(Arrays.asList(new String[] { "txt", "xml" }));

	private HashMap<String, BufferedImage> imagesResources = new HashMap<String, BufferedImage>();
	private HashMap<String, String> textResources = new HashMap<String, String>();

	/**
	 * load all pictures resources contained in the res folder files contained
	 * in another folder under /res/ WON'T be loaded
	 * 
	 * @return a boolean indicating whether or not the import was successful.
	 */
	public boolean load() {

		File folder = new File("src/fr/choicegame/res");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String ext = getExtension(listOfFiles[i].getName());
				if (IMAGE_EXT.contains(ext)) {
					BufferedImage image =  loadImageAsset(listOfFiles[i].getPath());
					if(image != null){
						imagesResources.put(listOfFiles[i].getName(), image);
						System.out.println("Image File '" + listOfFiles[i].getName() + "' loaded");
					}
				} else if (TEXT_EXT.contains(ext)) {
					String textFile =  loadTextAsset(listOfFiles[i].getPath());
					if(textFile != null){
						textResources.put(listOfFiles[i].getName(), textFile);
						System.out.println("Text File " + listOfFiles[i].getName() + " loaded");
					}
				}
			}
		}

		return true;
	}

	public static BufferedImage loadImageAsset(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture de '"+path+"': " + e.getMessage());
			return null;
		}
	}

	public static String loadTextAsset(String pathToSource) {
		File f = new File(pathToSource);
		BufferedReader br = null;
		String content = null;
		try {
			br = new BufferedReader(new FileReader(f));
			content = "";

			String line = br.readLine();
			while (line != null) {
				content = content + line + "\n";
				line = br.readLine();
			}
		} catch (FileNotFoundException exception) {
			System.out.println("Le fichier n'a pas été trouvé");
			return null;
		} catch (IOException exception) {
			System.out.println("Erreur lors de la lecture de '"+pathToSource+"': " + exception.getMessage());
			return null;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
			}
		}
		return content;
	}

	public HashMap<String, String> getGameFiles() {
		return this.textResources;
	}

	public String getGameFile(String name) {
		return this.textResources.get(name);
	}

	public HashMap<String, BufferedImage> getGameAssets() {
		return this.imagesResources;
	}

	/**
	 * get the asset corresponding to name
	 * 
	 * @param name
	 *            the name of the resource
	 * @return the image if existing, null if not.
	 */
	public BufferedImage getGameAsset(String name) {
		return this.imagesResources.get(name);
	}

	public static String getExtension(String filePath) {
		return filePath.substring(filePath.lastIndexOf(".") + 1);
	}
}
