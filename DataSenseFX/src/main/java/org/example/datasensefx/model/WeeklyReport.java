package org.example.datasensefx.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Informe semanal de consumo por zonas
 */
public class WeeklyReport extends Report {
    
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalConsumption;
    private double averageConsumption;
    private double weekdayConsumption;
    private double weekendConsumption;
    private int totalMeasurements;
    
    public WeeklyReport(LocalDate startDate, LocalDate endDate) {
        super(
            "Informe Semanal por Zonas",
            "Comparativa de consumo entre zonas de la planta para la última semana",
            startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + 
            endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            ReportType.WEEKLY
        );
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    @Override
    public String generateContent() {
        StringBuilder content = new StringBuilder();
        
        content.append("═══════════════════════════════════════════════════════\n");
        content.append("  ").append(title).append("\n");
        content.append("═══════════════════════════════════════════════════════\n\n");
        
        content.append("📅 Período: ").append(period).append("\n");
        content.append("🕐 Generado: ").append(generatedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n\n");
        
        content.append("───────────────────────────────────────────────────────\n");
        content.append("  RESUMEN SEMANAL\n");
        content.append("───────────────────────────────────────────────────────\n\n");
        
        content.append(String.format("📊 Total de mediciones: %d\n", totalMeasurements));
        content.append(String.format("⚡ Consumo total: %.2f kWh\n", totalConsumption));
        content.append(String.format("📈 Consumo promedio diario: %.2f kWh\n", averageConsumption));
        content.append(String.format("🏢 Consumo días laborables: %.2f kWh\n", weekdayConsumption));
        content.append(String.format("🏖️ Consumo fin de semana: %.2f kWh\n\n", weekendConsumption));
        
        // Comparativa laborables vs fin de semana
        double weekdayPercentage = (weekdayConsumption / totalConsumption) * 100;
        double weekendPercentage = (weekendConsumption / totalConsumption) * 100;
        
        content.append("───────────────────────────────────────────────────────\n");
        content.append("  DISTRIBUCIÓN SEMANAL\n");
        content.append("───────────────────────────────────────────────────────\n\n");
        content.append(String.format("Días laborables: %.1f%%\n", weekdayPercentage));
        content.append(String.format("Fin de semana: %.1f%%\n\n", weekendPercentage));
        
        // Datos adicionales del mapa
        if (data.containsKey("consumptionByDay")) {
            content.append("───────────────────────────────────────────────────────\n");
            content.append("  CONSUMO POR DÍA DE LA SEMANA\n");
            content.append("───────────────────────────────────────────────────────\n\n");
            content.append(data.get("consumptionByDay")).append("\n");
        }
        
        if (data.containsKey("consumptionByZone")) {
            content.append("───────────────────────────────────────────────────────\n");
            content.append("  CONSUMO POR ZONA\n");
            content.append("───────────────────────────────────────────────────────\n\n");
            content.append(data.get("consumptionByZone")).append("\n");
        }
        
        return content.toString();
    }
    
    @Override
    public String generateSummary() {
        return String.format(
            "Consumo total: %.2f kWh | Laborables: %.2f kWh | Fin de semana: %.2f kWh",
            totalConsumption, weekdayConsumption, weekendConsumption
        );
    }
    
    // Getters y Setters
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
    
    public double getWeekdayConsumption() {
        return weekdayConsumption;
    }
    
    public void setWeekdayConsumption(double weekdayConsumption) {
        this.weekdayConsumption = weekdayConsumption;
    }
    
    public double getWeekendConsumption() {
        return weekendConsumption;
    }
    
    public void setWeekendConsumption(double weekendConsumption) {
        this.weekendConsumption = weekendConsumption;
    }
    
    public int getTotalMeasurements() {
        return totalMeasurements;
    }
    
    public void setTotalMeasurements(int totalMeasurements) {
        this.totalMeasurements = totalMeasurements;
    }
}

