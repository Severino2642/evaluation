# A Faire CRM

## 1 - SPRING BOOT - JAVA

- **Configuration du projet**

  - Configuration de google cloud

    - [X] Activation des API (Drive, Calendar, Gmail)

    - Configuration de l'identifiant client
      - [X] Acces au donnes du clients

  - [X] Configuration de "application.properties"
  - [X] Installation des dependances
  - [X] Run projet
- **Test projet**

  - [X] Fonctionnalite meteo
    - Erreur : ne s'affiche pas, Solution : Recuperation de cle api dans "https://www.weatherapi.com/"
  - [X] Authentification avec google en tant qu'employee
    - Erreur : 500 , Solution : Reglage de "application.properties" et "google cloud" pour le token
  - [X] Activation des services googles

  - Test CRUD
    - [X] Service google
      - Erreur : Calendar erreur 500, Solution : Modification du fuseau horaire
    - [X] Autres cruds (Login : Employe, Ticket, ...)
- **Reconstruction du MCD**
- Creation de repository git

  - Merge projet
- **Fonctionnalites :**

  - [X] Fonctionnalités de reinitialisation de données
  - [X] Import de fichier sur projet
  - [ ] Generation de donnees

  * Gestion de budget et de depense de customer

    * [X] MCD

    * Budget

      * [X] Ajouter cote admin
      * [X] Lister pour customer et admin
      * [X] Total budget par customer
    * Depenses

      * Lead
        * [X] Ajouter cote admin
        * [X] Lister pour customer et admin
          * [X] Verification si dépassement de budget
          * [X] Message de confirmation si dépassement de budget
        * [X] Total depense par customer
      * Ticket
        * [X] Ajouter cote admin
        * [X] Lister pour customer et admin
          * [X] Verification si dépassement de budget
          * [X] Message de confirmation si dépassement de budget
        * [X] Total depense par customer

    * [X] Controller RestApi pour le projet dotnet

    * [X] Message d'alert si taux d'alerte est atteint ["Le client a atteint 0% de son budget (Reste : 10000)"] 

## 2 - DOTNET - C#

- [X] Initialisaion du projet
- [X] Creation de repository git
  - Merge projet

- **Fonctionnalites :**
  - [X] Login admin (Manager) par RestApi avec le projet java
  - [X] Graphique sur les donnees Budgets des customers
  - [X] Graphique sur les donnees Lead Depense des customers
  - [X] Graphique sur les donnees Ticket Depense des customers
  - [X] Donnees de type total sur Budgets des customers
  - [X] Donnees de type total sur Lead Depense des customers
  - [X] Donnees de type total sur Ticket Depense des customers
  - [X] Modification du montant Lead des customers
  - [X] Modification du montant Ticket des customers
  - [X] Suppression de Lead des customers
  - [X] Suppression de Ticket des customers
  - [X] Configuration du taux d'alert (en %) sur le budget
