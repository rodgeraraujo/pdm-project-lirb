package nf.co.rogerioaraujo.lirb.services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import nf.co.rogerioaraujo.lirb.activity.RegisterActivity;
import nf.co.rogerioaraujo.lirb.database.MySQL;
import nf.co.rogerioaraujo.lirb.model.User;

public class RegisterUserService extends AsyncTask<String, String, String> {
    private ProgressDialog mProgressDialog;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private User user;
    private String msg;

    //query
    public static final String INSERT =
            "INSERT INTO sql10281687.user_data(username, email, password, name, dateRegister) VALUES(?,?,MD5(?),?,?)";

    //MySQL instace
    MySQL mySQL = new MySQL();

    public RegisterUserService(Context context, User user) {
        this.context = context;
        this.user = user;
    }



    @Override
    protected void onPreExecute() {
        mProgressDialog = ProgressDialog.show(
                context,
                "",
                "Please wait, getting database...");
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Connection connection = mySQL.newConnection();
            mProgressDialog.show();
            if (connection == null) {
                mProgressDialog.dismiss();
                msg = "Connection goes wrong";

            } else {
                mProgressDialog.setMessage("Connected...");
                mProgressDialog.dismiss();

                PreparedStatement pstmt = connection.prepareStatement(INSERT);

                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getEmail());
                pstmt.setString(3, user.getPassword());
                pstmt.setString(4, user.getName());
                pstmt.setDate(5, user.getDateRegister());
                pstmt.executeUpdate();

                mProgressDialog.setMessage("User registed!");
                mProgressDialog.dismiss();
                msg = "User registered successfully";
                return msg;
            }

            connection.close();
        } catch (SQLException e) {
            mProgressDialog.dismiss();
            msg = "User not registered";
            e.printStackTrace();
        }
        return "";
    }
}