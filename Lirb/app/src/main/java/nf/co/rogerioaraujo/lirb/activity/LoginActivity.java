package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nf.co.rogerioaraujo.lirb.R;

public class LoginActivity extends AppCompatActivity {

    private EditText userId, password;
    private Button btnLogin;

    private RequestQueue requestQueue;
    private String USER;
    private String PASSWORD;
    private String URL;
//    private static final String URL = "http://lirb.000webhostapp.com/lirb/user_control.php";
    private StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        userId = findViewById(R.id.txtUser);
        password = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);

        requestQueue = Volley.newRequestQueue(this);

        btnLogin.setOnClickListener(view -> {

            USER = userId.getText().toString();
            PASSWORD = password.getText().toString();
            URL = "http://lirb.000webhostapp.com/lirb/user_control.php?user="+USER+"&password="+PASSWORD;

            // parse user info to HomeActivity
            parseUser(USER);

            // make request from url
            request = new StringRequest(Request.Method.POST, URL, response -> {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.names().get(0).equals("success")){
                        Toast.makeText(
                                getApplicationContext(),
                                "SUCCESS: "+jsonObject.getString("success"),
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                    else if(jsonObject.names().get(0).equals("invalid")){
                        Toast.makeText(
                                getApplicationContext(),
                                "WARNING: " +jsonObject.getString("invalid"),
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(
                                getApplicationContext(),
                                "ERROR: " +jsonObject.getString("error"),
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
            }){
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("userInfo", userId.getText().toString());
                    hashMap.put("password", password.getText().toString());

                    return hashMap;
                }
            };
            requestQueue.add(request);
        });
    }

    private void parseUser(String USER) {
        Intent parse = new Intent(this, HomeActivity.class);
        parse.putExtra("userId", USER);
        startActivity(parse);
    }

    public void goRegisterActivity(View view) {
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerIntent);
    }

}
