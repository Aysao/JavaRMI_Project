# Java RMI, Bag Of Tasks & Persistance

## Enonce

L'objectif est de soumettre, à partir de clients multiples, des requêtes à un système qui va les exécuter
en parallèle sur un ou plusieurs SGBD. On commencera avec un seul SGBD.
Les clients envoient à un objet distribué des requêtes SQL dont la durée d'exécution peut être longue.
L'objet distribué va les répartir à des workers qui ensuite font un call-back vers le src.client pour lui délivrer les résultats.

### Questions:

1. Faire un schéma général avec des étapes
2. Réaliser un diagramme de classes
3. Descriptif des choix techniques réalisés
4. Un scénario de test et des instructions pour exécuter le code.

## Commandes de build

Compiler le src.client: `ant src.client-jar`

Compiler le serveur: `ant src.server-jar`

## Commandes d'exécution

Lancer le serveur: `ant run-src.server`

Lancer le src.client: `ant run-src.client`

Client : génère task avec code SQL, l'envoie au bag of tasks
BOT : génère un identifiant de tâche, l'affecte à la tâche et le retourne au client
BOT : Affecte une connection à la tâche et la met dans la liste des taches à faire
BOT : si pas de connection disponible, mettre la tâche dans une liste d'attente.
Worker: Récupère la tâche et l'exécute
Worker: renvoie un message au bag of task ? ou au client direct ?


[ ] Refonte du client
[x] Vérifier commandes client bag of tasks
[x] refaire le worker
[ ] sortir un jeu de test