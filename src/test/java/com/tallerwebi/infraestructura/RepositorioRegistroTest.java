package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioRegistro;
import com.tallerwebi.dominio.Usuario;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioRegistroTest {

    /*
      1. sePuedeGuardarUnUsuario
    * 2. sePuedeBuscarUsuarioPorMail
    * */

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    RepositorioRegistro repositorioRegistro;

    @Test
    @Transactional
    @Rollback
    public void sePuedeGuardarUnUsuario() {
        //given
        Usuario usuario = new Usuario();
        usuario.setEmail("flor@gmail.com");
        usuario.setPassword("1234567");

        //when
        repositorioRegistro.guardar(usuario);
        //then
        assertThat(usuario.getId(), notNullValue());
    }

    @Test
    @Transactional
    @Rollback
    public void sePuedeBuscarUsuarioPorMail() {
        //given
        Usuario usuario1 = new Usuario();
        usuario1.setEmail("flor@gmail.com");
        usuario1.setPassword("1234567");

        this.sessionFactory.getCurrentSession().save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("flor2@gmail.com");
        usuario2.setPassword("abcdefg");

        this.sessionFactory.getCurrentSession().save(usuario2);
        //when
        Usuario usuarioBuscado =  repositorioRegistro.buscarPorMail("flor2@gmail.com");
        //then
        assertThat(usuarioBuscado, notNullValue());
        assertThat(usuarioBuscado.getPassword(), equalToIgnoringCase("abcdefg"));

    }
}
