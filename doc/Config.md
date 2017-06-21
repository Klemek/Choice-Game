# The Configuration File
## Introduction
The configuration file is named 'config.cfg', you will write in it every specifications for your game and about the maps details. Here is an example 'config.cfg', every properties are detailled.
## Example 'config.cfg'
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

## Properties list

### Resources
| Property | Info | Default value |
|-|-|-:|
| tile_size | the tile size in every tilesets | 32 |
| start_map | the name of the first map file |  |
| player_tileset | the name of the player tileset at start |  |
| tilesets_folder | the name of the tilesets folder  | tilesets |
| maps_folder | the name of the maps folder | maps |
| fonts_folder | the name of the fonts folder | fonts |
| font_file | the name of your custom font file |  |
| font_name | the name of your custom font in the system | Consolas |
| dialog_tileset | the name of the dialog tileset |  |

### Characters
| Property | Info | Default value |
|-|-|-:|
| character_speed | the character speed in blocks/3ms (*30 = blocks/s) | 0.1 |
| hitbox_start_x | the hitbox of the player in a 1x1 square | 0.25 |
| hitbox_start_y | the hitbox of the player in a 1x1 square | 0.7 |
| hitbox_size_x | the hitbox of the player in a 1x1 square | 0.5 |
| hitbox_size_y | the hitbox of the player in a 1x1 square | 0.3 |

### Maps
| Property | Info | Default value |
|-|-|-:|
| background_1_layer | the name of the first background layer in the maps | bg1 |
| background_2_layer | the name of the second background layer in the maps | bg2 |
| foreground_1_layer | the name of the first foreground layer in the maps | fg1 |
| foreground_2_layer | the name of the second foreground layer in the maps | fg2 |
| type_layer | the name of the type layer in the maps | type |
| info_layer | the name of the info background layer in the maps | info |
| type_tileset | the name of the type tileset in the maps | type |
| type_property | the name of the type property in the type tileset | type |
| info_tileset | the name of the info tileset in the maps | info |
| event_property | the name of the event property in the info tileset | event |


[Back to Table of Contents](Documentation.md#table-of-contents)