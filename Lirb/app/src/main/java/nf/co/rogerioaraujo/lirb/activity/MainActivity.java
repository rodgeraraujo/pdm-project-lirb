package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import nf.co.rogerioaraujo.lirb.R;

public class MainActivity extends AppCompatActivity {

    private TextView textv;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textv = (TextView) findViewById(R.id.textViewFazerLogin);
//        textv.setShadowLayer(30, 0, 0, Color.BLACK);

        actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void goLoginActivity(View view) {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public void goRegisterActivity(View view) {
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerIntent);
        finish();
    }
}
