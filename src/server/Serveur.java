package server;

import java.rmi.*;

public class Serveur
{

    public static void main(String[] args)
    {
        try{
            IBagOfTask srv = new BagOfTask();
            String nom = "serveurcompte";
            Naming.rebind(nom, srv);
            System.out.println("--- serveur enregistré ---");

        }
        catch (Exception e)
        {
            System.err.println("Erreur: " + e);
        }
    }

    
}