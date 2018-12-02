package com.silventino.agenda5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

    private BancoDeDados bancoDeDados;

    private EditText txtUsuario;
    private EditText txtSenha;
    private Button btnEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bancoDeDados = BancoDeDados.getInstancia(getApplicationContext());

        txtUsuario = findViewById(R.id.txtCadastroUsuario);
        txtSenha = findViewById(R.id.txtCadastroSenha);
        btnEnviar = findViewById(R.id.btnEnviarLogin);

        setClickListeners();
    }

    private void setClickListeners(){
        final LoginActivity ponteiroThis = this;
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bancoDeDados.login(txtUsuario.getText().toString(), txtSenha.getText().toString(), ponteiroThis);
            }
        });
    }

    public void receberLogin(int id){
        if(id > 0){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("id", id);
            Log.d("OIA O USUARIO AQUI", id + "");
            startActivity(i);
        }
        else{
            Toast.makeText(this, "Credenciais incorrestas.", Toast.LENGTH_SHORT).show();
        }
    }

}