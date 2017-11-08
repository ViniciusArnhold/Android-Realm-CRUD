package br.unisinos.db2realm.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Anuncio extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    private String titulo;

    private String descricao;

    private Date dataPublicacao;

    private double valor;

    private boolean disponivel;

    private String anuncianteID;

    public Anuncio() {

    }

    public Anuncio(String idAnunciante, String titulo, String descricao, double valor, boolean disponivel) {
        this.anuncianteID = idAnunciante;
        dataPublicacao = new Date();
        this.titulo = titulo;
        this.descricao = descricao;
        this.valor = valor;
        this.disponivel = disponivel;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Date getDataPublicacao() {
        return this.dataPublicacao;
    }

    public String getId() {
        return this.id;
    }

    public double getValor() {
        return this.valor;
    }

    public boolean isDisponivel() {
        return this.disponivel;
    }

    public Anunciante getAnunciante() {
        return Realm.getDefaultInstance().where(Anunciante.class)
                .equalTo("id", anuncianteID).findFirst();
    }

    public Anuncio setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public Anuncio setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Anuncio setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    public Anuncio setValor(double valor) {
        this.valor = valor;
        return this;
    }

    public Anuncio setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
        return this;
    }
}
