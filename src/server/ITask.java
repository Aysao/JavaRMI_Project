//TODO: faire l'interface de la tâche pour les workers

package server;

import javax.sql.rowset.CachedRowSet;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;

public interface ITask extends Serializable {
    public void execute();
    public void setParameters(String URL, String UID, String password);
    public CachedRowSet getResult();

    public void setID(int ID);
    public int getID();
}