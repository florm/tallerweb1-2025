package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioRegistroImpl implements ServicioRegistro {

    @Override
    public Usuario registrar(String email, String password) {

        if (password.length() < 6) {
            throw new PasswordInvalidaException();
        }
        return new Usuario();
    }
}
