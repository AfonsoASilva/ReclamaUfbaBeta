package br.com.reclamaufbabeta.Controller;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

import br.com.reclamaufbabeta.DAO.UsuarioDAO;
import br.com.reclamaufbabeta.InserePostActivity;
import br.com.reclamaufbabeta.R;
import br.com.reclamaufbabeta.UsuarioActivity;
import br.com.reclamaufbabeta.modelo.Post;
import br.com.reclamaufbabeta.modelo.Usuario;

public class UsuarioController {
    private static UsuarioDAO usuarioDAO;
    private static UsuarioController instance;

    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static UsuarioController getInstance(Context context) {
        if (instance == null) {
            usuarioDAO = new UsuarioDAO(context);
        }
        return instance;
    }

    public UsuarioController(UsuarioActivity activity) {
        this.usuario = new Usuario();
        this.usuario.setNome(((EditText)activity.findViewById(R.id.usuario_nome)).getText().toString());;
        this.usuario.setEmail(((EditText)activity.findViewById(R.id.usuario_email)).getText().toString());;
        this.usuario.setSenha(((EditText)activity.findViewById(R.id.usuario_senha)).getText().toString());;

        //foto= (ImageView) activity.findViewById(R.id.formulario_foto);


    }

    public UsuarioController() {}



    public void insert(Usuario usuario) throws Exception {
        usuarioDAO.insert(usuario);
    }

    public void insert() throws Exception {
        usuarioDAO.insert(this.usuario);
    }
    public void update() throws Exception {
        usuarioDAO.update(this.usuario);
    }
    public List<Usuario> findAll() throws Exception {
        return usuarioDAO.findAll();
    }

    public static boolean Validacao(String email, String senha, Context context) throws Exception {
        UsuarioDAO usuarioDAO = new UsuarioDAO(context);
        Usuario usuario = usuarioDAO.findByLogin(email, senha);
        if (usuario == null || usuario.getEmail() == null || usuario.getSenha() == null) {
            return false;
        }
        String informado = email + senha;
        String esperado = usuario.getEmail() + usuario.getSenha();
        if (informado.equals(esperado)) {
            return true;
        }
        return false;
    }
}