# Events

## Introduction

Les événements sont les principaux éléments d'édition du jeu. C'est ici que vous pourrez créer toutes les intéractions que pourra faire votre personnage. Ils devraient être dans les propriétés de la tile info dans votre [map creation](Map_creation.md#map-creation).

## Format:
	EVENT (PARAM) {OPTIONAL PARAM}

## List :
### Logical :

* TRIGGER (TRIGNAME) {ON/OFF} #Créer/éditer un trigger
* IFT {NOT} (TRIGNAME) ... {ELSE ...} END #Tester un trigger
* VAR (VARNAME) (VALUE) #Créer/éditer une variable locale 
* VARG (VARNAME) (VALUE) #Créer/éditer une variable globale
* IF (VARNAME) (==/!=/>/</>=/<=) (VALUE) ... {ELSE ...} END #Tester une variable
* IFC {TRUE/FALSE} ... {ELSE ...} END #Tester la collision (TRUE) ou l'intéraction (FALSE)
* ICZ (VARNAME) {VALUE} #Augmenter var de 1 ou value 
* DCZ (VARNAME) {VALUE} #Diminuer var de 1 ou value 
* RANDOM (VARNAME) (MINVALUE) (MAXVALUE) #Génère un nombre aléatoire entre min et max et l'associe à une variable


### Visual effects:

* SAY (TEXT) {IMAGEID} #Affiche du texte (met le jeu en pause)
* DIALOG (TEXT) (VARNAME) (OPTION1) (OPTION2) ... #Affiche un choix et retourne le résultat dans une variable (commence à 1)
* SHAKE (INTENSITY) #Secoue l'écran avec un certaine intensité (0 pour arreter)
* FILTER (R) (G) (B) (A) {TIME} / OFF {TIME} #Ajoute un filtre au jeu (valeur rgba entre 0 et 1), durée par défaut 0.5 seconde


### Game interaction:

* MAP (X) (Y) (LAYER) (TILESET) (ID) #Editer une tile de la carte
* MAPR (DX) (DY) (LAYER) (TILESET) (ID) #Editer une tile de la map relativement à la source de l'évènement
* PLAYERTILESET (TILESET) #Change le tileset du joueur
* CHGMAP (MAPNAME) {PLAYERX} {PLAYERY} #Change la carte et (optionnel) déplace le joueur dessus
* MVPLAYER (PLAYERX) (PLAYERY) #Déplace le joueur sur la carte
* MVRPLAYER (PLAYERDX) (PLAYERDY) #Déplace le joueur relativement à la carte
* PLAYERFACE (WEST/SOUTH/NORTH/EAST) #Change la direction du joueur
* STOP {ON/OFF} #Empêche le joueur de bouger
* WAIT (TIME) #Attend un certain temps avant un autre événement
* PAUSE (TIME) #Met le jeu en pause (attente + empêche le joueur de bouger)

## Informations

Lorsqu'un événement comme RANDOM ou DIALOG place une valeur dans une variable, il va tester si la variable globale existe avant de l'enregistrer dans une variable temporaire. Donc vous pouvez enregistrer ces événements dans une variable ainsi :


	VARG IMPORTANTCHOICE 0
	DIALOG "What do you want ?" IMPORTANTCHOICE "Pizza" "Icecream"

* Les sons joués depuis le dossier des sons sont en **format .ogg**
* Vérifiez les logs lorsque le jeu tourne pour voir si certaines commandes sont mauvaises, elles ne seront pas enregistrées pour ne pas corrompre le jeu. 

## Examples
### Example 1 (Change carte):
	IFC  # Si le joueur marche dessus
	   FILTER 0 0 0 1  # ajouter un filtre noir
	   SOUND "dooropen" P # Jouer le son de la porte qui s'ouvre
	   PAUSE 0.5  # Tout mettre en pause le temps du filtre
	   SOUND OFF # Enlever la musique de la map
	   CHGMAP "othermap" 1 0  # Changer de map au coordonnées fournies
	END	
### Example 2 (Entrer la carte):
	MVPLAYER 9 13 # Déplacer le joueur au bon endroit
	SOUND "doorclose" P # Joue le son de la porte qui se ferme
	SOUND "music" R # Joue la musique de la map
	PLAYERFACE WEST # Change l'orientation du joueur
	FILTER OFF # Enlever le filtre noir si il y a
	PAUSE 0.5 # Tout mettre en pause le temps que le filtre noir s'enlève
### Example 3 (Lit):
	DIALOG "Que voulez-vous faire ?" CHOICE1 "Dormir jusqu'au matin"  "Dormir jusqu'au soir"  "Annuler"
	IF CHOICE1 == 1
	   SAY "Vous dormez jusqu'au matin"
	   TRIGGER DAY ON
	END
	IF CHOICE1 == 2
	   SAY "Vous dormez jusqu'au soir"
	   TRIGGER DAY OFF
	END
### Example 4 (Coffre):
	IFT NOT CHEST152 # Event unique
	   IFT KEY152 # Trigger de la clé du coffre
	      MAPR 0 0 1 CHEST1 1 # Change la tile pour montrer le coffre ouvert
	      RANDOM VAL 1 3 # Génère un nombre aléatoire
	      IF VAL == 1
		      SAY "Vous avez trouvé de la nourriture"
		      IF FOOD == 0 #La variable globale n'existe peut etre pas
		      		VARG FOOD 0 #Crée la variable globale
		      END
		      ICZ FOOD 1 # Augmente la variable globale nourriture
	      ELSE
	      		SAY "Ce coffre est vide"
	      END
	      MAPR 0 0 1 CHEST1 0 # Change la tile pour montrer le coffre fermé
		   TRIGGER CHEST152 ON
	   ELSE
	      SAY "Vous n'avez pas la clé du coffre."
	   END
	ELSE
	   SAY "Vous avez déjà fouillé ce coffre."
	END

	
[Back to Table of Contents](Documentation.md#table-of-contents)
