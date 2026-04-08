package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioFarmacia {
    List<Farmacia> buscarPorNombre(String nombre);

    List<Farmacia> buscarPorCalle(String calle);

    List<Farmacia> buscarPorNombreLocalidad(String nombreLocalidad);

    List<Farmacia> buscarPorLocalidad(Localidad localidad);
}
