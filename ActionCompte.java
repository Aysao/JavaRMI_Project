import java.rmi.*;

public interface ActionCompte extends Remote
{
    public ICompte getCompte(int id) throws RemoteException;
}