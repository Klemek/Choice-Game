package fr.choicegame.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import fr.choicegame.Game;
import fr.choicegame.Hud;
import fr.choicegame.Tile;
import fr.choicegame.TileImage;
import fr.choicegame.character.Direction;

public class EventComputer implements GameEventListener {
	
	private Game game;
	
	private static final String VARIABLE = "VARIABLE";
	private static final String TRIGGER = "TRIGGER";
	private static final String TEXT = "TEXT";
	private static final String VALUE = "VALUE";
	private static final String INTVALUE = "INTVALUE";
	private static final String FLOATVALUE = "FLOATVALUE";
	private static final String ID = "ID";
	
	private String savedEvent;
	private int savedx, savedy, savedi;
	private boolean savedc;
	private HashMap<String, Integer> savedvars;
	
	public EventComputer(Game game) {
		this.game = game;
	}
	
	// Functions

	public void eventCalled(String event, int x, int y, boolean collide) {
		eventCalled(event, x, y,0,new HashMap<>(), collide);
	}
	
	public void eventCalled(String event, int x, int y, int start, HashMap<String, Integer> vars, boolean collide) {
		String actions[] = event.split("\n");
		System.out.println("Event in ("+x+","+y+") "+(start==0?"":"resume"));
		int jump = 0;
		for(int i = start; i < actions.length; i++){
			String action = actions[i];
			action = action.replace("\\n","\n").replace("\\r","\r");
			action = action.split("#")[0].trim(); //TODO escape # in text args
			if(action.length()>0){
				String spl1[] = action.split(" ",2);
				String cmd = spl1[0];
				if(jump == 0){
					System.out.println(">"+action);//TODO debug
					String args[] = new String[0];
					if(spl1.length>1)
						args = getArgs(spl1[1], true);
					switch(cmd){
					
					/* Logical */
					
					case "TRIGGER": //TRIGGER (TRIGNAME) [ON/OFF] #Create/edit trigger
						switch(args.length){
						case 1: //TRIGGER (TRIGNAME)
							game.setTrigger(args[0], true);
							break;
						case 2: //TRIGGER (TRIGNAME) [ON/OFF]
							game.setTrigger(args[0], args[1].equals("ON"));
							break;
						}
						break;
					case "IFT": //IFT [NOT] (TRIGNAME) ... [ELSE ...] END #Test trigger
						switch(args.length){
						case 1: //IFT TRIGNAME
							if(!game.getTrigger(args[0]))
								jump++;
							break;
						case 2: //IFT NOT TRIGNAME
							if(game.getTrigger(args[1]))
								jump++;
							break;
						}
						break;
					case "VAR": //VAR (VARNAME) (VALUE) #Create/edit local variable
						vars.put(args[0], Integer.parseInt(args[1]));
						break;
					case "VARG": //VARG (VARNAME) (VALUE) #Create/edit global variable
						game.setGlobalVariable(args[0], Integer.parseInt(args[1]));
						break;
					case "IF": //IF (VARNAME) (==/!=/>/</>=/<=) (VALUE) ... [ELSE ...] END #Test variable
						switch(args[1]){
						case "==": //IF (VARNAME) == (VALUE)
							if(vars.getOrDefault(args[0], game.getGlobalVariable(args[0]))!=Integer.parseInt(args[2]))
								jump++;
							break;
						case "!=": //IF (VARNAME) != (VALUE)
							if(vars.getOrDefault(args[0], game.getGlobalVariable(args[0]))==Integer.parseInt(args[2]))
								jump++;
							break;
						case ">": //IF (VARNAME) > (VALUE)
							if(vars.getOrDefault(args[0], game.getGlobalVariable(args[0]))<=Integer.parseInt(args[2]))
								jump++;
							break;
						case "<": //IF (VARNAME) < (VALUE)
							if(vars.getOrDefault(args[0], game.getGlobalVariable(args[0]))>=Integer.parseInt(args[2]))
								jump++;
							break;
						case ">=": //IF (VARNAME) >= (VALUE)
							if(vars.getOrDefault(args[0], game.getGlobalVariable(args[0]))<Integer.parseInt(args[2]))
								jump++;
							break;
						case "<=": //IF (VARNAME) <= (VALUE)
							if(vars.getOrDefault(args[0], game.getGlobalVariable(args[0]))>Integer.parseInt(args[2]))
								jump++;
							break;
						}
						break;
					case "IFC": //IFC [TRUE/FALSE] #Test event touched (true) or interacted (false)
						
						if(args.length == 0){
							if(!collide)
								jump++;
						}else{
							if((args[0].equals("TRUE") && !collide) || (args[0].equals("FALSE") && collide)){
								jump++;
							}
						}
						break;
					case "IFO": //IFO (WEST/SOUTH/NORTH/EAST) ... [ELSE ...] END #If player facing is ...
						switch(args[0]){
						case "WEST":
							if(this.game.getPlayer().getFacing() != Direction.WEST)
								jump++;
							break;
						case "SOUTH":
							if(this.game.getPlayer().getFacing() != Direction.SOUTH)
								jump++;
							break;
						case "NORTH":
							if(this.game.getPlayer().getFacing() != Direction.NORTH)
								jump++;
							break;
						case "EAST":
							if(this.game.getPlayer().getFacing() != Direction.EAST)
								jump++;
							break;
						}
						break;
					case "ELSE":
						jump++;
						break;
					case "ICZ": //ICZ (VARNAME) [VALUE] # Increase var from or value 
						switch(args.length){
						case 1: //ICZ (VARNAME)
							if(vars.containsKey(args[0]))
								vars.put(args[0], vars.get(args[0])+1);
							else
								game.setGlobalVariable(args[0], game.getGlobalVariable(args[0])+1);
							break;
						case 2: //ICZ (VARNAME) [VALUE]
							if(vars.containsKey(args[0]))
								vars.put(args[0], vars.get(args[0])+Integer.parseInt(args[1]));
							else
								game.setGlobalVariable(args[0], game.getGlobalVariable(args[0])+Integer.parseInt(args[1]));
							break;
						}
						break;
					case "DCZ": //DCZ (VARNAME) [VALUE] # Decrease var from or value 
						switch(args.length){
						case 1: //DCZ (VARNAME)
							if(vars.containsKey(args[0]))
								vars.put(args[0], vars.get(args[0])-1);
							else
								game.setGlobalVariable(args[0], game.getGlobalVariable(args[0])-1);
							break;
						case 2: //DCZ (VARNAME) [VALUE]
							if(vars.containsKey(args[0]))
								vars.put(args[0], vars.get(args[0])-Integer.parseInt(args[1]));
							else
								game.setGlobalVariable(args[0], game.getGlobalVariable(args[0])-Integer.parseInt(args[1]));
							break;
						}
						break;
					case "INVVAR": //INVVAR (ITEMID) (VARNAME) #Get the quantity of an item in the player inventory and put it in a var
						int itemid = Integer.parseInt(args[0]);
						if(game.getPlayer().getInventory().containsKey(itemid)){
							vars.put(args[1],game.getPlayer().getInventory().get(Integer.parseInt(args[0])));
						}
						break;
					case "RANDOM": //RANDOM (VARNAME) (MINVALUE) (MAXVALUE) #Generate integer random number between min and max and put it in a var
						int min = Integer.parseInt(args[1]);
						int max = Integer.parseInt(args[2]);
						int value = new Random().nextInt(max+1-min)+min;
						if(game.hasGlobalVariable(args[0])){
							game.setGlobalVariable(args[0], value);
						}else{
							vars.put(args[0], value);
						}
						break;
					
					/* Visual effects */	
						
					case "SAY": //SAY (TEXT) [IMAGEID] #Show text (pause game)
						switch(args.length){
						case 1: //SAY (TEXT)
							game.getHud().setMsg(getStringArg(args[0]));
							game.setWaitInput(true);
							pause(event,x,y,i,vars,collide);
							return;
						case 2://SAY (TEXT) (IMAGID)
							//TODO Image
							game.getHud().setMsg(getStringArg(args[0]));
							game.setWaitInput(true);
							pause(event,x,y,i,vars,collide);
							return;
						}
					case "DIALOG": //DIALOG (TEXT) (VARNAME) (OPTION1) (OPTION2) ... #Show a choice and return result on a viariable
						//TODO EVENT DIALOG
						String[] dial = new String[args.length-1];
						dial[0] = getStringArg(args[0]);
						for(int j = 1; j < dial.length; j++){
							dial[j] = getStringArg(args[j+1]);
						}
						game.getHud().setDialog(dial,args[1]);
						game.setWaitInput(true);
						pause(event,x,y,i,vars,collide);
						return;
					case "SHAKE": //SHAKE (INTENSITY) #Shake screen with intensity : (0 for stop)
						this.game.getRenderer().setShake(Float.parseFloat(args[0]));
						break;
					case "FILTER": //FILTER (R) (G) (B) (A) {TIME} / OFF {TIME} # Apply filter to game (values in range 0 to 1) default time 1 second
						switch(args.length){
						case 1: //FILTER OFF
							this.game.getHud().clearColorFilter(Hud.DEFAULT_FADE_TIME);
							break;
						case 2: //FILTER OFF (TIME)
							this.game.getHud().clearColorFilter(Float.parseFloat(args[1]));
							break;
						case 4: //FILTER (R) (G) (B) (A)
							this.game.getHud().setColorFilter(Float.parseFloat(args[0]),
															Float.parseFloat(args[1]),
															Float.parseFloat(args[2]),
															Float.parseFloat(args[3]),
															Hud.DEFAULT_FADE_TIME);
							break;
						case 5: //FILTER (R) (G) (B) (A) (TIME)
							this.game.getHud().setColorFilter(Float.parseFloat(args[0]),
															Float.parseFloat(args[1]),
															Float.parseFloat(args[2]),
															Float.parseFloat(args[3]),
															Float.parseFloat(args[4]));
							break;
						}
						break;
						
					/* Sound effects */
						
					case "SOUND": //SOUND (NAME) (R/P) / OFF #Play sound from name, use R to repeat, use OFF to remove all sounds
						switch(args.length){
						case 1: //SOUND OFF
							this.game.getSoundmanager().removeAllSoundSources();
							break;
						case 2://SOUND (NAME) (R/P)
							if(args[1].equals("R")){
								this.game.getSoundmanager().playSound(getStringArg(args[0]), true);
							}else{
								this.game.getSoundmanager().playSound(getStringArg(args[0]), false);
							}
							break;
						}
						break;
						
					/* Game interaction */
					
					case "INVADD": //INVADD (ITEMID) [NUM] [MSG] #Add 1 or NUM item(s) to the player and display it (optional)(pause)
						int itemcount1 = 0;
						int itemid1 = Integer.parseInt(args[0]);
						if(game.getPlayer().getInventory().containsKey(itemid1)){
							itemcount1 = game.getPlayer().getInventory().get(itemid1);
						}
						switch(args.length){
						case 1: //INVADD (ITEMID)
							game.getPlayer().getInventory().put(itemid1,itemcount1+1);
							break;
						case 2://INVADD (ITEMID) (NUM)
							game.getPlayer().getInventory().put(itemid1,itemcount1+Integer.parseInt(args[1]));
							break;
						case 3://INVADD (ITEMID) (NUM) (MSG)
							game.getPlayer().getInventory().put(itemid1,itemcount1+Integer.parseInt(args[1]));
							game.getHud().setMsg(getStringArg(args[2]));
							game.setWaitInput(true);
							pause(event,x,y,i,vars,collide);
							return;
						}
						break;
					case "INVDEL": //INVDEL (ITEMID) [NUM] [MSG] #Remove all or NUM item(s) to the player and display it (optional)(pause)
						int itemcount2 = 0;
						int itemid2 = Integer.parseInt(args[0]);
						if(game.getPlayer().getInventory().containsKey(itemid2)){
							itemcount2 = game.getPlayer().getInventory().get(itemid2);
						}
						switch(args.length){
						case 1: //INVDEL (ITEMID)
							game.getPlayer().getInventory().remove(itemid2);
							break;
						case 2://INVDEL (ITEMID) (NUM)
							game.getPlayer().getInventory().put(itemid2,Math.max(0,itemcount2-Integer.parseInt(args[1])));
							break;
						case 3://INVDEL (ITEMID) (NUM) (MSG)
							game.getPlayer().getInventory().put(itemid2,Math.max(0,itemcount2-Integer.parseInt(args[1])));
							game.getHud().setMsg(getStringArg(args[2]));
							game.setWaitInput(true);
							pause(event,x,y,i,vars,collide);
							return;
						}
						break;
					case "MAP": //MAP (X) (Y) (LAYER) [TILESET] [ID] #Edit one map's tile
						int x0 = Integer.parseInt(args[0]);
						int y0 = Integer.parseInt(args[1]);
						int layer = Integer.parseInt(args[2]);
						Tile t = game.getCurrentMap().getTile(x0,y0);
						TileImage[] ti = t.getImages();
						switch(args.length){
						case 3: //MAP (X) (Y) (LAYER)
							ti[layer] = null;
							t.setImages(ti);
							break;
						case 5: //MAP (X) (Y) (LAYER) [TILESET] [ID]
							String tileset = getStringArg(args[3]);
							int id = Integer.parseInt(args[4]);
							ti[layer] = new TileImage(id,tileset);
							t.setImages(ti);
							break;
						}
						break;
					case "MAPR": //MAPR (DX) (DY) (LAYER) [TILESET] [ID] #Edit one map's tile relatively to event's source
						int dx = Integer.parseInt(args[0]);
						int dy = Integer.parseInt(args[1]);
						int layer1 = Integer.parseInt(args[2]);
						Tile t1 = game.getCurrentMap().getTile(x+dx,y+dy);
						TileImage[] ti1 = t1.getImages();
						switch(args.length){
						case 3: //MAPR (DX) (DY) (LAYER)
							ti1[layer1] = null;
							t1.setImages(ti1);
							game.updateMap();
							break;
						case 5: //MAPR (DX) (DY) (LAYER) [TILESET] [ID]
							String tileset = getStringArg(args[3]);
							int id = Integer.parseInt(args[4]);
							ti1[layer1] = new TileImage(id,tileset);
							t1.setImages(ti1);
							game.updateMap();
							break;
						}
						break;
					case "PLAYERTILESET": //PLAYERTILESET (TILESET) #Change Player tileset
						game.getPlayer().setTileset(getStringArg(args[0]));
						break;
					case "NPCTILESET": //NPCTILESET (NPCID) (TILESET) #Change NPC tileset
						//TODO EVENT NPCTILESET
						game.getCurrentMap().getNpcs().get(getStringArg(args[0])).setTileset(getStringArg(args[1]));
						break;
					case "NPCANIMATION": //NPCTILESET (NPCID) (SOUTH/WEST/NORTH/EAST) (STOP/MARCH) #Change NPC animation
						//TODO EVENT NPCANIMATION
						break;
					case "CHGMAP": //CHGMAP (MAPNAME) [PLAYERX] [PLAYERY]
						switch(args.length){
						case 1: //CHGMAP (MAPNAME)
							game.setCurrentMap(getStringArg(args[0]));
							break;
						case 3://CHGMAP (MAPNAME) (PLAYERX) (PLAYERY)
							game.setCurrentMap(getStringArg(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
							break;
						}
						break;
					case "MVPLAYER": //MVPLAYER (PLAYERX) (PLAYERY) #Move player in current map
						game.getPlayer().setPosition(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
						break;
					case "MVRPLAYER": //MVRPLAYER (PLAYERDX) (PLAYERDY) #Move player relativelyin current map
						game.getPlayer().setPosition(Integer.parseInt(x+args[0]),Integer.parseInt(y+args[1]));
						break;
					case "STOP": // STOP {ON/OFF} #Prevent player from moving
						switch(args.length){
						case 0:
							this.game.setStopPlayer(true);
							break;
						case 1:
							this.game.setStopPlayer(args[1].equals("ON"));
							break;
						}
						break;
					case "WAIT": // WAIT (TIME) #Wait a amount of time before other event
						this.game.setWaitTime(Float.parseFloat(args[0]), false);
						pause(event,x,y,i,vars,collide);
						return;
					case "PAUSE": // PAUSE (TIME) #Pause game (wait + prevent player from moving)
						this.game.setWaitTime(Float.parseFloat(args[0]), true);
						pause(event,x,y,i,vars,collide);
						return;
					case "PLAYERFACE": // PLAYERFACE (WEST/SOUTH/NORTH/EAST) #Change player facing
						switch(args[0]){
						case "WEST":
							this.game.getPlayer().setFacing(Direction.WEST);
							break;
						case "SOUTH":
							this.game.getPlayer().setFacing(Direction.SOUTH);
							break;
						case "NORTH":
							this.game.getPlayer().setFacing(Direction.NORTH);
							break;
						case "EAST":
							this.game.getPlayer().setFacing(Direction.EAST);
							break;
						}
						break;
					}
				}else if((jump == 1 && cmd.equals("ELSE")) || cmd.equals("END")){
					jump--;
					System.out.println(">"+cmd);
				}else if(cmd.equals("IF") || cmd.equals("IFT")){
					jump++;
					System.out.println("#"+cmd);
				}else{
					System.out.println("#"+action);
				}
			}
		}
		this.savedEvent = null;
	}
	
	public static ArrayList<String> testEvent(String event) {
		
		String actions[] = event.split("\n");
		ArrayList<String> errors = new ArrayList<>();
		
		int jumplvl = 0;
		for(int i = 0; i < actions.length; i++){
			String action = actions[i].split("#")[0].trim();
			if(action.length()>0){
				String spl1[] = action.split(" ",2);
				String cmd = spl1[0];
				String args[] = new String[0];
				if(spl1.length>1)
					args = getArgs(spl1[1], true);
				switch(cmd){

				/* Logical */
				
				case "TRIGGER":  //TRIGGER (TRIGNAME) [ON/OFF] #Create/edit trigger
					testArgs(i,action,errors,args, new String[][]{{TRIGGER},{TRIGGER,"ON/OFF"}});
					break;
				case "IFT":  //IFT [NOT] (TRIGNAME) ... [ELSE ...] END #Test trigger
					jumplvl++;
					testArgs(i,action,errors,args, new String[][]{{TRIGGER},{"NOT", TRIGGER}});
					break;
				case "ELSE":
					if(jumplvl<0)
						System.err.println("No previous IF or IFT : ["+i+"]"+action);
					testArgs(i,action,errors,args, new String[][]{{}});
					break;
				case "END":
					if(jumplvl>0)
						jumplvl--;
					else
						System.err.println("No previous IF or IFT : ["+i+"]"+action);
					testArgs(i,action,errors,args, new String[][]{{}});
					break;
				case "VAR": //VAR (VARNAME) (VALUE) #Create/edit local variable
					testArgs(i,action,errors,args, new String[][]{{VARIABLE, VALUE}});
					break;
				case "VARG": //VARG (VARNAME) (VALUE) #Create/edit global variable
					testArgs(i,action,errors,args, new String[][]{{VARIABLE, VALUE}});
					break;
				case "IF": //IF (VARNAME) (==/!=/>/</>=/<=) (VALUE) ... [ELSE ...] END #Test variable
					jumplvl++;
					testArgs(i,action,errors,args, new String[][]{{VARIABLE,"==/!=/>/</>=/<=", VALUE}});
					break;
				case "IFC": //IFC [TRUE/FALSE] ... [ELSE ...] END #Test event touched (true) or interacted (false)
					jumplvl++;
					testArgs(i,action,errors,args, new String[][]{{},{"TRUE/FALSE"}});
					break;
				case "IFO": //IFO (WEST/SOUTH/NORTH/EAST) ... [ELSE ...] END #If player facing is ...
					jumplvl++;
					testArgs(i,action,errors,args, new String[][]{{},{"SOUTH/WEST/NORTH/EAST"}});
					break;
				case "ICZ": //ICZ (VARNAME) [VALUE] # Increase var from 1 or value 
					testArgs(i,action,errors,args, new String[][]{{VARIABLE},{VARIABLE, VALUE}});
					break;
				case "DCZ": //DCZ (VARNAME) [VALUE] # Decrease var from 1 or value 
					testArgs(i,action,errors,args, new String[][]{{VARIABLE},{VARIABLE, VALUE}});
					break;
				case "INVVAR": //INVVAR (ITEMID) (VARNAME) #Get the quantity of an item in the player inventory and put it in a var
					testArgs(i,action,errors,args, new String[][]{{ID,VARIABLE}});
					break;
				case "RANDOM": //RANDOM (VARNAME) (MINVALUE) (MAXVALUE) #Generate integer random number between min and max and put it in a var
					testArgs(i,action,errors,args, new String[][]{{VARIABLE,INTVALUE,INTVALUE}});
					break;
					
				/* Visual effects */	
					
				case "SAY": //SAY (TEXT) [IMAGEID] #Show text (pause game)
					testArgs(i,action,errors,args, new String[][]{{TEXT},{TEXT,ID}});
					break;
				case "DIALOG": //DIALOG (TEXT) (VARNAME) (OPTION1) (OPTION2) ... #Show a choice and return result on a viariable
					String[] argstype = {TEXT,VARIABLE,TEXT,TEXT};
					if(args.length>4){
						argstype = new String[args.length];
						argstype[0] = TEXT;
						argstype[1] = VARIABLE;
						for(int j = 2; j < args.length; j++)
							argstype[j] = TEXT;
					}
					testArgs(i,action,errors,args, new String[][]{argstype});
					break;
				case "SHAKE": ////SHAKE (INTENSITY) #Shake screen with intensity : (0 for stop)
					testArgs(i,action,errors,args, new String[][]{{VALUE}});
					break;
				case "FILTER": //FILTER (R) (G) (B) (A) {TIME} / OFF # Apply filter to game (rgba values in range 0 to 1)
					testArgs(i,action,errors,args, new String[][]{
						{"OFF"},
						{"OFF",VALUE},
						{FLOATVALUE,FLOATVALUE,FLOATVALUE,FLOATVALUE},
						{FLOATVALUE,FLOATVALUE,FLOATVALUE,FLOATVALUE,VALUE}});
					break;
				
				/* Sound effects */
					
				case "SOUND": // SOUND (NAME) (R/P) / OFF #Play sound from name, use R to repeat, use OFF to remove all sounds
					testArgs(i,action,errors,args, new String[][]{{TEXT,"R/P"},{"OFF"}});
					break;
					
				/* Game interaction */
					
				case "INVADD": //INVADD (ITEMID) [NUM] [MSG] #Add 1 or NUM item(s) to the player and display it (optional)(pause)
					testArgs(i,action,errors,args, new String[][]{{ID},{ID,INTVALUE},{ID,VALUE,TEXT}});
					break;
				case "INVDEL": //INVDEL (ITEMID) [NUM] [MSG] #Remove all or NUM item(s) to the player and display it (optional)(pause)
					testArgs(i,action,errors,args, new String[][]{{ID},{ID,INTVALUE},{ID,VALUE,TEXT}});
					break;
				case "MAP": //MAP (X) (Y) (LAYER) [TILESET] [ID] #Edit one map's tile
					testArgs(i,action,errors,args, new String[][]{{INTVALUE,INTVALUE,INTVALUE},{INTVALUE,INTVALUE,INTVALUE,TEXT,ID}});
					break;
				case "MAPR": //MAP (DX) (DY) (LAYER) [TILESET] [ID]  #Edit one map's tile relatively to event's source
					testArgs(i,action,errors,args, new String[][]{{INTVALUE,INTVALUE,INTVALUE},{INTVALUE,INTVALUE,INTVALUE,TEXT,ID}});
					break;
				case "PLAYERTILESET": //PLAYERTILESET (TILESET) #Change Player tileset
					testArgs(i,action,errors,args, new String[][]{{TEXT}});
					break;
				case "NPCANIMATION": //NPCTILESET (SOUTH/WEST/NORTH/EAST) [STOP/MARCH] #Change NPC animation
					testArgs(i,action,errors,args, new String[][]{{"SOUTH/WEST/NORTH/EAST"},{"SOUTH/WEST/NORTH/EAST","STOP/MARCH"}});
					break;
				case "NPCTILESET": //NPCTILESET (NPCID) (TILESET) #Change NPC tileset
					testArgs(i,action,errors,args, new String[][]{{ID,TEXT}});
					break;
				case "CHGMAP": //CHGMAP (MAPNAME) [PLAYERX] [PLAYERY]
					testArgs(i,action,errors,args, new String[][]{{TEXT},{TEXT,INTVALUE,INTVALUE}});
					break;
				case "MVPLAYER": //MVPLAYER (PLAYERX) (PLAYERY) #Move player in current map
					testArgs(i,action,errors,args, new String[][]{{INTVALUE,INTVALUE}});
					break;
				case "MVRPLAYER": //MVRPLAYER (PLAYERDX) (PLAYERDY) #Move player relatively in current map
					testArgs(i,action,errors,args, new String[][]{{INTVALUE,INTVALUE}});
					break;
				case "STOP": // STOP {ON/OFF} #Prevent player from moving
					testArgs(i,action,errors,args, new String[][]{{},{"ON/OFF"}});
					break;
				case "WAIT": // WAIT (TIME) #Wait a amount of time before other event
					testArgs(i,action,errors,args, new String[][]{{VALUE}});
					break;
				case "PAUSE": // PAUSE (TIME) #Pause game (wait + prevent player from moving)
					testArgs(i,action,errors,args, new String[][]{{VALUE}});
					break;
				case "PLAYERFACE": // PLAYERFACE (WEST/SOUTH/NORTH/EAST) #Change player facing
					testArgs(i,action,errors,args, new String[][]{{"SOUTH/WEST/NORTH/EAST"}});
					break;
				default:
					errors.add("Unknown event action : ["+i+"]"+action);
					break;
				}
			}
		}
		
		if(jumplvl>0)
			errors.add(jumplvl+" level(s) of IF or IFT not closed [End of event]");
		return errors;
	}
	
	private static String[] getArgs(String sargs, boolean keepquotes){
		//"\"Je suis une phrase\" ARG1 \"Je suis une autre phrase\" \"phrase 3\" TEST TEST"
		ArrayList<String> args = new ArrayList<>();
		boolean quote = false;;
		String arg = "";
		for(char c:sargs.toCharArray()){
			switch(c){
			case '"':
				quote = !quote;
				if(!quote){
					if(keepquotes)
						arg = "\""+arg+"\"";
					args.add(arg);
					arg = "";
				}
				break;
			case ' ':
				if(!quote){
					if(arg != "")
						args.add(arg);
					arg = "";
					break;
				}
			default:
				arg+=c;
				break;
			}
		}
		if(arg!="")
			args.add(arg);
		return args.toArray(new String[0]);
	}

	private static void testArgs(int i,String action, ArrayList<String> errors, String args[], String values[][]){
		int j = -1;
		for(int j1 = 0; j1 < values.length; j1++){
			if(args.length == values[j1].length){
				j = j1;
			}
		}
		if(j == -1){
			errors.add("Wrong number of arguments : ["+i+"]"+action);
		}else{
			for(int j2 = 0; j2 < args.length; j2++){
				switch(values[j][j2]){
				case TRIGGER:
				case VARIABLE:
					if(args[j2].startsWith("\""))
						errors.add("Argument "+(j2+1)+" must be a "+values[j][j2]+" : ["+i+"]"+action);
					break;
				case TEXT:
					if(!args[j2].startsWith("\""))
						errors.add("Argument "+(j2+1)+" must be a TEXT : ["+i+"]"+action);
					break;
				case VALUE:
					if(!isValue(args[j2]))
						errors.add("Argument "+(j2+1)+" must be a VALUE : ["+i+"]"+action);
					break;
				case INTVALUE:
					if(!isInteger(args[j2]))
						errors.add("Argument "+(j2+1)+" must be an INTEGER VALUE : ["+i+"]"+action);
					break;
				case FLOATVALUE:
					if(!isValue(args[j2])){
						errors.add("Argument "+(j2+1)+" must be a FLOAT VALUE (between 0 and 1) : ["+i+"]"+action);
					}else{
						float fvalue = Float.parseFloat(args[j2]);
						if(fvalue<0 || fvalue>1)
							errors.add("Argument "+(j2+1)+" must be a FLOAT VALUE (between 0 and 1) : ["+i+"]"+action);
					}
					break;
				case ID:
					if(!isInteger(args[j2]))
						errors.add("Argument "+(j2+1)+" must be an ID : ["+i+"]"+action);
					break;
				default:
					if(args[j2].startsWith("\""))
						errors.add("Argument "+(j2+1)+" must be ["+values[j][j2]+"] : ["+i+"]"+action);
					else{
						String vals[] = values[j][j2].split("/");
						boolean good = false;
						for(String val:vals){
							if(args[j2].equals(val)){
								good = true;
								break;
							}
						}
						if(!good)
							errors.add("Argument "+(j2+1)+" must be ["+values[j][j2]+"] : ["+i+"]"+action);
					}
					break;
				}
			}
		}
	}
	
	private void pause(String event, int x, int y, int i, HashMap<String, Integer> vars, boolean collide){
		this.savedEvent = event;
		this.savedx = x;
		this.savedy = y;
		this.savedi = i+1;
		this.savedvars = vars;
		this.savedc = collide;
	}
	
	public void resume(){
		if(savedEvent != null){
			this.eventCalled(savedEvent, savedx, savedy, savedi, savedvars, savedc);
		}
	}
	
	public void setTempVar(String key, Integer value){
		if(savedvars != null){
			savedvars.put(key, value);
		}
		
	}
	
	private static String getStringArg(String arg){
		return arg.substring(1,arg.length()-1);
	}
	
	private static boolean isInteger(String s) {
	    try{
	    	Integer.parseInt(s);
	    	return true;
	    }catch(NumberFormatException e){
	    	return false;
	    }
	}
	
	private static boolean isValue(String s) {
		try{
	    	Float.parseFloat(s);
	    	return true;
	    }catch(NumberFormatException e){
	    	return false;
	    }
	}
	
}
