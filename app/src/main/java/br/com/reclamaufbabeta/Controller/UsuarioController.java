package br.com.reclamaufbabeta.Controller;

import android.content.Context;

import java.util.List;

import br.com.reclamaufbabeta.DAO.UsuarioDAO;
import br.com.reclamaufbabeta.modelo.Usuario;

public class UsuarioController {
    private static UsuarioDAO usuarioDAO;
    private static UsuarioController instance;

    public static UsuarioController getInstance(Context context) {
        if (instance == null) {
            instance = new UsuarioController();
            usuarioDAO = new UsuarioDAO(context);
        }
        return instance;
    }
    public void insert(Usuario usuario) throws Exception {
        usuarioDAO.insert(usuario);
    }
    public void update(Usuario usuario) throws Exception {
        usuarioDAO.update(usuario);
    }
    public List<Usuario> findAll() throws Exception {
        return usuarioDAO.findAll();
    }

    public boolean validacao(String login, String senha) throws Exception {
        Usuario usuario = usuarioDAO.findByLogin(login, senha);
        if (usuario == null || usuario.getUsuario() == null || usuario.getSenha() == null) {
            return false;
        }
        String informado = login + senha;
        String esperado = usuario.getUsuario() + usuario.getSenha();
        if (informado.equals(esperado)) {
            return true;
        }
        return false;
    }
}