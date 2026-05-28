package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioRegistro;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioRegistroImpl implements RepositorioRegistro {


    SessionFactory sessionFactory;

    @Autowired
    public RepositorioRegistroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Usuario usuario) {
        this.sessionFactory.getCurrentSession().save(usuario);
    }

    @Override
    public Usuario buscarPorMail(String mail) {
        return (Usuario) this.sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", mail))
                .uniqueResult();
    }
}
