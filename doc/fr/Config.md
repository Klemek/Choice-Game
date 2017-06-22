# Le fichier de configuration
## Introduction
Le fichier de configuration s'appelle 'config.cfg'. Il faut écrire à l'intérieur la totalité des spécifications du jeu et les détails des cartes.


## Liste des propriétés
Voici la liste des propriétés disponibles à l'édition
### Ressources
| Propriété | Info | Valeur par défaut |
|-|-|-:|
| tile_size | la taille des tiles dans tous les tilesets | 32 |
| start_map | le nom du fichier de la première carte |  |
| player_tileset | le nom du tileset de départ du joueur |  |
| tilesets_folder | le nom du dossier des tilesets | tilesets |
| maps_folder | le nom du dossier des sons | maps |
| sounds_folder | le nom du dossier des sons | sounds |
| fonts_folder | le nom du dossier des polices| fonts |
| font_file | le nom du fichier des polices personnalisées |  |
| font_name | le nom de la police personnalisée dans le système | Consolas |
| dialog_tileset | le nom du tileset de dialogue |  |

### Personnages
| Propriété | Info | Valeur par défaut |
|-|-|-:|
| character_speed | la vitesse du personnage en blocks/3ms (*30 = blocks/s) | 0.1 |
| hitbox_start_x | la hitbox du joueur dans un carré 1x1 | 0.25 |
| hitbox_start_y | la hitbox du joueur dans un carré 1x1 | 0.7 |
| hitbox_size_x | la hitbox du joueur dans un carré 1x1 | 0.5 |
| hitbox_size_y | la hitbox du joueur dans un carré 1x1 | 0.3 |

### Maps
| Propriété | Info | Valeur par défaut |
|-|-|-:|
| background_1_layer | le nom de la premiere couche de l'arrière plan dans les cartes | bg1 |
| background_2_layer | le nom de la seconde couche de l'arrière plan dans les cartes | bg2 |
| foreground_1_layer | le nom de la premiere couche du premier plan dans les cartes | fg1 |
| foreground_2_layer | le nom de la seconde couche du premier plan dans les cartes | fg2 |
| type_layer | le nom de la couche type dans les cartes | type |
| info_layer | le nom de la couche info de l'arriere plan dans les cartes | info |
| type_tileset | le nom du tileset type dans les cartes | type |
| type_property | le nom de la propriété type dans le tileset type | type |
| info_tileset | le nom du tileset info dans les cartes | info |
| event_property | le nom de la propriété event dans le tileset info | event |
| npc_property | le nom de la propriété npc dans le tileset info | npc |
| tileset_property | le nom de la propriété tileset dans le tileset info | tileset |

## Exemple de 'config.cfg'
Voici un exemple de 'config.cfg'

	[Resources]
	tile_size : 32
	start_map : start
	player_tileset : character1
	tilesets_folder : tilesets
	maps_folder : maps
	fonts_folder : fonts
	font_file : Roboto-Black.ttf
	font_name : Roboto Bk
	dialog_tileset : dialog
	sounds_folder : sounds
	
	[Characters]
	character_speed = 0.1
	hitbox_start_x = 0.25
	hitbox_start_y = 0.7
	hitbox_size_x = 0.5
	hitbox_size_y = 0.3
	
	[Maps]
	background_1_layer : bg1
	background_2_layer : bg2
	foreground_1_layer : fg1
	foreground_2_layer : fg2
	type_layer : type
	info_layer : info
	type_tileset : type
	type_property : type
	info_tileset : info
	event_property : event
	npc_property : npc
	tileset_property : tileset

[Retour au sommaire](Documentation.md#sommaire)
