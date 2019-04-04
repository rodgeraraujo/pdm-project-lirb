package nf.co.rogerioaraujo.lirb.services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import nf.co.rogerioaraujo.lirb.database.MySQL;

public class DatabaseSelectService extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ProgressDialog mProgressDialog;

//    private String SELECT = "SELECT\n" +
//            "   count(book_data.book_title) AS quantidade\n" +
//            "FROM\n" +
//            "   user_data\n" +
//            "INNER JOIN\n" +
//            "   book_data ON user_data.user_id = book_data.user_id_fk\n" +
//            "WHERE\n" +
//            "user_data.user_name = '"+ user_id + "' OR user_data.user_email = '" + user_id + "'";

    private String user_id;
    private String msg;

    //MySQL instace
    MySQL mySQL = new MySQL();

    public DatabaseSelectService(Context context, String user_id) {
        this.context = context;
        this.user_id = user_id;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {

            Connection connection = mySQL.newConnection();

            if (connection == null) {
                msg = "Problema com a conexão :(";
            } else {

                String SELECT = "SELECT\n" +
                        "   user_data.user_id AS id," +
                        "   count(book_data.book_title) AS quantidade\n" +
                        "FROM\n" +
                        "   user_data\n" +
                        "INNER JOIN\n" +
                        "   book_data ON user_data.user_id = book_data.user_id_fk\n" +
                        "WHERE\n" +
                        "user_data.user_name = '"+ user_id + "' OR user_data.user_email = '" + user_id + "'";

                Statement st = (Statement) connection.createStatement();
                ResultSet rs = st.executeQuery(SELECT);

                while (rs.next()){
                    String id = String.valueOf(rs.getInt(1));
                    String quantidade = String.valueOf(rs.getInt(2));
                    msg =  id + "-" + quantidade;
                }

                connection.close();
                return msg;
            }
        } catch (SQLException e) {
            msg = "Houve algum problema tente novamente";
            e.printStackTrace();
        }
        return msg;
    }
}