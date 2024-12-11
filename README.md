# Chatop - API de Location Immobilière

Application backend permettant la mise en relation entre locataires et propriétaires, offrant une API REST sécurisée pour la gestion des locations immobilières.

## Fonctionnalités Principales

- Authentification sécurisée avec JWT
- Gestion des utilisateurs (locataires et propriétaires)
- Gestion des annonces de location
- Système de messagerie entre utilisateurs
- Upload et gestion des images des biens
- Documentation API avec Swagger

## Stack Technique

- Java 17
- Spring Boot 3.3.4
- Spring Security avec JWT
- MySQL
- Maven
- Swagger/OpenAPI pour la documentation
- Lombok
- BCrypt pour le hashage des mots de passe

## Prérequis

- JDK 17
- MySQL 8.0+
- Maven 3.8+ (ou utiliser le wrapper Maven inclus)

## Installation et Configuration

1. **Cloner le repository**

```bash
git clone https://github.com/0cl0ck/Chatop-backend
cd Chatop-backend
```

2. **Configuration de la base de données**

- Créer une base de données MySQL nommée `chatop`
- Copier le fichier `application.properties.example` vers `application.properties`
- Configurer les paramètres de connexion dans `application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chatop
spring.datasource.username=votre_username
spring.datasource.password=votre_password
```

3. **Lancer l'application**

```bash
./mvnw spring-boot:run
```

L'application sera accessible sur `http://localhost:3001`

## Documentation API

La documentation Swagger de l'API est disponible à l'adresse :
`http://localhost:3001/swagger-ui/index.html`

## Structure du Projet

```
src/main/java/com/chatop/
├── config/         # Configurations (Security, JWT, etc.)
├── controller/     # Contrôleurs REST
├── dto/           # Objets de transfert de données
├── model/         # Entités JPA
├── repository/    # Repositories Spring Data
├── service/       # Logique métier
└── security/      # Configuration sécurité et JWT
```

## Endpoints Principaux

- `/api/auth/*` - Authentification et gestion des utilisateurs
- `/api/rentals/*` - Gestion des locations
- `/api/messages/*` - Système de messagerie
- `/api/user/*` - Informations utilisateurs

## Sécurité

- Authentification via JWT
- Protection CORS configurée
- Endpoints sécurisés avec Spring Security
- Hashage des mots de passe avec BCrypt
