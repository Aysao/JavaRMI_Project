package server;
import java.sql.Statement;
import java.sql.*;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;

public class ConnexionPool {
    private final int nmin = 0;
    private final int nmax = 10;
    private int compteur;
    private Queue<Statement> connexionQueue;

    public ConnexionPool() {
        compteur = 0;
        connexionQueue = new LinkedTransferQueue<Statement>();
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@eluard:1521:ense2022", "mj662957","mj662957");
            for (int i = 0; i < nmax; i++){
                connexionQueue.add(con.createStatement());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public Statement getConnection(){
        compteur++;
        if(compteur <= nmax)
            return connexionQueue.poll();
        return null;
    }

    public void setConnection(Statement stmt) {
        connexionQueue.add(stmt);
        compteur--;
    }

}
