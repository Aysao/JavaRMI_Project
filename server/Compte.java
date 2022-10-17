package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Compte extends UnicastRemoteObject implements ICompte{
    private volatile int solde;

    public Compte() throws RemoteException
    {
        super();
        this.solde = 0;
    }

    public Compte(int solde) throws RemoteException{
        super();
        this.solde = solde;
    }

    public synchronized int add(int amount) throws RemoteException
    {
        this.solde += amount;
        return this.solde;
    }

    public synchronized int remove(int amount) throws RemoteException
    {
        if ((this.solde - amount) < 0)
        {
            return -1;
        }
        else
        {
            return this.solde -= amount;
        }
    }

    public int getSolde() throws RemoteException
    {
        return this.solde;
    }
}