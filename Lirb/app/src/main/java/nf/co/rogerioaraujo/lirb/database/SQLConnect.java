package nf.co.rogerioaraujo.lirb.database;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import nf.co.rogerioaraujo.lirb.model.User;

public class SQLConnect extends AsyncTask<String, String, String> {
    private ProgressDialog mProgressDialog;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private User user;
    private String msg;

    //MySQL
    MySQL mySQL = new MySQL();

    public SQLConnect(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = ProgressDialog.show(context, "",
                "Please wait, getting database...");
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Connection connection = mySQL.newConnection();

//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            connection = DriverManager
//                    .getConnection(
//                            mySQL.getURL(),
//                            mySQL.getUSER(),
//                            mySQL.getPASSWORD()
//                    );

            mProgressDialog.show();
            if (connection == null) {
                msg = "Connection goes wrong";
            } else {
                msg = "Connection success";
//                String query = "SELECT * FROM `book_data` WHERE 1";
//                String insert = "INSERT INTO `book_data` (`bookId`, `title`, `pages`, `edition`, `pubDate`, `author`, `thumbnail`, `sinopse`, `userId`) " +
//                                "VALUES ('2', 'Teste 2', '430', 'Vol. 1', '2019-03-04', 'Author teste 3', 'https://i.imgur.com/L6ByHQ6.png', 'Lorem ipsum dolor set amet...', '1')";
//                Statement stmt = connection.createStatement();
//                stmt.execute(insert);
                return "";
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}