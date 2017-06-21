# Events

## Introduction

Les événements sont les principaux éléments d'édition du jeu. C'est ici que vous pourrez créer toutes les intéractions que pourra faire votre personnage. Ils devraient être dans les propriétés de la tile info dans votre [map creation](Map_creation.md#map-creation).

## Format:
	EVENT (PARAM) {OPTIONAL PARAM}

## List :
### Logical :

* TRIGGER (TRIGNAME) {ON/OFF} #Créer/éditer trigger
* IFT {NOT} (TRIGNAME) ... {ELSE ...} END #Tester trigger
* VAR (VARNAME) (VALUE) #Créer/éditer variable locale 
* VARG (VARNAME) (VALUE) #Créer/éditer variable globale
* IF (VARNAME) (==/!=/>/</>=/<=) (VALUE) ... {ELSE ...} END #Tester variable
* IFC {TRUE/FALSE} ... {ELSE ...} END #Tester la collision (TRUE) ou l'intéraction (FALSE)
* ICZ (VARNAME) {VALUE} #Augmenter var de 1 ou value 
* DCZ (VARNAME) {VALUE} #Diminuer var de 1 ou value 
* INVVAR (ITEMID) (VARNAME) #Obtenir la quantité d'un item dans l'inventaire du joueur et l'associe à une var
* RANDOM (VARNAME) (MINVALUE) (MAXVALUE) #Génère un nombre aléatoire entre min et max et l'associe à une var


### Visual effects:

* SAY (TEXT) {IMAGEID} #Affiche texte (met le jeu en pause)
* DIALOG (TEXT) (VARNAME) (OPTION1) (OPTION2) ... #Affiche un choix et retourne le résultat dans une variable
* SHAKE (ON/OFF) {TIME} #Active le tremblement de l'écran pour un temps puis le désactive
* FILTER (R) (G) (B) (A) {TIME} / OFF {TIME} #Ajoute un filtre au jeu (valeur rgba entre 0 et 1), durée par défaut 0.5 seconde


### Game interaction:

* INVADD (ITEMID) {NUM} {MSG} #Ajoute 1 ou NUM objet(s) au joueur and l'affiche (optionnel)(pause)
* INVDEL (ITEMID) {NUM} {MSG} #Retire tout ou NUM objet(s) au joueur et l'affiche (optionnel)(pause)
* MAP (X) (Y) (LAYER) (TILESET) (ID) #Editer une tile de la carte
* MAPR (DX) (DY) (LAYER) (TILESET) (ID) #Editer une tile de la map relativement à la source de l'évènement
* PLAYERTILESET (TILESET) #Change le tileset du joueur
* NPCTILESET (SOUTH/WEST/NORTH/EAST) {STOP/MARCH} #Change l'animation du PNJ
* NPCTILESET (NPCID) (TILESET) #Change le tileset du PNJ
* CHGMAP (MAPNAME) {PLAYERX} {PLAYERY} #Change la carte et (optionnel) déplace le joueur dessus
* MVPLAYER (PLAYERX) (PLAYERY) #Déplace le joueur sur la carte
* MVRPLAYER (PLAYERDX) (PLAYERDY) #Déplace le joueur relativement à la carte
* STOP {ON/OFF} #Empêche le joueur de bouger
* WAIT (TIME) #Attend un certain temps avant un autre événement
* PAUSE (TIME) #Met le jeu en pause (attente + empêche le joueur de bouger)

## Informations

Lorsqu'un événement comme RANDOM, INVAR ou DIALOG place une valeur dans une variable, il va tester si la variable globale existe avant de l'enregistrer dans une variable temporaire. Donc vous pouvez enregistrer ces événements dans une variable ainsi :


	VARG IMPORTANTCHOICE 0
	DIALOG "What do you want ?" IMPORTANTCHOICE "Pizza" "Icecream"


* Vérifier les logs lorsque le jeu tourne pour voir si certaines commandes sont mauvaises, elles ne seront pas enregistrées pour ne pas corrompre le jeu. 

## Examples
### Example 1 (Change carte):
	IFC  # If the player walks on it
	   FILTER 0 0 0 1  # Add a black filter
	   PAUSE 0.5  # Pause everything the time the filter fade in
	   CHGMAP "othermap" 1 0  # Change map at given coordinates
	END	
### Example 2 (Entrer la carte):
	MVPLAYER 9 13 # Move the player to right spot
	FILTER OFF # Remove black filter if any
	PAUSE 0.5 # Pause everything the time the filter fade out
### Example 3 (Lit):
	DIALOG "What do you want to do ?" CHOICE1 "Sleep until morning"  "Sleep until night"  "Cancel"
	IF CHOICE1==0
	   SAY "You sleep until morning"
	   TRIGGER DAY ON
	END
	IF CHOICE1==1
	   SAY "You sleep until night"
	   TRIGGER DAY OFF
	END
### Example 4 (Coffre):
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
