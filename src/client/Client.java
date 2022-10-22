package client;

import server.IBagOfTask;
import server.ICompte;
import java.io.*;
import java.rmi.*;


public class Client {
    public static void main(String args[])
    {
        IBagOfTask srv = null;


        try{
            srv = (IBagOfTask) Naming.lookup("serveurcompte");
        }catch(Exception e)
        {
            System.err.println("Erreur, impossible de trouver le serveur | " + e);
            System.exit(1);
        }

        boolean stop = false;
        int idCompte = -1;
        ICompte compte = null;

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
            else if (str.contains("/depot") && idCompte != -1)
            {
                try{
                    int amount = Integer.parseInt(str.substring(7));
                    int solde = compte.add(amount);
                    if (solde == -1)
                    {
                        System.err.println("Erreur pendant le depot");
                    }
                    else 
                    {
                        System.out.println("dépot réussi. solde: " + solde);
                    }
                }
                catch(Exception e) {
                    if (e instanceof NumberFormatException)
                    {
                        System.err.println("Erreur, format  de la commande invalide");
                    }
                    else 
                    {
                        System.err.println("Erreur: " + e);
                    }
                }
                
            }
            else if (str.contains("/retrait") && idCompte != -1)
            {
                try {
                    int amount = Integer.parseInt(str.substring(9));
                    int solde = compte.remove(amount);
                    if (solde == -1)
                    {
                        System.err.println("Erreur pendant le retrait");
                    }
                    else 
                    {
                        System.out.println("retrait réussi. solde: " + solde);
                    }
                }
                catch(Exception e){
                    if (e instanceof NumberFormatException)
                    {
                        System.err.println("Erreur, format de la commande invalide");
                    }
                    else 
                    {
                        System.err.println("Erreur: " + e);
                    }
                }
                
            }
            else if (str.contains("/getcompte") && idCompte != -1)
            {
                System.out.println("identifiant du compte: " + idCompte);
            }

            else if (str.contains("/Connexion"))
            {
                int amount = Integer.parseInt(str.substring(11));
                System.out.println("identifiant du compte: " + amount);
                idCompte = amount;
                try {
                    //compte = srv.getCompte(amount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
            else if (str.contains("/setcompte"))
            {
                try {
                    idCompte = Integer.parseInt(str.substring(11));
                    //compte = srv.getCompte(idCompte);
                }
                catch(Exception e)
                {
                    if (e instanceof NumberFormatException)
                    {
                        System.err.println("Erreur, format invalide");
                    }
                    else 
                    {
                        System.err.println("Erreur: " + e);
                    }
                }
            }
            else if (str.contains("/solde") && idCompte != -1)
            {
                try{
                    int solde = compte.getSolde();
                    System.out.println("Il reste " + solde + " sur le compte");
                }catch(Exception e)
                {
                    System.err.println("Erreur pendant l'envoi de la commande");
                    e.printStackTrace();
                }
            }
            else if (str.contains("/help"))
            {
                help();
            }

            if (idCompte == -1)
            {
                System.out.println("il n'y a pas de compte courant");
                System.out.println("utilisez /setcompte pour en définir un");
            }
        }
    }

    public static void help()
    {
        System.out.println("/depot nombre");
        System.out.println("/retrait nombre");
        System.out.println("/solde");
        System.out.println("/getcompte");
        System.out.println("/setcompte id");
        System.out.println("/quit");
    }
}
