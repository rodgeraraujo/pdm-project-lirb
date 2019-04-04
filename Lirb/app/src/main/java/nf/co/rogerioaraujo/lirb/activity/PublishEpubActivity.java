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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishEpubActivity extends AppCompatActivity {

    private TextInputLayout textInputTitulo, textInputAutor, textInputEdicao, textInputAno, textInputIsbn, textInputSinopse, textInputIdioma;
    private Button fileInputCover, fileInputEpub, buttonInputPublish;
    private String epubPath, coverPath;

    private String USER_ID;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        // get id from user session
        Intent intent = getIntent();
        USER_ID = intent.getExtras().getString("USER_ID");

        Objects.requireNonNull(getSupportActionBar()).setTitle("Publique uma nova história");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fileInputEpub = findViewById(R.id.item_file_Epub);
        fileInputCover = findViewById(R.id.item_input_Cover);
        buttonInputPublish = findViewById(R.id.inputButtonPublish);

        // to get epub path from storage
        fileInputEpub.setOnClickListener(v -> new MaterialFilePicker()
                .withActivity(PublishEpubActivity.this)
                .withRequestCode(1)
                .withFilter(Pattern.compile(".*\\.epub")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(false) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start());

        fileInputCover.setOnClickListener(v -> new MaterialFilePicker()
                .withActivity(PublishEpubActivity.this)
                .withRequestCode(2)
                .withFilter(Pattern.compile(".*\\.jpg$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(false) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
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
//        button = findViewById(R.id.importEpub);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
//                return;
//            }
//        }

//        enable_button();
//    }

//    private void enable_button() {
//        button.setOnClickListener(view -> new MaterialFilePicker()
//                .withActivity(PublishEpubActivity.this)
//                .withRequestCode(10)
//                .start());
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//            enable_button();
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
//            }
//        }
//    }

//    ProgressDialog progress;
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
//        if (requestCode == 10 && resultCode == RESULT_OK) {
//
//            progress = new ProgressDialog(PublishEpubActivity.this);
//            progress.setTitle("Uploading");
//            progress.setMessage("Please wait...");
//            progress.show();
//
//            Thread t = new Thread(() -> {
//
//                File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
//                String content_type = getMimeType(f.getPath());
//
//                Log.d("FILE::", f + "");
//                Log.d("CONTENT_TYPE::", content_type + "");
//
//                String file_path = f.getAbsolutePath();
//                Log.d("FILE_PATH::", file_path + "");
//
//                OkHttpClient client = new OkHttpClient();
//                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);
//
//                Log.d("FILE_BODY::", file_body + "");
//
//                RequestBody request_body = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("type", content_type)
//                        .addFormDataPart("uploaded_file", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
//
//                        .build();
//
//                Log.d("PATH_REQUEST_BODY::", request_body + "");
//                int USER_ID = 1;
//                int BOOK_ID = 2;
//                Request request = new Request.Builder()
//                        .url("https://lirb.000webhostapp.com/api/reader/createEpub.php?user=" + USER_ID + "&book=" + BOOK_ID)
//                        .post(request_body)
//                        .build();
//
//                Log.d("PATH_REQUEST::", request + "");
//
//                try {
//                    Response response = client.newCall(request).execute();
//
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Error : " + response);
//                    }
//
//                    progress.dismiss();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//            });
//
//            t.start();
//
//
//        }
//    }
//
//    private String getMimeType(String path) {
//
//        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
//        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//    }

    public void goHomeActivity(View view) {
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeIntent);
    }


    // get file path


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

    public void publicarEpub(View v) {
        if (!validateTitle() | !validateEdition() | !validateSinopse() |
                !validateYear() | !validateLanguage() | !validateIsbn() | !validateAuthor()) {
            return;
        }

        progress = new ProgressDialog(PublishEpubActivity.this);
        progress.setTitle("Uploading");
        progress.setMessage("Please wait...");
        progress.show();

        buttonInputPublish.setText("Publicando...");

        try {

            // select the amount of books the user have
            DatabaseSelectService selectService = new DatabaseSelectService(this, USER_ID);
            String result = selectService.execute("").get();
            int ID_USER = Integer.parseInt(result.substring(0,1));
            int ID_BOOK = Integer.parseInt(result.substring(result.lastIndexOf("-") + 1));

            System.out.println(result);

            String EXTENSION = coverPath.substring(coverPath.lastIndexOf("."));

            String cover = "http://lirb.000webhostapp.com/api/upload/images/user_u00" + ID_USER + "/" + "book_b00" + ID_BOOK + EXTENSION;

            String titulo = Objects.requireNonNull(textInputTitulo.getEditText()).getText().toString();
            String autor = Objects.requireNonNull(textInputAutor.getEditText()).getText().toString();
            String edicao = Objects.requireNonNull(textInputEdicao.getEditText()).getText().toString();
            int ano = Integer.parseInt(Objects.requireNonNull(textInputAno.getEditText()).getText().toString());
            String sinopse = Objects.requireNonNull(textInputSinopse.getEditText()).getText().toString();
            String isbn = Objects.requireNonNull(textInputIsbn.getEditText()).getText().toString();
            String idioma = Objects.requireNonNull(textInputIdioma.getEditText()).getText().toString();

            java.util.Date newDate = new Date();
            java.sql.Date pubDate = new java.sql.Date (newDate.getTime());

            // create book object
            Book book = new Book(titulo, edicao, pubDate, ano, autor, cover, sinopse, idioma, isbn, ID_USER);

            // instace book register service
            RegisterBookService register = new RegisterBookService(this, book);
            String msg = register.execute("").get();

            String URL = "https://lirb.000webhostapp.com/api/reader/createEpub.php?user="+ID_USER+"&book="+ID_BOOK+"&id="+msg;
            System.out.println(URL);
            uploadFile(URL, epubPath);

            String URL_2 = "https://lirb.000webhostapp.com/api/upload/uploadPic.php?user="+ID_USER+"&book="+ID_BOOK;
            System.out.println(URL_2);
            uploadFile(URL_2, coverPath);

            progress.dismiss();

            Toast.makeText(this, "Livro publicado com sucesso!", Toast.LENGTH_SHORT).show();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void uploadFile(String url, String file) {
        Thread t = new Thread(() -> {

            File f  = new File(file);
            String content_type  = getMimeType(f.getPath());

            String file_path = f.getAbsolutePath();
            OkHttpClient client = new OkHttpClient();
            RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

            RequestBody request_body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("type",content_type)
                    .addFormDataPart("uploaded_file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
//                    .url("https://lirb.000webhostapp.com/api/upload/uploadPic.php")
                    .post(request_body)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if(!response.isSuccessful()){
                    throw new IOException("Error : "+response);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        t.start();
    }

    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}
