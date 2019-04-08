package nf.co.rogerioaraujo.lirb.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import nf.co.rogerioaraujo.lirb.database.MySQL;
import nf.co.rogerioaraujo.lirb.model.User;

public class UserInfoService extends AsyncTask<String, String, String> {
    @SuppressLint("StaticFieldLeak")
    private Context context;

    private User info;

    //MySQL instace
    MySQL mySQL = new MySQL();

    public UserInfoService(Context context, User info) {
        this.context = context;
        this.info = info;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
