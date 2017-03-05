# Choice-Game

Un jeu dans lequel vos choix dÃ©cident de votre scÃ©nario !


## Documentation

### Events

####Format:
EVENT (PARAM) {OPTIONAL PARAM}

####List :
#####Logical :

* TRIGGER (TRIGNAME) {ON/OFF} #Create/edit trigger
* IFT {NOT} (TRIGNAME) ... {ELSE ...} END #Test trigger
* VAR (VARNAME) (VALUE) #Create/edit local variable
* VARG (VARNAME) (VALUE) #Create/edit global variable
* IF (VARNAME) (==/!=/>/</>=/<=) (VALUE) ... {ELSE ...} END #Test variable
* ICZ (VARNAME) {VALUE} #Increase var from 1 or value 
* DCZ (VARNAME) {VALUE} #Decrease var from 1 or value 
* INVVAR (ITEMID) (VARNAME) #Get the quantity of an item in the player inventory and put it in a var

#####Visual effects:

* SAY (TEXT) {IMAGEID} #Show text (pause game)
* DIALOG (TEXT) (VARNAME) (OPTION1) (OPTION2) ... #Show a choice and return result on a viariable
* SHAKE (ON/OFF) {TIME} #Toggle shake screen for a time or until off

#####Game interaction:

* INVADD (ITEMID) {NUM} {MSG} #Add 1 or NUM item(s) to the player and display it (optional)(pause)
* INVDEL (ITEMID) {NUM} {MSG} #Remove all or NUM item(s) to the player and display it (optional)(pause)
* MAP (X) (Y) (LAYER) (TILESET) (ID) #Edit one map's tile
* MAPR (DX) (DY) (LAYER) (TILESET) (ID) #Edit one map's tile relatively to event's source
* PLAYERTILESET (TILESET) #Change Player tileset
* NPCTILESET (SOUTH/WEST/NORTH/EAST) {STOP/MARCH} #Change NPC animation
* NPCTILESET (NPCID) (TILESET) #Change NPC tileset
* CHGMAP (MAPNAME) {PLAYERX} {PLAYERY} #Change map and (optional) move player in it

####Examples
#####Example 1 (Bed):
```
DIALOG "What do you want to do ?" CHOICE1 "Sleep until morning"  "Sleep until night"  "Cancel"
IF CHOICE1==0
   SAY "You sleep until morning"
   TRIGGER DAY ON
END
IF CHOICE1==1
   SAY "You sleep until night"
   TRIGGER DAY OFF
END
```
#####Example 2 (Chest):
```
IF NOT CHEST152 #One time event
   INVVAR 152 KEY152
   IF KEY152>=1
      INVDEL 152 1 "You use " #"{Clé de coffre}x1"
      MAPR 0 0 1 CHEST1 1
      SAY "You find some food"
      INVADD 12 1 "You find : " 
      MAPR 0 0 1 CHEST1 0
      TRIGGER CHEST152 ON
   ELSE
      SAY "You don't have this chest's key."
   END
ELSE
   SAY "You already searched this chest."
END
```
