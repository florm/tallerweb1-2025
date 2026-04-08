package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioRegistro;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrectaException;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

    ServicioRegistro servicioRegistro;

    @Autowired
    public ControladorRegistro(ServicioRegistro servicioRegistro) {
        this.servicioRegistro = servicioRegistro;
    }


    public ModelAndView registrar(String mail, String password, String repeticionPassword) {
        ModelMap model = new ModelMap();
        if(mail.isEmpty()){
            return registroFallido(model,"El mail es obligatorio" );
        }
        if(!password.equals(repeticionPassword)){
            return registroFallido(model,"El password no coincide");
        }

        try{
            servicioRegistro.registrar(mail, password);
        }catch (PasswordLongitudIncorrectaException ex){
            return registroFallido(model,"La password debe tener al menos 6 caracteres");
        }


        return new ModelAndView("login");
    }

    private ModelAndView registroFallido(ModelMap model, String mensaje){
        model.put("error", mensaje);
        return new ModelAndView("registro", model);
    }
}
