# Java RMI, Bag Of Tasks & Persistance

## Enonce

L'objectif est de soumettre, à partir de clients multiples, des requêtes à un système qui va les exécuter
en parallèle sur un ou plusieurs SGBD. On commencera avec un seul SGBD.
Les clients envoient à un objet distribué des requêtes SQL dont la durée d'exécution peut être longue.
L'objet distribué va les répartir à des workers qui ensuite font un call-back vers le client pour lui délivrer les résultats.

### Questions:

1. Faire un schéma général avec des étapes
2. Réaliser un diagramme de classes
3. Descriptif des choix techniques réalisés
4. Un scénario de test et des instructions pour exécuter le code.

## Commandes de build

Compiler le client: `ant client-jar`

Compiler le serveur: `ant server-jar`

## Commandes d'exécution

Lancer le serveur: `ant run-server`

Lancer le client: `ant run-client`