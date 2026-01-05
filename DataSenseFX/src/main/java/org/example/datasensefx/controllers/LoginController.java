package org.example.datasensefx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.example.datasensefx.model.User;
import org.example.datasensefx.services.AuthService;
import org.example.datasensefx.utils.SceneManager;

public class LoginController {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private Button loginButton;

    private AuthService authService;

    @FXML
    public void initialize() {
        authService = new AuthService();

        // ENTER en el campo email → pasar al campo password
        emailTextField.setOnKeyPressed(event -> {
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
        String email = emailTextField.getText();
        String password = passwordField.getText();

        if (email == null || email.trim().isEmpty() || password.isEmpty()) {
            showAlert("Error", "Por favor ingresa tu email y contraseña");
            return;
        }

        // Autenticar con la base de datos
        User user = authService.login(email.trim(), password);

        if (user != null) {
            try {
                // La sesión ya fue guardada por AuthService.login()
                SceneManager.switchScene(
                        "/org/example/datasensefx/views/dashboard-view.fxml",
                        "DataSense - Dashboard",
                        1000,
                        700
                );
                System.out.println("✅ Login exitoso: " + user.getEmail() + " - " + user.getRol());
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "No se pudo cargar el dashboard: " + e.getMessage());
            }
        } else {
            showAlert("Error", "Email o contraseña incorrectos");
        }
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
