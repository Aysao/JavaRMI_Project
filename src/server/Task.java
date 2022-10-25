package server;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;

//TODO: faire l'implémentation de ITask
public abstract class AbstractTask implements ITask {
    private String SQL;
    private String URL;
    private String UID;
    private String password;
    private Connection connection;
    private Statement statement;
    private int ID;

    public AbstractTask(String SQL) {
        this.SQL = SQL;
    }

    public void setParameters(String URL, String UID, String password) {
        this.URL = URL;
        this.UID = UID;
        this.password = password;
    }

    public void setID(int ID) { this.ID = ID;}
    public int getID() {return this.ID;}

    private void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.connection = DriverManager.getConnection(this.URL, this.UID, this.password);
        this.statement = this.connection.createStatement();
    }

    private void destroyConnection() throws SQLException {
        this.statement.close();
        this.connection.close();
    }

    abstract public void execute();

    public ResultSet getResult() {
        return this.result;
    }
}