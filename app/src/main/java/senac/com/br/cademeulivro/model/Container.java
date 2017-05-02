package senac.com.br.cademeulivro.model;

import java.util.Date;
import java.util.List;

public class Container {
    private Integer idContainer;
    private String nomeContainer;
    private int iconeContainer;
    private String localContainer;
    private Date ultimaModificacao;
    private List<Obra> obrasContidas;

    public Container() {

    }

    public List<Obra> getObrasContidas() {
        return obrasContidas;
    }

    public void setObrasContidas(List<Obra> obrasContidas) {
        this.obrasContidas = obrasContidas;
    }

    public Integer getIdContainer() {
        return idContainer;
    }

    public void setIdContainer(Integer idContainer) {
        this.idContainer = idContainer;
    }

    public String getNomeContainer() {
        return nomeContainer;
    }

    public void setNomeContainer(String nomeContainer) {
        this.nomeContainer = nomeContainer;
    }

    public int getIconeContainer() {
        return iconeContainer;
    }

    public void setIconeContainer(int iconeContainer) {
        this.iconeContainer = iconeContainer;
    }

    public String getLocalContainer() {
        return localContainer;
    }

    public void setLocalContainer(String localContainer) {
        this.localContainer = localContainer;
    }

    public Date getUltimaModificacao() {
        return ultimaModificacao;
    }

    public void setUltimaModificacao(Date ultimaModificacao) {
        this.ultimaModificacao = ultimaModificacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Container container = (Container) o;

        return idContainer.equals(container.idContainer);

    }

    @Override
    public int hashCode() {
        return idContainer.hashCode();
    }
}
