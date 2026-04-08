package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String calle;

    @ManyToOne
    private Localidad localidad;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCalle() {
        return calle;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Localidad getLocalidad() {
        return localidad;
    }
}
