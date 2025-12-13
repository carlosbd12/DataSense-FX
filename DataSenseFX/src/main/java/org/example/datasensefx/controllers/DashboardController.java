package org.example.datasensefx.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import org.example.datasensefx.model.Measurement;
import org.example.datasensefx.model.Rol;
import org.example.datasensefx.services.DataLoader;
import org.example.datasensefx.services.MainController;
import org.example.datasensefx.utils.FxChartUtils;
import org.example.datasensefx.utils.SceneManager;
import org.example.datasensefx.utils.UserSession;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardController {

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
    private Label lblUserRole;   // Label para mostrar el rol

    // Labels para estadísticas generales
    @FXML
    private Label lblTotalMediciones;

    @FXML
    private Label lblConsumoPromedio;

    @FXML
    private Label lblRangoConsumo;

    @FXML
    private Label lblCO2Total;

    @FXML
    private Label lblPicoConsumo;

    @FXML
    private Label lblDatasetInfo;

    // Contenedores para los gráficos energéticos
    @FXML
    private VBox consumoPorHoraChart;

    @FXML
    private VBox consumoPorTipoCargaChart;

    @FXML
    private VBox consumoPorDiaChart;

    @FXML
    private VBox co2PorDiaChart;

    @FXML
    private VBox weekdayVsWeekendChart;

    // DatePicker para seleccionar fecha del gráfico de consumo por hora
    @FXML
    private DatePicker datePickerConsumo;

    // ToggleButtons para cambiar entre Total y Promedio
    @FXML
    private ToggleButton btnTotalConsumo;

    @FXML
    private ToggleButton btnPromedioConsumo;

    @FXML
    private ToggleButton btnTotalCO2;

    @FXML
    private ToggleButton btnPromedioCO2;

    @FXML
    private ToggleButton btnTotalTipoCarga;

    @FXML
    private ToggleButton btnPromedioTipoCarga;

    // Controlador de lógica de negocio energética
    private MainController energyController;
    private List<Measurement> energyData;

    /*
     * Método que se ejecuta automáticamente después de cargar el FXML
     */
    @FXML
    public void initialize() {
        // Configurar eventos de los botones del sidebar
        btnInicio.setOnAction(event -> handleInicio());
        btnDispositivos.setOnAction(event -> handleDispositivos());
        btnInformes.setOnAction(event -> handleInformes());
        btnConfiguracion.setOnAction(event -> handleConfiguracion());

        // Cargar datos energéticos y crear gráficos
        loadEnergyData();

        // Datos de sesión
        UserSession session = UserSession.getInstance();

        // Mostrar email del usuario arriba a la derecha
        String email = session.getUserEmail();
        if (email == null || email.isEmpty()) {
            lblUserName.setText("Invitado ▼");
        } else {
            lblUserName.setText(email + " ▼");
        }

        // Ajustar opciones y texto según el rol
        Rol rol = session.getRol();
        if (rol != null) {
            switch (rol) {
                case RESPONSABLE_PLANTA:
                    // Responsable energético de planta industrial
                    lblUserRole.setText("Responsable energético de planta");
                    System.out.println("Rol: Responsable de planta");

                    // ❌ No puede tocar Configuración
                    btnConfiguracion.setDisable(true);
                    break;

                case GESTOR_EDIFICIO:
                    // Gestor de planta
                    lblUserRole.setText("Gestor de planta");
                    System.out.println("Rol: Gestor de planta");

                    // Solo debería centrarse en informes
                    btnDispositivos.setDisable(true);
                    btnConfiguracion.setDisable(true);
                    break;

                case ADMIN_PLATAFORMA:
                    // Administrador de la plataforma
                    lblUserRole.setText("Administrador de la plataforma");
                    System.out.println("Rol: Admin plataforma");
                    // Admin: todo habilitado
                    break;
            }
        } else {
            // Por si acaso se entra sin rol
            lblUserRole.setText("Sin rol asignado");
            btnConfiguracion.setDisable(true);
        }
    }

    /*
     * Maneja el click en el botón "Inicio"
     */
    @FXML
    private void handleInicio() {
        System.out.println("Ya estás en Inicio");
        // Aquí podrías recargar datos del dashboard si quieres
        // loadDashboardData();
    }

    /*
     * Maneja el click en el botón "Dispositivos"
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

    /*
     * Maneja el click en el botón "Informes"
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

    /*
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

    /*
     * Maneja el click en el botón "Cerrar Sesión"
     */
    @FXML
    private void handleLogout() {
        try {
            // Limpiar sesión antes de volver al login
            UserSession.getInstance().clearSession();

            SceneManager.switchScene("/org/example/datasensefx/views/login-view.fxml",
                    "DataSense - Iniciar Sesión", 500, 650);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cerrar sesión");
        }
    }

    /**
     * Método auxiliar para mostrar alertas
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Carga los datos energéticos desde CSV y crea los gráficos
     */
    private void loadEnergyData() {
        try {
            System.out.println("🔄 Iniciando carga de datos energéticos...");

            // Intentar primero cargar desde el classpath (recursos empaquetados)
            try {
                energyData = DataLoader.loadFromClasspath("/data/steel_industry_data.csv");
                System.out.println("✓ Datos cargados desde classpath");
            } catch (Exception e1) {
                System.out.println("⚠ No se pudo cargar desde classpath, intentando con ruta del sistema de archivos...");

                // Si falla, intentar con ruta del sistema de archivos (modo desarrollo)
                Path csvPath = Path.of("src/main/resources/data/steel_industry_data.csv");

                if (!csvPath.toFile().exists()) {
                    // Intentar ruta alternativa
                    csvPath = Path.of("DataSenseFX/src/main/resources/data/steel_industry_data.csv");
                }

                if (!csvPath.toFile().exists()) {
                    csvPath = Path.of("src/main/resources/data/sample.csv");
                }

                if (csvPath.toFile().exists()) {
                    energyData = DataLoader.loadFromCsv(csvPath);
                    System.out.println("✓ Datos cargados desde sistema de archivos: " + csvPath);
                } else {
                    throw new IOException("No se encontró el archivo CSV en ninguna ubicación");
                }
            }

            if (energyData == null || energyData.isEmpty()) {
                System.err.println("⚠ No se pudieron cargar datos energéticos. Los gráficos no se mostrarán.");
                showAlert("Error de datos", "No se pudieron cargar los datos energéticos. Verifica que el archivo CSV existe.");
                return;
            }

            System.out.println("✓ Cargadas " + energyData.size() + " mediciones energéticas");

            // Inicializar controlador de análisis
            energyController = new MainController();
            energyController.setData(energyData);

            // Crear gráficos
            createEnergyCharts();

        } catch (Exception e) {
            System.err.println("✗ Error cargando datos energéticos: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Error al cargar datos: " + e.getMessage());
        }
    }

    /**
     * Crea los gráficos energéticos y los agrega a los contenedores
     */
    private void createEnergyCharts() {
        try {
            // Actualizar estadísticas generales
            updateStatistics();

            // Configurar DatePicker
            setupDatePicker();

            // Configurar ToggleButtons
            setupToggleButtons();

            // Gráfico 1: Consumo por Hora del Día (LineChart) - Inicialmente con datos totales
            updateHourlyChart(null);

            // Gráfico 2: Consumo por Tipo de Carga (BarChart) - Inicialmente Total
            updateLoadTypeChart();

            // Gráfico 3: Consumo por Día de la Semana (BarChart) - Inicialmente Total
            updateDayOfWeekChart();

            // Gráfico 4: CO2 por Día de la Semana (BarChart) - Inicialmente Total
            updateCO2Chart();

            // Gráfico 5: Weekday vs Weekend (BarChart) - NUEVO
            Map<String, Double> weekdayVsWeekend = energyController.calculateTotalUsageByWeekStatus();
            BarChart<String, Number> weekStatusChart = FxChartUtils.createBarChartFromStringMap(
                "Consumo: Laborables vs Fin de Semana",
                "Tipo de Día",
                "Consumo Total (kWh)",
                weekdayVsWeekend
            );
            weekdayVsWeekendChart.getChildren().clear();
            weekdayVsWeekendChart.getChildren().add(weekStatusChart);

            System.out.println("✓ Todos los gráficos energéticos creados exitosamente");

        } catch (Exception e) {
            System.err.println("✗ Error creando gráficos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Actualiza las estadísticas generales en las tarjetas KPI
     */
    private void updateStatistics() {
        try {
            // Obtener estadísticas básicas: [total_mediciones, consumo_promedio, consumo_min, consumo_max, co2_total]
            double[] stats = energyController.getBasicStatistics();

            // Actualizar labels
            if (lblTotalMediciones != null) {
                lblTotalMediciones.setText(String.format("%,.0f", stats[0]));
            }

            if (lblConsumoPromedio != null) {
                lblConsumoPromedio.setText(String.format("%.2f kWh", stats[1]));
            }

            if (lblRangoConsumo != null) {
                lblRangoConsumo.setText(String.format("Min: %.2f | Max: %.2f kWh", stats[2], stats[3]));
            }

            if (lblCO2Total != null) {
                lblCO2Total.setText(String.format("%,.0f kg", stats[4]));
            }

            if (lblPicoConsumo != null) {
                lblPicoConsumo.setText(String.format("%.2f kWh", stats[3]));
            }

            if (lblDatasetInfo != null) {
                LocalDate firstDate = energyController.getFirstAvailableDate();
                LocalDate lastDate = energyController.getLastAvailableDate();
                lblDatasetInfo.setText(String.format("%,.0f mediciones (%s a %s)",
                    stats[0], firstDate, lastDate));
            }

            System.out.println("✓ Estadísticas actualizadas: " +
                String.format("Total: %.0f, Promedio: %.2f kWh, CO2: %.0f kg",
                stats[0], stats[1], stats[4]));

        } catch (Exception e) {
            System.err.println("✗ Error actualizando estadísticas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Configura el DatePicker con las fechas disponibles y el listener
     */
    private void setupDatePicker() {
        if (datePickerConsumo == null) {
            System.err.println("⚠ DatePicker no está inicializado");
            return;
        }

        // Obtener rango de fechas disponibles
        LocalDate firstDate = energyController.getFirstAvailableDate();
        LocalDate lastDate = energyController.getLastAvailableDate();

        if (firstDate != null && lastDate != null) {
            // Establecer la primera fecha como valor inicial
            datePickerConsumo.setValue(firstDate);

            System.out.println("📅 Fechas disponibles: " + firstDate + " a " + lastDate);
        }

        // Agregar listener para cambios de fecha
        datePickerConsumo.setOnAction(event -> {
            LocalDate selectedDate = datePickerConsumo.getValue();
            if (selectedDate != null) {
                System.out.println("📅 Fecha seleccionada: " + selectedDate);
                updateHourlyChart(selectedDate);
            }
        });
    }

    /**
     * Actualiza el gráfico de consumo por hora según la fecha seleccionada
     * @param date Fecha a mostrar, o null para mostrar datos totales
     */
    private void updateHourlyChart(LocalDate date) {
        try {
            Map<Integer, Double> usageByHour;
            String chartTitle;

            if (date == null) {
                // Mostrar datos totales (suma de todos los días)
                usageByHour = energyController.calculateTotalUsageByHour();
                chartTitle = "Consumo Total por Hora (Todos los días)";
            } else {
                // Mostrar datos de un día específico
                usageByHour = energyController.calculateUsageByHourForDate(date);
                chartTitle = "Consumo por Hora - " + date.toString();
            }

            // Crear y mostrar el gráfico
            LineChart<Number, Number> hourChart = FxChartUtils.createLineChartFromMap(
                chartTitle,
                usageByHour
            );
            consumoPorHoraChart.getChildren().clear();
            consumoPorHoraChart.getChildren().add(hourChart);

            System.out.println("✓ Gráfico de consumo por hora actualizado" +
                             (date != null ? " para " + date : " (total)"));

        } catch (Exception e) {
            System.err.println("✗ Error actualizando gráfico por hora: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ejemplo: Método para cargar datos del dashboard
     */
    private void loadDashboardData() {
        // Recargar datos energéticos
        loadEnergyData();
    }

    /**
     * Configura los listeners de los ToggleButtons
     */
    private void setupToggleButtons() {
        // Toggle para Tipo de Carga
        if (btnTotalTipoCarga != null && btnPromedioTipoCarga != null) {
            btnTotalTipoCarga.setOnAction(e -> updateLoadTypeChart());
            btnPromedioTipoCarga.setOnAction(e -> updateLoadTypeChart());
        }

        // Toggle para Consumo por Día
        if (btnTotalConsumo != null && btnPromedioConsumo != null) {
            btnTotalConsumo.setOnAction(e -> updateDayOfWeekChart());
            btnPromedioConsumo.setOnAction(e -> updateDayOfWeekChart());
        }

        // Toggle para CO2
        if (btnTotalCO2 != null && btnPromedioCO2 != null) {
            btnTotalCO2.setOnAction(e -> updateCO2Chart());
            btnPromedioCO2.setOnAction(e -> updateCO2Chart());
        }
    }

    /**
     * Actualiza el gráfico de consumo por tipo de carga según el toggle seleccionado
     */
    private void updateLoadTypeChart() {
        try {
            boolean isTotal = btnTotalTipoCarga != null && btnTotalTipoCarga.isSelected();

            if (isTotal) {
                // Mostrar consumo TOTAL por tipo de carga
                Map<String, Double> usageByLoadType = energyController.calculateTotalUsageByLoadType();
                BarChart<String, Number> loadTypeChart = FxChartUtils.createBarChartFromStringMap(
                    "Consumo Total por Tipo de Carga",
                    "Tipo de Carga",
                    "Consumo Total (kWh)",
                    usageByLoadType
                );
                consumoPorTipoCargaChart.getChildren().clear();
                consumoPorTipoCargaChart.getChildren().add(loadTypeChart);
                System.out.println("✓ Gráfico de tipo de carga actualizado (Total)");
            } else {
                // Mostrar consumo PROMEDIO por tipo de carga
                Map<String, double[]> avgUsageByLoadType = energyController.calculateAverageUsageByLoadType();

                // Convertir Map<String, double[]> a Map<String, Double> (solo el promedio)
                Map<String, Double> avgMap = new HashMap<>();
                for (Map.Entry<String, double[]> entry : avgUsageByLoadType.entrySet()) {
                    avgMap.put(entry.getKey(), entry.getValue()[0]); // [0] es el promedio
                }

                BarChart<String, Number> loadTypeChart = FxChartUtils.createBarChartFromStringMap(
                    "Consumo Promedio por Tipo de Carga",
                    "Tipo de Carga",
                    "Consumo Promedio (kWh)",
                    avgMap
                );
                consumoPorTipoCargaChart.getChildren().clear();
                consumoPorTipoCargaChart.getChildren().add(loadTypeChart);
                System.out.println("✓ Gráfico de tipo de carga actualizado (Promedio)");
            }
        } catch (Exception e) {
            System.err.println("✗ Error actualizando gráfico de tipo de carga: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el gráfico de consumo por día de la semana según el toggle seleccionado
     */
    private void updateDayOfWeekChart() {
        try {
            boolean isTotal = btnTotalConsumo != null && btnTotalConsumo.isSelected();

            if (isTotal) {
                // Mostrar consumo TOTAL por día
                Map<String, Double> totalUsageByDay = energyController.calculateTotalUsageByDayOfWeek();
                BarChart<String, Number> dayChart = FxChartUtils.createBarChartFromStringMap(
                    "Consumo Total por Día",
                    "Día de la Semana",
                    "Consumo Total (kWh)",
                    totalUsageByDay
                );
                consumoPorDiaChart.getChildren().clear();
                consumoPorDiaChart.getChildren().add(dayChart);
                System.out.println("✓ Gráfico de día de semana actualizado (Total)");
            } else {
                // Mostrar consumo PROMEDIO por día
                Map<String, Double> avgUsageByDay = energyController.calculateAverageUsageByDayOfWeek();
                BarChart<String, Number> dayChart = FxChartUtils.createBarChartFromStringMap(
                    "Consumo Promedio por Día",
                    "Día de la Semana",
                    "Consumo Promedio (kWh)",
                    avgUsageByDay
                );
                consumoPorDiaChart.getChildren().clear();
                consumoPorDiaChart.getChildren().add(dayChart);
                System.out.println("✓ Gráfico de día de semana actualizado (Promedio)");
            }
        } catch (Exception e) {
            System.err.println("✗ Error actualizando gráfico de día de semana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el gráfico de CO2 por día de la semana según el toggle seleccionado
     */
    private void updateCO2Chart() {
        try {
            boolean isTotal = btnTotalCO2 != null && btnTotalCO2.isSelected();

            if (isTotal) {
                // Mostrar CO2 TOTAL por día (promedio de CO2 diario)
                Map<String, Double> co2ByDay = energyController.calculateTotalCO2ByDayOfWeek();
                BarChart<String, Number> co2Chart = FxChartUtils.createBarChartFromStringMap(
                    "Emisiones CO₂ Promedio por Día",
                    "Día de la Semana",
                    "CO₂ Promedio (kg)",
                    co2ByDay
                );
                co2PorDiaChart.getChildren().clear();
                co2PorDiaChart.getChildren().add(co2Chart);
                System.out.println("✓ Gráfico de CO2 actualizado (Promedio)");
            } else {
                // Mostrar CO2 TOTAL acumulado por día
                Map<String, Double> totalCO2ByDay = energyController.calculateTotalCO2ByDayOfWeekTotal();
                BarChart<String, Number> co2Chart = FxChartUtils.createBarChartFromStringMap(
                    "Emisiones CO₂ Total por Día",
                    "Día de la Semana",
                    "CO₂ Total (kg)",
                    totalCO2ByDay
                );
                co2PorDiaChart.getChildren().clear();
                co2PorDiaChart.getChildren().add(co2Chart);
                System.out.println("✓ Gráfico de CO2 actualizado (Total)");
            }
        } catch (Exception e) {
            System.err.println("✗ Error actualizando gráfico de CO2: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
