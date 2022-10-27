import javax.sql.rowset.CachedRowSet;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class BagOfTask extends UnicastRemoteObject implements IBagOfTask {
    private Deque<ITask> tasks;
    private HashMap<Integer, CachedRowSet> results;
    private int taskCounter;
    private final String URL = "jdbc:oracle:thin:@eluard:1521:ense2022";
    private final String UID = "mj662957";
    private final String password = "mj662957";

    public BagOfTask() throws RemoteException {
        super();

        this.tasks = new ArrayDeque<ITask>();
        this.results = new HashMap<Integer, CachedRowSet>();
    }

    public int submitTask(ITask t) throws RemoteException {
        System.out.println("test");
        taskCounter++;
        t.setID(taskCounter);
        t.setParameters(this.URL, this.UID, this.password);
        tasks.addLast(t);
        System.out.println("[INFO] task " + taskCounter + " ready. in queue: " + tasks.size());
        return taskCounter;
    }

    public CachedRowSet getResult(int ID) throws RemoteException {
        CachedRowSet out = this.results.get(ID);
        this.results.remove(ID);
        return out;
    }

    public ITask getNext() {
        System.out.println("[INFO] task dispatched");
        return tasks.pop();
    }

    public boolean hasTaskAvailable() {
        return !tasks.isEmpty();
    }

    public boolean isTaskCompleted(int taskID) { return results.containsKey(taskID);}

    public void giveResult(ITask t) throws RemoteException {
        results.put(t.getID(), t.getResult());
        System.out.println("[INFO] Received result for task " + t.getID() + ". in queue: " + results.size());
    }
}