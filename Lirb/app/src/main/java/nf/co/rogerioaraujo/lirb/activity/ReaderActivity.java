package nf.co.rogerioaraujo.lirb.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nf.co.rogerioaraujo.lirb.R;

public class ReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        getSupportActionBar().setTitle("Nome da Obra");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
