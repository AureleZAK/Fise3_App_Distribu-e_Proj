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

**Docker**,**Docker Compose** et **Maven** installés sur votre machine

## Base de donnée

La persistence du projet se fait sur une base de donnée PostgeSQL. La configuration de la base de données est faite dans le fichier application.properties.
la base de données est accessible via *jdbc:postgresql://localhost:5432/appdistribproj*
et on s'y connecte avec le nom : appdistribuser et le mot de passe : appdistribpass
Elle est connfigure en create-drop donc s'initialise et se detruit à chaque lancement.

## Configuration et démarrage du projet

### 1. Cloner le projet

Clonez le projet en utilisant l'une des commandes suivantes :

```bash
git clone https://github.com/AureleZAK/Fise3_App_Distribuee_Proj.git
```
ou
```bash
git clone git@github.com:AureleZAK/Fise3_App_Distribuee_Proj.git
```

### 2. Se rendre dans le repertoire de l'application
```bash
cd app
```

### 3. Construire le projet
On utilise clean install pour utiliser la target dans le docker-compose
```bash
mvn clean install
```

### Construire le docker et lancer l'application avec docker-compose
```bash
docker-compose build
docker-compose up
```

Le docker-compose va créer et démarrer les conteneurs pour l'application et la base de données.
L'application sera accessible sur **http://localhost:8080**.
PostgreSQL sera accessible sur **localhost:5432**.

## Initialisation de l'application

L'application est générée de base avec 5 équipe et 7 joueurs définis dans la classe **DataInit**

Au lancement de l'applicaiton, les tests unitaires des services et des controllers de Team et Player se lancent automatiquement

## Utilisation de l'application 

Vous pouvez interagir avec l'API via des outils comme [Postman](https://www.postman.com/) ou [cURL](https://curl.se/).

### Gestion des équipes :
  
- **Récupérer toutes les équipes**  
  `GET http://localhost:8080/teams`  

- **Récupérer une équipe par ID**  
  `GET http://localhost:8080/teams/{id}`  

- **Ajouter une équipe**  
  `POST http://localhost:8080/teams`
  Attention, on peut ajouter à une équipe des joueurs ou pasais on ne peut pas indiquer des joueurs avec un ID qui n'existe pas
  Exemple de requête pour ajouter une nouvelle équipe :
  ```json
  POST http://localhost:8080/teams
  {
    "name": "FC Barcelona",
    "players": [{"id": 1}, {"id": 7}],
    "sport": "Football",
    "stadium": "Camp Nou",
    "city": "Barcelona",
    "coach": "Xavi Hernandez"
  }
  
- **Modifier une équipe**
`PUT http://localhost:8080/teams/{id}`
Attention, on peut ajouter à une équipe des joueurs ou pasais on ne peut pas indiquer des joueurs avec un ID qui n'existe pas
Exemple de requête pour modifier une équipe :
```json
PUT http://localhost:8080/teams/1
{
  "name": "FC barcelona",
  "players": [{"id": 8}, {"id": 7}],
  "sport": "Football",
  "stadium": "Camp Nou",
  "city": "Barcelona",
  "coach": "Xavi Hernandez"
}
```

- **Supprimer une équipe**
`POST http://localhost:8080/teams/{id}`

### Gestion des joueurs :

- **Récupérer tous les joueurs**  
  `GET http://localhost:8080/players`  

- **Récupérer un joueur par ID**  
  `GET http://localhost:8080/players/{id}`  

- **Ajouter un joueur**
`POST http://localhost:8080/players`
Attention, on peut ajouter un joueur sans équipe mais on ne peut pas indiquer une équipe avec un ID qui n'existe pas
Exemple de requête pour un ajouter un nouveau joueur :
```json
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
```

- **Modifier un joueur**
`PUT http://localhost:8080/players/{id}`
Attention, on peut modifier un joueur sans équipe mais on ne peut pas indiquer une équipe avec un ID qui n'existe pas
Exemple de requête pour modifier un joueur :
```json
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
```

- **Supprimer un joueur**
`POST http://localhost:8080/players/{id}`
