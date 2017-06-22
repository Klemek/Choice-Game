# Events

## Introduction

Events are the main part of the game edition. This is where you will set your story through every interactions your player can make. They should be in the properties of the info tiles in your [map creation](Map_creation.md#map-creation).

## Format:
	EVENT (PARAM) {OPTIONAL PARAM}

## List :
### Logical :

* TRIGGER (TRIGNAME) {ON/OFF} #Create/edit trigger
* IFT {NOT} (TRIGNAME) ... {ELSE ...} END #Test trigger
* VAR (VARNAME) (VALUE) #Create/edit local variable
* VARG (VARNAME) {VALUE} #Create/edit global variable
* IF (VARNAME) (==/!=/>/</>=/<=) (VALUE) ... {ELSE ...} END #Test variable
* IFC {TRUE/FALSE} ... {ELSE ...} END #Test if collision event (TRUE) or interact (FALSE)
* IFO (WEST/SOUTH/NORTH/EAST) ...  {ELSE ...} END #If player facing is ...
* ICZ (VARNAME) {VALUE} #Increase var from 1 or value 
* DCZ (VARNAME) {VALUE} #Decrease var from 1 or value 
* RANDOM (VARNAME) (MINVALUE) (MAXVALUE) #Generate random number between min and max and put it in a variable

### Visual effects:

* SAY (TEXT) {CONTINUE} #Show text (pause game if not continue)
* DELETEMSG #Delete current message
* DIALOG (TEXT) (VARNAME) (OPTION1) (OPTION2) ... #Show a choice and return result on a viariable (start at 1)
* SHAKE (INTENSITY) #Shake screen with intensity : (0 for stop)
* FILTER (R) (G) (B) (A) {TIME} / OFF {TIME} # Apply filter to game (rgba values in range 0 to 1) default time 0.5 second

### Sound effects :

* SOUND (NAME) (R/P) / OFF #Play sound from name, use R to repeat, use OFF to remove all sounds

### Game interaction:

* MAP (X) (Y) (LAYER) {TILESET} {ID} #Edit one map's tile
* MAPR (DX) (DY) (LAYER) {TILESET} {ID} #Edit one map's tile relatively to event's source
* PLAYERTILESET (TILESET) #Change Player tileset
* CHGMAP (MAPNAME) {PLAYERX} {PLAYERY} #Change map and (optional) move player in it
* MVPLAYER (PLAYERX) (PLAYERY) #Move player in current map
* MVRPLAYER (PLAYERDX) (PLAYERDY) #Move player relatively in current map
* PLAYERWALK (ON/OFF) #Set player to walk in the direction he is facing (if facing change, same direction)
* PLAYERFACE (WEST/SOUTH/NORTH/EAST) #Change player facing
* MVNPC (NPCNAME) (NPCX) (NPCY) #Move npc in current map
* MVRNPC (NPCNAME) (NPCDX) (NPCDY) #Move npc relatively in current map
* NPCWALK (NPCNAME) (ON/OFF) #Set npc to walk in the direction he is facing (if facing change, same direction)
* NPCFACE (NPCNAME) (WEST/SOUTH/NORTH/EAST) #Change npc facing
* NPCTILESET (NPCNAME) (TILESET) #Change npc tileset
* STOP {ON/OFF} #Prevent player from moving
* WAIT (TIME) #Wait a amount of time before other event
* PAUSE (TIME) #Pause game (wait + prevent player from moving)

## Informations

* When a event like RANDOM or DIALOG set a value in a variable, it will test if a global variable exist before registering it in a temporary variable. So, you can register theses events in a variable like that :


	VARG IMPORTANTCHOICE 0
	DIALOG "What do you want ?" IMPORTANTCHOICE "Pizza" "Icecream"

* Sounds played are from the sound folder and are in **.ogg format**
* Check logs when running the game to see if some commands are wrong, they will not be registered to not corrupt the game.

## Examples
### Example 1 (Change map):
	IFC  # If the player walks on it
	   FILTER 0 0 0 1  # Add a black filter
	   SOUND "dooropen" P # Play the sound of opening door
	   PAUSE 0.5  # Pause everything the time the filter fade in
	   SOUND OFF # Remove music of the map
	   CHGMAP "othermap" 1 0  # Change map at given coordinates
	END	
### Example 2 (Enter the map):
	MVPLAYER 9 13 # Move the player to right spot
	FILTER OFF # Remove black filter if any
	SOUND "doorclose" P # Play the sound of closing door
	SOUND "music" R # Play this map's music
	PLAYERFACE WEST # Change player orientation
	PAUSE 0.5 # Pause everything the time the filter fade out
### Example 3 (Bed):
	DIALOG "What do you want to do ?" CHOICE1 "Sleep until morning"  "Sleep until night"  "Cancel"
	IF CHOICE1 == 1
	   SAY "You sleep until morning"
	   TRIGGER DAY ON
	END
	IF CHOICE1 == 2
	   SAY "You sleep until night"
	   TRIGGER DAY OFF
	END
### Example 4 (Chest):
	IFT NOT CHEST152 # One time event
	   IFT KEY152 # chest's key trigger
	      MAPR 0 0 1 "chest1" 1 # Change the tile to set it open
	      RANDOM VAL 1 3 # Generate a random number
	      IF VAL == 1
		      SAY "You find some food"
		      VARG FOOD # Create global variable if it not exists
		      ICZ FOOD 1 # Augmente la variable globale nourriture
	      ELSE
	      		SAY "This chest is empty"
	      END
	      MAPR 0 0 1 "chest1" 0 # Change the tile to set it close
	      TRIGGER CHEST152 ON
	   ELSE
	      SAY "You don't have the key of this chest."
	   END
	ELSE
	   SAY "You already searched this chest."
	END

	
[Back to Table of Contents](Documentation.md#table-of-contents)