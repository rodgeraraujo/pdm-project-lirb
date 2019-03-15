package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import nf.co.rogerioaraujo.lirb.R;

public class PublishEpubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        getSupportActionBar().setTitle("Publique uma nova história");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void goHomeActivity(View view) {
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeIntent);
    }
}
