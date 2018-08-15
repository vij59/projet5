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


Corrections : 
#Model : 
- classe EcritureComptable :  la méthode getTotalCredit() appelait la méthode getDebit() au lieu de getCredit().

#Consumer : 
Dans le RowMapper, ajout de la classe SequenceEcritureComptableRM
Dans l'interface ComptabiliteDao et dans classe ComptabiliteDaoImpl, ajout des méthodes :  
getLastSequenceEcritureComptable(int year,String code)
updateSequenceEcritureComptable
insertSequenceEcritureComptable
et modifications correspondantes dans le sqlContext.xml

#Business :
Classes ComptabiliteManagerImpl et ComptabiliteManagerImplTest modifiées : 
=> modification le la méthode addReference et de sa méthode de test en suivant la javadoc
==> mise en place de la RG5 dans la méthode checkEcritureComptableUnit

Tests : 
#model : 
- amélioration des tests unitaires de la classe EcritureComptableTest
- tests de toutes les autres classes de model
#business :
- tests unitaires dans le dossier test
- tests d'intégration dans le dossier test-business, utilisant le SpringRegistry et le BusinessTestCase ainsi que le TestInitSpring

#consumer :
- tests d'intégration mis en place
- datasourceContext configuré pour appeler la base de données

#Jacoco couverture des tests :
ajout du plugin jacoco dans le pom parent et dans jenkins afin de mesurer la couverture des tests.

### Jenkins Installation
mettre en place jenkins sur le pc en 
téléchargeant Jenkins : https://jenkins.io/doc/pipeline/tour/getting-started/
Ouvrir le terminal dans le répertoire des téléchargements
lancer la commande :
java -jar jenkins.war --httpPort=8080
entrer l'adresse suivante dans le navigateur : 
http://localhost:8080

### Jenkins Configuration
Nouvel Item<br/>
donner un nom au projet<br/>
Construire un projet freestyle<br/>
Valider<br/>

cocher Github project et entrer l'adresse Git de votre projet <br/>
Gestion de code source : cocher Git<br/>
Build : ajouter une action :<br/><br/>
1/Executer une ligne de commande batch Windows: <br/><br/>
   cd docker/dev <br/>
   docker-compose stop <br/>
   docker-compose rm -f <br/>
   docker-compose up -d <br/>
   sleep 10s <br/>
   cd .. <br/>
   cd .. <br/>
   cd src <br/>
   mvn clean install sonar:sonar -P test-consumer,test-business<br/>

2/Executer une ligne de commande batch Windows:<br/><br/>
   cd docker/dev<br/>
   docker-compose stop<br/>
   docker-compose rm -f<br/>

et enfin : Apply and Save<br/>
   
### Run Jenkins
sur la page du projet jenkins, choisir l'option : Lancer un build<br/>
cliquer sur la jauge du build en cours pour acceder à la console (console output)<br/>
Voilà le tour est joué<br/>
