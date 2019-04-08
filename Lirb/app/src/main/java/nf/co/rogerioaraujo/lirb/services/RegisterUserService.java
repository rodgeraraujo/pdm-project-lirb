package nf.co.rogerioaraujo.lirb.services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import nf.co.rogerioaraujo.lirb.database.MySQL;
import nf.co.rogerioaraujo.lirb.model.User;

public class RegisterUserService extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private ProgressDialog mProgressDialog;

    private User user;
    private String msg;

    //MySQL instace
    MySQL mySQL = new MySQL();

    //query SQL
    public static final String INSERT =
            "INSERT INTO `lirb_data`.`user_data` (`user_name`, `user_email`, `user_pass`, `user_fullName`, `user_dateRegister`) " +
                    "VALUES(?, ?,MD5(?), ?, ?)";

    public RegisterUserService(Context context, User user) {
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
            Log.d("CONN", "3");
            if (connection == null) {
                Log.d("CONN", "4");
                msg = "Connection goes wrong";
            } else {
                Log.d("CONN", "5");
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
            Log.d("CONN", "6");
            msg = "User not registered";
            e.printStackTrace();
        }
        return msg;
    }
}