package org.example.datasensefx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.datasensefx.model.Rol;
import org.example.datasensefx.utils.SceneManager;
import org.example.datasensefx.utils.UserSession;

public class ConfigController {

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnDispositivos;

    @FXML
    private Button btnInformes;

    @FXML
    private Button btnConfiguracion;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblAdminInfo;

    /**
     * Método que se ejecuta automáticamente después de cargar el FXML
     */
    @FXML
    public void initialize() {
        // Configurar eventos del sidebar
        btnInicio.setOnAction(event -> handleInicio());
        btnDispositivos.setOnAction(event -> handleDispositivos());
        btnInformes.setOnAction(event -> handleInformes());
        btnConfiguracion.setOnAction(event -> handleConfiguracion());

        // Datos de sesión
        UserSession session = UserSession.getInstance();

        String email = session.getUserEmail();
        if (email == null || email.isEmpty()) {
            lblUserName.setText("Invitado ▼");
        } else {
            lblUserName.setText(email + " ▼");
        }

        Rol rol = session.getRol();

        // Defensa extra: solo el ADMIN debe poder estar aquí
        if (rol != Rol.ADMIN_PLATAFORMA) {
            showAlert("Permiso denegado",
                    "Solo el administrador de la plataforma puede acceder al panel de configuración.");

            try {
                SceneManager.switchScene("/org/example/datasensefx/views/dashboard-view.fxml",
                        "DataSense - Dashboard", 1000, 700);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        if (lblAdminInfo != null) {
            lblAdminInfo.setText("Estás gestionando la plataforma como administrador.");
        }
    }

    /**
     * Navega al dashboard
     */
    @FXML
    private void handleInicio() {
        try {
            SceneManager.switchScene("/org/example/datasensefx/views/dashboard-view.fxml",
                    "DataSense - Dashboard", 1000, 700);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar el dashboard");
        }
    }

    /**
     * Navega a Dispositivos
     */
    @FXML
    private void handleDispositivos() {
        try {
            SceneManager.switchScene("/org/example/datasensefx/views/devices-view.fxml",
                    "DataSense - Dispositivos", 1000, 700);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista de dispositivos");
        }
    }

    /**
     * Navega a Informes
     */
    @FXML
    private void handleInformes() {
        try {
            SceneManager.switchScene("/org/example/datasensefx/views/reports-view.fxml",
                    "DataSense - Informes", 1000, 700);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista de informes");
        }
    }

    /**
     * Ya estamos en configuración
     */
    @FXML
    private void handleConfiguracion() {
        System.out.println("Ya estás en Configuración");
    }

    /**
     * Cerrar sesión
     */
    @FXML
    private void handleLogout() {
        try {
            UserSession.getInstance().clearSession();

            SceneManager.switchScene("/org/example/datasensefx/views/login-view.fxml",
                    "DataSense - Iniciar Sesión", 500, 650);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cerrar sesión");
        }
    }

    /**
     * Mostrar alertas
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

