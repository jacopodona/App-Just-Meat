package com.example.justmeat.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.justmeat.R;
import com.google.android.material.navigation.NavigationView;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar=findViewById(R.id.homepage_toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.homepage_drawer_layout);
        NavigationView navigationview= findViewById(R.id.homepage_nav_view);
        navigationview.setNavigationItemSelectedListener(this);//ho aggiunto implements per aggiungere il listener per il cambio di fragment

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null) {//serve per mantenere il fragment corretto aperto in caso l'activity venga ricostruita (es: cambio di orientamento distrugge e ricrea activity)
            //inizializzo il primo fragment a trova supermercati
            getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new TrovaSupermercatiFragment()).commit();
            navigationview.setCheckedItem(R.id.homepage_nav_trova_supermercati);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {//metodo che gestisce il cambio fragment
        switch(menuItem.getItemId()){
            case R.id.homepage_nav_trova_supermercati:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new TrovaSupermercatiFragment()).commit();
                break;
            case R.id.homepage_nav_miei_ordini:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new MieiOrdiniFragment()).commit();
                break;
            case R.id.homepage_nav_ordini_preferiti:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new OrdiniPreferitiFragment()).commit();
                break;
            case R.id.homepage_nav_indirizzi_preferiti:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new IndirizziPreferitiFragment()).commit();
                break;
            case R.id.homepage_nav_impostazioni:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new ImpostazioniFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {//override del back button per chiudere il menu drawer
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
