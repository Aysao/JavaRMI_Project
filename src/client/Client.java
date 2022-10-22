package client;

import server.IBagOfTask;

import java.io.*;
import java.rmi.*;

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
            else if (str.contains("/execute")) {
                //trouver comment saisir une commande SQL ?
                //ou proposer des commandes prédéfinies ?
            }

        }
    }

    public static void help()
    {
        System.out.println("/quit");
    }
}
