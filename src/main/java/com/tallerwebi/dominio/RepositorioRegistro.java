package com.tallerwebi.dominio;

public interface RepositorioRegistro {
    void guardar(Usuario usuario);

    Usuario buscarPorMail(String mail);
}
