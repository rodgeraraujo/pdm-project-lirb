package nf.co.rogerioaraujo.lirb.services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import nf.co.rogerioaraujo.lirb.database.MySQL;

public class SearchBookURIService extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ProgressDialog mProgressDialog;

    private String book_id;
    private String msg;

    //MySQL instaceb
    MySQL mySQL = new MySQL();

    public SearchBookURIService(Context context, String book_id) {
        this.context = context;
        this.book_id = book_id;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {

            Connection connection = mySQL.newConnection();

            if (connection == null) {
                msg = "Problema com a conexão :(";
            } else {

                String SELECT = "SELECT published_data.reader_url AS url FROM published_data WHERE book_fk = "+ book_id;

                System.out.println(SELECT);

                Statement st = (Statement) connection.createStatement();
                ResultSet rs = st.executeQuery(SELECT);

                if (rs.next()){
                    String id = rs.getString(1);
                    msg =  id;
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