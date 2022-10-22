package server;

import java.rmi.*;
import java.sql.ResultSet;

public interface IBagOfTask extends Remote {
    // commandes client
    int submitTask(ITask t) throws RemoteException;
    ResultSet getResult(int ID) throws RemoteException;

    // Commandes Worker
    ITask getNext() throws RemoteException;

    void giveResult(ITask t) throws RemoteException;

    boolean hasTaskAvailable() throws RemoteException;
}