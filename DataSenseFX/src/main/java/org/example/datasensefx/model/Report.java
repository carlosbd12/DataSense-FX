package org.example.datasensefx.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase base para todos los tipos de informes
 */
public abstract class Report {
    
    protected String title;
    protected String description;
    protected LocalDateTime generatedAt;
    protected String period;
    protected ReportType type;
    protected Map<String, Object> data;
    
    public enum ReportType {
        DAILY("Informe Diario"),
        WEEKLY("Informe Semanal"),
        MONTHLY("Informe Mensual"),
        EFFICIENCY("Informe de Eficiencia");
        
        private final String displayName;
        
        ReportType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public Report(String title, String description, String period, ReportType type) {
        this.title = title;
        this.description = description;
        this.period = period;
        this.type = type;
        this.generatedAt = LocalDateTime.now();
        this.data = new HashMap<>();
    }
    
    // Getters y Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
    
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
    
    public String getPeriod() {
        return period;
    }
    
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public ReportType getType() {
        return type;
    }
    
    public void setType(ReportType type) {
        this.type = type;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    public void addData(String key, Object value) {
        this.data.put(key, value);
    }
    
    public Object getData(String key) {
        return this.data.get(key);
    }
    
    /**
     * Método abstracto que cada tipo de informe debe implementar
     * para generar su contenido específico
     */
    public abstract String generateContent();
    
    /**
     * Genera un resumen del informe
     */
    public abstract String generateSummary();
}

