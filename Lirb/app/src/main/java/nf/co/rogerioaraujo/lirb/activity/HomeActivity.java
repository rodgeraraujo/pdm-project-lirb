package nf.co.rogerioaraujo.lirb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import nf.co.rogerioaraujo.lirb.R;
import nf.co.rogerioaraujo.lirb.activity.fragments.TabOneFragment;
import nf.co.rogerioaraujo.lirb.activity.fragments.TabThreeFragment;
import nf.co.rogerioaraujo.lirb.activity.fragments.TabTwoFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // search view
    private MaterialSearchView materialSearchView;
    private String[] list;

    // tabs
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get user id
        getUserSession();

        // List of suggestions
        list = new String[]{
                "Book 01",
                "BOOK 02",
                "Author Teste",
                "Autho 2",
                "User 1",
                "User 3",
                "Book legal ",
                "Nikan",
                "Rodger"
        };

        // Search view
        materialSearchView = findViewById(R.id.search_view);
        materialSearchView.closeSearch();
        materialSearchView.setSuggestions(list);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // here create the filtering
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // make changes in realtime if you typing
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        // Tabs
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // get data from LoginActivity
        //getUser();
        System.out.println("Test " + userId);

        // floating button options
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent publishIntent = new Intent(getApplicationContext(), PublishActivity.class);
            startActivity(publishIntent);
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    // tabs on home
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabOneFragment(), "Recentes");
        adapter.addFragment(new TabTwoFragment(), "Mais avaliados");
        adapter.addFragment(new TabThreeFragment(), "Populares");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void getUserSession() {
        Bundle userCode = getIntent().getExtras();
        userId = userCode.getString("sessionId");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu1 this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu1.home, menu);
        getMenuInflater().inflate(R.menu.menu1, menu);

        MenuItem item = menu.findItem(R.id.action_search);

        //if (item == null ) Log.d("rogerio","Valor nulo");

        materialSearchView.setMenuItem(item);

        return true;
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
            Toast.makeText(
                    getApplicationContext(),
                    "Logout successfully",
                    Toast.LENGTH_SHORT).show();
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
