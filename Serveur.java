import java.rmi.server.*;
import java.rmi.*;
import java.util.HashMap;
import java.sql.*;




public class Serveur
{

    public static void main(String[] args)
    {
        try{
            ActionCompteImpl srv = new ActionCompteImpl();
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