package br.com.senac.cademeulivro.util.itemListView;

/**
 * Created by joaos on 02/07/2017.
 */

public class ItemListViewTagDialog {

    private String nome;
    private int cor;

    public ItemListViewTagDialog() {
    }

    public ItemListViewTagDialog(String nome, int cor) {
        this.nome = nome;
        this.cor = cor;
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

}