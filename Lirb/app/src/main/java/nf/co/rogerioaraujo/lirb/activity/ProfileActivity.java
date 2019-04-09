package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.model.User;
import nf.co.rogerioaraujo.lirb.services.UserInfoService;

public class ProfileActivity extends AppCompatActivity {

    private Button editInfo;

    private TextView textUsername, textName, textDescription;
    private ImageView selectedImageView;

    private String USER_ID;

    private User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Perfil do Usuário");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get id from user session
        Intent intent = getIntent();
        USER_ID = intent.getExtras().getString("USER_ID");

        try {
            UserInfoService userInfoService = new UserInfoService(this, USER_ID);
            userInfo = userInfoService.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        textUsername = findViewById(R.id.u_Username);
        textName = findViewById(R.id.u_Name);
        textDescription = findViewById(R.id.u_Description);

        textUsername.setText(userInfo.getUsername());
        textName.setText(userInfo.getName());
        textDescription.setText(userInfo.getDescription());
    }

    public void goBookProfile(View view) {
        Intent profileIntent = new Intent(getApplicationContext(), BookActivity.class);
        startActivity(profileIntent);
    }

    public void updateUserInfo(View view) {
        Intent editInfo = new Intent(getApplicationContext(), ProfileEditActivity.class);
        editInfo.putExtra("USER_ID", USER_ID);
        editInfo.putExtra("USER_NAME", userInfo.getUsername());
        editInfo.putExtra("USER_DESCRIPTION", userInfo.getDescription());
        startActivity(editInfo);
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