package br.com.senac.cademeulivro.model;

import java.util.List;

public class Biblioteca {
    private Integer idBiblioteca;
    private List<Container> containers;

    public Integer getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(Integer idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }
}
