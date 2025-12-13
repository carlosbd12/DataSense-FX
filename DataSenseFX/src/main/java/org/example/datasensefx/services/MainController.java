package org.example.datasensefx.services;

import org.example.datasensefx.model.Measurement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Controlador principal que procesa y analiza los datos de consumo energético.
 * Proporciona métodos para calcular estadísticas por tipo de carga, día, hora, etc.
 */
public class MainController {
    private List<Measurement> data;

    public void setData(List<Measurement> data) {
        this.data = data;
    }

    /**
     * Calcula el consumo promedio por tipo de carga
     * @return HashMap: tipo_carga -> [promedio_kWh]
     */
    public Map<String, double[]> calculateAverageUsageByLoadType() {
        Map<String, List<Double>> usageByLoadType = new HashMap<>();

        // Agrupar consumos por tipo de carga
        for (Measurement m : data) {
            usageByLoadType
                .computeIfAbsent(m.getLoadType(), k -> new ArrayList<>())
                .add(m.getUsageKWh());
        }

        // Calcular promedio para cada tipo
        Map<String, double[]> averageUsageByLoadType = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : usageByLoadType.entrySet()) {
            String loadType = entry.getKey();
            List<Double> usages = entry.getValue();

            double sum = 0;
            for (double usage : usages) {
                sum += usage;
            }
            double average = sum / usages.size();
            averageUsageByLoadType.put(loadType, new double[]{average});
        }

