package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import nf.co.rogerioaraujo.lirb.R;

public class ProfileActivity extends AppCompatActivity {

    private String USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Perfil do Usuário");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get id from user session
        Intent intent = getIntent();
        USER_ID = intent.getExtras().getString("USER_ID");

        Toast.makeText(
                getApplicationContext(),
                USER_ID,
                Toast.LENGTH_LONG
        ).show();
    }

    public void goBookProfile(View view) {
        Intent profileIntent = new Intent(getApplicationContext(), BookActivity.class);
        startActivity(profileIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
