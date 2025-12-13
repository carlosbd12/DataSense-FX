package org.example.datasensefx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.datasensefx.model.Rol;
import org.example.datasensefx.utils.SceneManager;
import org.example.datasensefx.utils.UserSession;

public class ReportsController {

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

    /**
     * Método que se ejecuta automáticamente después de cargar el FXML
     */
    @FXML
    public void initialize() {
        // Configurar eventos de los botones del sidebar
        btnInicio.setOnAction(event -> handleInicio());
        btnDispositivos.setOnAction(event -> handleDispositivos());
        btnInformes.setOnAction(event -> handleInformes());
        btnConfiguracion.setOnAction(event -> handleConfiguracion());

        // Datos de sesión
        UserSession session = UserSession.getInstance();

        // Mostrar email del usuario arriba a la derecha
        String email = session.getUserEmail();
        if (email == null || email.isEmpty()) {
            lblUserName.setText("Invitado ▼");
        } else {
            lblUserName.setText(email + " ▼");
        }

        // Ajustar botones según el rol
        Rol rol = session.getRol();
        if (rol != null) {
            if (rol == Rol.GESTOR_EDIFICIO) {
                // El gestor solo debería moverse por informes
                btnDispositivos.setDisable(true);
            }
            if (rol != Rol.ADMIN_PLATAFORMA) {
                // Solo el admin puede tocar Configuración
                btnConfiguracion.setDisable(true);
            }
        } else {
            // Sin rol → por seguridad, sin configuración
            btnConfiguracion.setDisable(true);
        }
    }

    /**
     * Maneja el click en el botón "Inicio"
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
     * Maneja el click en el botón "Dispositivos"
     * Navega a la vista de dispositivos
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
     * Maneja el click en el botón "Informes"
     * Ya estamos en informes
     */
    @FXML
    private void handleInformes() {
        System.out.println("Ya estás en Informes");
    }

    /**
     * Maneja el click en el botón "Configuración"
     * Solo el ADMIN puede acceder
     */
    @FXML
    private void handleConfiguracion() {
        Rol rol = UserSession.getInstance().getRol();

        if (rol != Rol.ADMIN_PLATAFORMA) {
            showAlert("Permiso denegado",
                    "Solo el administrador de la plataforma puede acceder a la configuración.");
            return;
        }

        try {
            SceneManager.switchScene("/org/example/datasensefx/views/config-view.fxml",
                    "DataSense - Configuración", 1000, 700);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista de configuración");
        }
    }

    /**
     * Maneja el click en cualquiera de los botones "Ver informe"
     */
    @FXML
    private void handleVerInforme() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pendiente de implementación");
        alert.setHeaderText(null);
        alert.setContentText("Esta funcionalidad de visualización de informes está pendiente de implementación.");
        alert.showAndWait();
    }

    /**
     * Maneja el click en el botón "Cerrar Sesión"
     * Regresa a la pantalla de login
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
     * Método auxiliar para mostrar alertas de error
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

