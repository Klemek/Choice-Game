package fr.choicegame.event;

import java.util.ArrayList;
import java.util.HashMap;

import fr.choicegame.Game;

public class EventComputer implements GameEventListener {
	
	private Game game;
	
	public EventComputer(Game game) {
		this.game = game;
	}
	
	// Functions

	public void eventCalled(String event, int x, int y) {
		
		String actions[] = event.split("\n");
		
		HashMap<String, Integer> vars;
		
		for(String action:actions){
			String spl1[] = action.split(" ",2);
			String cmd = spl1[0];
			String args[] = getArgs(spl1[1]);
			switch(cmd){
			default:
				System.err.println("Unknown event action : "+action);
				break;
			}
		}
		//TODO
	}
	
	private static String[] getArgs(String sargs){
		//"\"Je suis une phrase\" ARG1 \"Je suis une autre phrase\" \"phrase 3\" TEST TEST"
		ArrayList<String> args = new ArrayList<>();
		boolean quote = false;;
		String arg = "";
		for(char c:sargs.toCharArray()){
			switch(c){
			case '"':
				quote = !quote;
				if(!quote){
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

}
