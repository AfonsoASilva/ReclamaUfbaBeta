package br.com.reclamaufbabeta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.reclamaufbabeta.Controller.UsuarioController;
import br.com.reclamaufbabeta.DAO.UsuarioDAO;
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
        usuarioController = new UsuarioController();
        UsuarioController.getInstance(this);
        campoUsuario = (EditText) findViewById(R.id.login_usuario);
        campoSenha = (EditText) findViewById(R.id.login_senha);

        Usuario usuario = new Usuario(1000,"Administrador", "adm", "adm");
        try {
            usuarioController.insert(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button entrarBtn = (Button) findViewById(R.id.login_entrar);
        Button cadBtn = (Button)  findViewById(R.id.login_cadastrar);

        entrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()){
                    Intent mapsAcitivity = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(mapsAcitivity);
                }
            }
        });

        cadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent usuarioAcitivity = new Intent(LoginActivity.this, UsuarioActivity.class);
                startActivity(usuarioAcitivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        try{
            List<Usuario> usuarios = usuarioDAO.findAll();
            System.out.println(usuarios.size());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean validar() {
        String usuario = campoUsuario.getText().toString();
        String senha = campoSenha.getText().toString();
        try {
            boolean isValid = UsuarioController.Validacao(usuario, senha, this);
            if (isValid) {
                Toast.makeText(LoginActivity.this, "E-mail e senha validados com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Verifique E-mail e senha!", Toast.LENGTH_SHORT).show();
            }
            return isValid;
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "Erro validando E-mail e senha", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
    }
}