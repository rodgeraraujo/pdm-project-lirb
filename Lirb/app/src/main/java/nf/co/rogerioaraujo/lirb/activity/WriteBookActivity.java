package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import nf.co.rogerioaraujo.lirb.R;

public class WriteBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_book);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Escreva sua história");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Substituir com sua ação", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    public void goHomeActivity(View view) {
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeIntent);
    }
}
