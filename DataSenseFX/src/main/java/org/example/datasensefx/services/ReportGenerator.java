package org.example.datasensefx.services;

import org.example.datasensefx.model.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio para generar informes a partir de datos de mediciones
 */
public class ReportGenerator {
    
    private List<Measurement> measurements;
    private MainController dataController;
    
    public ReportGenerator(List<Measurement> measurements) {
        this.measurements = measurements;
        this.dataController = new MainController();
        this.dataController.setData(measurements);
    }
    
    /**
     * Genera un informe diario para la fecha más reciente disponible
     */
    public DailyReport generateDailyReport() {
        if (measurements == null || measurements.isEmpty()) {
            return null;
        }
        
        // Obtener la fecha más reciente
        LocalDate latestDate = measurements.stream()
            .map(m -> m.getDate().toLocalDate())
            .max(LocalDate::compareTo)
            .orElse(LocalDate.now());
        
        return generateDailyReport(latestDate);
    }
    
    /**
     * Genera un informe diario para una fecha específica
     */
    public DailyReport generateDailyReport(LocalDate date) {
        DailyReport report = new DailyReport(date);
        
        // Filtrar mediciones del día
        List<Measurement> dayMeasurements = measurements.stream()
            .filter(m -> m.getDate().toLocalDate().equals(date))
            .collect(Collectors.toList());
        
        if (dayMeasurements.isEmpty()) {
            return report;
        }
        
        // Calcular estadísticas
        report.setTotalMeasurements(dayMeasurements.size());
        
        double totalConsumption = dayMeasurements.stream()
            .mapToDouble(Measurement::getUsageKWh)
            .sum();
        report.setTotalConsumption(totalConsumption);
        
        double avgConsumption = dayMeasurements.stream()
            .mapToDouble(Measurement::getUsageKWh)
            .average()
            .orElse(0.0);
        report.setAverageConsumption(avgConsumption);
        
        // Pico y mínimo
        Measurement peakMeasurement = dayMeasurements.stream()
            .max(Comparator.comparingDouble(Measurement::getUsageKWh))
            .orElse(null);
        
        if (peakMeasurement != null) {
            report.setPeakConsumption(peakMeasurement.getUsageKWh());
            report.setPeakHour(peakMeasurement.getDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        
        Measurement minMeasurement = dayMeasurements.stream()
            .min(Comparator.comparingDouble(Measurement::getUsageKWh))
            .orElse(null);
        
        if (minMeasurement != null) {
            report.setMinConsumption(minMeasurement.getUsageKWh());
            report.setMinHour(minMeasurement.getDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        
        // CO2 total
        double totalCO2 = dayMeasurements.stream()
            .mapToDouble(Measurement::getCo2)
            .sum();
        report.setTotalCO2(totalCO2);
        
        // Consumo por hora
        Map<Integer, Double> consumptionByHour = dayMeasurements.stream()
            .collect(Collectors.groupingBy(
                m -> m.getDate().getHour(),
                Collectors.summingDouble(Measurement::getUsageKWh)
            ));
        
        StringBuilder hourlyData = new StringBuilder();
        consumptionByHour.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> hourlyData.append(String.format("%02d:00 - %.2f kWh\n", entry.getKey(), entry.getValue())));
        
        report.addData("consumptionByHour", hourlyData.toString());
        
        // Consumo por tipo de carga
        Map<String, Double> consumptionByLoadType = dayMeasurements.stream()
            .collect(Collectors.groupingBy(
                Measurement::getLoadType,
                Collectors.summingDouble(Measurement::getUsageKWh)
            ));
        
        StringBuilder loadTypeData = new StringBuilder();
        consumptionByLoadType.forEach((type, consumption) -> 
            loadTypeData.append(String.format("%s: %.2f kWh\n", type, consumption)));
        
        report.addData("consumptionByLoadType", loadTypeData.toString());
        
        return report;
    }
    
    /**
     * Genera un informe semanal para la última semana disponible
     */
    public WeeklyReport generateWeeklyReport() {
        if (measurements == null || measurements.isEmpty()) {
            return null;
        }
        
        // Obtener la fecha más reciente y calcular la semana
        LocalDate endDate = measurements.stream()
            .map(m -> m.getDate().toLocalDate())
            .max(LocalDate::compareTo)
            .orElse(LocalDate.now());
        
        LocalDate startDate = endDate.minusDays(6);
        
        return generateWeeklyReport(startDate, endDate);
    }
    
    /**
     * Genera un informe semanal para un rango de fechas
     */
    public WeeklyReport generateWeeklyReport(LocalDate startDate, LocalDate endDate) {
        WeeklyReport report = new WeeklyReport(startDate, endDate);
        
        // Filtrar mediciones de la semana
        List<Measurement> weekMeasurements = measurements.stream()
            .filter(m -> {
                LocalDate date = m.getDate().toLocalDate();
                return !date.isBefore(startDate) && !date.isAfter(endDate);
            })
            .collect(Collectors.toList());
        
        if (weekMeasurements.isEmpty()) {
            return report;
        }
        
        // Calcular estadísticas
        report.setTotalMeasurements(weekMeasurements.size());
        
        double totalConsumption = weekMeasurements.stream()
            .mapToDouble(Measurement::getUsageKWh)
            .sum();
        report.setTotalConsumption(totalConsumption);
        
        double avgConsumption = totalConsumption / 7.0; // Promedio diario
        report.setAverageConsumption(avgConsumption);
        
        // Consumo laborables vs fin de semana
        double weekdayConsumption = weekMeasurements.stream()
            .filter(m -> m.getWeekStatus().equalsIgnoreCase("Weekday"))
            .mapToDouble(Measurement::getUsageKWh)
            .sum();
        report.setWeekdayConsumption(weekdayConsumption);
        
        double weekendConsumption = weekMeasurements.stream()
            .filter(m -> m.getWeekStatus().equalsIgnoreCase("Weekend"))
            .mapToDouble(Measurement::getUsageKWh)
            .sum();
        report.setWeekendConsumption(weekendConsumption);

        // Consumo por día de la semana
        Map<String, Double> consumptionByDay = weekMeasurements.stream()
            .collect(Collectors.groupingBy(
                Measurement::getDayOfWeek,
                Collectors.summingDouble(Measurement::getUsageKWh)
            ));

        StringBuilder dayData = new StringBuilder();
        consumptionByDay.forEach((day, consumption) ->
            dayData.append(String.format("%s: %.2f kWh\n", day, consumption)));

        report.addData("consumptionByDay", dayData.toString());

        return report;
    }

    /**
     * Genera un informe mensual para el mes más reciente disponible
     */
    public MonthlyReport generateMonthlyReport() {
        if (measurements == null || measurements.isEmpty()) {
            return null;
        }

        // Obtener el mes más reciente
        YearMonth latestMonth = measurements.stream()
            .map(m -> YearMonth.from(m.getDate()))
            .max(YearMonth::compareTo)
            .orElse(YearMonth.now());

        return generateMonthlyReport(latestMonth);
    }

    /**
     * Genera un informe mensual para un mes específico
     */
    public MonthlyReport generateMonthlyReport(YearMonth month) {
        MonthlyReport report = new MonthlyReport(month);

        // Filtrar mediciones del mes
        List<Measurement> monthMeasurements = measurements.stream()
            .filter(m -> YearMonth.from(m.getDate()).equals(month))
            .collect(Collectors.toList());

        if (monthMeasurements.isEmpty()) {
            return report;
        }

        // Calcular estadísticas
        report.setTotalMeasurements(monthMeasurements.size());

        double totalConsumption = monthMeasurements.stream()
            .mapToDouble(Measurement::getUsageKWh)
            .sum();
        report.setTotalConsumption(totalConsumption);

        double totalCO2 = monthMeasurements.stream()
            .mapToDouble(Measurement::getCo2)
            .sum();
        report.setTotalCO2(totalCO2);

        // Coste promedio diario
        int daysInMonth = month.lengthOfMonth();
        double avgDailyCost = report.getTotalCost() / daysInMonth;
        report.setAverageDailyCost(avgDailyCost);

        // Día de mayor coste (agrupando por día)
        Map<LocalDate, Double> consumptionByDay = monthMeasurements.stream()
            .collect(Collectors.groupingBy(
                m -> m.getDate().toLocalDate(),
                Collectors.summingDouble(Measurement::getUsageKWh)
            ));

        double peakDayConsumption = consumptionByDay.values().stream()
            .max(Double::compareTo)
            .orElse(0.0);
        report.setPeakDayCost(peakDayConsumption * report.getCostPerKWh());

        // Comparación con mes anterior (si hay datos)
        YearMonth previousMonth = month.minusMonths(1);
        List<Measurement> previousMonthMeasurements = measurements.stream()
            .filter(m -> YearMonth.from(m.getDate()).equals(previousMonth))
            .collect(Collectors.toList());

        if (!previousMonthMeasurements.isEmpty()) {
            double prevConsumption = previousMonthMeasurements.stream()
                .mapToDouble(Measurement::getUsageKWh)
                .sum();
            report.setPreviousMonthConsumption(prevConsumption);
            report.setPreviousMonthCost(prevConsumption * report.getCostPerKWh());
        }

        return report;
    }

    /**
     * Genera un informe de eficiencia para el período completo de datos
     */
    public EfficiencyReport generateEfficiencyReport() {
        if (measurements == null || measurements.isEmpty()) {
            return null;
        }

        LocalDate startDate = measurements.stream()
            .map(m -> m.getDate().toLocalDate())
            .min(LocalDate::compareTo)
            .orElse(LocalDate.now().minusDays(30));

        LocalDate endDate = measurements.stream()
            .map(m -> m.getDate().toLocalDate())
            .max(LocalDate::compareTo)
            .orElse(LocalDate.now());

        return generateEfficiencyReport(startDate, endDate);
    }

    /**
     * Genera un informe de eficiencia para un rango de fechas
     */
    public EfficiencyReport generateEfficiencyReport(LocalDate startDate, LocalDate endDate) {
        EfficiencyReport report = new EfficiencyReport(startDate, endDate);

        // Filtrar mediciones del período
        List<Measurement> periodMeasurements = measurements.stream()
            .filter(m -> {
                LocalDate date = m.getDate().toLocalDate();
                return !date.isBefore(startDate) && !date.isAfter(endDate);
            })
            .collect(Collectors.toList());

        if (periodMeasurements.isEmpty()) {
            return report;
        }

        report.setTotalMeasurements(periodMeasurements.size());

        // Factor de potencia promedio
        double avgPowerFactor = periodMeasurements.stream()
            .mapToDouble(Measurement::getLaggingPowerFactor)
            .average()
            .orElse(0.0);
        report.setAveragePowerFactor(avgPowerFactor);

        // Intensidad energética
        double totalConsumption = periodMeasurements.stream()
            .mapToDouble(Measurement::getUsageKWh)
            .sum();
        double energyIntensity = totalConsumption / periodMeasurements.size();
        report.setEnergyIntensity(energyIntensity);

        // Intensidad de CO2
        double totalCO2 = periodMeasurements.stream()
            .mapToDouble(Measurement::getCo2)
            .sum();
        double co2Intensity = totalCO2 / totalConsumption;
        report.setCo2Intensity(co2Intensity);

        // Factor de carga (simplificado)
        double avgConsumption = totalConsumption / periodMeasurements.size();
        double peakConsumption = periodMeasurements.stream()
            .mapToDouble(Measurement::getUsageKWh)
            .max()
            .orElse(1.0);
        double loadFactor = (avgConsumption / peakConsumption) * 100;
        report.setLoadFactor(loadFactor);

        // Distribución de carga
        Map<String, Long> loadTypeCount = periodMeasurements.stream()
            .collect(Collectors.groupingBy(Measurement::getLoadType, Collectors.counting()));

        long total = periodMeasurements.size();
        double lightLoad = loadTypeCount.getOrDefault("Light_Load", 0L) * 100.0 / total;
        double mediumLoad = loadTypeCount.getOrDefault("Medium_Load", 0L) * 100.0 / total;
        double maximumLoad = loadTypeCount.getOrDefault("Maximum_Load", 0L) * 100.0 / total;

        report.setLightLoadPercentage(lightLoad);
        report.setMediumLoadPercentage(mediumLoad);
        report.setMaximumLoadPercentage(maximumLoad);

        // Oportunidades de mejora
        List<String> opportunities = new ArrayList<>();

        if (avgPowerFactor < 85) {
            opportunities.add("Mejorar el factor de potencia mediante compensación reactiva");
        }
        if (loadFactor < 60) {
            opportunities.add("Optimizar la distribución de cargas para mejorar el factor de carga");
        }
        if (maximumLoad > 40) {
            opportunities.add("Reducir picos de consumo mediante gestión de demanda");
        }
        if (co2Intensity > 0.5) {
            opportunities.add("Implementar fuentes de energía renovable para reducir emisiones");
        }

        report.setImprovementOpportunities(opportunities.toArray(new String[0]));

        return report;
    }

    // Getters y Setters
    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
        this.dataController.setData(measurements);
    }
}

