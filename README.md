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
- BCrypt pour le hashage des mots de passe

## Prérequis

- JDK 17
- MySQL 8.0+
- Maven 3.8+ (ou utiliser le wrapper Maven inclus)

## Installation et Configuration

### 1. Base de données MySQL

1. Connectez-vous à MySQL :

```bash
mysql -u root -p
```

2. Créez la base de données :

```sql
CREATE DATABASE IF NOT EXISTS chatop;
```

3. Structure de la base de données :

```sql
USE chatop;

-- Table Users
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255),
    full_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Table Rentals
CREATE TABLE rentals (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    surface INT,
    price INT,
    picture VARCHAR(255),
    description VARCHAR(255),
    owner_id INT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

-- Table Messages
CREATE TABLE messages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    rental_id INT,
    user_id INT,
    message VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (rental_id) REFERENCES rentals(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### 2. Configuration de l'application

1. Clonez le repository :

```bash
git clone https://github.com/0cl0ck/Chatop-backend
cd Chatop-backend
```

2. Configurez les variables d'environnement :

- Copiez le fichier `application.properties.example` vers `application.properties`

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

3. Modifiez le fichier `application.properties` avec vos informations :

```properties
# Configuration serveur
spring.application.name=chatop
server.port=3001

# Configuration JWT
security.jwt.secret-key=VOTRE_CLE_SECRETE_ICI
security.jwt.expiration-time=86400000

# Configuration base de données
spring.datasource.url=jdbc:mysql://localhost:3306/chatop?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC
spring.datasource.username=VOTRE_USERNAME
spring.datasource.password=VOTRE_PASSWORD

# Configuration Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Configuration des fichiers
file.upload-dir=uploads/images
app.base-url=http://localhost:3001
```

4. Créez le dossier pour les uploads :

```bash
mkdir -p uploads/images
```

### 3. Lancement de l'application

1. Compilez et lancez l'application :

```bash
./mvnw spring-boot:run
```

2. Vérifiez que l'application est lancée en accédant à :

- API : http://localhost:3001/api
- Documentation Swagger : http://localhost:3001/swagger-ui/index.html

## Documentation API

La documentation complète de l'API est disponible via Swagger UI à l'adresse :
http://localhost:3001/swagger-ui/index.html

### Points d'entrée principaux

- Authentication :

  - POST /api/auth/login : Connexion utilisateur
  - POST /api/auth/register : Inscription utilisateur
  - GET /api/auth/me : Profil utilisateur connecté

- Rentals :

  - GET /api/rentals : Liste des locations
  - GET /api/rentals/{id} : Détails d'une location
  - POST /api/rentals : Création d'une location
  - PUT /api/rentals/{id} : Modification d'une location

- Messages :

  - POST /api/messages : Envoi d'un message

- Users :
  - GET /api/user/{id} : Informations utilisateur

## Sécurité

L'API utilise JWT (JSON Web Token) pour l'authentification. Pour accéder aux endpoints protégés :

1. Obtenez un token via /api/auth/login
2. Incluez le token dans le header Authorization : `Bearer <votre_token>`

## Structure du Projet

```
src/main/java/com/chatop/
├── config/         # Configurations (Security, JWT, etc.)
├── controller/     # Contrôleurs REST
├── dtos/           # Objets de transfert de données
├── model/         # Entités JPA
├── repository/    # Repositories Spring Data
├── service/       # Logique métier
```
