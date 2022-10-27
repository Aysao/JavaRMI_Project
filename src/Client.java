import javax.sql.rowset.CachedRowSet;
import java.io.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.Random;


public class Client {
    public static void main(String args[])
    {
        IBagOfTask srv = null;
        ArrayList<Integer> taskList = new ArrayList<Integer>();
        Random r = new Random();

        try{
            srv = (IBagOfTask) Naming.lookup(Serveur.SRV_NAME);
        }catch(Exception e)
        {
            System.err.println("Erreur, impossible de trouver le serveur | " + e);
            System.exit(1);
        }


        boolean stop = false;

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        while (!stop)
        {
            System.out.println("[==================================================]");
            System.out.println("[INFO] Utilisez /help pour avoir une liste des commandes disponibles");

            String str = "";
            try{
                str = input.readLine();
            }
            catch(Exception e)
            {
                System.out.println("Erreur pendant la saisie: " + e);
            }

            if (str.contains("/quit"))
            {
                stop = true;
            }
            else if (str.contains("/help"))
            {
                help();
            }
            else if (str.contains("/testconnection")) {
                try {
                    boolean out = srv.hasTaskAvailable();
                    System.out.println(out);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            else if (str.contains("/connexion")) {
                System.out.println("Entrez votre identifiant : ");
                try{
                    str = input.readLine();
                    Task t = new Task("SELECT * FROM COMPTE WHERE login='"+str+"'");
                    int nTask = srv.submitTask(t);
                    taskList.add(nTask);
                    System.out.println("Commande " + nTask + " envoyée au serveur. utilisez /checkresult pour avoir les résultats");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            //pour créer une tâche SQL générique
            else if (str.contains("/createtask")) {
                System.out.println("Entrez une commande SQL:");
                try {
                    String command = input.readLine();
                    Task t = new Task(command);
                    int nTask = srv.submitTask(t);
                    taskList.add(nTask);
                    System.out.println("Commande " + nTask + " envoyée au serveur. utilisez /checkresult pour avoir les résultats");
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }

            //pour vérifier si la tâche est finie et afficher le résultat si elle est complétée.
            else if (str.contains("/checkresult "))
            {
                String taskNumber = str.substring(13);
                int nTask = Integer.parseInt(taskNumber);
                if (taskList.contains(nTask)) {
                    try {
                        if (srv.isTaskCompleted(nTask)) {
                            CachedRowSet resultat = srv.getResult(nTask);
                            //TODO: mettre a jour la lecture des résultats
                            while(resultat.next())
                            {
                                System.out.println("Id unique : " + resultat.getInt("id"));
                                System.out.println("Nom de compte : "+resultat.getString("login"));
                            }
                            taskList.remove((Object) nTask); //sinon le considère comme un index
                        }
                        else {
                            System.out.println("commande toujours en cours de traitement, réessayez plus tard");
                        }
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("Numéro de commande invalide");
                }
            }

            //pour lister toutes les tâches en cours d'exécution.
            else if (str.contains("/tasks")) {
                System.out.println("Commandes en cours de traitement: ");
                for (int taskID : taskList) {
                    System.out.println(taskID);
                }
                System.out.println("----");
            }

            else if (str.contains("/inscription")) {
                System.out.println("Entrez votre identifiant : ");
                try{
                    String login = input.readLine();
                    int id = r.nextInt();
                    ITask t = new Task("INSERT INTO COMPTE VALUES ("+id+", '"+login+"')");
                    int nTask = srv.submitTask(t);
                    taskList.add(nTask);
                    System.out.println("Commande " + nTask + " envoyée au serveur. utilisez /checkresult pour avoir les résultats");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
            else if (str.contains("/suppression")) {
                System.out.println("Entrez l'identifiant unique du compte à supprimer : ");
                try{
                    str = input.readLine();
                    Task t = new Task("DELETE FROM COMPTE WHERE id='"+str+"'");
                    int nTask = srv.submitTask(t);
                    taskList.add(nTask);
                    System.out.println("Commande " + nTask + " envoyée au serveur. utilisez /checkresult pour avoir les résultats");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }

        }
    }

    public static void help()
    {
        System.out.println("Commandes disponibles: ");
        System.out.println("/quit => Quitter le client");
        System.out.println("/connexion => récupérer un compte");
        System.out.println("/inscription => créer un compte");
        System.out.println("/suppression => supprimer un compte");
        System.out.println("/createtask => saisir une commande SQL");
        System.out.println("/tasks => lister les commandes en cours de traitement");
        System.out.println("/checkresult <task> => vérifier l'avancement d'une tâche et récupérer le résultat si disponible");
    }
}
