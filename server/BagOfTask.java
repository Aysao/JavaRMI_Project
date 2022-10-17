package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;


public class BagOfTask extends UnicastRemoteObject implements IBagOfTask{
    private HashMap<Integer, Compte> liste;
    private ConnexionPool connexionPool;
    private Deque<ITask> tasks;
    private Deque<ITask> results;

    public BagOfTask() throws RemoteException
    {
        super();

        this.tasks = new ArrayDeque<ITask>();
        this.results = new ArrayDeque<ITask>();
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

    public Compte getCompte(int id) throws RemoteException
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

    public void submitTask(ITask t) throws RemoteException {
        tasks.addLast(t);
        System.out.println("added task. length = " + tasks.size());
    }

    public ITask getNext() {

        System.out.println("task sent to worker");
        return tasks.pop();
    }

    public void giveResult(ITask t) throws RemoteException {
        results.addLast(t);

        System.out.println("total: " + results.size());
    }
}