package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class    BagOfTask extends UnicastRemoteObject implements IBagOfTask {
    private Deque<ITask> tasks;
    private Deque<ITask> waitingConnection;
    private HashMap<Integer, ResultSet> results;
    private final int MAX_CONNECTIONS = 10;
    private Deque<Statement> connexionPool;
    private volatile int taskCounter = 0;

    public BagOfTask() throws RemoteException {
        super();

        this.tasks = new ArrayDeque<ITask>();
        this.results = new HashMap<Integer, ResultSet>();
        this.connexionPool = new ArrayDeque<Statement>();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@eluard:1521:ense2022", "mj662957",
                    "mj662957");
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                connexionPool.add(con.createStatement());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int submitTask(ITask t) throws RemoteException {
        taskCounter++;
        if (connexionPool.isEmpty()) {
            t.setID(taskCounter);
            waitingConnection.addLast(t);
            System.out.println("task waiting for connection. in waiting queue: " + waitingConnection.size());
        }
        else {
            t.setConnection(connexionPool.pop());
            t.setID(taskCounter);
            tasks.addLast(t);
            System.out.println("added task. in queue: " + tasks.size());
        }
        return taskCounter;
    }

    @Override
    public ResultSet getResult(int ID) throws RemoteException {
        ResultSet out = this.results.get(ID);
        this.results.remove(ID);
        return out;
    }

    public ITask getNext() {
        System.out.println("task sent to worker");
        return tasks.pop();
    }

    public boolean hasTaskAvailable() {
        return !tasks.isEmpty();
    }

    public void giveResult(ITask t) throws RemoteException {
        connexionPool.add(t.getConnection());
        if (!waitingConnection.isEmpty()) {
            ITask toTransfer = waitingConnection.getFirst();
            toTransfer.setConnection(connexionPool.getFirst());
            tasks.addLast(toTransfer);
        }
        results.put(t.getID(), t.getResult());
    }
}