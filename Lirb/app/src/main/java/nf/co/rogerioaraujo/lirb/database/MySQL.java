package nf.co.rogerioaraujo.lirb.database;

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
