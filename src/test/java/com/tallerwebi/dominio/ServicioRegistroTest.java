package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrectaException;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicioRegistroTest {



    /*
    * un usuario se puede registrar si tiene email y password
    * si la password tiene menos de 6 caracteres el registro falla
    * si el usuario existe el registro falla
    * */

    ServicioRegistro servicioRegistro = new ServicioRegistroImpl();

    @Test
    public void siElUsuarioTieneEmailYPasswordElRegistroEsExitoso() {
        givenNoExisteUsuario();
        Usuario usuarioCreado = whenRegistroUsuarioCon("flor@gmail.com", "123456");
        thenElRegistroEsExitoso(usuarioCreado);
    }

    @Test
    public void siLaPasswordTieneMenosDeSeisCaracteresElRegistroFalla() {
        givenNoExisteUsuario();
//        Usuario usuarioCreado = whenRegistroUsuarioCon("flor@gmail.com", "1234");
//        thenElRegistroFalla(usuarioCreado);
        assertThrows(
                PasswordLongitudIncorrectaException.class,
                ()-> whenRegistroUsuarioCon("flor@gmail.com", "1234")
        );

    }

    @Test
    public void siUsuarioExisteElRegistroFalla() {
        String email = "flor@gmail.com";
        String password = "123456";
        givenExisteUsuario(email, password);
//        Usuario usuarioCreado = whenRegistroUsuarioCon(email, password);
//        thenElRegistroFalla(usuarioCreado);
        assertThrows(
                UsuarioExistente.class,
                ()-> whenRegistroUsuarioCon(email, password)
        );
    }

    private void givenExisteUsuario(String email, String password) {
        servicioRegistro.registrar(email, password);
    }

    private void givenNoExisteUsuario() {

    }

    private Usuario whenRegistroUsuarioCon(String email, String password) {
        return servicioRegistro.registrar(email, password);
    }

    private void thenElRegistroEsExitoso(Usuario usuario) {
        assertThat(usuario, notNullValue());
    }

    private void thenElRegistroFalla(Usuario usuario) {
        assertThat(usuario, nullValue());
    }



}
