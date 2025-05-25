# Gestion-pressing
Le pressing est un service raffiné qui prend soin de vos vêtements délicats grâce au nettoyage à sec, pour une tenue toujours impeccable et élégante


# Fonctionnalités

- Authentification sécurisée avec rôles (`admin`, `caissier`, `employé`)
- Gestion des clients
- Enregistrement et suivi des commandes de vêtements
- Paiements partiels ou complets par plusieurs moyens (cash, mobile money, carte)
- Suivi du stock de produits de nettoyage avec alertes
- Historique des actions (journal d'activité)
- Interface utilisateur intuitive (Swing ou JavaFX)

---

# Technologies utilisées

- Java SE (JDK 11+)
- Java Swing / JavaFX (interface utilisateur)
- MySQL / MariaDB (base de données relationnelle)
- JDBC (connexion à la base de données)
- Apache NetBeans (IDE de développement)
- Hashage des mots de passe (bcrypt ou SHA-256)

# Configuration requise

- JDK 11 ou plus
- Apache NetBeans (ou tout IDE Java)
- Serveur MySQL ou MariaDB
- Fichier `mysql-connector-java-x.x.x.jar` dans `/lib`
- Base de données créée à partir du fichier : `pressing.sql`


# Installation

1. **Cloner le projet**  
   ```bash
   git clone https://github.com/votre-utilisateur/Gestion-pressing.git
   cd Gestion-pressing
   git init

   ```

2. **Configurer la base de données**
   - Importer le fichier `pressing.sql` dans MySQL
   - Modifier le fichier `resources/config.properties` avec vos identifiants MySQL

3. **Lancer l’application**
   - Ouvrir le projet avec NetBeans
   - Lancer `Main.java`

## 📁 Structure du projet

```bash
Gestion-pressing/
│
├── src/
│   └── com/Gestion-pressing/
│       ├── model/                  # Représentation de la base de données
│       │   ├── Utilisateur.java
│       │   ├── Client.java
│       │   ├── Article.java
│       │   ├── Commande.java
│       │   ├── DétailCommande.java
│       │   ├── Paiement.java
│       │   ├── StockProduit.java
│       │   └── JournalActivite.java
│       │
│       ├── dao/                    # Accès aux données (JDBC / Hibernate)
│       │   ├── UtilisateurDAO.java
│       │   ├── ClientDAO.java
│       │   ├── ArticleDAO.java
│       │   ├── CommandeDAO.java
│       │   ├── DétailCommandeDAO.java
│       │   ├── PaiementDAO.java
│       │   ├── StockProduitDAO.java
│       │   └── JournalActiviteDAO.java
│       │
│       ├── contrôleur/             # Logique métier / traitement
│       │   ├── AuthController.java
│       │   ├── ClientController.java
│       │   ├── CommandeController.java
│       │   ├── PaiementController.java
│       │   └── StockController.java
│       │
│       ├── view/                   # Interfaces graphiques Swing ou JavaFX
│       │   ├── LoginView.java
│       │   ├── DashboardView.java
│       │   ├── ClientsView.java
│       │   ├── CommandesView.java
│       │   ├── PaiementsView.java
│       │   └── StockView.java
│       │
│       └── utils/                  # Outils divers (validation, hachage, etc.)
│           ├── DatabaseConnection.java
│           ├── PasswordUtils.java
│           └── DateUtils.java
│
├── lib/                           # Bibliothèques externes (.jar)
├── resources/                     # Fichiers de config, images, etc.
│   └── config.properties
└── Main.java                      # Point d'entrée de l'application

# Equipe de developpement

-Mulho-MABIALA
-Megui Rocha

# Licence

Ce projet est sous licence MIT


