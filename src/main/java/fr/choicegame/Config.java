package fr.choicegame;

import java.util.HashMap;

public class Config {

	private static Config instance;
	
	private HashMap<String, String> values;
	private HashMap<String, String> defaultValues;
	
	public static final String CONFIG_FILE = "/config.cfg";
	
	//Resources
	
	public static final String TILESETS_FOLDER = "tilesets_folder";
	private static final String TILESETS_FOLDER_DEFAULT = "tilesets";
	
	public static final String MAPS_FOLDER = "maps_folder";
	private static final String MAPS_FOLDER_DEFAULT = "maps";
	
	public static final String TILE_SIZE = "tile_size";
	private static final int TILE_SIZE_DEFAULT = 32;
	
	public static final String ITEMS_TILESET = "items_tileset";
	private static final String ITEMS_TILESET_DEFAULT = null;
	
	public static final String PLAYER_TILESET = "player_tileset";
	private static final String PLAYER_TILESET_DEFAULT = null;
	
	public static final String START_MAP = "start_map";
	private static final String START_MAP_DEFAULT = null;
	
	public static final String FONT_FILE = "font_file";
	private static final String FONT_FILE_DEFAULT = null;
	
	public static final String FONT_NAME = "font_name";
	private static final String FONT_NAME_DEFAULT = "Consolas";
	
	//Character
	
	public static final String CHARACTER_SPEED = "character_speed";
	private static final float CHARACTER_SPEED_DEFAULT = 0.1f;
	
	public static final String HITBOX_START_X = "hitbox_start_x";
	private static final float HITBOX_START_X_DEFAULT = 0.25f;
	
	public static final String HITBOX_START_Y = "hitbox_start_y";
	private static final float HITBOX_START_Y_DEFAULT = 0.7f;
	
	public static final String HITBOX_SIZE_X = "hitbox_size_x";
	private static final float HITBOX_SIZE_X_DEFAULT = 0.5f;
	
	public static final String HITBOX_SIZE_Y = "hitbox_size_y";
	private static final float HITBOX_SIZE_Y_DEFAULT = 0.3f;
	
	//Maps
	
	public static final String BACKGROUND_1_LAYER = "background_1_layer";
	private static final String BACKGROUND_1_LAYER_DEFAULT = "bg1";
	
	public static final String BACKGROUND_2_LAYER = "background_2_layer";
	private static final String BACKGROUND_2_LAYER_DEFAULT = "bg2";
	
	public static final String FOREGROUND_1_LAYER = "foreground_1_layer";
	private static final String FOREGROUND_1_LAYER_DEFAULT = "fg1";
	
	public static final String FOREGROUND_2_LAYER = "foreground_2_layer";
	private static final String FOREGROUND_2_LAYER_DEFAULT = "fg2";
	
	public static final String INFO_LAYER = "info_layer";
	private static final String INFO_LAYER_DEFAULT = "info";
	
	public static final String TYPE_LAYER = "type_layer";
	private static final String TYPE_LAYER_DEFAULT = "type";
	
	public static final String TYPE_TILESET = "type_tileset";
	private static final String TYPE_TILESET_DEFAULT = "type";
	
	public static final String TYPE_PROPERTY = "type_property";
	private static final String TYPE_PROPERTY_DEFAULT = "type";
	
	public static final String INFO_TILESET = "info_tileset";
	private static final String INFO_TILESET_DEFAULT = "info";
	
	public static final String EVENT_PROPERTY = "event_property";
	private static final String EVENT_PROPERTY_DEFAULT = "event";
	
	public static final String NPC_PROPERTY = "npc_property";
	private static final String NPC_PROPERTY_DEFAULT = "npc";
	
	private Config(){
		values = new HashMap<>();
		defaultValues = new HashMap<>();
		loadDefaultValues();
	}
	
	private void loadDefaultValues(){
		defaultValues.putAll(defaultValues);
		defaultValues.put(TILESETS_FOLDER, TILESETS_FOLDER_DEFAULT);
		defaultValues.put(MAPS_FOLDER, MAPS_FOLDER_DEFAULT);
		defaultValues.put(ITEMS_TILESET, ITEMS_TILESET_DEFAULT);
		defaultValues.put(PLAYER_TILESET, PLAYER_TILESET_DEFAULT);
		defaultValues.put(TILE_SIZE, ""+TILE_SIZE_DEFAULT);
		defaultValues.put(START_MAP, START_MAP_DEFAULT);
		defaultValues.put(FONT_FILE, FONT_FILE_DEFAULT);
		defaultValues.put(FONT_NAME, FONT_NAME_DEFAULT);
		defaultValues.put(CHARACTER_SPEED, ""+CHARACTER_SPEED_DEFAULT);
		defaultValues.put(HITBOX_START_X, ""+HITBOX_START_X_DEFAULT);
		defaultValues.put(HITBOX_START_Y, ""+HITBOX_START_Y_DEFAULT);
		defaultValues.put(HITBOX_SIZE_X, ""+HITBOX_SIZE_X_DEFAULT);
		defaultValues.put(HITBOX_SIZE_Y, ""+HITBOX_SIZE_Y_DEFAULT);
		defaultValues.put(BACKGROUND_1_LAYER, BACKGROUND_1_LAYER_DEFAULT);
		defaultValues.put(BACKGROUND_2_LAYER, BACKGROUND_2_LAYER_DEFAULT);
		defaultValues.put(FOREGROUND_1_LAYER, FOREGROUND_1_LAYER_DEFAULT);
		defaultValues.put(FOREGROUND_2_LAYER, FOREGROUND_2_LAYER_DEFAULT);
		defaultValues.put(INFO_LAYER, INFO_LAYER_DEFAULT);
		defaultValues.put(TYPE_LAYER, TYPE_LAYER_DEFAULT);
		defaultValues.put(TYPE_TILESET, TYPE_TILESET_DEFAULT);
		defaultValues.put(TYPE_PROPERTY, TYPE_PROPERTY_DEFAULT);
		defaultValues.put(INFO_TILESET, INFO_TILESET_DEFAULT);
		defaultValues.put(EVENT_PROPERTY, EVENT_PROPERTY_DEFAULT);
		defaultValues.put(NPC_PROPERTY, NPC_PROPERTY_DEFAULT);
		values.putAll(defaultValues);
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
