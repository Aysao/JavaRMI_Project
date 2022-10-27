import javax.sql.rowset.CachedRowSet;
import java.io.*;
import java.rmi.*;
import java.util.ArrayList;



public class Client {
    public static void main(String args[])
    {
        IBagOfTask srv = null;
        ArrayList<Integer> taskList = new ArrayList<Integer>();

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
            else if (str.contains("/test")) {
                try {
                    boolean out = srv.hasTaskAvailable();
                    System.out.println(out);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
                //trouver comment saisir une commande SQL ?
                //ou proposer des commandes prédéfinies ?
            else if (str.contains("/connexion")) {
                System.out.println("Entrez votre identifiant : ");
                try{
                    str = input.readLine();
                    Task t = new Task("SELECT * FROM COMPTE WHERE compte='"+str+"'");
                    System.out.println(t.toString());
                    int nTask = srv.submitTask(t);
                    taskList.add(nTask);
                    CachedRowSet resultat = srv.getResult(nTask);
                    //TODO: mettre a jour la lecture des résultats
                    while(resultat.next())
                    {
                        System.out.println("Id unique : " + resultat.getInt("_id"));
                        System.out.println("Nom de compte : "+resultat.getString("compte"));
                    }
                    taskList.remove(nTask);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            //pour créer une tâche SQL générique
            else if (str.contains("/createtask")) {
                System.out.println("Write your SQL command:");
                try {
                    String command = input.readLine();
                    Task t = new Task(command);
                    int nTask = srv.submitTask(t);
                    taskList.add(nTask);
                    System.out.println("Task submitted to server successfully");

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
                                System.out.println("Id unique : " + resultat.getInt("_id"));
                                System.out.println("Nom de compte : "+resultat.getString("compte"));
                            }
                            taskList.remove(nTask);
                        }
                        else {
                            System.out.println(" Task not completed, please try again later");
                        }
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("Invalid task number");
                }
            }

            //pour lister toutes les tâches en cours d'exécution.
            else if (str.contains("/tasks")) {
                System.out.println("Current active tasks: ");
                for (int taskID : taskList) {
                    System.out.println(taskID);
                }
                System.out.println("----");
            }

            else if (str.contains("/Inscription")) {
                System.out.println("Entrer votre identifiant : ");
                try{
                    str = input.readLine();
                    ITask t = new Task("INSERT INTO COMPTE VALUES ('"+str+"')");
                    System.out.println(t);
                    System.out.println(srv);
                    int nTask = srv.submitTask(t);
                    System.out.println("Inscription réussie...");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                
            }

            else if (str.contains("/Suppression")) {
                System.out.println("Entrer votre identifiant : ");
                try{
                    str = input.readLine();
                    Task t = new Task("DELETE FROM COMPTE WHERE compte='"+str+"')");
                    srv.submitTask(t);
                    System.out.println("Suppresion réussie...");
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
        System.out.println("/quit");
        System.out.println("/Connexion");
        System.out.println("/Create");
    }
}
