//Limpieza de datos y carga desde CSV
package org.example.datasensefx.services;

import org.example.datasensefx.model.Measurement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Cargador de datos desde archivos CSV
 * Soporta múltiples formatos de CSV
 */
public class DataLoader {

    public static void main(String[] args) {
        try {
            Path ruta = Path.of("src/main/resources/data/steel_industry_data.csv");
            List<Measurement> datos = loadFromCsv(ruta);
            System.out.println("Registros cargados: " + datos.size());
            for (int i = 0; i < Math.min(5, datos.size()); i++) {
                System.out.println(datos.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga datos desde un CSV usando Apache Commons CSV
     * Soporta tanto el formato simple (device, power, timestamp) como el formato industrial (11 columnas)
     */
    public static List<Measurement> loadFromCsv(Path path) throws IOException {
        List<Measurement> out = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setTrim(true).build();
            CSVParser csvParser = csvFormat.parse(reader);

            int recordCount = 0;
            int successCount = 0;

            for (CSVRecord record : csvParser) {
                recordCount++;
                try {
                    Measurement measurement = parseRecord(record);
                    if (measurement != null) {
                        out.add(measurement);
                        successCount++;
                    } else {
                        if (recordCount <= 5) {
                            System.err.println("Registro " + recordCount + " no reconocido: formato desconocido");
                        }
                    }
                } catch (Exception e) {
                    if (recordCount <= 5) {
                        System.err.println("Error en registro " + recordCount + ": " + e.getMessage());
                    }
                }
            }

            System.out.println("Total de registros procesados: " + recordCount + ", exitosos: " + successCount);
        }

        System.out.println("Total de mediciones cargadas: " + out.size());
        return out;
    }

    /**
     * Carga datos desde un CSV ubicado en el classpath (recursos)
     * Este método es útil cuando el CSV está empaquetado dentro del JAR
     * @param resourcePath Ruta del recurso, ej: "/data/steel_industry_data.csv"
     */
    public static List<Measurement> loadFromClasspath(String resourcePath) throws IOException {
        List<Measurement> out = new ArrayList<>();

        System.out.println("Intentando cargar recurso desde classpath: " + resourcePath);

        // Intentar cargar desde el classpath
        InputStream inputStream = DataLoader.class.getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IOException("No se pudo encontrar el recurso en el classpath: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().setTrim(true).build();
            CSVParser csvParser = csvFormat.parse(reader);

            int recordCount = 0;
            int successCount = 0;

            for (CSVRecord record : csvParser) {
                recordCount++;
                try {
                    Measurement measurement = parseRecord(record);
                    if (measurement != null) {
                        out.add(measurement);
                        successCount++;
                    } else {
                        if (recordCount <= 5) {
                            System.err.println("Registro " + recordCount + " no reconocido: formato desconocido");
                        }
                    }
                } catch (Exception e) {
                    if (recordCount <= 5) {
                        System.err.println("Error en registro " + recordCount + ": " + e.getMessage());
                    }
                }
            }

            System.out.println("Total de registros procesados desde classpath: " + recordCount + ", exitosos: " + successCount);
        }

        System.out.println("Total de mediciones cargadas desde classpath: " + out.size());
        return out;
    }

    /**
     * Parsea un registro CSV y lo convierte en una Measurement
     * Detecta automáticamente el formato
     */
    private static Measurement parseRecord(CSVRecord record) {
        try {
            // Obtener encabezados disponibles - revisar si existe la columna Usage_kWh
            boolean hasUsageKWh = record.isMapped("Usage_kWh");
            boolean hasDevice = record.isMapped("device");

            // Debug para primeros registros
            if (record.getRecordNumber() <= 3) {
                System.out.println("Record #" + record.getRecordNumber() + ": hasUsageKWh=" + hasUsageKWh + ", hasDevice=" + hasDevice);
                System.out.println("  Valores: date=" + record.get("date") + ", Usage_kWh=" + record.get("Usage_kWh"));
            }

            // Formato 1: Industrial (11 columnas)
            if (hasUsageKWh) {
                return parseIndustrialFormat(record);
            }

            // Formato 2: Simple (device, power, timestamp)
            if (hasDevice) {
                return parseSimpleFormat(record);
            }

            // Si no detecta el formato, retorna null
            return null;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parsea el formato industrial de 11 columnas
     */
    private static Measurement parseIndustrialFormat(CSVRecord record) throws Exception {
        String dateStr = record.get("date").trim();

        LocalDateTime date = parseDate(dateStr);
        if (date == null) {
            if (record.getRecordNumber() <= 3) {
                System.err.println("No se pudo parsear fecha: " + dateStr);
            }
            return null;
        }

        try {
            double usageKWh = Double.parseDouble(record.get("Usage_kWh").trim());
            double laggingReactive = Double.parseDouble(record.get("Lagging_Current_Reactive.Power_kVarh").trim());
            double leadingReactive = Double.parseDouble(record.get("Leading_Current_Reactive_Power_kVarh").trim());
            double co2 = Double.parseDouble(record.get("CO2(tCO2)").trim());
            double laggingPF = Double.parseDouble(record.get("Lagging_Current_Power_Factor").trim());
            double leadingPF = Double.parseDouble(record.get("Leading_Current_Power_Factor").trim());
            int nsm = (int) Double.parseDouble(record.get("NSM").trim()); // Parsear como double primero, luego castear a int
            String weekStatus = record.get("WeekStatus").trim();
            String dayOfWeek = record.get("Day_of_week").trim();
            String loadType = record.get("Load_Type").trim();

            return new Measurement(date, usageKWh, laggingReactive, leadingReactive, co2,
                    laggingPF, leadingPF, nsm, weekStatus, dayOfWeek, loadType);
        } catch (Exception e) {
            if (record.getRecordNumber() <= 3) {
                System.err.println("Error parseando valores en registro " + record.getRecordNumber() + ": " + e.getMessage());
            }
            throw e;
        }
    }

    /**
     * Parsea el formato simple (device, power, timestamp)
     */
    private static Measurement parseSimpleFormat(CSVRecord record) throws Exception {
        String device = record.get("device").trim();
        double power = Double.parseDouble(record.get("power").trim());
        LocalDateTime timestamp = LocalDateTime.parse(record.get("timestamp").trim());

        return new Measurement(timestamp, power, 0.0, 0.0, 0.0, 1.0, 1.0, 0,
                "Unknown", "Unknown", device);
    }

    /**
     * Parsea fechas en múltiples formatos
     */
    private static LocalDateTime parseDate(String dateStr) {
        dateStr = dateStr.trim();

        // Intentar formatos comunes
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(dateStr, formatter);
            } catch (Exception e) {
                // Continuar con el siguiente formato
            }
        }

        return null;
    }
}

