//TODO: définir l'implémentation d'un worker

/*
 * Se connecte au serveur
 * récupère une tâche
 * se connecte au SGBD via la tâche
 * retourne le résultat via callback
 */

package server;
import java.rmi.Naming;

public class Worker {
    public static void main(String[] args) {
        IBagOfTask bot = null;
        try {
            bot = (IBagOfTask) Naming.lookup("serveurcompte");
            for (int i = 0; i < 2; i++) {
                System.out.println("index: " + i);
                ITask t = bot.getNext();
                System.out.println("got task");
                t.execute();
                System.out.println("executed task");
                bot.giveResult(t);
                System.out.println("callback done");
            }
        }
        catch(Exception e) {
            System.err.println("Erreur: " + e.getLocalizedMessage());
            System.exit(1);
        }
    }
}