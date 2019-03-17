package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import nf.co.rogerioaraujo.lirb.R;

public class BookActivity extends AppCompatActivity {

    private TextView bookTitle, bookAuthor, bookSinopse;
    private ImageView bookThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        bookTitle = findViewById(R.id.bookTitle);
        bookAuthor = findViewById(R.id.bookAuthor);
        bookSinopse = findViewById(R.id.bookSinopse);
        bookThumbnail = findViewById(R.id.bookThumbnail);

        // receive data
        Intent intent = getIntent();
        String title = intent.getExtras().getString("Title");
        String author = intent.getExtras().getString("Author");
        String sinopse = intent.getExtras().getString("Sinopse");
        String thumbnail = intent.getExtras().getString("Thumbnail");

        // setting values
        bookTitle.setText(title);
        bookAuthor.setText(author);
        bookSinopse.setText(sinopse);
        Glide.with(BookActivity.this)
                .load(thumbnail)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(bookThumbnail);

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void goReaderActivity(View view) {
        Intent readerIntent = new Intent(getApplicationContext(), ReaderActivity.class);
        startActivity(readerIntent);
    }
}
