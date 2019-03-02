package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import nf.co.rogerioaraujo.lirb.database.SQLConnect;
import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText txtUsername, txtName, txtPassword, txtEmail;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // get values from layout
        txtUsername = findViewById(R.id.txtUsername);
        txtName = findViewById(R.id.txtName);
        txtPassword = findViewById(R.id.txtPassword);
        txtEmail = findViewById(R.id.txtEmail);
        Date dateRegister = new Date();

        // create a new user
//        user = new User(
//                txtUsername.getText().toString(),
//                txtEmail.getText().toString(),
//                txtPassword.getText().toString(),
//                txtName.getText().toString(),
//                (java.sql.Date) dateRegister,
//                Status.ACTIVE
//        );

    }

    public void btnConn(View view) {
        SQLConnect sendObj = (SQLConnect) new SQLConnect(this, user).execute("");
        System.out.println("retorno" + sendObj.toString());


    }

    public void goLoginActivity(View view) {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
    }

}
