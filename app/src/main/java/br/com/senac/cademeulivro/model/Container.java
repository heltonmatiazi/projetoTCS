package br.com.senac.cademeulivro.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Container implements Serializable {
    private Integer idContainer;
    private Integer idBiblioteca;
    private String nomeContainer;
    private String localContainer;
    private Date ultimaModificacao;
    private List<Obra> obrasContidas;
    private ContainerTipos mContainerTipos;
    private Integer totalObras;

    public Container() {

    }

    public Integer getTotalObras() {
        if(getObrasContidas() == null) {
            return 0;
        } else {
            return getObrasContidas().size();
        }
    }

    public void setTotalObras(Integer totalObras) {
        this.totalObras = totalObras;
    }

    public ContainerTipos getContainerTipos() {
        return mContainerTipos;
    }

    public void setContainerTipos(ContainerTipos containerTipos) {
        this.mContainerTipos = containerTipos;
    }

    public Integer getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(Integer idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
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
