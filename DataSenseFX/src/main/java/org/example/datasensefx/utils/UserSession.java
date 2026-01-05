package org.example.datasensefx.utils;

import org.example.datasensefx.model.Rol;
import org.example.datasensefx.model.User;

public class UserSession {
    private static UserSession instance;

    private User currentUser;

    // Mantener compatibilidad con código existente
    private String userEmail;
    private String userName;
    private Rol rol;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Establece el usuario actual de la sesión.
     *
     * @param user Usuario autenticado
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (user != null) {
            this.userEmail = user.getEmail();
            this.userName = user.getNombre();
            this.rol = user.getRol();
        } else {
            this.userEmail = null;
            this.userName = null;
            this.rol = null;
        }
    }

    /**
     * Obtiene el usuario actual de la sesión.
     *
     * @return Usuario actual o null si no hay sesión
     */
    public User getCurrentUser() {
        return currentUser;
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
        return currentUser != null || userEmail != null;
    }

    public void clearSession() {
        currentUser = null;
        userEmail = null;
        userName = null;
        rol = null;
    }
}