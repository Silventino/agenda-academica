package com.silventino.agenda5;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class LoginActivity extends AppCompatActivity{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    //    private MaterialCalendarView calendario;
    private BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // banco de dados
//        bancoDeDados = BancoDeDados.getInstancia();
//        bancoDeDados.addEvento(new Evento(14,11,2018,12,00, "Estudar para prova de compiladores", "vai ser dificil"));
//        bancoDeDados.addGrupo(new Grupo("Compiladores"));
//        bancoDeDados.addGrupo(new Grupo("IHC"));
//        bancoDeDados.addGrupo(new Grupo("Amigos do Ribo"));
//        bancoDeDados.addGrupo(new Grupo("CG"));
        //



    }

    private void setClickListeners(){

    }

}
