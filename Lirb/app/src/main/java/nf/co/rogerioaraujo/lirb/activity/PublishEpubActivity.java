package nf.co.rogerioaraujo.lirb.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.model.Book;
import nf.co.rogerioaraujo.lirb.services.DatabaseSelectService;
import nf.co.rogerioaraujo.lirb.services.RegisterBookService;
import nf.co.rogerioaraujo.lirb.services.UploadFiletService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishEpubActivity extends AppCompatActivity {

    private TextInputLayout textInputTitulo, textInputAutor, textInputEdicao, textInputAno,
            textInputIsbn, textInputSinopse, textInputIdioma;
    private Button fileInputCover, fileInputEpub, buttonInputPublish;
    private String epubPath, coverPath;

    private String USER_ID;
    private int id_user;
    private int id_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        getSupportActionBar().setTitle("Publique uma nova história");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get id from user session
        Intent intent = getIntent();
        USER_ID = intent.getExtras().getString("USER_ID");

        System.out.println(USER_ID);

        fileInputEpub = findViewById(R.id.item_file_Epub);
        fileInputCover = findViewById(R.id.item_input_Cover);
        buttonInputPublish = findViewById(R.id.inputButtonPublish);

        // to get epub path from storage
        fileInputEpub.setOnClickListener(v -> new MaterialFilePicker()
                .withActivity(PublishEpubActivity.this)
                .withRequestCode(1)
                .withFilter(Pattern.compile(".*\\.epub"))
                .withFilterDirectories(false)
                .withHiddenFiles(true)
                .start());

        // to get cover path from storage
        fileInputCover.setOnClickListener(v -> new MaterialFilePicker()
                .withActivity(PublishEpubActivity.this)
                .withRequestCode(2)
                .withFilter(Pattern.compile(".*\\.jpg$"))
                .withFilterDirectories(false)
                .withHiddenFiles(true)
                .start());

        // get text from inputs
        textInputTitulo = findViewById(R.id.text_input_Title);
        textInputAutor = findViewById(R.id.text_input_Author);
        textInputSinopse = findViewById(R.id.text_input_Sinopse);
        textInputEdicao = findViewById(R.id.text_input_Edition);
        textInputAno = findViewById(R.id.text_input_Year);
        textInputIsbn = findViewById(R.id.text_input_Isbn);
        textInputIdioma = findViewById(R.id.text_input_Language);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            epubPath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            fileInputEpub.setText(epubPath);
//            Toast.makeText(this, epubPath, Toast.LENGTH_LONG).show();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            coverPath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            fileInputCover.setText(coverPath);
        }
    }

    // functions to valid inputs
    private boolean validateTitle() {
        String tituloInput = Objects.requireNonNull(textInputTitulo.getEditText()).getText().toString().trim();

        if (tituloInput.isEmpty()) {
            textInputTitulo.setError("Não pode está vazio");
            return false;
        } else {
            textInputTitulo.setError(null);
            return true;
        }
    }

    public void publicarEpub(View v) {

        if (!validateTitle() | !validateEdition() | !validateSinopse() |
                !validateYear() | !validateLanguage() | !validateIsbn() | !validateAuthor()) {
            return;
        }

        try {

            // select the amount of books the user have
            getBookId();

            // select the user id
            getUserId();

//            progress.setMessage("Salvando informações...");
            // save book info on database
            String idBook = saveBookInfo();

            String url_epub = "http://192.168.1.140/rodger/api/reader/createEpub.php?user="+id_user+"&book="+id_book+"&id="+idBook;
            String url_cover = "http://192.168.1.140/rodger/api/upload/uploadPic.php?user="+id_user+"&book="+id_book;

//            progress.setMessage("Upload do ePub...");
            // upload epub file
            uploadFile(url_epub, epubPath);

//            progress.setMessage("Upload da capa...");
            // upload book cover
            uploadFile(url_cover, coverPath);

            clearInputs();

//            progress.dismiss();

            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            Toast.makeText(this, "Livro publicado com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private boolean validateSinopse() {
        String sinopseInput = Objects.requireNonNull(textInputSinopse.getEditText()).getText().toString().trim();

        if (sinopseInput.isEmpty()) {
            textInputSinopse.setError("Aqui não pode estar vazio");
            return false;
        } else if (sinopseInput.length() > 240) {
            textInputSinopse.setError("Sinopse muito longa, diminua um pouco");
            return false;
        } else {
            textInputSinopse.setError(null);
            return true;
        }
    }

    private boolean validateYear() {
        String yearInput = Objects.requireNonNull(textInputAno.getEditText()).getText().toString().trim();

        if (yearInput.isEmpty()) {
            textInputAno.setError("Não pode estar vazio");
            return false;
        } else {
            textInputAno.setError(null);
            return true;
        }
    }

    private boolean validateEdition() {
        String edicaoInput = Objects.requireNonNull(textInputEdicao.getEditText()).getText().toString().trim();

        if (edicaoInput.isEmpty()) {
            textInputEdicao.setError("Não pode estar vazio");
            return false;
        } else {
            textInputEdicao.setError(null);
            return true;
        }
    }

    private boolean validateLanguage() {
        String idiomaInput = Objects.requireNonNull(textInputIdioma.getEditText()).getText().toString().trim();

        if (idiomaInput.isEmpty()) {
            textInputIdioma.setError("Não pode estar vazio");
            return false;
        } else {
            textInputIdioma.setError(null);
            return true;
        }
    }

    private boolean validateIsbn() {
        String isbnInput = Objects.requireNonNull(textInputIsbn.getEditText()).getText().toString().trim();

        if (isbnInput.isEmpty()) {
            textInputIsbn.setError("Não pode estar vazio");
            return false;
        } else {
            textInputIsbn.setError(null);
            return true;
        }
    }

    private boolean validateAuthor() {
        String autorInput = Objects.requireNonNull(textInputAutor.getEditText()).getText().toString().trim();

        if (autorInput.isEmpty()) {
            textInputAutor.setError("Não pode estar vazio");
            return false;
        } else {
            textInputAutor.setError(null);
            return true;
        }
    }


    private void uploadFile(String url, String path) throws ExecutionException, InterruptedException {
        UploadFiletService upload = new UploadFiletService(this, url, path);
        upload.execute("").get();
    }

    private String saveBookInfo() throws ExecutionException, InterruptedException {
        // select .jpg from image path
        String extension = coverPath.substring(coverPath.lastIndexOf("."));

        String cover = "http://192.168.1.140/rodger/api/upload/images/user_u00" + id_user + "/" + "book_b00" + id_book + extension;

        String titulo = Objects.requireNonNull(textInputTitulo.getEditText()).getText().toString();
        String autor = Objects.requireNonNull(textInputAutor.getEditText()).getText().toString();
        String edicao = Objects.requireNonNull(textInputEdicao.getEditText()).getText().toString();
        int ano = Integer.parseInt(Objects.requireNonNull(textInputAno.getEditText()).getText().toString());
        String sinopse = Objects.requireNonNull(textInputSinopse.getEditText()).getText().toString();
        String isbn = Objects.requireNonNull(textInputIsbn.getEditText()).getText().toString();
        String idioma = Objects.requireNonNull(textInputIdioma.getEditText()).getText().toString();

        Date newDate = new Date();
        java.sql.Date pubDate = new java.sql.Date (newDate.getTime());

        // create book object
        Book book = new Book(titulo, edicao, pubDate, ano, autor, cover, sinopse, idioma, isbn, id_user);

        // register book info on db
        RegisterBookService register = new RegisterBookService(this, book);
        return register.execute("").get();
    }

    private void getBookId() throws ExecutionException, InterruptedException {
        DatabaseSelectService selectBooksCount = new DatabaseSelectService(this, USER_ID, 1);
        id_book = Integer.parseInt(selectBooksCount.execute("").get());
        System.out.println(id_book);
    }

    private void getUserId() throws ExecutionException, InterruptedException {
        DatabaseSelectService selectUserId = new DatabaseSelectService(this, USER_ID, 2);
        id_user = Integer.parseInt(selectUserId.execute("").get());
        System.out.println(id_user);
    }

    private void clearInputs() {
        Objects.requireNonNull(textInputTitulo.getEditText()).getText().clear();
        Objects.requireNonNull(textInputAutor.getEditText()).getText().clear();
        Objects.requireNonNull(textInputEdicao.getEditText()).getText().clear();
        Objects.requireNonNull(textInputAno.getEditText()).getText().clear();
        Objects.requireNonNull(textInputSinopse.getEditText()).getText().clear();
        Objects.requireNonNull(textInputIsbn.getEditText()).getText().clear();
        Objects.requireNonNull(textInputIdioma.getEditText()).getText().clear();
    }
}
