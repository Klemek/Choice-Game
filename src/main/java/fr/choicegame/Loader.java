package fr.choicegame;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import fr.choicegame.event.Event;
import fr.choicegame.lwjglengine.graph.Texture;

public class Loader {

	private static final ArrayList<String> IMAGE_EXT = new ArrayList<>(
			Arrays.asList(new String[] { "png", "jpg", "gif" }));
	private static final ArrayList<String> TEXT_EXT = new ArrayList<>(
			Arrays.asList(new String[] { "txt", "xml", "tmx", "cfg" }));

	private ArrayList<String> imageResources = new ArrayList<>();
	private HashMap<String, String> textResources = new HashMap<>();

	private String[] folders = { "/maps", "/tilesets" };

	private boolean ide;
	private File jarFile;

	/**
	 * load all pictures resources contained in the given folders, files contained
	 * in another folder WON'T be loaded
	 * 
	 * @return a boolean indicating whether or not the import was successful.
	 */
	public boolean load() {

		jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
		ide = !jarFile.isFile();

		if (ide) {
			System.out.println("Running with IDE");
		} else {
			System.out.println("Running with JAR file");
		}

		for (String folderPath : folders) {
			try {
				this.loadFolder(folderPath);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		this.loadFile(Config.CONFIG_FILE);
		
		return true;
	}

	private void loadFolder(String folderPath) throws IOException {
		//http://stackoverflow.com/questions/11012819/how-can-i-get-a-resource-folder-from-inside-my-jar-file
		if (!ide) { // Run with JAR file
			final JarFile jar = new JarFile(jarFile);
			//give all entries in jar
			final Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				String name = entries.nextElement().getName();
				//filter according to the path
				if (name.startsWith(folderPath.substring(1) + "/")) {
					loadFile("/" + name);
				}
			}
			jar.close();
		} else { // Run with IDE
			final URL url = Loader.class.getResource(folderPath);
			if (url != null) {
				try {
					final File apps = new File(url.toURI());
					for (File app : apps.listFiles()) {
						loadFile(folderPath + "/" + app.getName());
					}
				} catch (URISyntaxException ignored) {}
			}
		}
	}

	private void loadFile(String path) {
		String ext = getExtension(path);
		if (IMAGE_EXT.contains(ext)) {
			imageResources.add(path);
			System.out.println("Image File '" + path + "' detected");
		} else if (TEXT_EXT.contains(ext)) {
			String textFile = loadTextAsset(path);
			if (textFile != null) {
				textResources.put(path, textFile);
				System.out.println("Text File '" + path + "' loaded");
			}
		}
	}

	public HashMap<String, Texture> loadTextures(){
		//Make sure to call this function after LWJGL was initialized
		HashMap<String, Texture> textures = new HashMap<>();
		for(String path:imageResources){
			try {
				textures.put(path, new Texture(path));
				System.out.println("Image File '" + path + "' loaded");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error on reading '" + path + "': " + e.getMessage());
			}
		}
		return textures;
	}
	
	public HashMap<String, BufferedImage> loadGameAssets() {
		HashMap<String, BufferedImage> images = new HashMap<>();
		for(String path:imageResources){
			try {
				images.put(path, loadImageAsset(path));
				System.out.println("Image File '" + path + "' loaded");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error on reading '" + path + "': " + e.getMessage());
			}
		}
		return images;
	}
	
	public static BufferedImage loadImageAsset(String path) throws IOException {
		return ImageIO.read(Loader.class.getResource(path));
	}

	public static String loadTextAsset(String pathToSource) {
		BufferedReader br = null;
		String content = null;
		try {
			br = new BufferedReader(new InputStreamReader(Loader.class.getResourceAsStream(pathToSource), "utf-8"));
			content = "";

			String line = br.readLine();
			while (line != null) {
				content = content + line + "\n";
				line = br.readLine();
			}
		} catch (FileNotFoundException exception) {
			System.out.println("File '" + pathToSource + "' not found");
			return null;
		} catch (IOException exception) {
			System.out.println("Error on reading '" + pathToSource + "': " + exception.getMessage());
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
		if (!name.startsWith("/"))
			name = "/" + name;
		return this.textResources.get(name);
	}

	

	public Map loadMap(String mapName) {
		Map map = null;
		if (textResources != null && textResources.containsKey("/maps/" + mapName + ".tmx")) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringElementContentWhitespace(true);
			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				InputSource is = new InputSource(new StringReader(textResources.get("/maps/" + mapName + ".tmx")));
				Document svg = builder.parse(is);
				Element root = svg.getDocumentElement();
				if (root.getNodeName().equals("map")) {
					int width = Integer.parseInt(getAttribute(root, "width", "0"));
					int height = Integer.parseInt(getAttribute(root, "height", "0"));
					HashMap<String, Integer[][]> layers = new HashMap<>();
					HashMap<Integer, String> tilesets = new HashMap<>();
					HashMap<String, Integer> tilesetsAll = new HashMap<>();
					HashMap<Integer, Event> events = new HashMap<>();
					HashMap<Integer, String> npcs = new HashMap<>();
					HashMap<Integer, TileType> types = new HashMap<>();
					for (int i1 = 0; i1 < root.getChildNodes().getLength(); i1++) {
						Node child1 = root.getChildNodes().item(i1);
						switch (child1.getNodeName()) {
						case "layer":
							String layername = getAttribute(child1, "name", "unknown");
							Node data = getChildNode(child1, "data");
							if (data != null) {
								String[] slayer = data.getTextContent().replace("\n", "").trim().split(",");
								Integer[][] layer = new Integer[width][height];
								for (int x = 0; x < width; x++) {
									for (int y = 0; y < height; y++) {
										layer[x][y] = Integer.parseInt(slayer[x * width + y]);
									}
								}
								layers.put(layername, layer);
							}
							break;
						case "tileset":
							int firstid = Integer.parseInt(getAttribute(child1, "firstgid", "0"));
							int tilecount = Integer.parseInt(getAttribute(child1, "tilecount", "0"));
							String tilesetName = getAttribute(child1, "name", "unknown");
							switch (tilesetName) {
							case "info":
								for (int i2 = 0; i2 < child1.getChildNodes().getLength(); i2++) {
									Node child2 = child1.getChildNodes().item(i2);
									if (child2.getNodeName().equals("tile")) {
										int tileId = Integer.parseInt(getAttribute(child2, "id", "0"));
										HashMap<String, String> props = getTileProperties(child2);
										if (props.containsKey("event")) {

											events.put(firstid + tileId, new Event(props.get("event")));
										} else if (props.containsKey("npc")) {
											npcs.put(firstid + tileId, props.get("npc"));
										}
									}
								}
								break;
							case "type":
								for (int i2 = 0; i2 < child1.getChildNodes().getLength(); i2++) {
									Node child2 = child1.getChildNodes().item(i2);
									if (child2.getNodeName().equals("tile")) {
										int tileId = Integer.parseInt(getAttribute(child2, "id", "0"));
										HashMap<String, String> props = getTileProperties(child2);
										if (props.containsKey("type")) {
											switch (props.get("type")) {
											case "1":
												types.put(firstid + tileId, TileType.FLAT);
												break;
											case "2":
												types.put(firstid + tileId, TileType.SOLID);
												break;
											}
										}
									}
								}
								break;
							default:
								tilesetsAll.put(tilesetName, firstid);
								for (int j = firstid; j < firstid + tilecount; j++) {
									tilesets.put(j, tilesetName);
								}
								break;
							}
							break;
						}
					}

					if (layers.containsKey("bg1") && layers.containsKey("bg2") && layers.containsKey("fg1")
							&& layers.containsKey("fg2") && layers.containsKey("info") && layers.containsKey("type")) {
						map = new Map(width, height);
						for (int x = 0; x < width; x++) {
							for (int y = 0; y < height; y++) {
								TileImage[] images = new TileImage[4];
								TileType ttype = TileType.VOID;
								Event event = null;
								int bg1 = layers.get("bg1")[y][x];
								int bg2 = layers.get("bg2")[y][x];
								int fg1 = layers.get("fg1")[y][x];
								int fg2 = layers.get("fg2")[y][x];
								int info = layers.get("info")[y][x];
								int type = layers.get("type")[y][x];

								if (bg1 != 0 && tilesets.containsKey(bg1)) {
									String tileset = tilesets.get(bg1);
									images[0] = new TileImage(bg1 - tilesetsAll.get(tileset), tileset);
								}
								if (bg2 != 0 && tilesets.containsKey(bg2)) {
									String tileset = tilesets.get(bg2);
									images[1] = new TileImage(bg2 - tilesetsAll.get(tileset), tileset);
								}
								if (fg1 != 0 && tilesets.containsKey(fg1)) {
									String tileset = tilesets.get(fg1);
									images[2] = new TileImage(fg1 - tilesetsAll.get(tileset), tileset);
								}
								if (fg2 != 0 && tilesets.containsKey(fg2)) {
									String tileset = tilesets.get(fg2);
									images[3] = new TileImage(fg2 - tilesetsAll.get(tileset), tileset);
								}
								if (info != 0) {
									if (npcs.containsKey(info)) {
										// TODO create NPC
									}
									event = events.get(info);
								}
								if (type != 0 && types.containsKey(type))
									ttype = types.get(type);
								map.setTile(x, y, new Tile(ttype, images, event));
							}
						}
					} else {
						System.out.println("[LOADER]Invalid map:" + mapName);
					}

				}
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}

		}
		return map;
	}

