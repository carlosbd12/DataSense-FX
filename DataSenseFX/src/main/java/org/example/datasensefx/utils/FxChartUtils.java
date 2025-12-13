package org.example.datasensefx.utils;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.Map;

public class FxChartUtils {

    private static final String CHART_STYLES_CSS = "chart-styles.css";
    
    /**
     * Aplica los estilos CSS a un gráfico
     */
    private static void applyChartStyles(javafx.scene.chart.Chart chart) {
        try {
            String cssResource = FxChartUtils.class.getResource("/chart-styles.css").toExternalForm();
            chart.getStylesheets().add(cssResource);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el CSS de estilos: " + e.getMessage());
        }
    }

    /**
     * Crea un LineChart (Number, Number) para mostrar consumo por hora
     * Ideal para analizar patrones de consumo durante el día
     */
    public static LineChart<Number, Number> createLineChartFromMap(String title, Map<Integer, Double> data) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Hora del día");
        yAxis.setLabel("Consumo (kWh)");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(23);

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Consumo por hora");

        data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> series.getData().add(new XYChart.Data<>(e.getKey(), e.getValue())));

        lineChart.getData().add(series);
        
        // Aplicar stylesheet externo
        applyChartStyles(lineChart);
        
        lineChart.applyCss();
        lineChart.layout();
        
        for (XYChart.Series<Number, Number> s : lineChart.getData()) {
            Node line = s.getNode();
            if (line != null) {
                line.setStyle("-fx-stroke: #2d9d4a; -fx-stroke-width: 3;");
            }
            
            // Colorear los puntos
            for (XYChart.Data<Number, Number> item : s.getData()) {
                Node node = item.getNode();
                if (node != null) {
                    node.setStyle("-fx-background-color: #2d9d4a; -fx-padding: 3;");
                }
            }
        }
        
        return lineChart;
    }

    /**
     * Crea un ScatterChart (Number, Number) a partir de un mapa (x -> y).
     * Ideal para consumo por hora con puntos individuales
     */
    public static ScatterChart<Number, Number> createScatterFromMap(String title, Map<Integer, Double> data) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Hora del día");
        yAxis.setLabel("Consumo (kWh)");

        ScatterChart<Number, Number> scatter = new ScatterChart<>(xAxis, yAxis);
        scatter.setTitle(title);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Consumo por hora");

        data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> series.getData().add(new XYChart.Data<>(e.getKey(), e.getValue())));

        scatter.getData().add(series);
        return scatter;
    }

    /**
     * Crea un BarChart (Categoría, Número) para mostrar consumo por tipo de carga, día, etc
     * Ideal para comparar valores entre categorías
     */
    public static BarChart<String, Number> createBarChartFromStringMap(String title, String xAxisLabel, String yAxisLabel, Map<String, Double> data) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xAxisLabel);
        yAxis.setLabel(yAxisLabel);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Valor");

        data.forEach((category, value) -> {
            series.getData().add(new XYChart.Data<>(category, value));
        });

        barChart.getData().add(series);
        
        // Aplicar stylesheet externo
        applyChartStyles(barChart);
        
        barChart.applyCss();
        barChart.layout();
        
        for (XYChart.Series<String, Number> s : barChart.getData()) {
            for (XYChart.Data<String, Number> item : s.getData()) {
                Node node = item.getNode();
                if (node != null) {
                    node.setStyle("-fx-bar-fill: #2d9d4a;");
                }
            }
        }
        
        return barChart;
    }

    /**
     * Crea un BarChart para consumo promedio por tipo de carga
     * Ideal para analizar rendimiento relativo de categorías
     */
    public static BarChart<String, Number> createBarChartFromArrayMap(String title, String xAxisLabel, String yAxisLabel, Map<String, double[]> data) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xAxisLabel);
        yAxis.setLabel(yAxisLabel);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Valor promedio");

        data.forEach((loadType, values) -> {
            if (values.length > 0) {
                series.getData().add(new XYChart.Data<>(loadType, values[0]));
            }
        });

        barChart.getData().add(series);
        
        // Aplicar stylesheet externo
        applyChartStyles(barChart);
        
        barChart.applyCss();
        barChart.layout();
        
        for (XYChart.Series<String, Number> s : barChart.getData()) {
            for (XYChart.Data<String, Number> item : s.getData()) {
                Node node = item.getNode();
                if (node != null) {
                    node.setStyle("-fx-bar-fill: #2d9d4a;");
                }
            }
        }
        
        return barChart;
    }
}
