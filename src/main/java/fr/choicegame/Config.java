package fr.choicegame;

import java.util.HashMap;

public class Config {

	private static Config instance;
	
	private HashMap<String, String> values;
	private HashMap<String, String> defaultValues;
	
	public static final String CONFIG_FILE = "config.cfg";
	
	public static final String TILE_SIZE = "tile_size";
	private static final int TILE_SIZE_DEFAULT = 32;
	
	public static final String ITEMS_TILESET = "items_tileset";
	private static final String ITEMS_TILESET_DEFAULT = null;
	
	public static final String PLAYER_TILESET = "player_tileset";
	private static final String PLAYER_TILESET_DEFAULT = null;
	
	public static final String START_MAP = "start_map";
	private static final String START_MAP_DEFAULT = null;
	
	private Config(){
		values = new HashMap<>();
		defaultValues = new HashMap<>();
		loadDefault();
	}
	
	private void loadDefault(){
		values.putAll(defaultValues);
		values.put(ITEMS_TILESET, ITEMS_TILESET_DEFAULT);
		values.put(PLAYER_TILESET, PLAYER_TILESET_DEFAULT);
		values.put(TILE_SIZE, ""+TILE_SIZE_DEFAULT);
		values.put(START_MAP, START_MAP_DEFAULT);
	}
	
	public static void loadValues(String configText){
		if(configText != null){
			for(String line:configText.split("\n")){
				if(line.contains(":")){
					String[] value = line.trim().split(":",2);
					String key = value[0].trim();
					if(!getInstance().values.containsKey(key)){
						System.out.println("Unknown config key : "+key);
					}
					getInstance().values.put(key, value[1].trim());
				}
			}
		}
	}
	
	public static Config getInstance(){
		if(instance == null)
			instance = new Config();
		return instance;
	}
	
	public static String getValue(String key){
		return getInstance().values.get(key);
	}
	
	public static int getIntValue(String key){
		String value = getValue(key);
		try{
			return Integer.parseInt(value);
		}catch(NumberFormatException e){
			return Integer.parseInt(getInstance().defaultValues.get(key));
		}
	}
	
	public static double getDoubleValue(String key){
		String value = getValue(key);
		try{
			return Double.parseDouble(value);
		}catch(NumberFormatException e){
			return Double.parseDouble(getInstance().defaultValues.get(key));
		}
	}
	
	
}
