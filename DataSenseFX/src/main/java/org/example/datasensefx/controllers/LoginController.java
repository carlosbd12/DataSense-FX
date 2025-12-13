package org.example.datasensefx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.example.datasensefx.model.Rol;
import org.example.datasensefx.utils.SceneManager;
import org.example.datasensefx.utils.UserSession;

public class LoginController {

    @FXML
    private ComboBox<String> userComboBox;   // 👈 ahora usamos ComboBox

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        // Rellenamos el combo con los usuarios de demo
        userComboBox.getItems().addAll("operador", "gestor", "admin");
        userComboBox.getSelectionModel().selectFirst(); // selecciona el primero por defecto

        // ENTER en el combo → pasar al campo password
        userComboBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });

        // ENTER en password → hacer login
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });
    }

    @FXML
    private void handleLogin() {

        String usuario = userComboBox.getValue();
        String password = passwordField.getText();

        if (usuario == null || usuario.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Por favor selecciona un usuario y escribe la contraseña");
            return;
        }

        // Ahora authenticate devuelve un Rol (o null si las credenciales son incorrectas)
        Rol rol = authenticate(usuario, password);

        if (rol != null) {
            try {
                UserSession session = UserSession.getInstance();
                // Guardamos el "usuario" (operador/gestor/admin) como email para mostrarlo en la UI
                session.setUserEmail(usuario);
                session.setRol(rol);

                SceneManager.switchScene(
                        "/org/example/datasensefx/views/dashboard-view.fxml",
                        "DataSense - Dashboard",
                        1000,
                        700
                );
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "No se pudo cargar el dashboard");
            }
        } else {
            showAlert("Error", "Credenciales incorrectas");
        }
    }

    /**
     * Devuelve el rol asociado a las credenciales o null si son incorrectas.
     * Más adelante aquí irá la consulta real a la base de datos.
     */
    private Rol authenticate(String usuario, String password) {
        // Usuarios de prueba, uno por cada rol del sistema
        if (usuario.equals("operador") && password.equals("operador")) {
            return Rol.RESPONSABLE_PLANTA;
        }
        if (usuario.equals("gestor") && password.equals("gestor")) {
            return Rol.GESTOR_EDIFICIO;
        }
        if (usuario.equals("admin") && password.equals("admin")) {
            return Rol.ADMIN_PLATAFORMA;
        }

        // Credenciales incorrectas
        return null;
    }

    @FXML
    private void handleForgotPassword() {
        showAlert("Información", "Función de recuperación de contraseña en desarrollo");
    }

    @FXML
    private void handleCreateAccount() {
        showAlert("Información", "Función de registro en desarrollo");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
