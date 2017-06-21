# Build the Game
## 1. Requirements
Vous aurez besoin d'un gestionnaire d'archive (Comme WinRAR) et du programme choicegame. (lire [Get Started](Get_started.md#get-started) pour vous renseigner)

Pour chacune des étapes précédente vous devez avoir config.cfg qui met en place les noms de fichiers et certains fichiers
### The maps folder
Toutes les cartes que vous avez créée doivent être au format .tmx. Chaque tileset référencé dans chaque carte doit être dans le dossier des tilesets à proximiter de ce dossier.
### The tilesets folder
Vous devez avoir tous les tilesets utilisés pour vos cartes **au format png** tous autres formats provoque l'arrêt du jeu.

Vous devez également quelques tilesets de personnages (au moins un par défault pour le joueur) qui sera mis à ce format :
![character tileset example](img/character_tileset.png)

Respectez l'orientation du personnage, sinon le joueur ne regardera pas face à lui en marchant. De plus l'animation de la marche est sur la première et la troisième colonnes.
Vous aurez besoin en plus : le tileset des dialogues, cela créera le fond des dialogues et des messages. Il se compose en tiles de 4x3. La première partie 3x3 est le contour et le dernier tile en haut à droite est le curseur/pointeur.

![dialog.png example](img/dialog.png)
## The fonts folder
Si vous avez votre propre police de caractère vous devez le mettre dans ce fichier. Attention seul les .ttf (TrueTypeFonts) sont valides.
## 2. Add the resources
Ouvrir choicegame.jar en tant qu'archive.

Vous devez supprimer les dossiers de cartes, tilesets, polices de caractère et le fichier config contenu dedans.

Ensuite, vous pouvez mettre dans l'archive vos propres cartes, tilesets, polices de caractère et le fichier config contenu dedans.
## 3. Test the resources
Ouvrez un invité de commande and placé vous dans le dossier contenant l'archive .jar.

Puis essayer de le lancer avec (rempalcer 'choicegame.jar' par le nom de l'archive):
	
	java -jar choicegame.jar

Si tout est correct, le jeu doit normalement se démarrer avec la carte de départ et le personnage. Dans le cas contraire une erreur va s'afficher dans l'invité de commande. Elles seront indiquées par un '#'.
## 4. Troubleshooting

| Error log | Explication |
|-|-|
| No config file found | Le fichier config.cfg n'a pas été trouvé à la racine de l'archive |
| Error reading folder ... | Regarder si tous les fichiers ont le bon format (aucun .jpg ne doit être présent) |
| Font file ... in wrong format | Votre police de caractère n'est pas au format TrueTypeFonts |
| Font file ... not found | La police de caractère spécifiée dans config.cfg n'est pas dans le dossier associé. |
| Font ... not found on the Local Graphics Environment | Le nom de votre police de caractère n'est pas correct dans config.cfg. |
| Error on reading ... | Ceci est une erreur ... Google est ton ami. |
| [n] errors with event | Erreur de syntaxe dans les évènements, se référer à [Events](Events.md#events) |
| Invalid map : ... orientation is not orthogonal | Votre carte à mal été faite, se référer à [Map Creation](Map_creation.md#map-creation) |
| Invalid map : ... renderorder is not right-down | // |
| Invalid map : ... non existing layer | // |
| Invalid layer : ... no data | // |
| Invalid layer : ... encoding is not CSV | // |
| Invalid layer : ... encoding is not CSV | // |

## 5. Build for other operating systems
Quand votre jeu est opérationnel, vous pouvez compiler votre jeu pour d'autres systèmes d'exploitation, téléchargez l'archive déjà réalisée dans [builds folder](https://github.com/kalioz/Choice-Game/tree/master/builds) et remplacer les fichiers comme précédemment.

[Back to Table of Contents](Documentation.md#table-of-contents)
