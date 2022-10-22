package server;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        try {
            this.connection.clearBatch();
        }
        catch(Exception e) {
            System.err.println(e);
        }
        return this.connection;
    }

    public void execute() {
        try {
            this.resultat = this.connection.executeQuery(this.SQL);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public ResultSet getResult() {
        return this.resultat;
    }
}