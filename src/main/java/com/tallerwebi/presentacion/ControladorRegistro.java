package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

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

        if (datosRegistro.getPassword().length() < 6) {
            model.put("error", "La contraseña debe tener al menos 6 caracteres");
            return new ModelAndView("registro", model);
        }

        model.put("mensaje", "El registro fue exitoso");
        return new ModelAndView("login", model);
    }
}
