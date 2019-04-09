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
import nf.co.rogerioaraujo.lirb.model.User;

public class UpdateUserService extends AsyncTask<String, String, String> {
    private ProgressDialog pDialog;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    private String info;

    private User userInfo;

    //MySQL instace
    MySQL mySQL = new MySQL();

    public UpdateUserService(Context context, String info) {
        this.context = context;
        this.info = info;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pDialog = new ProgressDialog(context);
        pDialog.setTitle("Atualizar");
        pDialog.setMessage("Salvando novas informações...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.setInverseBackgroundForced(true);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {

            Connection connection = mySQL.newConnection();

            if (connection == null) {
                userInfo = new User();
            } else {
                String query = "UPDATE user_data SET user_description = '"+userInfo.getDescription()+"', " +
                        "user_name = '"+userInfo.getUsername()+"', " +
                        "user_fullName = '"+userInfo.getName()+"' " +
                        "WHERE user_name = '" + info + "' OR user_email = '" + info + "'";
                Statement st = (Statement) connection.createStatement();
                ResultSet rs = st.executeQuery(query);

                pDialog.dismiss();
                connection.close();
                return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
