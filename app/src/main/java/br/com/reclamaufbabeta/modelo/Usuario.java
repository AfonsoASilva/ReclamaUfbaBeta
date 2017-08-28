package br.com.reclamaufbabeta.modelo;

public class Usuario {
    private Integer id;
    private String email;
    private String senha;
    private String nome;

    public Usuario(Integer id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;

        this.email = email;
        this.senha = senha;
    }
    public Usuario(){}

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}