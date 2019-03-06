package nf.co.rogerioaraujo.lirb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {


    /* host biz.nf (don't work)
    private final String HOST = "185.176.43.97"; // fdb26.biz.nf
    private final String DATA_BASE = "2975601_lirb";
    private final String PORT = "3306";

    private final String USER = "2975601_lirb";
    private final String PASSWORD = "lirb_321";
    */

    // host freesqldatabase
    private final String HOST = "sql10.freesqldatabase.com";
    private final String DATA_BASE = "sql10281687";
    private final String PORT = "3306";

    private final String USER = "sql10281687";
    private final String PASSWORD = "fnbIiXbANd";
    private final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATA_BASE;

    public MySQL() {
    }

    public Connection newConnection() {
        try {
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
