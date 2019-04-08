package nf.co.rogerioaraujo.lirb.services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import nf.co.rogerioaraujo.lirb.activity.PublishEpubActivity;
import nf.co.rogerioaraujo.lirb.database.MySQL;

public class DatabaseSelectService extends AsyncTask<String, String, String> {

    private ProgressDialog pDialog;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    private String user_id;
    private String msg;
    private int type;

    //MySQL instace
    MySQL mySQL = new MySQL();

    public DatabaseSelectService(Context context, String user_id, int type) {
        this.context = context;
        this.user_id = user_id;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pDialog = new ProgressDialog(context);
        pDialog.setTitle("Publicando...");
        pDialog.setMessage("Verificando informações...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.setInverseBackgroundForced(true);
        pDialog.show();
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {

            Connection connection = mySQL.newConnection();

            if (connection == null) {
                msg = "Problema com a conexão :(";
            } else {

                if (this.type == 1) {

                    String query = "SELECT\n" +
                            "   count(book_data.book_title) AS quantidade\n" +
                            "FROM\n" +
                            "   user_data\n" +
                            "INNER JOIN\n" +
                            "   book_data ON user_data.user_id = book_data.user_id_fk\n" +
                            "WHERE\n" +
                            "user_data.user_name = '"+ user_id + "' OR user_data.user_email = '" + user_id + "'";
                    msg = executeQuery(connection, query);
                    return msg;

                } else if (this.type == 2) {

                    String query = "SELECT user_data.user_id AS id\n" +
                            "FROM user_data " +
                            "WHERE user_data.user_name = '"+ user_id + "' OR user_data.user_email = '" + user_id + "'";
                    msg = executeQuery(connection, query);
                    return msg;

                }

                connection.close();
                return "";
            }
        } catch (SQLException e) {
            msg = "Houve algum problema tente novamente";
            e.printStackTrace();
        }
        return msg;
    }

    private String executeQuery(Connection connection, String query) throws SQLException {

        Statement st = (Statement) connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()){
            String id = String.valueOf(rs.getInt(1));
            st.close();
            return id;
        }
        st.close();
        return "";
    }
}