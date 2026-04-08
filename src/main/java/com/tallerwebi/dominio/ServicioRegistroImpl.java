package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrectaException;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class ServicioRegistroImpl implements ServicioRegistro {

    Usuario usuario = new Usuario();
    @Override
    public Usuario registrar(String email, String password) {
        if(password.length()<6){
            throw new PasswordLongitudIncorrectaException();
        }
        if(buscarUsuario(email) != null){
            throw new UsuarioExistente();
        }
        guardarUsuario(email, password);
        return usuario;
    }

    private void guardarUsuario(String email, String password) {
        usuario.setEmail(email);
        usuario.setPassword(password);
    }

    private Usuario buscarUsuario(String email) {
        if(Objects.equals(usuario.getEmail(), email)){
            return usuario;
        }else{
            return null;
        }
    }
}
