-- ============================================
-- Script de creación de base de datos MySQL
-- para DataSenseFX
-- ============================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS datasensefx
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE datasensefx;

-- ============================================
-- Tabla: usuarios
-- ============================================
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol ENUM('RESPONSABLE_PLANTA', 'GESTOR_EDIFICIO', 'ADMIN_PLATAFORMA') NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso TIMESTAMP NULL,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_rol (rol),
    INDEX idx_activo (activo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Tabla: sesiones (opcional, para auditoría)
-- ============================================
CREATE TABLE IF NOT EXISTS sesiones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    fecha_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_logout TIMESTAMP NULL,
    ip_address VARCHAR(45),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    INDEX idx_usuario_id (usuario_id),
    INDEX idx_fecha_login (fecha_login)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Tabla: configuracion_usuario (opcional)
-- ============================================
CREATE TABLE IF NOT EXISTS configuracion_usuario (
    usuario_id INT PRIMARY KEY,
    tema VARCHAR(20) DEFAULT 'light',
    idioma VARCHAR(10) DEFAULT 'es',
    notificaciones BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Insertar usuarios de prueba
-- ============================================
-- NOTA: Las contraseñas están en TEXTO PLANO (solo para desarrollo/pruebas)
-- ⚠️ NUNCA usar esto en producción

INSERT INTO usuarios (username, email, nombre, password_hash, rol, activo) VALUES
('operador', 'operador@datasense.com', 'Juan Operador', 'operador', 'RESPONSABLE_PLANTA', TRUE);

INSERT INTO usuarios (username, email, nombre, password_hash, rol, activo) VALUES
('gestor', 'gestor@datasense.com', 'María Gestora', 'gestor', 'GESTOR_EDIFICIO', TRUE);

INSERT INTO usuarios (username, email, nombre, password_hash, rol, activo) VALUES
('admin', 'admin@datasense.com', 'Carlos Administrador', 'admin', 'ADMIN_PLATAFORMA', TRUE);

-- ============================================
-- Verificar datos insertados
-- ============================================
SELECT 
    id, 
    email, 
    nombre, 
    rol, 
    activo, 
    fecha_creacion 
FROM usuarios;

-- ============================================
-- Información adicional
-- ============================================
-- Para conectar desde Java, usa:
-- URL: jdbc:mysql://localhost:3306/datasensefx?useSSL=false&serverTimezone=UTC
-- Usuario: root (o el que hayas configurado)
-- Password: tu_password

-- Credenciales de prueba (puedes usar username o email):
-- Username: admin,    Email: admin@datasense.com,    Password: admin
-- Username: gestor,   Email: gestor@datasense.com,   Password: gestor
-- Username: operador, Email: operador@datasense.com, Password: operador

