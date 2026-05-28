package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioRegistroImpl implements ServicioRegistro {

    RepositorioRegistro repositorioRegistro;
    @Autowired
    public ServicioRegistroImpl(RepositorioRegistro repositorioRegistro) {
        this.repositorioRegistro = repositorioRegistro;
    }
    @Override
    public Usuario registrar(String email, String password) {
        if (password.length() < 6) {
            throw new PasswordInvalidaException();
        }
        if(repositorioRegistro.buscarPorMail(email) != null) {
            throw new UsuarioExistente();
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        this.repositorioRegistro.guardar(usuario);
        return usuario;
    }
}
