package nf.co.rogerioaraujo.lirb.services;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nf.co.rogerioaraujo.lirb.database.MySQL;
import nf.co.rogerioaraujo.lirb.model.User;

public class UserInfoService extends AsyncTask<User, User, User> {
    private ProgressDialog pDialog;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    private String info;

    private User userInfo;

    //MySQL instace
    MySQL mySQL = new MySQL();

    public UserInfoService(Context context, String info) {
        this.context = context;
        this.info = info;
    }

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//
//        pDialog = new ProgressDialog(context);
//        pDialog.setTitle("Atualizar");
//        pDialog.setMessage("Salvando novas informações...");
//        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pDialog.setIndeterminate(true);
//        pDialog.setCancelable(false);
//        pDialog.setInverseBackgroundForced(true);
//        pDialog.show();
//    }

    @Override
    protected User doInBackground(User... users) {
        try {

            Connection connection = mySQL.newConnection();

            if (connection == null) {
                userInfo = new User();
            } else {
                String query = "SELECT user_name, user_fullName, user_description, user_pic  FROM user_data WHERE user_name = '" + info + "' OR user_email = '" + info + "'";
                Statement st = (Statement) connection.createStatement();
                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    String username = rs.getString(1);
                    String fullname = rs.getString(2);
                    String description = rs.getString(3);
                    String image = rs.getString(4);
                    userInfo = new User(username, fullname, description, image);
                    // print the results
//                    System.out.format("%s, %s\n", username, description);
                }

                connection.close();
                return userInfo;
            }
        } catch (SQLException e) {
            userInfo = new User();
            e.printStackTrace();
        }
        return new User();
    }
}
