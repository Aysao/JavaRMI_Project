Client : génère task avec code SQL, l'envoie au bag of tasks

BOT : génère un identifiant de tâche, l'affecte à la tâche et le retourne au client

BOT : Affecte une connection à la tâche et la met dans la liste des taches à faire

BOT : si pas de connection disponible, mettre la tâche dans une liste d'attente.

Worker: Récupère la tâche et l'exécute

Worker: Envoie le résultat au bot

Client: demande les résultats

BOT: si disponible, retourne les résultats.