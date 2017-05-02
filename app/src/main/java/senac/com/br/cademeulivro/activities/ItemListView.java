package senac.com.br.cademeulivro.activities;

/**
 * Created by joaos on 01/04/2017.
 */

public class ItemListView {

    private String titulo;
    private String autor;
    private int capa;

    public ItemListView() {

    }

    public ItemListView(String titulo, String autor, int capa) {
        this.titulo = titulo;
        this.autor = autor;
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getCapa() {
        return capa;
    }

    public void setCapa(int capa) {
        this.capa = capa;
    }
}
