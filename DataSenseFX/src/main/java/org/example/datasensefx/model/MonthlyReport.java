package org.example.datasensefx.model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Informe mensual de costes energéticos
 */
public class MonthlyReport extends Report {
    
    private YearMonth reportMonth;
    private double totalConsumption;
    private double totalCost;
    private double averageDailyCost;
    private double peakDayCost;
    private double totalCO2;
    private int totalMeasurements;
    private double costPerKWh = 0.15; // Precio por kWh (configurable)
    
    // Comparación con mes anterior
    private double previousMonthConsumption;
    private double previousMonthCost;
    private double consumptionChange;
    private double costChange;
    
    public MonthlyReport(YearMonth reportMonth) {
        super(
            "Informe Mensual de Costes",
            "Detalle de costes energéticos y comparación con el mes anterior",
            reportMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            ReportType.MONTHLY
        );
        this.reportMonth = reportMonth;
    }
    
    @Override
    public String generateContent() {
        StringBuilder content = new StringBuilder();
        
        content.append("═══════════════════════════════════════════════════════\n");
        content.append("  ").append(title).append("\n");
        content.append("═══════════════════════════════════════════════════════\n\n");
        
        content.append("📅 Mes: ").append(period).append("\n");
        content.append("🕐 Generado: ").append(generatedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n\n");
        
        content.append("───────────────────────────────────────────────────────\n");
        content.append("  RESUMEN DE COSTES\n");
        content.append("───────────────────────────────────────────────────────\n\n");
        
        content.append(String.format("📊 Total de mediciones: %d\n", totalMeasurements));
        content.append(String.format("⚡ Consumo total: %.2f kWh\n", totalConsumption));
        content.append(String.format("💰 Coste total: %.2f €\n", totalCost));
        content.append(String.format("📈 Coste promedio diario: %.2f €\n", averageDailyCost));
        content.append(String.format("🔺 Día de mayor coste: %.2f €\n", peakDayCost));
        content.append(String.format("🌍 Emisiones CO₂: %.2f kg\n", totalCO2));
        content.append(String.format("💵 Precio por kWh: %.3f €\n\n", costPerKWh));
        
        // Comparación con mes anterior
        if (previousMonthConsumption > 0) {
            content.append("───────────────────────────────────────────────────────\n");
            content.append("  COMPARACIÓN CON MES ANTERIOR\n");
            content.append("───────────────────────────────────────────────────────\n\n");
            
            String consumptionTrend = consumptionChange >= 0 ? "↑" : "↓";
            String costTrend = costChange >= 0 ? "↑" : "↓";
            
            content.append(String.format("Consumo mes anterior: %.2f kWh\n", previousMonthConsumption));
            content.append(String.format("Cambio en consumo: %s %.2f%% (%.2f kWh)\n", 
                consumptionTrend, Math.abs(consumptionChange), 
                totalConsumption - previousMonthConsumption));
            
            content.append(String.format("\nCoste mes anterior: %.2f €\n", previousMonthCost));
            content.append(String.format("Cambio en coste: %s %.2f%% (%.2f €)\n\n", 
                costTrend, Math.abs(costChange), 
                totalCost - previousMonthCost));
            
            // Análisis
            if (consumptionChange < -5) {
                content.append("✅ Excelente: Reducción significativa del consumo\n");
            } else if (consumptionChange > 10) {
                content.append("⚠️ Atención: Incremento considerable del consumo\n");
            } else {
                content.append("ℹ️ Consumo estable respecto al mes anterior\n");
            }
        }
        
        // Datos adicionales del mapa
        if (data.containsKey("costByWeek")) {
            content.append("\n───────────────────────────────────────────────────────\n");
            content.append("  COSTES POR SEMANA\n");
            content.append("───────────────────────────────────────────────────────\n\n");
            content.append(data.get("costByWeek")).append("\n");
        }
        
        if (data.containsKey("costByLoadType")) {
            content.append("───────────────────────────────────────────────────────\n");
            content.append("  COSTES POR TIPO DE CARGA\n");
            content.append("───────────────────────────────────────────────────────\n\n");
            content.append(data.get("costByLoadType")).append("\n");
        }
        
        return content.toString();
    }
    
    @Override
    public String generateSummary() {
        String trend = costChange >= 0 ? "↑" : "↓";
        return String.format(
            "Coste total: %.2f € | Consumo: %.2f kWh | Cambio: %s %.1f%%",
            totalCost, totalConsumption, trend, Math.abs(costChange)
        );
    }
    
    // Getters y Setters
    public YearMonth getReportMonth() {
        return reportMonth;
    }
    
    public void setReportMonth(YearMonth reportMonth) {
        this.reportMonth = reportMonth;
    }
    
    public double getTotalConsumption() {
        return totalConsumption;
    }
    
    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
        this.totalCost = totalConsumption * costPerKWh;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public double getAverageDailyCost() {
        return averageDailyCost;
    }
    
    public void setAverageDailyCost(double averageDailyCost) {
        this.averageDailyCost = averageDailyCost;
    }
    
    public double getPeakDayCost() {
        return peakDayCost;
    }
    
    public void setPeakDayCost(double peakDayCost) {
        this.peakDayCost = peakDayCost;
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
    
    public double getCostPerKWh() {
        return costPerKWh;
    }
    
    public void setCostPerKWh(double costPerKWh) {
        this.costPerKWh = costPerKWh;
        this.totalCost = totalConsumption * costPerKWh;
    }
    
    public double getPreviousMonthConsumption() {
        return previousMonthConsumption;
    }
    
    public void setPreviousMonthConsumption(double previousMonthConsumption) {
        this.previousMonthConsumption = previousMonthConsumption;
        if (previousMonthConsumption > 0) {
            this.consumptionChange = ((totalConsumption - previousMonthConsumption) / previousMonthConsumption) * 100;
        }
    }
    
    public double getPreviousMonthCost() {
        return previousMonthCost;
    }
    
    public void setPreviousMonthCost(double previousMonthCost) {
        this.previousMonthCost = previousMonthCost;
        if (previousMonthCost > 0) {
            this.costChange = ((totalCost - previousMonthCost) / previousMonthCost) * 100;
        }
    }
    
    public double getConsumptionChange() {
        return consumptionChange;
    }
    
    public double getCostChange() {
        return costChange;
    }
}

