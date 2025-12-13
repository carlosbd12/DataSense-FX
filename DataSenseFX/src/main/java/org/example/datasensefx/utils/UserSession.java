package org.example.datasensefx.utils;

import org.example.datasensefx.model.Rol;

public class UserSession {
    private static UserSession instance;

    private String userEmail;
    private String userName;
    private Rol rol;   // Rol del usuario logueado

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // --- Email ---
    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public String getUserEmail() {
        return userEmail;
    }

    // Nombre (por si luego lo usamos)
    public void setUserName(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return userName;
    }

    // Rol
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Rol getRol() {
        return rol;
    }

    // Utilidad
    public boolean isLoggedIn() {
        return userEmail != null;
    }

    public void clearSession() {
        userEmail = null;
        userName = null;
        rol = null;
    }
}