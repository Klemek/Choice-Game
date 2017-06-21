# Map Creation
## 1. Requirements
Pour créer une carte vous aurez besoin de [Tiled](http://www.mapeditor.org/), un éditeur de carte qui va générer le bon format de carte pour ce logiciel.

## 2. Create a new map
Dans Tiled, créer une nouvelle carte dans "File>New>New Map...". Pour avoir le bon format de carte, vous devez sélectionner ces options :

![orientation : orthogonal, format : csv, order : right down](img/new_map.png)

N'oubliez pas de choisir la bonne taille de tile que vous utiliserez pour vos tilesets.
Sauvegardez votre map en .tmx où vous le souhaitez dans un dossier "maps" (important)
## 3. Import and configure information tilesets
Vous aurez besoin de 2 tilesets par défaut pour créer votre map, chacun devra avoir la même taille de tile et sera sauvegardé dans le même dossier "maps". Ils ne seront pas affichés dans le jeu, leur but est uniquement de stocker les informations dans la map.

* Un tileset 'type' qui contiendra 1 ou 2  tiles basiques pour remplir/délimiter la zone du joueur : 

![A 'type' tileset with 1 or 2 tiles](img/typetileset.png)


* Un tileset 'info' qui contiendra tous les événements que vous voudrez. On peut les distinguer par un label.

![An 'info' tileset with several events](img/infotileset.png)

Ensuite, vous allez les importer dans la carte de cette façon (rappelez vous leurs noms pour plus tard, en sachant que vous pouvez les renommer).

[select your source and enable 'Embed in map'](img/new_tileset.png)



Maintenant éditez le tile type


[](img/config_tileset.png)


ou pour chaque tile, ajouter une nouvelle propriété 'int' nommée 'type' (ou autre chose, mais rappelez vous du nom). Donnez lui la valeur 1 pour la première tile et la valeur 2 pour l'autre (si autre il y a).


[Add a new 'int' property named 'type'](img/add_property.png)


# 4. Import your own tilesets

ow you can import the same way any tilesets you will need to create your map. You will need to place them first in a "tilesets" folder next to the "maps" folder and in a png format. The name of the tileset itself is not important now.
Désormais vous pouvez importer de la même façon n'importe quel tileset dont vous auriez besoin pour créer votre map. Vous devrez d'abord les placer dans un fichier "tileset" à côté du dossier "maps" et dans un format png. Le nom du tileset n'est pas important maintenant. 


# 5. Create the default layers


Vous aurez besoin de 6 couches :

 2 couches informations : la couche 'info' pour les événements et la couche 'type' pour la zone du joueur.

 2 couches premier plan : elles seront affichées au dessus du personnage.
 
 2 couches arrière plan : elles seront affichées en dessous du personnage


Vous êtes libre de choisir les noms de ces couches. Faites attention à l'ordre dans le logiciel, puisqu'ils seront affichés dans cet ordre (pas dans le jeu).


[](img/layers.png)


# 6. Place your tiles

A présent, vous pouvez commencer à créer votre carte dans les couches premier plan et arrière plan.


[](img/example_map1.png)


# 7. Limit the player area

Vous devez maintenant délimiter la zone du joueur dans laquelle il pourra se déplacer librement. Pour cela il faut utiliser le tileset 'type' et la couche 'type'. Les tiles avec la valeur '1' seront libres alors que les autres (void or 2) bloqueront les déplacements du joueur. L'opacité de la couche dans le logiciel Tiled peut vous aider à créer cela. 


[](img/example_map2.png)


# 8. Create and place events

onfigure your 'info' tileset to add events. To do so, add a new string property named 'event' (or another name but remember it for later). Write the script of your event using the events (read [Events](Events.md#events) for the list of available commands).

Configurez votre tileset 'info' pour ajouter des événements. Pour cela, ajouter une nouvelle chaine de caractères (string) nommée 'event' (ou un autre nom que vous devrez retenir). Ecrivez le script de votre événement en utilisant les événements (lire [Events](Events.md#events) for the list of available commands).

[](img/add_event.png)


Vous pouvez ensuite placer votre événement nouvellement créé dans la carte dans la couche 'info'.


[](img/example_map3.png)


# 9. You are done

 Vous avez terminé, vous pouvez soit créer d'autres cartes ou finir votre jeu en créant le fichier de configuration (lire [The Configuration File](Config.md#the-configuration-file)) et le compiler (lire [Build the Game](Build.md#build-the-game)).

# 9. Additionnal information for maps


Votre personnage apparaitra dans la tile [0,0]. Vous devriez placer un événement ici qui le déplacera au bon endroit (cet événement sera pris en compte avant quoi que ce soit d'autre au lancement de la carte). 


[Back to Table of Contents](Documentation.md#table-of-contents)
