import java.rmi.*;

public class Serveur
{
    public static String SRV_NAME = "bagoftasks";

    public static void main(String[] args)
    {
        try{
            IBagOfTask srv = new BagOfTask();
            String nom = SRV_NAME;
            Naming.rebind(nom, srv);
            System.out.println("[INFO] Server registered to RMI");

        }
        catch (Exception e)
        {
            System.err.println("Erreur: " + e);
        }
    }

    
}