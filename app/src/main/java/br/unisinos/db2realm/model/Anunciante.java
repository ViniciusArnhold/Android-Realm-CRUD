package br.unisinos.db2realm.model;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Anunciante extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    @Required
    private String nome;

    @Required
    private String sobrenome;

    @Required
    private String telefone;

    @Required
    private String email;

    @Required
    private Date dataInicio;

    private double classificacao;

    @Required
    private String nomeFantasia;

    public Anunciante() {

    }

    public Anunciante(String nome, String sobrenome, String telefone, String email, Date dataInicio, double classificacao, String nomeFantasia) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.email = email;
        this.dataInicio = dataInicio;
        this.classificacao = classificacao;
        this.nomeFantasia = nomeFantasia;
    }

    public String getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public double getClassificacao() {
        return this.classificacao;
    }

    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    @Override
    public String toString() {
        return nome + " " + sobrenome + " : " + nomeFantasia;
    }
}
