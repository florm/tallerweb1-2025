package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Direccion;
import com.tallerwebi.dominio.Farmacia;
import com.tallerwebi.dominio.Localidad;
import com.tallerwebi.dominio.RepositorioFarmacia;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioFarmaciaTest {

    /*
    * 1. puedoObtenerFarmaciasPorNombre
    * 2. puedoObtenerFarmaciasPorCalle
    * 3. puedoObtenerFarmaciasPorNombreLocalidad
    * 4. puedoObtenerFarmaciasPorLocalidad
    *
    * */

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    RepositorioFarmacia repositorioFarmacia;

    @Test
    @Transactional
    @Rollback
    public void puedoObtenerFarmaciasPorNombreLike() {
        Localidad sanJusto = givenTengoUnaLocalidad("san justo");
        Direccion direccion1 = givenTengoUnaDireccion("rivadavia", sanJusto);
        Farmacia f1 = givenTengoUnaFarmacia("farmacity",direccion1);
        Farmacia f2 = givenTengoUnaFarmacia("farmaplus",direccion1);
        Farmacia f3 = givenTengoUnaFarmacia("chester",direccion1);
        List<Farmacia> farmaciasBuscadas = whenBuscoFarmaciasPorNombre("farma");
        thenObtengoUnaListaDeFarmacias(farmaciasBuscadas, 2);
    }

    private Farmacia givenTengoUnaFarmacia(String nombre, Direccion direccion) {
        Farmacia farmacia = new Farmacia();
        farmacia.setNombre(nombre);
        farmacia.setDireccion(direccion);
        sessionFactory.getCurrentSession().save(farmacia);
        return farmacia;
    }
    private List<Farmacia> whenBuscoFarmaciasPorNombre(String nombre) {
        List<Farmacia> listado = repositorioFarmacia.buscarPorNombre(nombre);
        return listado;
    }
    private void thenObtengoUnaListaDeFarmacias(List<Farmacia> listado, Integer cantidadEsperada) {
        assertThat(listado.size(), equalTo(cantidadEsperada));
    }

    @Test
    @Transactional
    @Rollback
    public void puedoObtenerFarmaciasPorCalle() {
        Localidad sanJusto = givenTengoUnaLocalidad("san justo");
        Direccion direccion1 = givenTengoUnaDireccion("rivadavia", sanJusto);
        Direccion direccion2 = givenTengoUnaDireccion("viedma", sanJusto);

        Farmacia f1 = givenTengoUnaFarmacia("farmacity", direccion1);
        Farmacia f2 = givenTengoUnaFarmacia("farmaplus", direccion1);
        Farmacia f3 = givenTengoUnaFarmacia("chester",direccion2);
        List<Farmacia> farmaciasBuscadas = whenBuscoFarmaciasPorCalle("viedma");
        thenObtengoUnaListaDeFarmacias(farmaciasBuscadas, 1);
    }

    private Direccion givenTengoUnaDireccion(String calle, Localidad localidad) {
        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setLocalidad(localidad);
        sessionFactory.getCurrentSession().save(direccion);
        return direccion;
    }

    private List<Farmacia> whenBuscoFarmaciasPorCalle(String calle) {
        return repositorioFarmacia.buscarPorCalle(calle);
    }

    @Test
    @Transactional
    @Rollback
    public void puedoObtenerFarmaciasPorNombreLocalidad() {
        Localidad sanJusto = givenTengoUnaLocalidad("san justo");
        Localidad haedo = givenTengoUnaLocalidad("haedo");

        Direccion direccion1 = givenTengoUnaDireccion("rivadavia", sanJusto);
        Direccion direccion2 = givenTengoUnaDireccion("viedma", haedo);

        Farmacia f1 = givenTengoUnaFarmacia("farmacity", direccion1);
        Farmacia f2 = givenTengoUnaFarmacia("farmaplus", direccion1);
        Farmacia f3 = givenTengoUnaFarmacia("chester",direccion2);

        List<Farmacia> buscadas = whenBuscoFarmaciasPorNombreLocalidad("haedo");
        thenObtengoUnaListaDeFarmacias(buscadas, 1);
    }

    private List<Farmacia> whenBuscoFarmaciasPorNombreLocalidad(String nombreLocalidad) {
        return repositorioFarmacia.buscarPorNombreLocalidad(nombreLocalidad);
    }

    private Localidad givenTengoUnaLocalidad(String nombreLocalidad) {
        Localidad localidad = new Localidad();
        localidad.setNombre(nombreLocalidad);
        sessionFactory.getCurrentSession().save(localidad);
        return localidad;
    }

    @Test
    @Transactional
    @Rollback
    public void puedoObtenerFarmaciasPorLocalidad() {
        Localidad sanJusto = givenTengoUnaLocalidad("san justo");
        Localidad haedo = givenTengoUnaLocalidad("haedo");

        Direccion direccion1 = givenTengoUnaDireccion("rivadavia", sanJusto);
        Direccion direccion2 = givenTengoUnaDireccion("viedma", haedo);

        Farmacia f1 = givenTengoUnaFarmacia("farmacity", direccion1);
        Farmacia f2 = givenTengoUnaFarmacia("farmaplus", direccion1);
        Farmacia f3 = givenTengoUnaFarmacia("chester",direccion2);

        List<Farmacia> buscadas = whenBuscoFarmaciasPorLocalidad(sanJusto);
        thenObtengoUnaListaDeFarmacias(buscadas, 2);
    }

    private List<Farmacia> whenBuscoFarmaciasPorLocalidad(Localidad localidad) {
        return repositorioFarmacia.buscarPorLocalidad(localidad);
    }

}
