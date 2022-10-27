//TODO: définir l'implémentation d'un worker

/*
 * Se connecte au serveur
 * récupère une tâche
 * se connecte au SGBD via la tâche
 * retourne le résultat via callback
 */

import java.rmi.Naming;

public class Worker extends Thread{
    IBagOfTask bot = null;
    public Worker() {
        try {
            this.bot = (IBagOfTask) Naming.lookup(Serveur.SRV_NAME);
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
                    }
                    else {
                        System.out.println("Awaiting server");
                        sleep(500);
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