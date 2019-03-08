package nf.co.rogerioaraujo.lirb.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import nf.co.rogerioaraujo.lirb.services.RegisterUserService;
import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.model.Status;
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

    }

    public void btnConn(View view) {

        ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("Verificando dados, por favor espere...");
        dialog.show();

        java.util.Date newDate = new Date();
        java.sql.Date dateRegister = new java.sql.Date (newDate.getTime());

        // create a new user
        user = new User(
                txtUsername.getText().toString(),
                txtEmail.getText().toString(),
                txtPassword.getText().toString(),
                txtName.getText().toString(),
                dateRegister,
                Status.ACTIVE
        );

        String msg = "";

        if (user.getUsername().equals("") || user.getEmail().equals("") ||
                user.getPassword().equals("") || user.getName().equals("")) {
            dialog.dismiss();
            msg = "Inputs are invalids or void";
        } else {
            try {
                RegisterUserService register = new RegisterUserService(this, user);
                msg = register.execute("").get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

        }
        // clear the inputs TextEdit
        clearInputs();

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        dialog.dismiss();
        startActivity(loginIntent);
    }

    private void clearInputs() {
        txtUsername.getText().clear();
        txtName.getText().clear();
        txtPassword.getText().clear();
        txtEmail.getText().clear();
    }

    public void goLoginActivity(View view) {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
    }

}
