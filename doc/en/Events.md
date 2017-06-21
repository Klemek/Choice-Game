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
* VARG (VARNAME) (VALUE) #Create/edit global variable
* IF (VARNAME) (==/!=/>/</>=/<=) (VALUE) ... {ELSE ...} END #Test variable
* IFC {TRUE/FALSE} ... {ELSE ...} END #Test if collision event (TRUE) or interact (FALSE)
* ICZ (VARNAME) {VALUE} #Increase var from 1 or value 
* DCZ (VARNAME) {VALUE} #Decrease var from 1 or value 
* INVVAR (ITEMID) (VARNAME) #Get the quantity of an item in the player inventory and put it in a var
* RANDOM (VARNAME) (MINVALUE) (MAXVALUE) #Generate random number between min and max and put it in a var

### Visual effects:

* SAY (TEXT) {IMAGEID} #Show text (pause game)
* DIALOG (TEXT) (VARNAME) (OPTION1) (OPTION2) ... #Show a choice and return result on a viariable
* SHAKE (ON/OFF) {TIME} #Toggle shake screen for a time or until off
* FILTER (R) (G) (B) (A) {TIME} / OFF {TIME} # Apply filter to game (rgba values in range 0 to 1) default time 0.5 second

### Game interaction:

* INVADD (ITEMID) {NUM} {MSG} #Add 1 or NUM item(s) to the player and display it (optional)(pause)
* INVDEL (ITEMID) {NUM} {MSG} #Remove all or NUM item(s) to the player and display it (optional)(pause)
* MAP (X) (Y) (LAYER) (TILESET) (ID) #Edit one map's tile
* MAPR (DX) (DY) (LAYER) (TILESET) (ID) #Edit one map's tile relatively to event's source
* PLAYERTILESET (TILESET) #Change Player tileset
* NPCTILESET (SOUTH/WEST/NORTH/EAST) {STOP/MARCH} #Change NPC animation
* NPCTILESET (NPCID) (TILESET) #Change NPC tileset
* CHGMAP (MAPNAME) {PLAYERX} {PLAYERY} #Change map and (optional) move player in it
* MVPLAYER (PLAYERX) (PLAYERY) #Move player in current map
* MVRPLAYER (PLAYERDX) (PLAYERDY) #Move player relatively in current map
* STOP {ON/OFF} #Prevent player from moving
* WAIT (TIME) #Wait a amount of time before other event
* PAUSE (TIME) #Pause game (wait + prevent player from moving)

## Informations

* When a event like RANDOM, INVVAR or DIALOG set a value in a variable, it will test if a global variable exist before registering it in a temporary variable. So, you can register theses events in a variable like that :


	VARG IMPORTANTCHOICE 0
	DIALOG "What do you want ?" IMPORTANTCHOICE "Pizza" "Icecream"


* Check logs when running the game to see if some commands are wrong, they will not be registered to not corrupt the game.

## Examples
### Example 1 (Change map):
	IFC  # If the player walks on it
	   FILTER 0 0 0 1  # Add a black filter
	   PAUSE 0.5  # Pause everything the time the filter fade in
	   CHGMAP "othermap" 1 0  # Change map at given coordinates
	END	
### Example 2 (Enter the map):
	MVPLAYER 9 13 # Move the player to right spot
	FILTER OFF # Remove black filter if any
	PAUSE 0.5 # Pause everything the time the filter fade out
### Example 3 (Bed):
	DIALOG "What do you want to do ?" CHOICE1 "Sleep until morning"  "Sleep until night"  "Cancel"
	IF CHOICE1==0
	   SAY "You sleep until morning"
	   TRIGGER DAY ON
	END
	IF CHOICE1==1
	   SAY "You sleep until night"
	   TRIGGER DAY OFF
	END
### Example 4 (Chest):
	IF NOT CHEST152 # One time event
	   INVVAR 152 KEY152
	   IF KEY152>=1
	      INVDEL 152 1 "You use " # "{Chest key}x1"
	      MAPR 0 0 1 CHEST1 1 # Change the tile to set it open
	      SAY "You find some food"
	      INVADD 12 1 "You find : " # "{Canned food}x1"
	      MAPR 0 0 1 CHEST1 0 # Change the tile to set it close
	      TRIGGER CHEST152 ON
	   ELSE
	      SAY "You don't have the key of this chest."
	   END
	ELSE
	   SAY "You already searched this chest."
	END

	
[Back to Table of Contents](Documentation.md#table-of-contents)