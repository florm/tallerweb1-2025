package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Farmacia;
import com.tallerwebi.dominio.Localidad;
import com.tallerwebi.dominio.RepositorioFarmacia;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioFarmaciaImpl implements RepositorioFarmacia {

    @Autowired
    SessionFactory sessionFactory;

    /*
    * SELECT * FROM Farmacia where nombre like 'farma%'
    * */

    @Override
    public List<Farmacia> buscarPorNombre(String nombre) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Farmacia.class)
                .add(Restrictions.like("nombre", nombre + '%' ))
                .list();

    }

    /*
    * SELECT * FROM Farmacia f join Direccion d
    *   ON f.idDireccion = d.Id
    *   where d.Calle = 'viedma'
    * */

    @Override
    public List<Farmacia> buscarPorCalle(String calle) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Farmacia.class)
                .createAlias("direccion", "d")
                .add(Restrictions.eq("d.calle", calle))
                .list();

    }

    @Override
    public List<Farmacia> buscarPorNombreLocalidad(String nombreLocalidad) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Farmacia.class)
                .createAlias("direccion", "d")
                .createAlias("d.localidad", "l")
                .add(Restrictions.eq("l.nombre", nombreLocalidad))
                .list();
    }

    @Override
    public List<Farmacia> buscarPorLocalidad(Localidad localidad) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Farmacia.class)
                .createAlias("direccion", "d")
                .add(Restrictions.eq("d.localidad", localidad))
                .list();

    }

    /*
    * FARMACIA1 -> DIRECCION1 -> SAN JUSTO
    *
    * FARMACIA2 -> DIRECCION2 -> SAN JUSTO
    *
    * FARMACIA3 -> DIRECCION3 -> HAEDO
    *
    *
    * */
}
