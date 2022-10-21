package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class BagOfTask extends UnicastRemoteObject implements IBagOfTask {
    private Deque<ITask> tasks;
    private Deque<ITask> results;private const
    int MAX_CONNECTIONS = 10;
    private int nConnections = 0;
    private Deque<Statement> connexionPool;

    public BagOfTask() throws RemoteException {
        super();

        this.tasks = new ArrayDeque<ITask>();
        this.results = new ArrayDeque<ITask>();
        liste = new HashMap<Integer, Compte>();
        this.connexionPool = new ArrayDeque<Statement>();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@eluard:1521:ense2022", "mj662957",
                    "mj662957");
            for (int i = 0; i < nmax; i++) {
                connexionQueue.add(con.createStatement());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitTask(ITask t) throws RemoteException {
        t.setConnection(connexionPool.pop());
        tasks.addLast(t);
        System.out.println("added task. length = " + tasks.size());
    }

    public ITask getNext() {
        System.out.println("task sent to worker");
        return tasks.pop();
    }

    public void giveResult(ITask t) throws RemoteException {
        connexionPool.add(t.getConnection());
        results.addLast(t);
    }
}