	public static String getAttribute(Node n, String name, String defaultValue) {
		if (!hasAttribute(n, name))
			return defaultValue;
		else
			return n.getAttributes().getNamedItem(name).getTextContent();
	}

	public static boolean hasAttribute(Node n, String name) {
		return n.hasAttributes() && n.getAttributes().getNamedItem(name) != null;
	}

	public static Node getChildNode(Node n, String name) {
		for (int i = 0; i < n.getChildNodes().getLength(); i++) {
			Node child = n.getChildNodes().item(i);
			if (child.getNodeName().equals(name))
				return child;
		}
		return null;
	}

	public static HashMap<String, String> getTileProperties(Node tileNode) {
		HashMap<String, String> props = new HashMap<>();
		if (tileNode.getNodeName().equals("tile")) {
			Node properties = getChildNode(tileNode, "properties");
			if (properties != null) {
				for (int i = 0; i < properties.getChildNodes().getLength(); i++) {
					Node child = properties.getChildNodes().item(i);
					if (child.getNodeName().equals("property")) {
						String name = getAttribute(child, "name", "");
						String value = getAttribute(child, "value", null);
						if (value == null)
							value = child.getTextContent();
						if (value != null && !value.equals(""))
							props.put(name, value);
					}
				}
			}
		}
		return props;
	}

	/**
	 * get the asset corresponding to name
	 * 
	 * @param name
	 *            the name of the resource
	 * @return the image if existing, null if not.
	 */
	/*public BufferedImage getGameAsset(String name) {
		if (!name.startsWith("/"))
			name = "/" + name;
		return this.imagesResources.get(name);
	}*/

	public static String getExtension(String filePath) {
		return filePath.substring(filePath.lastIndexOf(".") + 1);
	}

}
