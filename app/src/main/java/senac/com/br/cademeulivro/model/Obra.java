package senac.com.br.cademeulivro.model;

import java.util.List;

public class Obra {
    private Integer idObra;
    private int numerodePaginas;
    private String autor;
    private String editora;
    private List<Tag> tags;

    public Integer getIdObra() {
        return idObra;
    }

    public void setIdObra(Integer idObra) {
        this.idObra = idObra;
    }

    public int getNumerodePaginas() {
        return numerodePaginas;
    }

    public void setNumerodePaginas(int numerodePaginas) {
        this.numerodePaginas = numerodePaginas;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
