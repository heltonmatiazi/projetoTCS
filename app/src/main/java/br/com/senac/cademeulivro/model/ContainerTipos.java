package br.com.senac.cademeulivro.model;

import java.io.Serializable;

public class ContainerTipos implements Serializable{
    private Integer _id;
    private String tipoNome;
    private Integer tipoIcone;


    public ContainerTipos(Integer _id, String tipoNome, Integer tipoIcone) {
        this._id = _id;
        this.tipoNome = tipoNome;
        this.tipoIcone = tipoIcone;
    }

    public ContainerTipos(Integer _id) {
        this._id = _id;
    }

    public ContainerTipos() {
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getTipoNome() {
        return tipoNome;
    }

    public void setTipoNome(String tipoNome) {
        this.tipoNome = tipoNome;
    }

    public Integer getTipoIcone() {
        return tipoIcone;
    }

    public void setTipoIcone(Integer tipoIcone) {
        this.tipoIcone = tipoIcone;
    }
}
