import java.rmi.*;

public interface ICompte extends Remote{
    int add(int amount) throws RemoteException;
    int remove(int amount) throws RemoteException;
    int getSolde() throws RemoteException;
}
