# Test weathery app

Ce projet est un test technique pour Stellantis. C'est une application Android développée en Kotlin.
Elle a pour objectif d'avoir les informations météo d'une ville choisie.  

## Description de l'application
## Analyse fonctionnelle

### Comportement de l'application
L'application contient 3 écrans :
- Un écran (principal) affichant la liste des villes recherchées
- Un écran permettant de rechercher une ville et l'ajouter à la liste
- Un écran affichant les infos météo d'une ville

Lorsque l'on recherche une ville et qu'on la sélectionne, on tombe sur l'écran météo.
Cette ville est, dans le même temps, ajoutée à la liste des villes.

## Analyse technique
### Architecture
La structure de l'application est en clean architecture MVVM 
![Alt text](https://github.com/Asiri75/WeatheryApp/raw/main/architecture.png "app architecture")

### Explications de certains choix
#### Utilisation d'un autre endpoint Weather API
L'endpoint proposé par la documentation renvoyait une erreur 401 malgré l'utilisation d'une clé api valide.
Pour finir le test dans les temps, j'ai dû utiliser un autre endpoint fonctionnel.

#### Tests unitaires légers
J'accorde beaucoup d'importances aux TU malgré le manque de tests sur ce test.
Ma dernière grosse mission était un gros projet multi-module à fort traffic, et le code coverage était de 52%
Chaque feature devait être accompagnée de test unitaires.

#### Design léger
Je suis le genre d'artiste qui a besoin d'une inspiration (Zeplin, Figma).
Avec cette inspiration, je peux reproduire au pixel près le besoin UI.


### Choix des librairies

#### Gestion des dépendances
Pour simplifier la gestion des dépendances entre les fichiers, nous utilisons Dagger Hilt. 
Elle permet d'injecter les dépendances de classe sans avoir à créer de nouvelles instances pour chaque utilisation.

#### Persistance des données
Pour garder les données en interne, nous utilisons la librairie **Room**. Elle permet de simplifier les requêtes vers la base de données, alléger la quantité de code et s'intègre bien dans le concept d'AAC.

#### Récupération des données de l'API
Pour faire une requête HTTP vers le l'API public, nous utilisons la librairie **Retrofit** développée par Square.
Cette librairie a également l'avantage de simplifier l'appel vers le serveur. Accompagnée de la librairie **GSON converter**, elle permet également de simplifier la conversion de l'objet JSON reçu.

#### Recherche de ville
Pour simplifier la recherche de ville, nous utiliser la librairie de Google **Place SDK**.
La recherche se fait ainsi sur un écran lancé par la librairie.
Ensuite, elle nous retourne un objet Place contenant le nom de la ville et sa localisation.

#### Autres librairies utilisées
#### Kotlin Flow
Pour la transmission de data venant de l'api et la base de données

## En savoir plus sur Anas
### Caméléon
J'ai travaillé dans différents contextes :
- Petite app interne, app à fort traffic
- Start-up, grand groupe, entreprise R&D

### Familier avec l'environnement automobile
Ce n'est pas indiqué sur mon CV, mais j'ai travaillé 4 mois en 2017 pour Continental dans un projet de tableau de bord de Citroen Cactus.

### À l'aise en équipe pluridisciplinaire
Ayant des expérience en backend, iOS et QA, je peux facilement communiquer avec l'équipe en dehors des devs Android.

### Mentor Android
J'accompagne des étudiants Android depuis 2018.
Ce qui me permet d'être à jour sur les nouveautés d'Android, les bonnes pratiques et les nouvelles fonctionnalités
