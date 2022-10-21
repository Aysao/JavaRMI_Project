package server;

import java.rmi.*;
import java.sql.ResultSet;

public interface IBagOfTask extends Remote {
    // commandes client
    public void submitTask(ITask t) throws RemoteException;

    public ResultSet getResult(ITask t) throws RemoteException;

    // Commandes Worker
    public ITask getNext() throws RemoteException;

    public void giveResult(ITask t) throws RemoteException;
}