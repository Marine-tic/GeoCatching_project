Team name :

B-LPDAM-GeoCatching

Team members : 

- De Robert Vincent
- Mennella Lo�c
- Paroux J�r�my
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
		- T�l�charger Maven � ce lien : https://maven.apache.org/download.cgi
		- Installer Maven (Suivre ce tutoriel : https://maven.apache.org/install.html)
		- Effectuer cette suite de commandes :
			- Build : mvn package
			- Run : mvn jetty:run

			- URL pour appeler le service de verifcation du token: http://localhost:8080/GeoCatchingWebService/validate/{leTokenAVerifier}
			- URL pour appeler le service d'ajout d'utilisateur: http://localhost:8080/GeoCatchingWebService/ListPlayers/Add/{name}/{latitude}/{longitude}
			- URL pour appeler le service de deconnection d'utilisateur: http://localhost:8080/GeoCatchingWebService/ListPlayers//Deconnection/{name}
			- URL pour appeler le service de modification d'utilisateur: http://localhost:8080/GeoCatchingWebService/ListPlayers//UpdatePosistion/{name}/{latitude}/{longitude}
			- URL pour appeler le service de la liste des utilisateur connect�: http://localhost:8080/GeoCatchingWebService/ListPlayers//List
			- URL pour appeler le service de la cr�ation d'une partie: http://localhost:8080/GeoCatchingWebService//Partie/AddPartie/{name}/{dateFin}
			- URL pour appeler le service de la cr�ation de zone et de terrain: http://localhost:8080/GeoCatchingWebService//Partie/AddTerrain/{type}/{partie}/{coordonnee} (type = "0" pour terrain etautre pour zone)