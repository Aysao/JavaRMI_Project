import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;


public class ActionCompteImpl extends UnicastRemoteObject implements ActionCompte{
    private HashMap<Integer, Compte> liste;
    private ConnexionPool connexionPool;

    public ActionCompteImpl() throws RemoteException
    {
        super();
        liste = new  HashMap<Integer, Compte>();
        connexionPool = new ConnexionPool();
        try{
            Statement statement = connexionPool.getConnection();
            ResultSet resultSet =  statement.executeQuery("SELECT * FROM COMPTE");
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                int solde = resultSet.getInt("SOLDE");
                System.out.println("id= " + id + "; solde= " + solde);
                liste.put(id, new Compte(solde));
            }
            connexionPool.setConnection(statement);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public ICompte getCompte(int id) throws RemoteException
    {
        if (liste.get(id) == null)
        {
            System.out.println("Création de compte ... ");
            liste.put(id, new Compte());
            try {
                Statement statement = connexionPool.getConnection();
                ResultSet resultSet =  statement.executeQuery("INSERT INTO COMPTE(id, SOLDE) VALUES ("+id+", 0)");
                connexionPool.setConnection(statement);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
        }

        

        return liste.get(id);
    }
}