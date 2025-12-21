package org.example.datasensefx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.example.datasensefx.model.Report;
import org.example.datasensefx.model.Rol;
import org.example.datasensefx.utils.SceneManager;
import org.example.datasensefx.utils.UserSession;

import java.time.format.DateTimeFormatter;

/**
 * Controlador para la vista de detalle de un informe
 */
public class ReportDetailController {
    
    @FXML
    private Button btnInicio;
    
    @FXML
    private Button btnDispositivos;
    
    @FXML
    private Button btnInformes;
    
    @FXML
    private Button btnConfiguracion;
    
    @FXML
    private Button btnVolver;
    
    @FXML
    private Button btnExportPDF;
    
    @FXML
    private Label lblUserName;
    
    @FXML
    private Label lblReportTitle;
    
    @FXML
    private Label lblReportType;
    
    @FXML
    private Label lblReportPeriod;
    
    @FXML
    private Label lblGeneratedAt;
    
    @FXML
    private TextArea txtReportContent;
    
    private Report currentReport;
    
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
        
        // Mostrar email del usuario
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
                btnDispositivos.setDisable(true);
            }
            if (rol != Rol.ADMIN_PLATAFORMA) {
                btnConfiguracion.setDisable(true);
            }
        } else {
            btnConfiguracion.setDisable(true);
        }
    }
    
    /**
     * Establece el informe a mostrar
     */
    public void setReport(Report report) {
        this.currentReport = report;
        displayReport();
    }
    
    /**
     * Muestra el contenido del informe en la interfaz
     */
    private void displayReport() {
        if (currentReport == null) {
            txtReportContent.setText("No hay informe para mostrar.");
            return;
        }
        
        // Actualizar labels
        lblReportTitle.setText(currentReport.getTitle());
        lblReportType.setText(currentReport.getType().getDisplayName());
        lblReportPeriod.setText("Período: " + currentReport.getPeriod());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        lblGeneratedAt.setText("Generado: " + currentReport.getGeneratedAt().format(formatter));
        
        // Mostrar contenido del informe
        String content = currentReport.generateContent();
        txtReportContent.setText(content);
    }
    
    /**
     * Maneja el click en el botón "Volver"
     */
    @FXML
    private void handleVolver() {
        try {
            SceneManager.switchScene("/org/example/datasensefx/views/reports-view.fxml",
                    "DataSense - Informes", 1000, 700);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo volver a la vista de informes");
        }
    }
    
    /**
     * Maneja el click en el botón "Exportar PDF"
     */
    @FXML
    private void handleExportPDF() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exportar PDF");
        alert.setHeaderText(null);
        alert.setContentText("La funcionalidad de exportación a PDF estará disponible próximamente.");
        alert.showAndWait();
    }
    
    /**
     * Navega al Dashboard
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
        handleVolver();
    }
    
    /**
     * Navega a Configuración
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
     * Cierra sesión
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
     * Muestra una alerta de error
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

