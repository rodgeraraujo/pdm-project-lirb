package nf.co.rogerioaraujo.lirb.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
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
//            USER = "rodger";
//            PASSWORD = "mnb";
            String PASSWORD_HASH = md5hashing(PASSWORD);
            URL = "http://lirb.000webhostapp.com/lirb/user_control.php?user="+USER+"&password="+PASSWORD_HASH;
            Log.d("URL",URL);
            Log.d("PASSWORD",PASSWORD_HASH);

            // make request from url
            request = new StringRequest(Request.Method.POST, URL, response -> {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.names().get(0).equals("success")){
                        Toast.makeText(
                                getApplicationContext(),
                                //"SUCCESS: "+jsonObject.getString("success"),
                                jsonObject.getString("success"),
                                Toast.LENGTH_SHORT).show();

                        Intent parse = new Intent(getApplicationContext(), HomeActivity.class);
                        parse.putExtra("sessionId", USER);


                        startActivity(parse);

                        //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                    else if(jsonObject.names().get(0).equals("invalid")){
                        Toast.makeText(
                                getApplicationContext(),
                                //"WARNING: " +jsonObject.getString("invalid"),
                                jsonObject.getString("invalid"),
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(
                                getApplicationContext(),
                                //"ERROR: " +jsonObject.getString("error"),
                                jsonObject.getString("error"),
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

    public void goRegisterActivity(View view) {
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerIntent);
    }

    public static String md5hashing(String password) {
        String hashtext = null;
        try
        {
            String plaintext = password;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while(hashtext.length() < 32 ){
                hashtext = "0"+hashtext;
            }
        } catch (Exception e1) {
            // TODO: handle exception
            System.out.println();
            Log.d("md5Hashing",e1.getClass().getName() + ": " + e1.getMessage());
            //JOptionPane.showMessageDialog(null,e1.getClass().getName() + ": " + e1.getMessage());
        }
        return hashtext;
    }

}