        return averageUsageByLoadType;
    }
    
    /**
     * Calcula el consumo TOTAL por hora del día (suma de todos los registros por hora)
     * @return HashMap: hora (0-23) -> consumo_total_kWh
     */
    public Map<Integer, Double> calculateTotalUsageByHour() {
        Map<Integer, Double> usageByHour = new HashMap<>();

        // Agrupar y sumar consumos por hora
        for (Measurement m : data) {
            int hour = m.getDate().getHour();
            usageByHour.put(hour, usageByHour.getOrDefault(hour, 0.0) + m.getUsageKWh());
        }

        return usageByHour;
    }

    /**
     * Calcula el consumo por hora para un día específico
     * @param date La fecha a analizar
     * @return HashMap: hora (0-23) -> consumo_kWh en ese día
     */
    public Map<Integer, Double> calculateUsageByHourForDate(LocalDate date) {
        Map<Integer, Double> usageByHour = new HashMap<>();

        for (Measurement m : data) {
            if (m.getDate().toLocalDate().equals(date)) {
                int hour = m.getDate().getHour();
                usageByHour.put(hour, usageByHour.getOrDefault(hour, 0.0) + m.getUsageKWh());
            }
        }

        return usageByHour;
    }

    /**
     * Obtiene la primera fecha disponible en los datos
     * @return La primera fecha con datos, o null si no hay datos
     */
    public LocalDate getFirstAvailableDate() {
        if (data == null || data.isEmpty()) {
            return null;
        }
        return data.getFirst().getDate().toLocalDate();
    }

    /**
     * Obtiene la última fecha disponible en los datos
     * @return La última fecha con datos, o null si no hay datos
     */
    public LocalDate getLastAvailableDate() {
        if (data == null || data.isEmpty()) {
            return null;
        }
        return data.getLast().getDate().toLocalDate();
    }
    
    /**
     * Calcula el CO2 promedio por día de la semana
     * @return HashMap: día_semana -> co2_promedio
     */
    public Map<String, Double> calculateTotalCO2ByDayOfWeek() {
        Map<String, List<Double>> co2ByDay = new HashMap<>();

        for (Measurement m : data) {
            String day = m.getDayOfWeek();
            co2ByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(m.getCo2());
        }

        // Ordenar días de la semana correctamente
        String[] daysOrder = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        Map<String, Double> averageByDay = new LinkedHashMap<>();
        
        for (String day : daysOrder) {
            if (co2ByDay.containsKey(day)) {
                List<Double> values = co2ByDay.get(day);
                double average = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                averageByDay.put(day, average);
            }
        }

        return averageByDay;
    }

    /**
     * Calcula el consumo por tipo de carga
     * @return HashMap: tipo_carga -> consumo_total_kWh
     */
    public Map<String, Double> calculateTotalUsageByLoadType() {
        Map<String, Double> usageByLoadType = new HashMap<>();

        for (Measurement m : data) {
            String loadType = m.getLoadType();
            usageByLoadType.put(loadType, usageByLoadType.getOrDefault(loadType, 0.0) + m.getUsageKWh());
        }

        return usageByLoadType;
    }

    /**
     * Calcula el consumo por estado de semana (Weekday vs Weekend)
     * @return HashMap: estado -> consumo_total_kWh
     */
    public Map<String, Double> calculateTotalUsageByWeekStatus() {
        Map<String, Double> usageByStatus = new HashMap<>();

        for (Measurement m : data) {
            String status = m.getWeekStatus();
            usageByStatus.put(status, usageByStatus.getOrDefault(status, 0.0) + m.getUsageKWh());
        }

        return usageByStatus;
    }

    /**
     * Calcula el consumo promedio por día de la semana
     * @return HashMap: día_semana -> consumo_promedio_kWh
     */
    public Map<String, Double> calculateAverageUsageByDayOfWeek() {
        Map<String, List<Double>> usageByDay = new HashMap<>();

        for (Measurement m : data) {
            String day = m.getDayOfWeek();
            usageByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(m.getUsageKWh());
        }

        // Ordenar días de la semana correctamente
        String[] daysOrder = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        Map<String, Double> averageByDay = new LinkedHashMap<>();
        
        for (String day : daysOrder) {
            if (usageByDay.containsKey(day)) {
                double average = usageByDay.get(day).stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                averageByDay.put(day, average);
            }
        }

        return averageByDay;
    }

    /**
     * Obtiene todos los días únicos presentes en los datos
     * @return List de LocalDate con todos los días en el dataset
     */
    public List<LocalDate> getAvailableDates() {
        return data.stream()
            .map(m -> m.getDate().toLocalDate())
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    /**
     * Obtiene estadísticas generales del dataset
     * @return Array: [total_mediciones, consumo_promedio, consumo_min, consumo_max, co2_total]
     */
    public double[] getBasicStatistics() {
        if (data == null || data.isEmpty()) {
            return new double[]{0, 0, 0, 0, 0};
        }

        double sum = 0;
        double minUsage = Double.MAX_VALUE;
        double maxUsage = Double.MIN_VALUE;
        double totalCO2 = 0;

        for (Measurement m : data) {
            double usage = m.getUsageKWh();
            sum += usage;
            minUsage = Math.min(minUsage, usage);
            maxUsage = Math.max(maxUsage, usage);
            totalCO2 += m.getCo2();
        }

        double average = sum / data.size();
        return new double[]{data.size(), average, minUsage, maxUsage, totalCO2};
    }

    /**
     * Calcula el consumo TOTAL por día de la semana
     * @return HashMap: día_semana -> consumo_total_kWh
     */
    public Map<String, Double> calculateTotalUsageByDayOfWeek() {
        Map<String, Double> usageByDay = new HashMap<>();

        for (Measurement m : data) {
            String day = m.getDayOfWeek();
            usageByDay.put(day, usageByDay.getOrDefault(day, 0.0) + m.getUsageKWh());
        }

        // Ordenar días de la semana correctamente
        String[] daysOrder = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        Map<String, Double> orderedUsageByDay = new LinkedHashMap<>();
        
        for (String day : daysOrder) {
            if (usageByDay.containsKey(day)) {
                orderedUsageByDay.put(day, usageByDay.get(day));
            }
        }

        return orderedUsageByDay;
    }

    /**
     * Calcula el CO2 TOTAL por día de la semana
     * @return HashMap: día_semana -> co2_total
     */
    public Map<String, Double> calculateTotalCO2ByDayOfWeekTotal() {
        Map<String, Double> co2ByDay = new HashMap<>();

        for (Measurement m : data) {
            String day = m.getDayOfWeek();
            co2ByDay.put(day, co2ByDay.getOrDefault(day, 0.0) + m.getCo2());
        }

        // Ordenar días de la semana correctamente
        String[] daysOrder = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        Map<String, Double> orderedCO2ByDay = new LinkedHashMap<>();
        
        for (String day : daysOrder) {
            if (co2ByDay.containsKey(day)) {
                orderedCO2ByDay.put(day, co2ByDay.get(day));
            }
        }

        return orderedCO2ByDay;
    }
}
