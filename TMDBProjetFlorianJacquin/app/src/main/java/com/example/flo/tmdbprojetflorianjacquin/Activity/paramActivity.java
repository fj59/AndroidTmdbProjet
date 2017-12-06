package com.example.flo.tmdbprojetflorianjacquin.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.flo.tmdbprojetflorianjacquin.Objet.Film;
import com.example.flo.tmdbprojetflorianjacquin.Objet.Serie;
import com.example.flo.tmdbprojetflorianjacquin.R;
import com.example.flo.tmdbprojetflorianjacquin.util.statics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class paramActivity extends AppCompatActivity {

    RadioGroup rdgpLangue,rdgpImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);
        rdgpLangue=(RadioGroup)findViewById(R.id.RGLangue);
        if(statics.langue.contains("fr")) {
            rdgpLangue.check(R.id.RBFr);
        }
        if(statics.langue.contains("en")) {
            rdgpLangue.check(R.id.RBEn);
        }

        rdgpImage=(RadioGroup)findViewById(R.id.RGQImage);
        if(statics.qualiteI.contains("w780")) {
            rdgpImage.check(R.id.RbMAx);
        }
        if(statics.qualiteI.contains("w500")) {
            rdgpImage.check(R.id.RbMoy);
        }
        if(statics.qualiteI.contains("w300")) {
            rdgpImage.check(R.id.RbMin);
        }
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
        getMenuInflater().inflate(R.menu.param, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }





    @Override
    public void recreate() {
        super.recreate();
        Locale locale = new Locale(statics.langue);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_films_series);
        if(statics.langue.contains("fr")) {
            rdgpLangue.check(R.id.RBFr);
        }
        if(statics.langue.contains("en")) {
            rdgpLangue.check(R.id.RBEn);
        }

        if(statics.qualiteI.contains("780")) {
            rdgpImage.check(R.id.RbMAx);
        }
        if(statics.qualiteI.contains("w500")) {
            rdgpImage.check(R.id.RbMoy);
        }
        if(statics.qualiteI.contains("w300")) {
            rdgpImage.check(R.id.RbMin);
        }
    }



    @Override
    public void finish() {

        super.finish();

    }

    public void francais(View v)
    {
        statics.langue="fr";
        this.recreate();
    }
    public void anglais(View v)
    {
        statics.langue="en";
        this.recreate();
    }

    public void max(View v)
    {
        statics.qualiteI="w780";
        statics.qualiteB="w1280";
    }
    public void moyen(View v)
    {
        statics.qualiteI="w500";
        statics.qualiteB="w780";
    }

    public void min(View v)
    {
        statics.qualiteI="w300";
            statics.qualiteB="w500";
    }


}


