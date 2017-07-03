package br.com.senac.cademeulivro.model;

import java.io.Serializable;

public class Tag implements Serializable{
    private Integer idTag;
    private String nomeTag;
    private String corHex;
    private int totalUsos;

    public Integer getIdTag() {
        return idTag;
    }

    public void setIdTag(Integer idTag) {
        this.idTag = idTag;
    }

    public String getNomeTag() {
        return nomeTag;
    }

    public void setNomeTag(String nomeTag) {
        this.nomeTag = nomeTag;
    }

    public String getCorHex() {
        return corHex;
    }

    public void setCorHex(String corHex) {
        this.corHex = corHex;
    }

    public int getTotalUsos() {
        return totalUsos;
    }

    public void setTotalUsos(int totalUsos) {
        this.totalUsos = totalUsos;
    }
}
