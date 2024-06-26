# Back-end

La partie back-end a été réalisée avec *Java Spring Boot* comme convenu. La base de donnée retenue est MongoDB. Le lancement de l'application (base de données comprise) se fait via des conteneurs *docker*.

# Prérequis

- Système d'exploitation de type Debian
- Docker installé (procédure d'installation [ici](https://docs.docker.com/engine/install/))
- Make installé (commande d'installation : `sudo apt install make`)

# Contenu

- [back](back) : Le projet back. Dans ce dossier se trouve un fichier [Dockerfile](back/Dockerfile) qui permet de builder l'image docker.
- [db](db) : Dossier contenant le script de création de la base de données. Ce script est utilisé lors de l'instanciation du conteneur MongoDB. J'ai copié les produits présents dans [products.json](front/src/assets/products.json) pour peupler la base de données.
- [front](front) : Le projet front. J'ai utilisé le fichier [Dockerfile](front/Dockerfile) présent pour builder l'image docker.
- [postman](postman) : Dossier contenant la collection de requêtes Postman pour effectuer les tests fonctionnels de l'API.
- [docker-compose.yml](docker-compose.yml) : Le script permettant de déployer toute l'application.
- [Makefile](Makefile) : Le fichier contenant la liste des commandes make

# Lancement de l'application

Lancer la commande `make run` pour déployer l'application entière.

Lors du déploiement, les conteneurs suivants sont lancés dans cet ordre :

- mongodb : initialisé avec le script présent dans [db](db)

- back : une image `alten/back` est créée à partir du fichier [back/Dockerfile](back/Dockerfile) puis utilisée pour instancier le conteneur

- front : une image `alten/front` est créée à partir du fichier [front/Dockerfile](front/Dockerfile) puis utilisée pour instancier le conteneur

# Swagger UI

Une fois l'application lancée, il est possible de consulter l'api swagger à l'adresse suivante : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

# Tests postman

Une fois l'application lancée, lancer la commande `make test` afin de lancer les tests postman. Les résultats s'affichent dans la console.


# Arrêt de l'application

Pour arrêter le déploiement, lancer `make stop`.