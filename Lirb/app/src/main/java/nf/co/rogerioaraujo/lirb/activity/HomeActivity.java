package nf.co.rogerioaraujo.lirb.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.webService.Adapter.CustomAdapter;
import nf.co.rogerioaraujo.lirb.webService.Data.DataJson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int id;

    // cardview data
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<DataJson> data_list;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get data from LoginActivity
        //getUser();
        System.out.println("Test " + userId);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent publishIntent = new Intent(getApplicationContext(), PublishActivity.class);
            startActivity(publishIntent);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        //load data
        recyclerView = findViewById(R.id.recycler_view);
        data_list  = new ArrayList<>();
        loadDataFromServer(0); // id 0, to get the first iten from db

        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(this, data_list);
        recyclerView.setAdapter(adapter);

        // scroll to call load data for more 4 itens
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                id = data_list.get(data_list.size()-1).getBookId();

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1){
                    loadDataFromServer(id);
                }

            }
        });
    }

    private void getUser() {
        Bundle userCode = getIntent().getExtras();
        userId = userCode.getString("userId");
    }

    private void loadDataFromServer(final int id) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                System.out.println("test : " + id);

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://lirb.000webhostapp.com/scriptJson.php?id=" + id)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        DataJson data = new DataJson(
                                object.getInt("bookId"),
                                object.getString("title"),
                                object.getString("author"),
                                object.getString("thumbnail"),
                                object.getString("sinopse")
                        );

                        data_list.add(data);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    Context c = getApplicationContext();
                    //Toast.makeText(c, "No more itens to show", Toast.LENGTH_LONG).show();
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String msgToast = "Ainda não está funcinando, quem sabe na proxima release!";

        if (id == R.id.nav_profile) {
            // Handle the camera action
            Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(profileIntent);
        } else if (id == R.id.nav_home_app) {

            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);

        } else if (id == R.id.nav_my_books) {

            showToast(msgToast);

        } else if (id == R.id.nav_buyer_books) {

            showToast(msgToast);

        } else if (id == R.id.nav_settings) {

            showToast(msgToast);

        } else if (id == R.id.nav_about) {

            showToast(msgToast);

        }else if (id == R.id.nav_feedback) {

            showToast(msgToast);

        }else if (id == R.id.nav_logout) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showToast(String msgToast) {
        Toast.makeText(
                getApplicationContext(),
                msgToast,
                Toast.LENGTH_LONG
        ).show();
    }

    public void goBookProfile(View view) {
        Intent bookIntent = new Intent(getApplicationContext(), BookActivity.class);
        startActivity(bookIntent);
    }
}
