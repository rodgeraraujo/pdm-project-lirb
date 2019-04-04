package nf.co.rogerioaraujo.lirb.database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

//    private final String HOST = "remotemysql.com";
//    private final String DATA_BASE = "ZgIlDoFntY";
//    private final String PORT = "3306";
//
//    private final String USER = "ZgIlDoFntY";
//    private final String PASSWORD = "1C5oAUmCAV";

    private final String HOST = "sql10.freesqldatabase.com";
    private final String DATA_BASE = "sql10286563";
    private final String PORT = "3306";

    private final String USER = "sql10286563";
    private final String PASSWORD = "TIIncHp29L";

//    private final String HOST = "remotemysql.com";
//    private final String DATA_BASE = "ZgIlDoFntY";
//    private final String PORT = "3306";
//
//    private final String USER = "ZgIlDoFntY";
//    private final String PASSWORD = "1C5oAUmCAV";

    private final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATA_BASE;

    public MySQL() {
    }

    public Connection newConnection() {
        Log.d("CONEXÃO", "6");
        try {
            Log.d("CONEXÃO", "7.1");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager
                    .getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            Log.d("CONEXÃO", "7.2");
            e.printStackTrace();
        }

        Log.d("CONEXÃO", "7.3");
        return null;
    }

    public String getURL() {
        return URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }
}
