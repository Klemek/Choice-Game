# Documentation

## Introduction

Choice-game est un moteur de jeu personnalisable où vous pouvez jouer l'histoire que vous voulez.

## Sommaire
* [Créer une carte](Map_creation.md#créer-une-carte)
* [Les événements](Events.md#les-événements)
* [Le fichier de configuration](Config.md#le-fichier-de-configuration)
* [Compiler le jeu](Build.md#compiler-le-jeu)

## Bien démarrer
### Pré-requis
Vous avez nécéssairement besoin de Java Runtime Environnement pour lancer le jeu ([ici pour le télécharger](https://www.java.com/download/)) et un gestionnaire d'archive (comme WinRAR).
### 1. Téléchargez le code depuis le dépot
Vous avez besoin du code d'origine pour travailler. Pour ce faire, vous devez soit le compiler par vous même avec Gradle soit en téléchargeant un compilateur pour votre système d'exploitation venant de [dossier builds](https://github.com/kalioz/Choice-Game/tree/master/builds). Au final, vous avez un fichier .jar exécutable et vous pouvez jouer à la démonstration du contenu.
### 2. Créez vos cartes
Afin de créer vos propre carte vous aurez besoin de [Tiled](http://www.mapeditor.org/) un éditeur de carte gratuit qui va générer des cartes adaptées pour ce programme. Les cartes seront créées en deux étapes :

* Créer l'apparence générale de la carte : à lire [Créer une carte](Map_creation.md#créer-une-carte)
* Créer les évènements et les scripts qui serviront d'hisoire principale : à lire [Events](Events.md#les-événements)

### 3. Compilez le jeu avec votre histoire
L'étape finale est de charger toutes les ressources que vous avez dans le jeu et de les configurer. Vous avez besoin :

* Créer le fichier de configuration : à lire[The Configuration File](Config.md#le-fichier-de-configuration)
* Charger les ressources dans le jeu : à lire[Build the Game](Build.md#compiler-le-jeu)

### 4. C'est tout
Maintenant vous pouvez librement l'envoyer pour les autres joueurs pour qu'ils puissent y jouer.

## Résolution des erreurs
Vous pouvez voir le support [içi](Build.md#3-tester-les-ressources)
