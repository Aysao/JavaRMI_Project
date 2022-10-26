package server;

import javax.sql.rowset.*;
import java.sql.*;

//TODO: faire l'implémentation de ITask
public class Task implements ITask {
    private String SQL;
    private String URL;
    private String UID;
    private String password;
    private CachedRowSet result;
    private int ID;

    public Task(String SQL) {
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

    }

    private void destroyConnection() throws SQLException {

    }

    public void execute() {
        try {
            System.out.println("Begin task execution");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection c = DriverManager.getConnection(this.URL, this.UID, this.password);
            Statement s = c.createStatement();
            this.result = RowSetProvider.newFactory().createCachedRowSet();
            System.out.println("Connection to DB OK");
            s.execute(this.SQL);
            this.result.populate(s.getResultSet());
            System.out.println("Query executed");
            s.close();
            c.close();
        }
        catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public CachedRowSet getResult() {return this.result;}
}