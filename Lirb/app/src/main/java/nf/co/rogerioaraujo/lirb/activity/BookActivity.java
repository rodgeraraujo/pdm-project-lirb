package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import nf.co.rogerioaraujo.lirb.R;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getSupportActionBar().setTitle("Informações do Livro");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void goReaderActivity(View view) {
        Intent readerIntent = new Intent(getApplicationContext(), ReaderActivity.class);
        startActivity(readerIntent);
    }
}
