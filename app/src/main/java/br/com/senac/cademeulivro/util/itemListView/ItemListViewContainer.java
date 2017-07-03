package br.com.senac.cademeulivro.util.itemListView;

import java.util.Date;

/**
 * Created by joaos on 03/07/2017.
 */

public class ItemListViewContainer {

    private String nomeContainer;
    private int icone;
    private Date ultimaLimpeza;
    private String local;
    private int totalObra;

    public ItemListViewContainer() {

    }

    public ItemListViewContainer(String nomeContainer, int icone, Date ultimaLimpeza, String local, int totalObra) {
        this.nomeContainer = nomeContainer;
        this.icone = icone;
        this.ultimaLimpeza = ultimaLimpeza;
        this.local = local;
        this.totalObra = totalObra;
    }

    public String getNomeContainer() {
        return nomeContainer;
    }

    public void setNomeContainer(String nomeContainer) {
        this.nomeContainer = nomeContainer;
    }

    public int getIcone() {
        return icone;
    }

    public void setIcone(int icone) {
        this.icone = icone;
    }

    public Date getUltimaLimpeza() {
        return ultimaLimpeza;
    }

    public void setUltimaLimpeza(Date ultimaLimpeza) {
        this.ultimaLimpeza = ultimaLimpeza;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getTotalObra() {
        return totalObra;
    }

    public void setTotalObra(int totalObra) {
        this.totalObra = totalObra;
    }
}
