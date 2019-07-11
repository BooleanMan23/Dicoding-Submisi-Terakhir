package com.example.dicoding_submisi2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.dicoding_submisi2.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static SQLiteDatabase myDatabase;
   public static ArrayList<Double> favoriteId = new ArrayList<Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            myDatabase = this.openOrCreateDatabase("favorite", MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS favorite (title VARCHAR, overview VARCHAR, posterLink VARCHAR, releaseDate VARCHAR,  id DOUBLE, isMovie DOUBLE )");
//            String sql = "DELETE FROM movie WHERE id=0";
//            myDatabase.compileStatement(sql);
        }
        catch (Exception E){

        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Toolbar toolbar = findViewById(R.id.toolbar); //Inisialisasi dan Implementasi id Toolbar

        setSupportActionBar(toolbar); // Memasang Toolbar pada Aplikasi

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Memanggil/Memasang menu item pada toolbar dari layout menu_bar.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public  static  void showInLog(){
        Cursor c = MainActivity.myDatabase.rawQuery("SELECT * FROM favorite", null );
        //mengambil index kolum
        int titleIndex = c.getColumnIndex("title");
        int overviewIndex = c.getColumnIndex("overview");
        int posterLinkIndex = c.getColumnIndex("posterLink");
        int releaseDateIndex = c.getColumnIndex("releaseDate");
        int idIndex = c.getColumnIndex("id");
        int isMovieIndex = c.getColumnIndex("isMovie");
        Log.i("hasil", "===============");
        c.moveToFirst();
        while (c != null){
            Log.i("hasil-title", c.getString(titleIndex));
            Log.i("hasil-overview", c.getString(overviewIndex));
            Log.i("hasil-posterLink", c.getString(posterLinkIndex));
            Log.i("hasil-releaseDate", c.getString(releaseDateIndex));
            Log.i("hasil-id", c.getString(idIndex));
            Log.i("hasil-isMovie", c.getString(isMovieIndex));

            c.moveToNext();


        }



    }
}