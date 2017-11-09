package edu.ecu.cs.bookshelf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Intent newIntent(Context packageContext)
    {
        Intent intent = new Intent(packageContext, NavigationDrawerActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_user_dashboard) {
            // Handle the camera action
            Intent i = new Intent(NavigationDrawerActivity.this, UserDashboardActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_search_books)
        {
            Intent i = new Intent(NavigationDrawerActivity.this, BookListActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_fav_books)
        {
            Toast.makeText(NavigationDrawerActivity.this, R.string.fav_book, Toast.LENGTH_SHORT ).show();
        }
        else if (id == R.id.nav_manage)
        {
            Toast.makeText(NavigationDrawerActivity.this, R.string.manage_profile, Toast.LENGTH_SHORT ).show();
        }
        else if (id == R.id.nav_share)
        {
            Toast.makeText(NavigationDrawerActivity.this, R.string.share_application, Toast.LENGTH_SHORT ).show();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, getString(R.string.send_app_info));
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_subject));
            i = Intent.createChooser(i, getString(R.string.send_report));
            startActivity(i);
        }
        else if (id == R.id.nav_sign_out)
        {
            Toast.makeText(NavigationDrawerActivity.this, R.string.sign_out_string, Toast.LENGTH_SHORT ).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
