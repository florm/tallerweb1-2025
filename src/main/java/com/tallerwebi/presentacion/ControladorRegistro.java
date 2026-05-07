package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PasswordInvalidaException;
import com.tallerwebi.dominio.ServicioRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

    ServicioRegistro servicioRegistro;

    // busca en contructor que necesita inyectar
    // encuentra interfaz llamada ServicioRegistro
    // busca quien implemtenta ServicioRegistro y encuentra ServicioRegistroImpl
    // hace una copia de ServicioRegistroImpl y le agrega metodos y magia ServicioRegistroImplDeSpring
    // inyecta en el contructor ServicioRegistroImplDeSpring


    @Autowired
    public ControladorRegistro(ServicioRegistro servicioRegistro) {
        this.servicioRegistro = servicioRegistro;
    }

    public ModelAndView registrar(DatosRegistro datosRegistro) {
        ModelMap model = new ModelMap();
        if (datosRegistro.getMail().isEmpty()) {
            model.put("error", "El email es obligatorio");
            return new ModelAndView("registro", model);
        }
        if (datosRegistro.getPassword().isEmpty()) {
            model.put("error", "El password es obligatorio");
            return new ModelAndView("registro", model);
        }

        try{
            servicioRegistro.registrar(datosRegistro.getMail(), datosRegistro.getPassword());
        }
        catch(PasswordInvalidaException ex){
            model.put("error", "La contraseña debe tener al menos 6 caracteres");
            return new ModelAndView("registro", model);
        }


        model.put("mensaje", "El registro fue exitoso");
        return new ModelAndView("login", model);
    }
}
