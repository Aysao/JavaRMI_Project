package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Deque;

public class BagOfTask extends UnicastRemoteObject implements IBagOfTask {
    private Deque<ITask> tasks;
    private Deque<ITask> results;
    private final int MAX_CONNECTIONS = 10;
    private Deque<Statement> connexionPool;

    public BagOfTask() throws RemoteException {
        super();

        this.tasks = new ArrayDeque<ITask>();
        this.results = new ArrayDeque<ITask>();
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

    public void submitTask(ITask t) throws RemoteException {
        t.setConnection(connexionPool.pop());
        tasks.addLast(t);
        System.out.println("added task. length = " + tasks.size());
    }

    @Override
    public ResultSet getResult(ITask t) throws RemoteException {
        return null;
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