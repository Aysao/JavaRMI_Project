package server;

import java.sql.ResultSet;
import java.sql.Statement;

//TODO: faire l'implémentation de ITask
public class Task implements ITask {
    private String SQL;
    private Statement connection;
    private ResultSet resultat;

    public Task(String SQL) {
        this.SQL = SQL;
    }

    public void setConnection(Statement s) {
        this.connection = s;
    }

    public Statement getConnection() {
        this.connection.clearBatch();
        return this.connection;
    }

    public void execute() {
        this.resultat = this.connection.executeQuery(this.SQL);
    }

    public ResultSet getResult() {
        return this.resultat;
    }
}