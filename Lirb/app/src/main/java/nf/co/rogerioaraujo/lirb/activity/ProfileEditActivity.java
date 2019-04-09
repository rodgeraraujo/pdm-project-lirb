package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.model.User;
import nf.co.rogerioaraujo.lirb.services.UpdateUserService;

public class ProfileEditActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;

    private TextInputLayout textInputNome, textInputDescricao;

    private ImageView selectedImageView;
    private EditText titleEditText;

    private String USER_ID, USER_DESCRIPTION, USER_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        // get id from user session
        Intent intent = getIntent();
        USER_ID = intent.getExtras().getString("USER_ID");
        USER_NAME = intent.getExtras().getString("USER_NAME");
        USER_DESCRIPTION = intent.getExtras().getString("USER_DESCRIPTION");

        this.selectedImageView = (ImageView) findViewById(R.id.u_Picture);

        textInputNome = findViewById(R.id.edit_input_name);
        textInputDescricao = findViewById(R.id.edit_input_description);

        textInputNome.getEditText().setText(USER_NAME);
        textInputDescricao.getEditText().setText(USER_DESCRIPTION);
    }

    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    public void openCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }

    public void cancel(View view) {
        finish();
    }

    public void save(View view) {
        Bitmap image = ((BitmapDrawable)selectedImageView.getDrawable()).getBitmap();
//        finish();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        String name = textInputNome.getEditText().getText().toString();
        String description = textInputDescricao.getEditText().getText().toString();

        User user = new User(name, description, encodedImage);

        String msg = "";

        try {
            UpdateUserService update = new UpdateUserService(this, user, USER_ID);
            msg = update.execute("").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (msg == "success") {
            Toast.makeText(
                    getApplicationContext(),
                    "Informações atualizadas",
                    Toast.LENGTH_LONG
            ).show();

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Ocorreu algum erro :(",
                    Toast.LENGTH_LONG
            ).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                selectedImageView.setImageBitmap(BitmapFactory.decodeStream(imageStream));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            selectedImageView.setImageBitmap(imageBitmap);
        }
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
