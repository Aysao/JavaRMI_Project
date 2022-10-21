//TODO: faire l'interface de la tâche pour les workers

package server;

public interface ITask {
    public void execute();

    public ResultSet getResult();

    public void setConnection(Statement s);

    public Statement getConnection();
}