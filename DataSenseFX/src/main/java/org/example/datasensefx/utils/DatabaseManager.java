package org.example.datasensefx.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestor de conexiones a la base de datos
 * Utiliza HikariCP para pool de conexiones eficiente
 */
public class DatabaseManager {
    //PRUEBA
    private static HikariDataSource dataSource;
    
    static {
        try {
            // Cargar configuración desde database.properties
            Properties props = new Properties();
            InputStream input = DatabaseManager.class.getClassLoader()
                    .getResourceAsStream("database.properties");

            if (input == null) {
                throw new RuntimeException("No se encontró database.properties");
            }

            props.load(input);

            System.out.println("🔧 [DB DEBUG] Configuración cargada:");
            System.out.println("   URL: " + props.getProperty("db.url"));
            System.out.println("   Usuario: " + props.getProperty("db.username"));
            System.out.println("   Driver: " + props.getProperty("db.driver"));

            // Configurar HikariCP
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName(props.getProperty("db.driver"));

            // Configuración del pool
            config.setMaximumPoolSize(Integer.parseInt(
                props.getProperty("db.pool.maximumPoolSize", "10")));
            config.setMinimumIdle(Integer.parseInt(
                props.getProperty("db.pool.minimumIdle", "5")));
            config.setConnectionTimeout(Long.parseLong(
                props.getProperty("db.pool.connectionTimeout", "30000")));
            config.setIdleTimeout(Long.parseLong(
                props.getProperty("db.pool.idleTimeout", "600000")));
            config.setMaxLifetime(Long.parseLong(
                props.getProperty("db.pool.maxLifetime", "1800000")));

            // Configuraciones adicionales para mejor rendimiento
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);

            System.out.println("✅ Conexión a base de datos establecida correctamente");

        } catch (IOException e) {
            System.out.println("💥 [DB DEBUG] Error al cargar database.properties: " + e.getMessage());
            throw new RuntimeException("Error al cargar database.properties", e);
        } catch (Exception e) {
            System.out.println("💥 [DB DEBUG] Error al inicializar HikariCP: " + e.getMessage());
            throw new RuntimeException("Error al inicializar conexión a BD", e);
        }
    }
    
    /**
     * Obtiene una conexión del pool
     * 
     * @return Connection activa
     * @throws SQLException Si no se puede obtener la conexión
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    /**
     * Cierra el pool de conexiones
     * Debe llamarse al cerrar la aplicación
     */
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("✅ Pool de conexiones cerrado");
        }
    }
    
    /**
     * Verifica si la conexión está funcionando
     * 
     * @return true si la conexión es válida, false en caso contrario
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene información del pool de conexiones
     */
    public static void printPoolStats() {
        if (dataSource != null) {
            System.out.println("=== Pool de Conexiones ===");
            System.out.println("Conexiones activas: " + dataSource.getHikariPoolMXBean().getActiveConnections());
            System.out.println("Conexiones inactivas: " + dataSource.getHikariPoolMXBean().getIdleConnections());
            System.out.println("Conexiones totales: " + dataSource.getHikariPoolMXBean().getTotalConnections());
            System.out.println("Threads esperando: " + dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
        }
    }
}

