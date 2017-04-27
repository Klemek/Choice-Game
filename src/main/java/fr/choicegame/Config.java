package fr.choicegame;

import java.util.HashMap;

public class Config {

	private static Config instance;
	
	private HashMap<String, String> values;
	private HashMap<String, String> defaultValues;
	
	public static final String CONFIG_FILE = "/config.cfg";
	
	//Resources
	
	public static final String TILE_SIZE = "tile_size";
	private static final int TILE_SIZE_DEFAULT = 32;
	
	public static final String ITEMS_TILESET = "items_tileset";
	private static final String ITEMS_TILESET_DEFAULT = null;
	
	public static final String PLAYER_TILESET = "player_tileset";
	private static final String PLAYER_TILESET_DEFAULT = null;
	
	public static final String START_MAP = "start_map";
	private static final String START_MAP_DEFAULT = null;
	
	//Player
	
	public static final String HITBOX_START_X = "hitbox_start_x";
	private static final float HITBOX_START_X_DEFAULT = 0.25f;
	
	public static final String HITBOX_START_Y = "hitbox_start_y";
	private static final float HITBOX_START_Y_DEFAULT = 0.7f;
	
	public static final String HITBOX_SIZE_X = "hitbox_size_x";
	private static final float HITBOX_SIZE_X_DEFAULT = 0.5f;
	
	public static final String HITBOX_SIZE_Y = "hitbox_size_y";
	private static final float HITBOX_SIZE_Y_DEFAULT = 0.3f;
	
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
		values.put(HITBOX_START_X, ""+HITBOX_START_X_DEFAULT);
		values.put(HITBOX_START_Y, ""+HITBOX_START_Y_DEFAULT);
		values.put(HITBOX_SIZE_X, ""+HITBOX_SIZE_X_DEFAULT);
		values.put(HITBOX_SIZE_Y, ""+HITBOX_SIZE_Y_DEFAULT);
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
		}else{
			System.out.println("No config file found");
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
	
	public static float getFloatValue(String key){
		String value = getValue(key);
		try{
			return Float.parseFloat(value);
		}catch(NumberFormatException e){
			return Float.parseFloat(getInstance().defaultValues.get(key));
		}
	}
	
	
}
