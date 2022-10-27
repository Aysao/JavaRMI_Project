# Java RMI, Bag Of Tasks & Persistance

## Enonce

L'objectif est de soumettre, à partir de clients multiples, des requêtes à un système qui va les exécuter
en parallèle sur un ou plusieurs SGBD. On commencera avec un seul SGBD.
Les clients envoient à un objet distribué des requêtes SQL dont la durée d'exécution peut être longue.
L'objet distribué va les répartir à des workers qui ensuite font un call-back vers le src.client pour lui délivrer les résultats.

## Commandes de build

Compiler le client: `ant client-jar`

Compiler le serveur: `ant server-jar`

Compiler le worker: `ant worker-jar`

## Commandes d'exécution

Lancer le serveur: `ant run-server`

Lancer le client: `ant run-client`

Lancer le worker: `ant run-worker`

## Exécuter une commande SQL

Commandes prédéfinies:

- `/inscription` : ajoute une ligne dans la table
- `/connexion` : retourne une ligne dans la table
- `/suppression` : supprime une ligne dans la table
- `/createtask` : permet de saisir une commande SQL brute
- `/tasks` : liste les commandes SQL en cours d'exécution
- `/checkresult <task>` : récupère le résultat de la tâche si disponible
- `/help` : affiche l'aide dans l'application

