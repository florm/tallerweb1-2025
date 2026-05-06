package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ControladorRegistroTest {

    private final String email = "flor@mail.com";
    private final String password = "1234567";
    private final String passwordMenos6Caracteres = "1234";

    /*
    * 1. el registro es exitoso si se ingresa email y password
    * 2. el registro falla si no ingreso mail
    * 3. el registro falla si no ingreso password
    * 4. el registro falla si la password y la repeticion de password no coinciden
    * 5. el registro falla si el mail no tiene formato valido
    *
    *
    * 6. el registro falla si la contraseña tiene menos de 6 caracteres
    * 7. el registro falla si ya existe un usuario con el mismo email
    * */

    ControladorRegistro controladorRegistro = new ControladorRegistro();

    @Test
    public void siSeIngresaEmailYPasswordElRegistroExitoso() {
        //preparacion -> given
        givenNoExisteUsuario();
        DatosRegistro datosRegistro = new DatosRegistro(email, password);
        //ejecucion -> when
        ModelAndView modelAndView = whenRegistroUsuario(datosRegistro);
        //comprobacion -> then
        thenElRegistroEsExitoso(modelAndView);

    }

    private void thenElRegistroEsExitoso(ModelAndView modelAndView) {
        assertThat( modelAndView.getViewName(), equalToIgnoringCase("login"));
        assertThat(modelAndView.getModel().get("mensaje").toString(), equalToIgnoringCase("El registro fue exitoso"));
    }

    private ModelAndView whenRegistroUsuario(DatosRegistro datosRegistro) {
        ModelAndView mav = controladorRegistro.registrar(datosRegistro);
        return mav;
    }

    private void givenNoExisteUsuario() {

    }

    @Test
    public void elRegistroFallaSiNoIngresoMail() {
        givenNoExisteUsuario();
        DatosRegistro datosRegistro = new DatosRegistro("", password);
        ModelAndView modelAndView = whenRegistroUsuario(datosRegistro);
        thenElRegistroFalla(modelAndView, "El email es obligatorio");
    }

    private void thenElRegistroFalla(ModelAndView modelAndView, String mensaje) {
        assertThat( modelAndView.getViewName(), equalToIgnoringCase("registro"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase(mensaje));
    }

    @Test
    public void elRegistroFallaSiNoIngresoPassword() {
        givenNoExisteUsuario();
        DatosRegistro datosRegistro = new DatosRegistro(email, "");
        ModelAndView modelAndView = whenRegistroUsuario(datosRegistro);
        thenElRegistroFalla(modelAndView, "El password es obligatorio");
    }

    @Test
    public void elRegistroFallaSiLaPasswordTieneMenosDe6Caracteres() {
        givenNoExisteUsuario();
        DatosRegistro datosRegistro = new DatosRegistro(email, passwordMenos6Caracteres);
        ModelAndView modelAndView = whenRegistroUsuario(datosRegistro);
        thenElRegistroFalla(modelAndView, "La contraseña debe tener al menos 6 caracteres");

    }
}
