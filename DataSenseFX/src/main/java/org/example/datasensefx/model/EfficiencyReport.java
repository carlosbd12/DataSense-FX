package org.example.datasensefx.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Informe de eficiencia energética con KPIs
 */
public class EfficiencyReport extends Report {
    
    private LocalDate startDate;
    private LocalDate endDate;
    private double averagePowerFactor;
    private double loadFactor;
    private double energyIntensity;
    private double co2Intensity;
    private int totalMeasurements;
    
    // KPIs de eficiencia
    private double lightLoadPercentage;
    private double mediumLoadPercentage;
    private double maximumLoadPercentage;
    
    // Oportunidades de mejora
    private String[] improvementOpportunities;
    
    public EfficiencyReport(LocalDate startDate, LocalDate endDate) {
        super(
            "Informe de Eficiencia Energética",
            "Indicadores clave (KPI) de eficiencia, factores de carga y oportunidades de mejora",
            startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + 
            endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            ReportType.EFFICIENCY
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
        content.append("  INDICADORES CLAVE DE EFICIENCIA (KPI)\n");
        content.append("───────────────────────────────────────────────────────\n\n");
        
        content.append(String.format("📊 Total de mediciones: %d\n\n", totalMeasurements));
        
        // Factor de potencia
        content.append(String.format("⚡ Factor de potencia promedio: %.2f%%\n", averagePowerFactor));
        String pfStatus = averagePowerFactor >= 90 ? "✅ Excelente" : 
                         averagePowerFactor >= 80 ? "⚠️ Aceptable" : "❌ Requiere mejora";
        content.append(String.format("   Estado: %s\n\n", pfStatus));
        
        // Factor de carga
        content.append(String.format("📈 Factor de carga: %.2f%%\n", loadFactor));
        String lfStatus = loadFactor >= 70 ? "✅ Óptimo" : 
                         loadFactor >= 50 ? "⚠️ Moderado" : "❌ Bajo";
        content.append(String.format("   Estado: %s\n\n", lfStatus));
        
        // Intensidad energética
        content.append(String.format("🔋 Intensidad energética: %.2f kWh/medición\n", energyIntensity));
        content.append(String.format("🌍 Intensidad de CO₂: %.4f kg/kWh\n\n", co2Intensity));
        
        // Distribución de carga
        content.append("───────────────────────────────────────────────────────\n");
        content.append("  DISTRIBUCIÓN DE CARGA\n");
        content.append("───────────────────────────────────────────────────────\n\n");
        
        content.append(String.format("🟢 Carga ligera: %.1f%%\n", lightLoadPercentage));
        content.append(String.format("🟡 Carga media: %.1f%%\n", mediumLoadPercentage));
        content.append(String.format("🔴 Carga máxima: %.1f%%\n\n", maximumLoadPercentage));
        
        // Análisis de distribución
        if (maximumLoadPercentage > 40) {
            content.append("⚠️ Alta proporción de carga máxima - Considerar redistribución\n");
        } else if (lightLoadPercentage > 60) {
            content.append("ℹ️ Predomina carga ligera - Capacidad subutilizada\n");
        } else {
            content.append("✅ Distribución de carga equilibrada\n");
        }
        
        // Oportunidades de mejora
        if (improvementOpportunities != null && improvementOpportunities.length > 0) {
            content.append("\n───────────────────────────────────────────────────────\n");
            content.append("  OPORTUNIDADES DE MEJORA\n");
            content.append("───────────────────────────────────────────────────────\n\n");
            
            for (int i = 0; i < improvementOpportunities.length; i++) {
                content.append(String.format("%d. %s\n", i + 1, improvementOpportunities[i]));
            }
        }
        
        // Recomendaciones
        content.append("\n───────────────────────────────────────────────────────\n");
        content.append("  RECOMENDACIONES\n");
        content.append("───────────────────────────────────────────────────────\n\n");
        
        if (averagePowerFactor < 90) {
            content.append("• Instalar bancos de capacitores para mejorar el factor de potencia\n");
        }
        if (loadFactor < 60) {
            content.append("• Optimizar horarios de operación para mejorar el factor de carga\n");
        }
        if (maximumLoadPercentage > 40) {
            content.append("• Redistribuir cargas para evitar picos de consumo\n");
        }
        if (co2Intensity > 0.5) {
            content.append("• Considerar fuentes de energía renovable para reducir emisiones\n");
        }
        
        return content.toString();
    }
    
    @Override
    public String generateSummary() {
        return String.format(
            "Factor de potencia: %.1f%% | Factor de carga: %.1f%% | Intensidad CO₂: %.3f kg/kWh",
            averagePowerFactor, loadFactor, co2Intensity
        );
    }
    
    // Getters y Setters
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public double getAveragePowerFactor() {
        return averagePowerFactor;
    }
    
    public void setAveragePowerFactor(double averagePowerFactor) {
        this.averagePowerFactor = averagePowerFactor;
    }
    
    public double getLoadFactor() {
        return loadFactor;
    }
    
    public void setLoadFactor(double loadFactor) {
        this.loadFactor = loadFactor;
    }
    
    public double getEnergyIntensity() {
        return energyIntensity;
    }
    
    public void setEnergyIntensity(double energyIntensity) {
        this.energyIntensity = energyIntensity;
    }
    
    public double getCo2Intensity() {
        return co2Intensity;
    }
    
    public void setCo2Intensity(double co2Intensity) {
        this.co2Intensity = co2Intensity;
    }
    
    public int getTotalMeasurements() {
        return totalMeasurements;
    }
    
    public void setTotalMeasurements(int totalMeasurements) {
        this.totalMeasurements = totalMeasurements;
    }
    
    public double getLightLoadPercentage() {
        return lightLoadPercentage;
    }
    
    public void setLightLoadPercentage(double lightLoadPercentage) {
        this.lightLoadPercentage = lightLoadPercentage;
    }
    
    public double getMediumLoadPercentage() {
        return mediumLoadPercentage;
    }
    
    public void setMediumLoadPercentage(double mediumLoadPercentage) {
        this.mediumLoadPercentage = mediumLoadPercentage;
    }
    
    public double getMaximumLoadPercentage() {
        return maximumLoadPercentage;
    }
    
    public void setMaximumLoadPercentage(double maximumLoadPercentage) {
        this.maximumLoadPercentage = maximumLoadPercentage;
    }
    
    public String[] getImprovementOpportunities() {
        return improvementOpportunities;
    }
    
    public void setImprovementOpportunities(String[] improvementOpportunities) {
        this.improvementOpportunities = improvementOpportunities;
    }
}

