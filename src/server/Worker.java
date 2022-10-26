//TODO: définir l'implémentation d'un worker

/*
 * Se connecte au serveur
 * récupère une tâche
 * se connecte au SGBD via la tâche
 * retourne le résultat via callback
 */

package server;
import java.rmi.Naming;

import static server.Serveur.SRV_NAME;

public class Worker extends Thread{
    IBagOfTask bot = null;
    int awaitDelay = 500;
    public Worker() {
        try {
            this.bot = (IBagOfTask) Naming.lookup(SRV_NAME);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("--- Worker active ---");
    }

    public void run() {
        if (this.bot != null) {
            while(true) {
                try {
                    if (this.bot.hasTaskAvailable()) {
                        ITask t = bot.getNext();
                        if (t != null) {
                            System.out.println("got task: " + t.getID());
                            t.execute();
                            System.out.println("executed task");
                            bot.giveResult(t);
                            System.out.println("callback sent");
                        }
                        this.awaitDelay = 500; //reset
                    }
                    else {
                        System.out.println("Awaiting server. delay: " + awaitDelay + " ms");
                        sleep(awaitDelay);
                        awaitDelay *= 2;
                    }
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            System.out.println("Bag of Task is null ???");
        }
    }
    public static void main(String[] args) {
        Worker w = new Worker();
        w.start();
    }
}