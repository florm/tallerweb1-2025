package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioRegistro;
import com.tallerwebi.dominio.ServicioRegistroImpl;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrectaException;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorRegistroTest {



    /*
    * El usuario se puede registrar con email y password
    * Si no existe email el registro deberia fallar
    * Si no existe pass el registro falla
    *
    * */
    ServicioRegistro servicioRegistro = mock(ServicioRegistro.class);
    ControladorRegistro controladorRegistro = new ControladorRegistro(servicioRegistro);

    @Test
    public void elUsuarioSePuedeRegistrarConEmailYPassword() {
        //preparacion --> given
        givenNoExisteUsuario();
        //ejecucion --> when
        ModelAndView mav = whenRegistroUsuarioCon("flor@gmail.com", "1234", "1234");
        //comprobacion --> then
        thenElRegistroEsExitoso(mav);
    }

    @Test
    public void siNoExisteMailElRegistroFalla() {
        //preparacion --> given
        givenNoExisteUsuario();
        //ejecucion
        ModelAndView mav = whenRegistroUsuarioCon("", "1234", "1234");
        //comprobacion
        thenElRegistroFalla(mav, "El mail es obligatorio");
    }

    @Test
    public void siLaPasswordEsDistintaARepeticionDePasswordElReigstroFalla() {
        //preparacion --> given
        givenNoExisteUsuario();
        //ejecucion
        ModelAndView mav = whenRegistroUsuarioCon("flor@gmail.com", "1234", "12345");
        //comprobacion
        thenElRegistroFalla(mav, "El password no coincide");

    }

    @Test
    public void siLaPasswordTieneMenosDeSeisCaracteresElRegistroFalla() {
        givenNoExisteUsuario();
        doThrow(PasswordLongitudIncorrectaException.class)
                .when(servicioRegistro).registrar(anyString(), anyString());
        ModelAndView mav = whenRegistroUsuarioCon("flor@gmail.com", "1234", "1234");
        thenElRegistroFalla(mav, "La password debe tener al menos 6 caracteres");
    }

    private void thenElRegistroFalla(ModelAndView mav, String mensaje) {
        assertThat(mav.getViewName(), equalToIgnoringCase("registro"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase(mensaje));
    }

    private ModelAndView whenRegistroUsuarioCon(String email, String password, String repeticionPassword){
        ModelAndView mav =  controladorRegistro.registrar(email, password, repeticionPassword);
        return mav;
    }

    private void givenNoExisteUsuario() {

    }

    private void thenElRegistroEsExitoso(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("login"));
    }



}
