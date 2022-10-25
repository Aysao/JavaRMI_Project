package client;

import server.IBagOfTask;
import server.ITask;
import server.Task;

import java.io.*;
import java.rmi.*;
import java.sql.ResultSet;

import static server.Serveur.SRV_NAME;


public class Client {
    public static void main(String args[])
    {
        IBagOfTask srv = null;

        try{
            srv = (IBagOfTask) Naming.lookup(SRV_NAME);
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
                //trouver comment saisir une commande SQL ?
                //ou proposer des commandes prédéfinies ?
            else if (str.contains("/Connexion")) {
                System.out.println("Entrer votre identifiant : ");
                try{
                    str = input.readLine();
                    Task t = new Task("SELECT * FROM COMPTE WHERE compte='"+str+"'");
                    int nTask = srv.submitTask(t);
                    ResultSet resultat = srv.getResult(nTask);
                    while(resultat.next())
                    {
                        System.out.println("Id unique : " + resultat.getInt("_id"));
                        System.out.println("Nom de compte : "+resultat.getString("compte"));
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                
            }

            else if (str.contains("/Inscription")) {
                System.out.println("Entrer votre identifiant : ");
                try{
                    str = input.readLine();
                    ITask t = new Task("INSERT INTO COMPTE VALUES ('"+str+"')");
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
