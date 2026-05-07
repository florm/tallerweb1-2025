package com.tallerwebi.dominio;


import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicioRegistroTest {

    /*
     1. el registro es exitoso si se ingresa email y password
     2. el registro falla si la contraseña tiene menos de 6 caracteres
    *
     */
    private final String email = "test@test.com";
    private final String passwordInvalida = "1234";
    private final String password = "1234567hhh";

    ServicioRegistro servicioRegistro = new ServicioRegistroImpl();

    @Test
    public void siIngresoEmailYPsswordElRegitroEsExitoso() {
        givenUsuarioNoExiste();
        Usuario usuarioCreado = whenRegistroUsuario(email, password);
        thenElRegistroEsExitoso(usuarioCreado);

    }

    @Test
    public void registroFallaSiLaPasswordTieneMenosDe6Caracteres() {
        givenUsuarioNoExiste();
        assertThrows(PasswordInvalidaException.class, ()-> whenRegistroUsuario(email, passwordInvalida));
//        Usuario usuarioCreado = whenRegistroUsuario(email, passwordInvalida);
//        thenElRegistroFalla(usuarioCreado);

    }

    private void thenElRegistroFalla(Usuario usuarioCreado) {
        assertThat(usuarioCreado, is(nullValue()));
    }

    private void thenElRegistroEsExitoso(Usuario usuarioCreado) {
        assertThat(usuarioCreado, is(notNullValue()));
    }

    private Usuario whenRegistroUsuario(String email, String password) {
        Usuario usuario = servicioRegistro.registrar(email, password);
        return usuario;
    }

    private void givenUsuarioNoExiste() {
    }
}
