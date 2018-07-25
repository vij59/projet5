# MyERP

## Organisation du répertoire

*   `doc` : documentation
*   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `src` : code source de l'application


## Environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)



### Lancement

    cd docker/dev
    docker-compose up


### Arrêt

    cd docker/dev
    docker-compose stop


### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up


### Jenkins Installation
mettre en place jenkins sur le pc en 
téléchargeant Jenkins : https://jenkins.io/doc/pipeline/tour/getting-started/
Ouvrir le terminal dans le répertoire des téléchargements
lancer la commande :
java -jar jenkins.war --httpPort=8080
entrer l'adresse suivante dans le navigateur : 
http://localhost:8080

### Jenkins Configuration
Nouvel Item
donner un nom au projet
Construire un projet freestyle
Valider

cocher Github project et entrer l'adresse Git de votre projet
Gestion de code source : cocher Git
Build : ajouter une action :
1/Executer une ligne de commande batch Windows:
   cd docker/dev <br/>;
   docker-compose stop <br/>;
   docker-compose rm -f <br/>;
   docker-compose up -d <br/>;
   sleep 25s <br/>;
   cd .. <br/>;
   cd .. <br/>;
   cd src <br/>;
   mvn clean package -P test-consumer,test-business <br/>;

2/Executer une ligne de commande batch Windows:
   cd docker/dev
   docker-compose stop
   docker-compose rm -f

et enfin : Apply and Save
   
# Run Jenkins
sur la page du projet jenkins, choisir l'option : Lancer un build
cliquer sur la jauge du build en cours pour acceder à la console (console output)
Voilà le tour est joué
