# Fise3_App_Distribuée_Proj

Ce projet est une application Spring Boot utilisant JPA (Java Persistence API) pour gérer des équipes sportives et des joueurs. Il permet de créer, lire, mettre à jour et supprimer des équipes et des joueurs dans une base de données PostgreSQL.

L'application expose des API REST pour interagir avec les données des équipes et des joueurs, et utilise un front-end basé sur Thymeleaf pour afficher les informations.

## Technologies utilisées

- **Spring Boot** avec **JPA** et **Hibernate** (pour la gestion de la base de données)
- **PostgreSQL** (pour la base de données)
- **Thymeleaf** (pour le rendu des pages HTML côté serveur)
- **Docker** (pour la gestion des conteneurs)

## Fonctionnalités

- Créer une équipe et y associer des joueurs
- Lister toutes les équipes et leurs joueurs
- Modifier une équipe ou un joueur existant
- Supprimer une équipe ou un joueur
- Afficher des informations sur chaque joueur et chaque équipe (nom, sport, coach, etc.)

## Prérequis

**Docker** et **Docker Compose** installés sur votre machine

## Configuration et démarrage du projet

1. git clone https://github.com/AureleZAK/Fise3_App_Distribuee_Proj.git (ou git@github.com:AureleZAK/Fise3_App_Distribuee_Proj.git)

2. cd app/src/main/resources

3. docker-compose up

Le docker-compose va créer et démarrer les conteneurs pour l'application et la base de données.
L'application sera accessible sur **http://localhost:8080**.
PostgreSQL sera accessible sur **localhost:5432**.

## Initialisation de l'application

L'application est générée de base avec 5 équipe et 7 joueurs définis dans la classe **DataInit**

## Utilisation de l'application 

On peut attaquer notre api via un outil comme Postman par exemple

- Gestion des équipes :
  
*GET http://localhost:8080/teams* Pour récuperer toutes les équipes
*Get http://localhost:8080/teams/{id}* Pour récuperer les informations d'une équipe
*POST http://localhost:8080/teams* Pour ajouter une équipe
exemple :
POST http://localhost:8080/teams
{
  "name": "FC Barcelona",
  "sport": "Football",
  "stadium": "Camp Nou",
  "city": "Barcelona",
  "coach": "Xavi Hernandez"
}

*PUT http://localhost:8080/teams/{id}* Pour modifier une équipe
exemple :
PUT http://localhost:8080/teams/1
{
  "name": "FC Barcelona",
  "sport": "Football",
  "stadium": "Camp Nou",
  "city": "Barcelona",
  "coach": "**Nouveau Coach**"
}

*POST http://localhost:8080/teams/{id}* Pour supprimer une équipe

- Gestion des joueurs :
  
*GET http://localhost:8080/players* Pour recupérer tous les joueurs
*GET http://localhost:8080/players/{id}* Pour recuperer les informations d'un joueur
*POST http://localhost:8080/players Pour ajouter un joueur
exemple :
POST http://localhost:8080/players
{
  "firstName": "Lionel",
  "lastName": "Messi",
  "nationality": "Argentine",
  "age": "36",
  "height": "1.70",
  "weight": "72",
  "sport": "Football",
  "team": { "id": 1 },
  "number": "10"
}

*PUT http://localhost:8080/players/{id}* Pour modifier un joueur
exemple :
PUT http://localhost:8080/players/1
{
  "firstName": "Lionel",
  "lastName": "Messi",
  "nationality": "Argentine",
  "age": "36",
  "height": "1.70",
  "weight": "72",
  "sport": "Football",
  "team": { "id": 2 },
  "number": "**Nouveau numéro**"
}

*POST http://localhost:8080/players/{id}* Pour supprimer un joueur

