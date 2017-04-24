package fr.choicegame.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import fr.choicegame.Game;

public class EventComputer implements GameEventListener {
	
	private Game game;
	
	private static final String VARIABLE = "VARIABLE";
	private static final String TRIGGER = "TRIGGER";
	private static final String TEXT = "TEXT";
	private static final String VALUE = "VALUE";
	private static final String ID = "ID";
	
	public EventComputer(Game game) {
		this.game = game;
	}
	
	// Functions

	public void eventCalled(String event, int x, int y) {
		
		String actions[] = event.split("\n");
		
		HashMap<String, Integer> vars = new HashMap<>();
		boolean jump = false;
		for(String action:actions){
			System.out.println(">"+action);//TODO debug
			action = action.split("#")[0].trim(); //TODO espace # in text args
			if(action.length()>0){
				String spl1[] = action.split(" ",2);
				String cmd = spl1[0];
				if(!jump){
					String args[] = new String[0];
					if(spl1.length>1)
						args = getArgs(spl1[1], true);
					switch(cmd){
					case "TRIGGER": //TRIGGER (TRIGNAME) [ON/OFF] #Create/edit trigger
						switch(args.length){
						case 1: //TRIGGER (TRIGNAME)
							this.game.setTrigger(args[0], true);
							break;
						case 2: //TRIGGER (TRIGNAME) [ON/OFF]
							this.game.setTrigger(args[0], args[1].equals("ON"));
							break;
						}
						break;
					case "IFT": //IFT [NOT] (TRIGNAME) ... [ELSE ...] END #Test trigger
						switch(args.length){
						case 1: //IFT TRIGNAME
							if(!this.game.getTrigger(args[0]))
								jump = true;
							break;
						case 2: //IFT NOT TRIGNAME
							if(this.game.getTrigger(args[0]))
								jump = true;
							break;
						}
						break;
					case "VAR": //VAR (VARNAME) (VALUE) #Create/edit local variable
						vars.put(args[0], Integer.parseInt(args[1]));
						break;
					case "VARG": //VARG (VARNAME) (VALUE) #Create/edit global variable
						this.game.setGlobalVariable(args[0], Integer.parseInt(args[1]));
						break;
					case "IF": //IF (VARNAME) (==/!=/>/</>=/<=) (VALUE) ... [ELSE ...] END #Test variable
						switch(args[1]){
						case "==": //IF (VARNAME) == (VALUE)
							if(vars.getOrDefault(args[0], this.game.getGlobalVariable(args[0]))!=Integer.parseInt(args[2]))
								jump = true;
							break;
						case "!=": //IF (VARNAME) != (VALUE)
							if(vars.getOrDefault(args[0], this.game.getGlobalVariable(args[0]))==Integer.parseInt(args[2]))
								jump = true;
							break;
						case ">": //IF (VARNAME) > (VALUE)
							if(vars.getOrDefault(args[0], this.game.getGlobalVariable(args[0]))<=Integer.parseInt(args[2]))
								jump = true;
							break;
						case "<": //IF (VARNAME) < (VALUE)
							if(vars.getOrDefault(args[0], this.game.getGlobalVariable(args[0]))>=Integer.parseInt(args[2]))
								jump = true;
							break;
						case ">=": //IF (VARNAME) >= (VALUE)
							if(vars.getOrDefault(args[0], this.game.getGlobalVariable(args[0]))<Integer.parseInt(args[2]))
								jump = true;
							break;
						case "<=": //IF (VARNAME) <= (VALUE)
							if(vars.getOrDefault(args[0], this.game.getGlobalVariable(args[0]))>Integer.parseInt(args[2]))
								jump = true;
							break;
						}
						break;
					case "ELSE":
						jump = true;
						break;
					case "ICZ": //ICZ (VARNAME) [VALUE] # Increase var from or value 
						switch(args.length){
						case 1: //ICZ (VARNAME)
							if(vars.containsKey(args[0]))
								vars.put(args[0], vars.get(args[0])+1);
							else
								this.game.setGlobalVariable(args[0], this.game.getGlobalVariable(args[0])+1);
							break;
						case 2: //ICZ (VARNAME) [VALUE]
							if(vars.containsKey(args[0]))
								vars.put(args[0], vars.get(args[0])+Integer.parseInt(args[1]));
							else
								this.game.setGlobalVariable(args[0], this.game.getGlobalVariable(args[0])+Integer.parseInt(args[1]));
							break;
						}
						break;
					case "DCZ": //DCZ (VARNAME) [VALUE] # Decrease var from or value 
						switch(args.length){
						case 1: //DCZ (VARNAME)
							if(vars.containsKey(args[0]))
								vars.put(args[0], vars.get(args[0])-1);
							else
								this.game.setGlobalVariable(args[0], this.game.getGlobalVariable(args[0])-1);
							break;
						case 2: //DCZ (VARNAME) [VALUE]
							if(vars.containsKey(args[0]))
								vars.put(args[0], vars.get(args[0])-Integer.parseInt(args[1]));
							else
								this.game.setGlobalVariable(args[0], this.game.getGlobalVariable(args[0])-Integer.parseInt(args[1]));
							break;
						}
						break;
					case "INVVAR": //INVVAR (ITEMID) (VARNAME) #Get the quantity of an item in the player inventory and put it in a var
						//TODO EVENT INVVAR
						break;
					case "SAY": //SAY (TEXT) [IMAGEID] #Show text (pause game)
						//TODO EVENT SAY
						System.out.println(args[0]);
						switch(args.length){
						case 1: //SAY (TEXT)
							break;
						case 2://SAY (TEXT) (IMAGID)
							break;
						}
						break;
					case "DIALOG": //DIALOG (TEXT) (VARNAME) (OPTION1) (OPTION2) ... #Show a choice and return result on a viariable
						//TODO EVENT DIALOG
						break;
					case "SHAKE": //SHAKE (ON/OFF) [TIME] #Toggle shake screen for a time or until off
						//TODO EVENT SHAKE
						switch(args.length){
						case 1: //SHAKE (ON/OFF)
							break;
						case 2://SHAKE (ON/OFF) (TIME)
							break;
						}
						break;
					case "INVADD": //INVADD (ITEMID) [NUM] [MSG] #Add 1 or NUM item(s) to the player and display it (optional)(pause)
						//TODO EVENT INVADD
						switch(args.length){
						case 1: //INVADD (ITEMID)
							break;
						case 2://INVADD (ITEMID) (NUM)
							break;
						case 3://INVADD (ITEMID) (NUM) (MSG)
							break;
						}
						break;
					case "INVDEL": //INVDEL (ITEMID) [NUM] [MSG] #Remove 1 or NUM item(s) to the player and display it (optional)(pause)
						//TODO EVENT INVDEL
						switch(args.length){
						case 1: //INVDEL (ITEMID)
							break;
						case 2://INVDEL (ITEMID) (NUM)
							break;
						case 3://INVDEL (ITEMID) (NUM) (MSG)
							break;
						}
						break;
					case "MAP": //MAP (X) (Y) (LAYER) [TILESET] [ID] #Edit one map's tile
						//TODO EVENT MAP
						switch(args.length){
						case 3: //INVDEL (ITEMID)
							break;
						case 5://INVDEL (ITEMID) (NUM)
							break;
						}
						break;
					case "MAPR": //MAPR (DX) (DY) (LAYER) [TILESET] [ID] #Edit one map's tile relatively to event's source
						//TODO EVENT MAPR
						switch(args.length){
						case 3: //INVDEL (ITEMID)
							break;
						case 5://INVDEL (ITEMID) (NUM)
							break;
						}
						break;
					case "PLAYERTILESET": //PLAYERTILESET (TILESET) #Change Player tileset
						//TODO EVENT PLAYERTILESET
						break;
					case "NPCTILESET": //NPCTILESET (NPCID) (TILESET) #Change NPC tileset
						//TODO EVENT NPCTILESET
						break;
					case "NPCANIMATION": //NPCTILESET (SOUTH/WEST/NORTH/EAST) (STOP/MARCH) #Change NPC animation
						//TODO EVENT NPCANIMATION
						break;
					case "CHGMAP": //CHGMAP (MAPNAME) [PLAYERX] [PLAYERY]
						//TODO EVENT CHGMAP
						switch(args.length){
						case 1: //CHGMAP (MAPNAME)
							break;
						case 3://CHGMAP (MAPNAME) (PLAYERX) (PLAYERY)
							break;
						}
						break;
					}
				}else if(cmd.equals("ELSE") || cmd.equals("END")){
					jump = false;
				}else{
					System.out.println("[DEBUG]jumped:"+action);
				}
			}
		}
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
				case "ICZ": //ICZ (VARNAME) [VALUE] # Increase var from 1 or value 
					testArgs(i,action,errors,args, new String[][]{{VARIABLE},{VARIABLE, VALUE}});
					break;
				case "DCZ": //DCZ (VARNAME) [VALUE] # Decrease var from 1 or value 
					testArgs(i,action,errors,args, new String[][]{{VARIABLE},{VARIABLE, VALUE}});
					break;
				case "INVVAR": //INVVAR (ITEMID) (VARNAME) #Get the quantity of an item in the player inventory and put it in a var
					testArgs(i,action,errors,args, new String[][]{{ID,VARIABLE}});
					break;
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
				case "SHAKE": //SHAKE (ON/OFF) [TIME] #Toggle shake screen for a time or until off
					testArgs(i,action,errors,args, new String[][]{{"ON/OFF"},{"ON/OFF",VALUE}});
					break;
				case "INVADD": //INVADD (ITEMID) [NUM] [MSG] #Add 1 or NUM item(s) to the player and display it (optional)(pause)
					testArgs(i,action,errors,args, new String[][]{{ID},{ID,VALUE},{ID,VALUE,TEXT}});
					break;
				case "INVDEL": //INVDEL (ITEMID) [NUM] [MSG] #Remove all or NUM item(s) to the player and display it (optional)(pause)
					testArgs(i,action,errors,args, new String[][]{{ID},{ID,VALUE},{ID,VALUE,TEXT}});
					break;
				case "MAP": //MAP (X) (Y) (LAYER) [TILESET] [ID] #Edit one map's tile
					testArgs(i,action,errors,args, new String[][]{{VALUE,VALUE,VALUE},{VALUE,VALUE,VALUE,TEXT,ID}});
					break;
				case "MAPR": //MAP (DX) (DY) (LAYER) [TILESET] [ID]  #Edit one map's tile relatively to event's source
					testArgs(i,action,errors,args, new String[][]{{VALUE,VALUE,VALUE},{VALUE,VALUE,VALUE,TEXT,ID}});
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
					testArgs(i,action,errors,args, new String[][]{{TEXT},{TEXT,VALUE,VALUE}});
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
						arg = "\\"+arg+"\"";
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
					if(!isInteger(args[j2]))
						errors.add("Argument "+(j2+1)+" must be a VALUE : ["+i+"]"+action);
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
	
	private static boolean isInteger(String s) {
	    @SuppressWarnings("resource")
		Scanner sc = new Scanner(s.trim());
	    if(!sc.hasNextInt()) return false;
	    sc.nextInt();
	    return !sc.hasNext();
	}
	
}
