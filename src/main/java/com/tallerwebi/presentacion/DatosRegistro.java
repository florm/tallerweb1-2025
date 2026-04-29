package com.tallerwebi.presentacion;

import java.util.Collection;

public class DatosRegistro {

    private String mail;
    private String password;

    public DatosRegistro(String email, String password) {
        this.mail = email;
        this.password = password;
    }

    public DatosRegistro() {}

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
