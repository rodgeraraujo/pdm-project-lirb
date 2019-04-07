package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.concurrent.ExecutionException;

import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.services.SearchBookURIService;

public class BookActivity extends AppCompatActivity {

    private TextView bookTitle, bookAuthor, bookSinopse, bookId;
    private ImageView bookThumbnail;

    private String msg;
    private String id;

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
        id = String.valueOf(intent.getExtras().getInt("ID"));
        System.out.println(id);
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


        try {
            SearchBookURIService URI = new SearchBookURIService(this, id);
            msg = URI.execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Button read = findViewById(R.id.button_read_Book);
        read.setOnClickListener(v -> {
            Intent intent1 = new Intent(BookActivity.this, ReaderActivity.class);
            // parsing data to the book activity
            intent1.putExtra("URI", msg);
            startActivity(intent1);
        });
    }

    public void goReaderActivity(View view) {
        Intent readerIntent = new Intent(getApplicationContext(), ReaderActivity.class);
        startActivity(readerIntent);
    }
}
