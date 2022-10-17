package server;

import java.rmi.*;

public interface IBagOfTask extends Remote
{
    public ICompte getCompte(int id) throws RemoteException;

    public void submitTask(ITask t) throws RemoteException;
    public ITask getNext() throws RemoteException;
    public void giveResult(ITask t) throws RemoteException;
}