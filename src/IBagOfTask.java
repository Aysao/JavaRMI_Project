import javax.sql.rowset.CachedRowSet;
import java.rmi.*;

public interface IBagOfTask extends Remote {
    // commandes client
    public int submitTask(ITask t) throws RemoteException;
    public CachedRowSet getResult(int ID) throws RemoteException;
    public boolean isTaskCompleted(int taskID) throws RemoteException;

    // Commandes Worker
    public ITask getNext() throws RemoteException;

    public void giveResult(ITask t) throws RemoteException;

    public boolean hasTaskAvailable() throws RemoteException;
}