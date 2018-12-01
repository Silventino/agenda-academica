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
import android.widget.EditText;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class CadastroActivity extends AppCompatActivity{

    private BancoDeDados bancoDeDados;
    private EditText txtNome;
    private EditText txtUsuario;
    private EditText txtSenha;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        txtNome = findViewById(R.id.txtNome);
        txtSenha = findViewById(R.id.txtSenha);
        txtUsuario = findViewById(R.id.txtUsuario);
        btnEnviar = findViewById(R.id.btnEnviarCadastro);

        // banco de dados
        bancoDeDados = BancoDeDados.getInstancia(getApplicationContext());
        //

        setClickListeners();

    }

    private void setClickListeners(){
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = txtNome.getText().toString();
                if(nome == "" || nome == null){
                    Toast.makeText(CadastroActivity.this, "Campo nome deve ser preenchido.", Toast.LENGTH_SHORT).show();
                }

                String usuario = txtUsuario.getText().toString();
                if(usuario == "" || usuario == null){
                    Toast.makeText(CadastroActivity.this, "Campo usuário deve ser preenchido.", Toast.LENGTH_SHORT).show();
                }

                String senha = txtSenha.getText().toString();
                if(senha == "" || senha == null){
                    Toast.makeText(CadastroActivity.this, "Campo nome senha ser preenchido.", Toast.LENGTH_SHORT).show();
                }

                Usuario u = new Usuario(nome, usuario, senha);

                bancoDeDados.addUsuario(u);
            }
        });
    }

}