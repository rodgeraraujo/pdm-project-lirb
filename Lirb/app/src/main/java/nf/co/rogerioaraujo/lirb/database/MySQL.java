package nf.co.rogerioaraujo.lirb.database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    // database #1
//    private final String HOST = "remotemysql.com";
//    private final String DATA_BASE = "ZgIlDoFntY";
//    private final String PORT = "3306";
//
//    private final String USER = "ZgIlDoFntY";
//    private final String PASSWORD = "1C5oAUmCAV";

    //database #2
//    private final String HOST = "sql10.freesqldatabase.com";
//    private final String DATA_BASE = "sql10286563";
//    private final String PORT = "3306";
//
//    private final String USER = "sql10286563";
//    private final String PASSWORD = "TIIncHp29L";

    //database #3
//    private final String HOST = "remotemysql.com";
//    private final String DATA_BASE = "ZgIlDoFntY";
//    private final String PORT = "3306";
//
//    private final String USER = "ZgIlDoFntY";
//    private final String PASSWORD = "1C5oAUmCAV";

    private final String HOST = "192.168.1.139";
    private final String DATA_BASE = "lirb_data";
    private final String PORT = "3306";

    private final String USER = "root";
    private final String PASSWORD = "pass";

    private final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATA_BASE;

    public MySQL() {
    }

    public Connection newConnection() {
        try {
            Log.d("CONN", "1");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager
                    .getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        Log.d("CONN", "2");
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
