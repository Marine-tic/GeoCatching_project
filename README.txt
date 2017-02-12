Team name :

B-LPDAM-GeoCatching

Team members : 

- De Robert Vincent
- Mennella Loïc
- Paroux Jérémy
- Fafournoux Ludiwine
- Daufeld Laura
- Landraudie Marine

Description of contents:

For Android:
	- Developpement en Android natif
	- A ce jour, aucune installation
	- Minimum API : 19

For the web service:
	- Pour build et run le projet : 
		- Télécharger Maven à ce lien : https://maven.apache.org/download.cgi
		- Installer Maven (Suivre ce tutoriel : https://maven.apache.org/install.html)
		- Effectuer cette suite de commandes :
			- Build : mvn package
			- Run : mvn jetty:run

			- URL pour appeler le service de verifcation du token: http://localhost:8080/GeoCatchingWebService/validate/{leTokenAVerifier}
			- URL pour appeler le service d'ajout d'utilisateur: http://localhost:8080/GeoCatchingWebService/ListPlayers/Add (name,longitude,latitude)
			- URL pour appeler le service de deconnection d'utilisateur: http://localhost:8080/GeoCatchingWebService/ListPlayers/Deconnection/{name}
			- URL pour appeler le service de modification d'utilisateur: http://localhost:8080/GeoCatchingWebService/ListPlayers/UpdatePosistion/{name} (longitude,latitude)
			- URL pour appeler le service de la liste des utilisateur connecté: http://localhost:8080/GeoCatchingWebService/ListPlayers/List
			- URL pour appeler le service de la création d'une partie: http://localhost:8080/GeoCatchingWebService/Partie/AddPartie/(name, dateFin) (Date sous la forme jj/mm/aaaa exemple pour le 2 juin 2017 2/06/2016)
			- URL pour appeler le service de la création de zone et de terrain: http://localhost:8080/GeoCatchingWebService//Partie/AddTerrain/(partie, coordonnee, type) (type = "0" pour terrain et 1 pour zone , chaque coordonnées séparées par un -)
			- URL pour appeler le service de la liste des parties: http://localhost:8080/GeoCatchingWebService/Partie/ListPartie
			- URL pour appeler le service de la liste des terrain: http://localhost:8080/GeoCatchingWebService/Partie/GetTerrain/{name} (name correspond a la partie, les zones sont dedans aussi)