# Chatop

Application de mise en relation entre locataires et propriétaires.

## Technologies utilisées

- Java 17
- Spring Boot 3.3.4
- Spring Security
- JWT
- MySQL
- Maven

## Prérequis

- JDK 17
- MySQL
- Maven (ou utiliser le wrapper Maven inclus)

## Installation

1. Cloner le repository

```
git clone https://github.com/0cl0ck/Chatop-backend
```

2. Configurer la base de données

- Créer une base de données MySQL nommée `chatop`
- Copier `application.properties.example` vers `application.properties`
- Modifier les informations de connexion dans `application.properties`

3. Lancer l'application

```
./mvnw spring-boot:run
```

## Documentation API

La documentation Swagger est disponible à l'adresse : `http://localhost:8005/swagger-ui.html`

## Structure du projet

```
src/main/java/com/chatop/
├── controller/ # Contrôleurs REST
├── service/ # Logique métier
├── repository/ # Accès aux données
├── model/ # Entités
├── security/ # Configuration sécurité
└── config/ # Configurations
```
