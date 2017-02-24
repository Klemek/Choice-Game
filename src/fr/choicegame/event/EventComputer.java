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
			if(action.length()>0 && !action.startsWith("#")){
				String spl1[] = action.split(" ",2);
				String cmd = spl1[0];
				if(!jump){
					String args[] = getArgs(spl1[1], false);
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
					}
				}else if(cmd == "ELSE" || cmd == "END"){
					jump = false;
				}
			}
		}
		//TODO
	}
	
public static void testEvent(String event) {
		
		String actions[] = event.split("\n");
		
		HashMap<String, Integer> vars;
		int jumplvl = 0;
		for(int i = 0; i < actions.length; i++){
			String action = actions[i];
			if(action.length()>0 && !action.startsWith("#")){
				String spl1[] = action.split(" ",2);
				String cmd = spl1[0];
				
				String args[] = getArgs(spl1[1], true);
				switch(cmd){
				case "TRIGGER":  //TRIGGER (TRIGNAME) [ON/OFF] #Create/edit trigger
					switch(args.length){
					case 1:  //TRIGGER (TRIGNAME)
						testArgs(i, action, args, new String[]{TRIGGER});
						break;
					case 2:  //TRIGGER (TRIGNAME) [ON/OFF]
						testArgs(i, action, args, new String[]{TRIGGER, "ON/OFF"});
						break;
					default:
						System.err.println("Wrong number of arguments : ["+i+"]"+action);
						break;
					}
					break;
				case "IFT":  //IFT [NOT] (TRIGNAME) ... [ELSE ...] END #Test trigger
					jumplvl++;
					switch(args.length){
					case 1: //IFT (TRIGNAME)
						testArgs(i, action, args, new String[]{TRIGGER});
						break;
					case 2: //IFT NOT (TRIGNAME)
						testArgs(i, action, args, new String[]{"NOT", TRIGGER});
						break;
					default:
						System.err.println("Wrong number of arguments : ["+i+"]"+action);
						break;
					}
				case "ELSE":
					if(jumplvl<0)
						System.err.println("No previous IF or IFT : ["+i+"]"+action);
					if(args.length>0)
						System.err.println("Wrong number of arguments : ["+i+"]"+action);
					break;
				case "END":
					if(jumplvl>0)
						jumplvl--;
					else
						System.err.println("No previous IF or IFT : ["+i+"]"+action);
					if(args.length>0)
						System.err.println("Wrong number of arguments : ["+i+"]"+action);
					break;
				case "VAR": //VAR (VARNAME) (VALUE) #Create/edit local variable
					if(args.length!=2)
						System.err.println("Wrong number of arguments : ["+i+"]"+action);
					else
						testArgs(i, action, args, new String[]{VARIABLE, VALUE});
					break;
				case "VARG": //VARG (VARNAME) (VALUE) #Create/edit global variable
					if(args.length!=2)
						System.err.println("Wrong number of arguments : ["+i+"]"+action);
					else
						testArgs(i, action, args, new String[]{VARIABLE, VALUE});
					break;
				case "IF": //IF (VARNAME) (==/!=/>/</>=/<=) (VALUE) ... [ELSE ...] END #Test variable
					jumplvl++;
					if(args.length!=2)
						System.err.println("Wrong number of arguments : ["+i+"]"+action);
					else
						testArgs(i, action, args, new String[]{VARIABLE,"==/!=/>/</>=/<=", VALUE});
					break;
				case "ICZ": //ICZ (VARNAME) [VALUE] # Increase var from or value 
					switch(args.length){
					case 1: //ICZ (VARNAME)
						testArgs(i, action, args, new String[]{VARIABLE});
						break;
					case 2: //ICZ (VARNAME) [VALUE]
						testArgs(i, action, args, new String[]{VARIABLE, VALUE});
						break;
					default:
						System.err.println("Wrong number of arguments : ["+i+"]"+action);
						break;
					}
					break;
				case "DCZ": //DCZ (VARNAME) [VALUE] # Decrease var from or value 
					switch(args.length){
					case 1: //DCZ (VARNAME)
						testArgs(i, action, args, new String[]{VARIABLE});
						break;
					case 2: //DCZ (VARNAME) [VALUE]
						testArgs(i, action, args, new String[]{VARIABLE, VALUE});
						break;
					default:
						System.err.println("Wrong number of arguments : ["+i+"]"+action);
						break;
					}
					break;
				default:
					System.err.println("Unknown event action : ["+i+"]"+action);
					break;
				}
			}
		}
		
		if(jumplvl>0)
			System.err.println(jumplvl+" level(s) of IF or IFT not closed [End of event]");
		//TODO
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

	private static void testArgs(int i,String action, String args[], String values[]){
		for(int j = 0; j < args.length; j++){
			switch(values[j]){
			case TRIGGER:
			case VARIABLE:
				if(args[j].startsWith("\""))
					System.err.println("Argument "+(j+1)+" must be a "+values[j]+" : ["+i+"]"+action);
				break;
			case TEXT:
				if(!args[j].startsWith("\""))
					System.err.println("Argument "+(j+1)+" must be a TEXT : ["+i+"]"+action);
				break;
			case VALUE:
				if(!isInteger(args[j]))
					System.err.println("Argument "+(j+1)+" must be a VALUE : ["+i+"]"+action);
				break;
			case ID:
				if(!isInteger(args[j]))
					System.err.println("Argument "+(j+1)+" must be an ID : ["+i+"]"+action);
				break;
			default:
				if(args[j].startsWith("\""))
					System.err.println("Argument "+(j+1)+" must be ["+values[j]+"] : ["+i+"]"+action);
				else{
					String vals[] = values[j].split("/");
					boolean good = false;
					for(String val:vals){
						if(args[j].equals(val)){
							good = true;
							break;
						}
					}
					if(!good)
						System.err.println("Argument "+(j+1)+" must be ["+values[j]+"] : ["+i+"]"+action);
				}
				break;
			}
		}
	}
	
	private static boolean isInteger(String s) {
	    Scanner sc = new Scanner(s.trim());
	    if(!sc.hasNextInt()) return false;
	    sc.nextInt();
	    return !sc.hasNext();
	}
	
}
