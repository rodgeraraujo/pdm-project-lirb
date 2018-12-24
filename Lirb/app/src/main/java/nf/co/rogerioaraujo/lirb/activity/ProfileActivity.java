package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import nf.co.rogerioaraujo.lirb.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Perfil do Usuário");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void goBookProfile(View view) {
        Intent profileIntent = new Intent(getApplicationContext(), BookActivity.class);
        startActivity(profileIntent);
    }
}
