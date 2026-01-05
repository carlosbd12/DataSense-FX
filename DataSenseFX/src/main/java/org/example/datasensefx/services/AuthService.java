package org.example.datasensefx.services;

import org.example.datasensefx.dao.UserDAO;
import org.example.datasensefx.model.Rol;
import org.example.datasensefx.model.User;
import org.example.datasensefx.utils.UserSession;

import java.time.LocalDateTime;

/**
 * Servicio de autenticación y gestión de usuarios.
 */
public class AuthService {
    
    private final UserDAO userDAO;
    
    public AuthService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Autentica un usuario con username/email y contraseña.
     *
     * @param usernameOrEmail Username o email del usuario
     * @param password Contraseña en texto plano
     * @return Usuario autenticado o null si las credenciales son incorrectas
     */
    public User login(String usernameOrEmail, String password) {
        // Validar entrada
        if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
            System.out.println("❌ Username/Email vacío");
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("❌ Contraseña vacía");
            return null;
        }

        // Autenticar con la base de datos
        User user = userDAO.authenticate(usernameOrEmail.trim(), password);

        if (user != null) {
            // Guardar en sesión
            UserSession.getInstance().setCurrentUser(user);
            System.out.println("✅ Login exitoso: " + user.getUsername() + " - " + user.getEmail() + " (" + user.getRol() + ")");
        }

        return user;
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void logout() {
        UserSession.getInstance().clearSession();
        System.out.println("✅ Sesión cerrada");
    }

    /**
     * Verifica si hay un usuario autenticado.
     * 
     * @return true si hay sesión activa, false en caso contrario
     */
    public boolean isAuthenticated() {
        return UserSession.getInstance().getCurrentUser() != null;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param username Nombre de usuario único
     * @param email Email del usuario
     * @param nombre Nombre completo
     * @param password Contraseña en texto plano
     * @param rol Rol del usuario
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean registerUser(String username, String email, String nombre, String password, Rol rol) {
        // Validar username
        if (username == null || username.trim().isEmpty() || username.length() < 3) {
            System.out.println("❌ Username inválido (mínimo 3 caracteres)");
            return false;
        }

        // Validar email (simple)
        if (email == null || !email.contains("@")) {
            System.out.println("❌ Email inválido: " + email);
            return false;
        }

        // Validar contraseña (simple)
        if (password == null || password.length() < 3) {
            System.out.println("❌ Contraseña inválida (mínimo 3 caracteres)");
            return false;
        }

        // Validar nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("❌ Nombre vacío");
            return false;
        }

        // Verificar si el username o email ya existe
        if (userDAO.findByUsernameOrEmail(username) != null) {
            System.out.println("❌ El username ya está registrado: " + username);
            return false;
        }

        if (userDAO.findByEmail(email) != null) {
            System.out.println("❌ El email ya está registrado: " + email);
            return false;
        }

        // Crear usuario con contraseña en texto plano
        User newUser = new User(username, email, nombre, password, rol);
        newUser.setFechaCreacion(LocalDateTime.now());

        boolean created = userDAO.create(newUser);

        if (created) {
            System.out.println("✅ Usuario registrado: " + username);
        }

        return created;
    }

    /**
     * Cambia la contraseña del usuario actual.
     * 
     * @param currentPassword Contraseña actual
     * @param newPassword Nueva contraseña
     * @return true si se cambió correctamente, false en caso contrario
     */
    public boolean changePassword(String currentPassword, String newPassword) {
        User currentUser = UserSession.getInstance().getCurrentUser();

        if (currentUser == null) {
            System.out.println("❌ No hay sesión activa");
            return false;
        }

        // Verificar contraseña actual (comparación directa)
        if (!currentPassword.equals(currentUser.getPasswordHash())) {
            System.out.println("❌ Contraseña actual incorrecta");
            return false;
        }

        // Validar nueva contraseña (simple)
        if (newPassword == null || newPassword.length() < 3) {
            System.out.println("❌ Nueva contraseña inválida (mínimo 3 caracteres)");
            return false;
        }

        // Cambiar contraseña
        return userDAO.changePassword(currentUser.getId(), newPassword);
    }

    /**
     * Obtiene el usuario actualmente autenticado.
     * 
     * @return Usuario actual o null si no hay sesión
     */
    public User getCurrentUser() {
        return UserSession.getInstance().getCurrentUser();
    }
}

