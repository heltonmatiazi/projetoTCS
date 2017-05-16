package br.com.senac.cademeulivro.util.itemListView;

/**
 * Created by joaos on 23/04/2017.
 */

public class ItemListViewTags {

    private String nome;
    private int usos;
    private int cor;

    public ItemListViewTags() {
    }

    public ItemListViewTags(String nome, int cor) {
        this.nome = nome;
        this.cor= cor;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getUsos() {
        return usos;
    }

    public void setUsos(int usos) {
        this.usos = usos;
    }
}
