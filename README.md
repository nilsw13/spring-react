# Projet SpringReact

## Introduction

Ce projet est un starter kit d'application web type Saas multi-tenant développée avec **Spring Boot** pour le backend et **React** avec **Vite** pour le frontend. L'application permet aux utilisateurs de se connecter via OAuth2 (Google) et gère les utilisateurs et leurs données de manière sécurisée.

## Technologies Utilisées

- **Backend** : Spring Boot
- **Frontend** : React, Vite
- **Base de données** : H2 (ou autre selon la configuration)
- **Gestion des styles** : Tailwind CSS
- **Gestion des requêtes HTTP** : Axios
- **Authentification** : OAuth2 avec Google

## Structure du Projet

### Backend

- **src/main/java/com/nilsw13/springreact** : Contient les classes principales de l'application.
  - **controller** : Gère les requêtes HTTP.
  - **service** : Contient la logique métier.
  - **model** : Représente les entités de la base de données.
  - **dto** : Utilisé pour transférer des données entre le backend et le frontend.
  - **config** : Configuration de la sécurité et des filtres.
  - **tenant** : Gestion du contexte du tenant.

### Frontend

- **src** : Contient le code source de l'application React.
  - **components** : Composants réutilisables de l'interface utilisateur.
  - **pages** : Pages de l'application.
  - **(context)** : Gestion de l'état d'authentification.
  - **(api)** : Configuration d'Axios pour les appels API.

## Installation

### Prérequis

- Java 23 ou supérieur
- Node.js 16 ou supérieur
- Maven 

### Étapes d'installation

1. **Clonez le dépôt** :
   ```bash
   git clone https://github.com/votre-utilisateur/springreact.git
   cd springreact
   ```

2. **Backend** :
   - Accédez au dossier backend et exécutez :
   ```bash
   cd src/main/java/com/nilsw13/springreact
   mvn spring-boot:run
   ```

3. **Frontend** :
   - Accédez au dossier frontend et installez les dépendances :
   ```bash
   cd springreact-frontend
   npm install
   ```

   - Démarrez le serveur de développement :
   ```bash
   npm run dev
   ```

## Utilisation

- Accédez à l'application via `http://localhost:5173`.
- Utilisez le bouton "Login" pour vous connecter avec votre compte Google.

## Fonctionnalités

- Authentification via Google OAuth2.
- Gestion des utilisateurs avec un système multi-tenant.
- Interface utilisateur réactive avec Tailwind CSS.
- Appels API sécurisés avec Axios.

