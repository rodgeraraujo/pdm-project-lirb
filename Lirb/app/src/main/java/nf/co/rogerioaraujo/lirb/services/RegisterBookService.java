package nf.co.rogerioaraujo.lirb.services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import nf.co.rogerioaraujo.lirb.database.MySQL;
import nf.co.rogerioaraujo.lirb.model.Book;
import nf.co.rogerioaraujo.lirb.model.User;

public class RegisterBookService extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ProgressDialog mProgressDialog;

    private Book book;
    private User user;
    private String msg;

    //MySQL instace
    MySQL mySQL = new MySQL();

    //query SQL
    public static final String INSERT =
            "INSERT INTO ZgIlDoFntY.book_data(user_name, user_email, password, name, dateRegister) VALUES(?,?,MD5(?),?,?)";


    public RegisterBookService(Context context, User user) {
        this.context = context;
        this.user = user;
    }

//    @Override
//    protected void onPreExecute() {
//        mProgressDialog = ProgressDialog.show(
//                context,
//                "",
//                "Please wait, getting database...");
//    }

    @Override
    protected String doInBackground(String... strings) {
        try {

            Connection connection = mySQL.newConnection();

            if (connection == null) {
                msg = "Connection goes wrong";
            } else {
                PreparedStatement pstmt = connection.prepareStatement(INSERT);

                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getEmail());
                pstmt.setString(3, user.getPassword());
                pstmt.setString(4, user.getName());
                pstmt.setDate(5, user.getDateRegister());
                pstmt.executeUpdate();

                msg = "User registered successfully";

                pstmt.close();
                connection.close();
                return msg;
            }
        } catch (SQLException e) {
            msg = "User not registered";
            e.printStackTrace();
        }
        return msg;
    }
}
