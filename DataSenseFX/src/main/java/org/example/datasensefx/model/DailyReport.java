package org.example.datasensefx.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Informe diario de consumo energético
 */
public class DailyReport extends Report {
    
    private LocalDate reportDate;
    private double totalConsumption;
    private double averageConsumption;
    private double peakConsumption;
    private double minConsumption;
    private double totalCO2;
    private int totalMeasurements;
    private String peakHour;
    private String minHour;
    
    public DailyReport(LocalDate reportDate) {
        super(
            "Informe Diario de Consumo",
            "Resumen del consumo energético de las últimas 24 horas",
            reportDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            ReportType.DAILY
        );
        this.reportDate = reportDate;
    }
    
    @Override
    public String generateContent() {
        StringBuilder content = new StringBuilder();
        
        content.append("═══════════════════════════════════════════════════════\n");
        content.append("  ").append(title).append("\n");
        content.append("═══════════════════════════════════════════════════════\n\n");
        
        content.append("📅 Fecha: ").append(period).append("\n");
        content.append("🕐 Generado: ").append(generatedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n\n");
        
        content.append("───────────────────────────────────────────────────────\n");
        content.append("  RESUMEN DE CONSUMO\n");
        content.append("───────────────────────────────────────────────────────\n\n");
        
        content.append(String.format("📊 Total de mediciones: %d\n", totalMeasurements));
        content.append(String.format("⚡ Consumo total: %.2f kWh\n", totalConsumption));
        content.append(String.format("📈 Consumo promedio: %.2f kWh\n", averageConsumption));
        content.append(String.format("🔺 Pico de consumo: %.2f kWh (%s)\n", peakConsumption, peakHour));
        content.append(String.format("🔻 Consumo mínimo: %.2f kWh (%s)\n", minConsumption, minHour));
        content.append(String.format("🌍 Emisiones CO₂: %.2f kg\n\n", totalCO2));
        
        // Datos adicionales del mapa
        if (data.containsKey("consumptionByHour")) {
            content.append("───────────────────────────────────────────────────────\n");
            content.append("  CONSUMO POR HORA\n");
            content.append("───────────────────────────────────────────────────────\n\n");
            content.append(data.get("consumptionByHour")).append("\n");
        }
        
        if (data.containsKey("consumptionByLoadType")) {
            content.append("───────────────────────────────────────────────────────\n");
            content.append("  CONSUMO POR TIPO DE CARGA\n");
            content.append("───────────────────────────────────────────────────────\n\n");
            content.append(data.get("consumptionByLoadType")).append("\n");
        }
        
        return content.toString();
    }
    
    @Override
    public String generateSummary() {
        return String.format(
            "Consumo total: %.2f kWh | Promedio: %.2f kWh | Pico: %.2f kWh | CO₂: %.2f kg",
            totalConsumption, averageConsumption, peakConsumption, totalCO2
        );
    }
    
    // Getters y Setters
    public LocalDate getReportDate() {
        return reportDate;
    }
    
    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
    
    public double getTotalConsumption() {
        return totalConsumption;
    }
    
    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
    
    public double getAverageConsumption() {
        return averageConsumption;
    }
    
    public void setAverageConsumption(double averageConsumption) {
        this.averageConsumption = averageConsumption;
    }
    
    public double getPeakConsumption() {
        return peakConsumption;
    }
    
    public void setPeakConsumption(double peakConsumption) {
        this.peakConsumption = peakConsumption;
    }
    
    public double getMinConsumption() {
        return minConsumption;
    }
    
    public void setMinConsumption(double minConsumption) {
        this.minConsumption = minConsumption;
    }
    
    public double getTotalCO2() {
        return totalCO2;
    }
    
    public void setTotalCO2(double totalCO2) {
        this.totalCO2 = totalCO2;
    }
    
    public int getTotalMeasurements() {
        return totalMeasurements;
    }
    
    public void setTotalMeasurements(int totalMeasurements) {
        this.totalMeasurements = totalMeasurements;
    }
    
    public String getPeakHour() {
        return peakHour;
    }
    
    public void setPeakHour(String peakHour) {
        this.peakHour = peakHour;
    }
    
    public String getMinHour() {
        return minHour;
    }
    
    public void setMinHour(String minHour) {
        this.minHour = minHour;
    }
}

