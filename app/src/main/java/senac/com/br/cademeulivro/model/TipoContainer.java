package senac.com.br.cademeulivro.model;

public class TipoContainer {
    private Integer _id;
    private String tipoNome;
    private String tipoIcone;

    public TipoContainer(String tipoNome, String tipoIcone) {
        this.tipoNome = tipoNome;
        this.tipoIcone = tipoIcone;
    }

    public TipoContainer() {
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

    public String getTipoIcone() {
        return tipoIcone;
    }

    public void setTipoIcone(String tipoIcone) {
        this.tipoIcone = tipoIcone;
    }
}
