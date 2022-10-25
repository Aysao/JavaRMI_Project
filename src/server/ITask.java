//TODO: faire l'interface de la tâche pour les workers

package server;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;

public interface ITask extends Serializable {
    public void execute();

    public ResultSet getResult();

    public void setConnection(Statement s);

    public Statement getConnection();

    public void setID(int ID);
    public int getID();
}