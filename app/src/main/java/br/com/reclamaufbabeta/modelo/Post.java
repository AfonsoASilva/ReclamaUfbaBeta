package br.com.reclamaufbabeta.modelo;

import android.location.Location;

import java.io.Serializable;
import java.sql.Timestamp;

public class Post implements Serializable {

    private int id;
    private int id_usuario;
    private String descricao;
    private String titulo;
    private int like;
    private int deslike;
    private Timestamp dataHora;
    private Location localizacao;
    private byte[] photoBytes;

    public Location getLocalizacao() {
        return localizacao;
    }

    public byte[] getPhotoBytes() {
        return photoBytes;
    }

    public void setLocalizacao(Location localizacao) {
        this.localizacao = localizacao;
    }


    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDeslike() {
        return deslike;
    }

    public void setDeslike(int deslike) {
        this.deslike = deslike;
    }
    @Override
    public String toString(){
        return this.getId()+"-"+this.getTitulo();
    }

    public void setPhotoBytes(byte[] photoBytes) {
        this.photoBytes = photoBytes;
    }
}
