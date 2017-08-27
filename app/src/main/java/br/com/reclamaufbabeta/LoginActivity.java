package br.com.reclamaufbabeta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.reclamaufbabeta.Controller.UsuarioController;
import br.com.reclamaufbabeta.modelo.Usuario;


public class LoginActivity extends Activity {
    private EditText campoUsuario, campoSenha;
    private Context context;
    private UsuarioController usuarioController;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        usuarioController = UsuarioController.getInstance(context);
        campoUsuario = (EditText) findViewById(R.id.login_usuario);
        campoSenha = (EditText) findViewById(R.id.login_senha);

        Usuario usuario = new Usuario(1, "adm", "adm");
        try {
            usuarioController.insert(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button entrarBtn = (Button) findViewById(R.id.login_entrar);

        entrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()){
                    Intent mapsAcitivity = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(mapsAcitivity);
                }
            }
        });
    }



    public boolean validar() {
        String usuario = campoUsuario.getText().toString();
        String senha = campoSenha.getText().toString();
        try {
            boolean isValid = usuarioController.validacao(usuario, senha);
            if (isValid) {
                Toast.makeText(LoginActivity.this, "Usuario e senha validados com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Verifique usuario e senha!", Toast.LENGTH_SHORT).show();
            }
            return isValid;
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "Erro validando usuario e senha", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }
}