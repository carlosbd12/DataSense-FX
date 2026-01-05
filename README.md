# DataSense FX

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-21.0.6-blue?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.9+-red?style=for-the-badge&logo=apache-maven)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

**Sistema de Monitoreo y Gestión de Energía Industrial**

Una aplicación de escritorio JavaFX para análisis de consumo energético en plantas industriales del sector siderúrgico, con dashboards interactivos, generación de informes y gestión de usuarios basada en roles.

[Características](#características) •
[Instalación](#instalación) •
[Uso](#uso) •
[Documentación](#documentación) •
[Arquitectura](#arquitectura)

</div>

---

## 📋 Tabla de Contenidos

- [Descripción](#descripción)
- [Características](#características)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Arquitectura](#arquitectura)
- [Roles y Permisos](#roles-y-permisos)
- [Sistema de Informes](#sistema-de-informes)
- [Base de Datos](#base-de-datos)
- [Documentación Adicional](#documentación-adicional)
- [Contribución](#contribución)
- [Licencia](#licencia)

---

## 📖 Descripción

**DataSense FX** es una aplicación de escritorio desarrollada con JavaFX que permite a las plantas industriales monitorear, analizar y optimizar su consumo energético. El sistema procesa datos de mediciones en intervalos de 15 minutos, generando análisis detallados, visualizaciones interactivas y reportes profesionales en formato PDF.

### ¿Para quién es este proyecto?

- **Responsables de Planta**: Supervisión operativa del consumo energético
- **Gestores de Edificio**: Análisis de tendencias y generación de informes
- **Administradores**: Gestión completa de usuarios y configuración del sistema

---

## ✨ Características

### 🎛️ Dashboard Interactivo

- **Indicadores Clave (KPIs)**:
  - Total de mediciones
  - Consumo promedio (kWh)
  - Rango de consumo (mínimo/máximo)
  - Emisiones totales de CO₂
  - Seguimiento de picos de consumo

- **Gráficos Interactivos**:
  - 📈 Consumo por Hora (LineChart con DatePicker)
  - 📊 Consumo por Tipo de Carga (BarChart)
  - 📅 Consumo por Día de la Semana (BarChart)
  - 🌍 Emisiones de CO₂ por Día (BarChart)
  - ⚖️ Comparativa Días Laborables vs Fin de Semana
  - Alternancia entre vista total y promedio

### 📊 Sistema de Informes Profesionales

**4 Tipos de Informes**:

1. **Informe Diario**
   - Consumo total, promedio, pico y mínimo
   - Desglose horario completo
   - Distribución por tipo de carga
   - Emisiones de CO₂

2. **Informe Semanal**
   - Análisis de 7 días
   - Comparativa laborables vs fin de semana
   - Desglose por día de la semana
   - Consumo por zona

3. **Informe Mensual**
   - Consumo y costes mensuales
   - Coste por kWh (0.15€ por defecto)
   - Comparación con mes anterior
   - Análisis de tendencias (↑↓)

4. **Informe de Eficiencia**
   - Factor de potencia (objetivo: >85%)
   - Factor de carga (objetivo: >60%)
   - Intensidad energética y de CO₂
   - Distribución de carga (ligera/media/máxima)
   - Oportunidades de mejora
   - Recomendaciones automatizadas

### 📄 Exportación a PDF

- Diseño profesional con encabezado corporativo
- Pie de página con metadata (fecha, página, usuario)
- Tablas formateadas con colores corporativos
- Soporte completo para Unicode y emojis
- Nombre de archivo autogenerado
- FileChooser para selección de ubicación

### 👥 Gestión de Usuarios (Solo Admin)

- CRUD completo de usuarios
- Asignación de roles
- Activación/desactivación de cuentas
- Generación automática de contraseñas temporales
- Validación en tiempo real
- Tabla interactiva con ordenación
- Protecciones:
  - No se puede desactivar el último admin
  - No se puede desactivar la cuenta propia

### 🔐 Sistema de Autenticación

- Login con email/usuario y contraseña
- Gestión de sesión con patrón Singleton
- Control de acceso basado en roles (RBAC)
- Seguimiento de último acceso
- Cerrar sesión seguro

---

## 🛠️ Tecnologías

### Core

- **Java 21** - Plataforma moderna de Java
- **JavaFX 21.0.6** - Framework de UI de escritorio
- **Maven** - Gestión de dependencias y construcción

### UI & Visualización

- **ControlsFX 11.2.1** - Controles avanzados de JavaFX
- **XChart 3.8.4** - Librería de gráficos
- **TilesFX 21.0.9** - Componentes de dashboard tipo tile
- **Ikonli JavaFX 12.3.1** - Librería de iconos
- **BootstrapFX 0.4.0** - Estilos CSS tipo Bootstrap

### Base de Datos

- **MySQL 8.0+** - Base de datos principal
- **HikariCP 7.0.2** - Pool de conexiones de alto rendimiento
- **MySQL Connector J 8.2.0** - Driver JDBC

### Datos y Procesamiento

- **Apache Commons CSV 1.10.0** - Parseo robusto de CSV
- **OpenPDF 1.3.34** - Generación de documentos PDF

### Testing

- **JUnit Jupiter 5.12.1** - Framework de pruebas
- **Mockito 5.7.0** - Framework de mocking

---

## 📋 Requisitos Previos

### Software Necesario

- **Java JDK 21** o superior
  ```bash
  java -version
  # Debe mostrar: java version "21.x.x"
  ```

- **Maven 3.9+**
  ```bash
  mvn -version
  ```

- **MySQL 8.0+**
  ```bash
  mysql --version
  ```

- **Git** (para clonar el repositorio)

### Conocimientos Recomendados

- Java y programación orientada a objetos
- JavaFX y desarrollo de interfaces gráficas
- SQL y bases de datos relacionales
- Patrones de diseño (MVC, DAO, Singleton)

---

## 🚀 Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/DataSense-FX.git
cd DataSense-FX/DataSenseFX
```

### 2. Configurar la Base de Datos MySQL

#### Opción A: Configuración Automática

```bash
# Ejecutar el script de configuración
mysql -u root -p < database/setup.sql
```

#### Opción B: Configuración Manual

```sql
-- Crear la base de datos
CREATE DATABASE datasensefx CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE datasensefx;

-- Ejecutar el script completo
SOURCE database/setup.sql;
```

El script creará:
- Base de datos `datasensefx`
- Tabla `usuarios` con estructura completa
- 3 usuarios de prueba con diferentes roles

### 3. Configurar Conexión a Base de Datos

Editar el archivo `src/main/resources/database.properties`:

```properties
# Configuración de MySQL
db.url=jdbc:mysql://localhost:3306/datasensefx
db.username=tu_usuario
db.password=tu_contraseña
db.driver=com.mysql.cj.jdbc.Driver

# Pool de conexiones HikariCP
hikari.maximumPoolSize=10
hikari.minimumIdle=5
hikari.connectionTimeout=30000
hikari.idleTimeout=600000
hikari.maxLifetime=1800000
```

### 4. Compilar el Proyecto

```bash
mvn clean compile
```

### 5. Ejecutar la Aplicación

#### Con Maven:
```bash
mvn javafx:run
```

#### Con IDE (IntelliJ IDEA / Eclipse):
- Clase principal: `org.example.datasensefx.Launcher`
- Asegurarse de que VM options incluye: `--add-reads org.example.datasensefx=ALL-UNNAMED`

---

## ⚙️ Configuración

### Usuarios de Prueba

El sistema incluye 3 usuarios preconfigurados:

| Email | Contraseña | Rol | Permisos |
|-------|-----------|-----|----------|
| `admin@datasense.com` | `admin` | Admin Plataforma | Acceso completo + gestión de usuarios |
| `gestor@datasense.com` | `gestor` | Gestor Edificio | Dashboard + Informes |
| `operador@datasense.com` | `operador` | Responsable Planta | Dashboard + Dispositivos + Informes |

### Datos de Prueba

El proyecto incluye un dataset de ejemplo:
- **Ubicación**: `src/main/resources/data/steel_industry_data.csv`
- **Formato**: Datos industriales de planta siderúrgica
- **Período**: Enero 2018 en adelante
- **Intervalo**: Mediciones cada 15 minutos
- **Columnas**: 11 campos (consumo, factor de potencia, CO₂, tipo de carga, etc.)

### Personalización

#### Cambiar Precio por kWh

En `MonthlyReport.java`:
```java
private double costPerKWh = 0.15; // Modificar según tarifa local
```

#### Colores Corporativos del PDF

En `PDFExportService.java`:
```java
private static final Color COLOR_PRIMARY = new Color(41, 128, 185);
private static final Color COLOR_HEADER = new Color(52, 73, 94);
private static final Color COLOR_TABLE_HEADER = new Color(149, 165, 166);
```

---

## 📖 Uso

### 1. Iniciar Sesión

1. Ejecutar la aplicación
2. Ingresar email y contraseña
3. Click en "Iniciar Sesión"

### 2. Dashboard

- **Ver KPIs**: Métricas principales en la parte superior
- **Filtrar por fecha**: Usar DatePicker en gráfico de consumo por hora
- **Alternar vistas**: Botones "Total" / "Promedio" en cada gráfico
- **Navegación**: Sidebar izquierdo para cambiar de vista

### 3. Generar Informes

1. Click en **"Informes"** en el sidebar
2. Seleccionar tipo de informe:
   - Diario: Elegir fecha específica
   - Semanal: Automático (última semana)
   - Mensual: Automático (último mes)
   - Eficiencia: Automático (período completo)
3. Click en **"Ver informe"**
4. Revisar el informe generado
5. **Opcional**: Click en **"Exportar PDF"**
   - Seleccionar ubicación y nombre
   - El PDF se genera automáticamente

### 4. Gestión de Usuarios (Solo Admin)

1. Click en **"Configuración"** en el sidebar
2. **Crear usuario**:
   - Completar formulario (Username, Email, Nombre, Rol)
   - Click en "Agregar Usuario"
   - Se generará una contraseña temporal automáticamente
3. **Editar usuario**:
   - Click en botón "Editar" en la tabla
   - Modificar campos necesarios
   - Click en "Actualizar Usuario"
4. **Activar/Desactivar**:
   - Click en botón de estado en la tabla
   - Confirmación automática

### 5. Cerrar Sesión

- Click en el email del usuario (esquina superior derecha)
- Seleccionar "Cerrar Sesión"

---

## 📁 Estructura del Proyecto

```
DataSenseFX/
│
├── src/main/java/org/example/datasensefx/
│   ├── controllers/              # Controladores JavaFX (6 archivos)
│   │   ├── LoginController.java
│   │   ├── DashboardController.java
│   │   ├── DevicesController.java
│   │   ├── ReportsController.java
│   │   ├── ReportDetailController.java
│   │   └── ConfigController.java
│   │
│   ├── model/                    # Modelos de dominio (9 archivos)
│   │   ├── User.java
│   │   ├── Rol.java
│   │   ├── Measurement.java
│   │   ├── Report.java
│   │   ├── DailyReport.java
│   │   ├── WeeklyReport.java
│   │   ├── MonthlyReport.java
│   │   └── EfficiencyReport.java
│   │
│   ├── services/                 # Lógica de negocio (5 archivos)
│   │   ├── AuthService.java
│   │   ├── DataLoader.java
│   │   ├── MainController.java
│   │   ├── ReportGenerator.java
│   │   └── PDFExportService.java
│   │
│   ├── dao/                      # Acceso a datos (1 archivo)
│   │   └── UserDAO.java
│   │
│   ├── utils/                    # Utilidades (6 archivos)
│   │   ├── UserSession.java
│   │   ├── SceneManager.java
│   │   ├── DatabaseManager.java
│   │   ├── FxChartUtils.java
│   │   └── PasswordGenerator.java
│   │
│   ├── Main.java                 # Punto de entrada (con módulos)
│   └── Launcher.java             # Launcher sin módulos
│
├── src/main/resources/
│   ├── views/                    # Vistas FXML (6 archivos)
│   │   ├── login-view.fxml
│   │   ├── dashboard-view.fxml
│   │   ├── devices-view.fxml
│   │   ├── reports-view.fxml
│   │   ├── report-detail-view.fxml
│   │   └── config-view.fxml
│   │
│   ├── data/                     # Datos de ejemplo
│   │   └── steel_industry_data.csv
│   │
│   ├── images/                   # Recursos gráficos
│   │   └── logo.png
│   │
│   └── database.properties       # Configuración de BD
│
├── database/                     # Scripts de base de datos
│   ├── setup.sql                # Script de instalación
│   ├── README.md                # Guía de configuración de BD
│   └── INSTRUCCIONES_MYSQL_SIMPLIFICADO.md
│
├── pom.xml                       # Configuración de Maven
├── module-info.java              # Definición de módulo Java
└── README.md                     # Este archivo
```

---

## 🏗️ Arquitectura

### Patrón MVC (Model-View-Controller)

```
┌─────────────────────────────────────────────────────┐
│                     VISTA (FXML)                    │
│  ┌───────────┐  ┌──────────┐  ┌──────────────┐    │
│  │  Login    │  │Dashboard │  │  Informes    │    │
│  └─────┬─────┘  └────┬─────┘  └──────┬───────┘    │
└────────┼─────────────┼───────────────┼────────────┘
         │             │               │
         ▼             ▼               ▼
┌─────────────────────────────────────────────────────┐
│              CONTROLADORES (JavaFX)                 │
│  ┌─────────────────────────────────────────────┐   │
│  │  LoginController, DashboardController, etc │   │
│  └────────────────┬────────────────────────────┘   │
└───────────────────┼─────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────────────┐
│                   SERVICIOS                         │
│  ┌──────────┐  ┌────────────┐  ┌───────────────┐  │
│  │AuthService│ │ReportGen   │  │PDFExport     │  │
│  └─────┬────┘  └──────┬─────┘  └───────┬───────┘  │
└────────┼──────────────┼────────────────┼───────────┘
         │              │                │
         ▼              ▼                ▼
┌─────────────────────────────────────────────────────┐
│                   DAO / DATA                        │
│  ┌──────────┐  ┌────────────┐  ┌───────────────┐  │
│  │ UserDAO  │  │ DataLoader │  │ CSV Files     │  │
│  └─────┬────┘  └──────┬─────┘  └───────┬───────┘  │
└────────┼──────────────┼────────────────┼───────────┘
         │              │                │
         ▼              ▼                ▼
┌─────────────────────────────────────────────────────┐
│              BASE DE DATOS / ARCHIVOS               │
│              MySQL + CSV Dataset                    │
└─────────────────────────────────────────────────────┘
```

### Patrones de Diseño Implementados

1. **Singleton**: `UserSession`, `DatabaseManager`
2. **Factory**: `ReportGenerator` para diferentes tipos de informes
3. **DAO (Data Access Object)**: `UserDAO` para abstracción de BD
4. **MVC**: Separación clara entre Vista, Controlador y Modelo
5. **Service Layer**: Lógica de negocio encapsulada en servicios
6. **Strategy**: Diferentes estrategias de generación de informes

### Flujo de Datos

```
Usuario → Vista FXML → Controlador → Servicio → DAO → Base de Datos
   ↑                                                        ↓
   └────────────────── Respuesta ←─────────────────────────┘
```

---

## 👤 Roles y Permisos

### Matriz de Permisos

| Funcionalidad | Admin Plataforma | Responsable Planta | Gestor Edificio |
|--------------|------------------|-------------------|-----------------|
| Dashboard | ✅ | ✅ | ✅ |
| Dispositivos | ✅ | ✅ | ❌ |
| Informes | ✅ | ✅ | ✅ |
| Exportar PDF | ✅ | ✅ | ✅ |
| Configuración | ✅ | ❌ | ❌ |
| Gestión de Usuarios | ✅ | ❌ | ❌ |

### Descripción de Roles

**🔧 Admin Plataforma**
- Acceso completo a todas las funcionalidades
- Gestión de usuarios (CRUD)
- Configuración del sistema
- No puede desactivar su propia cuenta
- No puede desactivar al último admin

**⚡ Responsable Planta**
- Supervisión operativa del consumo
- Monitoreo de dispositivos
- Generación de informes
- Enfoque en operaciones diarias

**📊 Gestor Edificio**
- Análisis de tendencias de consumo
- Generación de informes estratégicos
- Vista de dashboard
- Enfoque en optimización y planificación

---

## 📊 Sistema de Informes

### Generación de Informes

```java
// Ejemplo de uso del ReportGenerator
ReportGenerator generator = new ReportGenerator(measurements);

// Informe Diario
DailyReport dailyReport = generator.generateDailyReport(LocalDate.of(2024, 1, 15));

// Informe Semanal
WeeklyReport weeklyReport = generator.generateWeeklyReport();

// Informe Mensual
MonthlyReport monthlyReport = generator.generateMonthlyReport();

// Informe de Eficiencia
EfficiencyReport efficiencyReport = generator.generateEfficiencyReport();
```

### Exportación a PDF

```java
// Ejemplo de exportación
PDFExportService pdfService = new PDFExportService();
File targetFile = new File("Informe_Diario_15_01_2024.pdf");
boolean success = pdfService.exportToPDF(report, targetFile);
```

### Métricas de Informes

#### Informe Diario
- Total de mediciones: ~96 (cada 15 min)
- Consumo total (kWh)
- Pico y hora del pico
- Mínimo y hora del mínimo
- Emisiones CO₂ totales

#### Informe Semanal
- Total de mediciones: ~672
- Consumo laborables vs fin de semana
- Distribución por día
- Porcentajes de distribución

#### Informe Mensual
- Coste total calculado
- Comparación con mes anterior
- Tendencia de cambio (%)
- Análisis de costes por semana

#### Informe de Eficiencia
- Factor de potencia: >85% = Excelente
- Factor de carga: >60% = Óptimo
- Intensidad de CO₂ (kg/kWh)
- Distribución de carga (ligera/media/máxima)
- Recomendaciones automáticas

---

## 🗄️ Base de Datos

### Esquema de la Base de Datos

#### Tabla: `usuarios`

```sql
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol ENUM('RESPONSABLE_PLANTA', 'GESTOR_EDIFICIO', 'ADMIN_PLATAFORMA')
        DEFAULT 'RESPONSABLE_PLANTA',
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso TIMESTAMP NULL,

    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_rol (rol),
    INDEX idx_activo (activo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### Configuración de HikariCP

```properties
# Pool de conexiones de alto rendimiento
hikari.maximumPoolSize=10        # Máximo de conexiones
hikari.minimumIdle=5             # Mínimo de conexiones idle
hikari.connectionTimeout=30000   # Timeout de conexión (30s)
hikari.idleTimeout=600000        # Timeout idle (10 min)
hikari.maxLifetime=1800000       # Vida máxima (30 min)
```

### Operaciones CRUD

El sistema implementa operaciones completas a través de `UserDAO`:

- **CREATE**: `create(User user)`
- **READ**: `findById(int id)`, `findAll()`, `findByEmail(String email)`
- **UPDATE**: `update(User user)`
- **DELETE**: `delete(int id)`

### Seguridad de Base de Datos

**Nota Importante**: La implementación actual utiliza contraseñas en texto plano para desarrollo. Para producción, se recomienda:

```java
// Implementar hashing BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode(plainPassword);
boolean matches = encoder.matches(plainPassword, hashedPassword);
```

---

## 📚 Documentación Adicional

### Documentos Incluidos

- **[MYSQL_SETUP_GUIDE.md](DataSenseFX/MYSQL_SETUP_GUIDE.md)** - Guía detallada de configuración de MySQL
- **[INSTRUCCIONES_MYSQL_SIMPLIFICADO.md](DataSenseFX/INSTRUCCIONES_MYSQL_SIMPLIFICADO.md)** - Instrucciones simplificadas
- **[database/README.md](DataSenseFX/database/README.md)** - Documentación de scripts SQL
- **[INSTRUCCIONES_CARGA_CSV.md](INSTRUCCIONES_CARGA_CSV.md)** - Guía para carga de datos CSV

### JavaDoc

Generar documentación JavaDoc:

```bash
mvn javadoc:javadoc
```

La documentación se generará en: `target/site/apidocs/`

### Diagramas

#### Diagrama de Clases (Simplificado)

```
┌─────────────┐
│   User      │
├─────────────┤
│ - id        │
│ - username  │
│ - email     │
│ - rol       │
│ - activo    │
└─────────────┘
       ↑
       │
┌──────┴──────────────────┐
│                         │
┌─────────────┐   ┌──────────────┐
│   Rol       │   │ UserSession  │
│ (ENUM)      │   │ (Singleton)  │
└─────────────┘   └──────────────┘

┌─────────────┐
│  Report     │
│ (Abstract)  │
├─────────────┤
│ + title     │
│ + period    │
│ + data      │
└──────┬──────┘
       │
   ┌───┴───┬───────┬──────────┐
   │       │       │          │
┌──▼───┐ ┌▼────┐ ┌▼──────┐ ┌▼────────┐
│Daily │ │Week │ │Month  │ │Efficiency│
│Report│ │Report│ │Report │ │Report   │
└──────┘ └─────┘ └───────┘ └─────────┘
```

---

## 🤝 Contribución

### Cómo Contribuir

1. **Fork** el repositorio
2. Crear una rama para tu feature (`git checkout -b feature/NuevaCaracteristica`)
3. Commit tus cambios (`git commit -m 'Agregar nueva característica'`)
4. Push a la rama (`git push origin feature/NuevaCaracteristica`)
5. Abrir un **Pull Request**

### Guía de Estilo

- **Java**: Seguir convenciones de Google Java Style Guide
- **Commits**: Mensajes descriptivos en español o inglés
- **Documentación**: JavaDoc para clases y métodos públicos
- **Testing**: Pruebas unitarias para lógica de negocio crítica

### Reporte de Bugs

Abrir un issue con:
- Descripción clara del problema
- Pasos para reproducir
- Versión de Java y JavaFX
- Sistema operativo
- Logs relevantes

---

## 🔧 Solución de Problemas

### Error: "package com.lowagie.text is not visible"

**Solución**: Asegúrate de que `module-info.java` incluya:
```java
requires java.desktop;
requires com.github.librepdf.openpdf;
```

### Error de Conexión a MySQL

**Verificar**:
1. MySQL está corriendo: `sudo service mysql status`
2. Credenciales correctas en `database.properties`
3. Base de datos creada: `SHOW DATABASES;`
4. Puerto 3306 abierto

### Gráficos no se Muestran

**Verificar**:
1. Datos CSV cargados correctamente
2. Rango de fechas en el dataset
3. Logs en consola para errores
4. `DataLoader` funciona correctamente

### PDF no se Genera

**Verificar**:
1. Permisos de escritura en el directorio
2. OpenPDF está en el classpath
3. Todos los datos del informe están completos
4. Logs en consola: `System.out.println`

---

## 📊 Roadmap

### Versión Actual: 1.0.0

- ✅ Dashboard interactivo
- ✅ Sistema de informes completo
- ✅ Exportación a PDF
- ✅ Gestión de usuarios
- ✅ Autenticación y roles

### Próximas Versiones

**v1.1.0** (Seguridad)
- [ ] Implementar BCrypt para passwords
- [ ] Agregar JWT para sesiones
- [ ] Logging de auditoría
- [ ] Encriptación de datos sensibles

**v1.2.0** (Funcionalidades)
- [ ] Exportación a Excel
- [ ] Envío de informes por email
- [ ] Alertas de consumo excesivo
- [ ] Dashboard personalizable

**v1.3.0** (Integración)
- [ ] API REST para integración
- [ ] WebSocket para actualizaciones en tiempo real
- [ ] Integración con sensores IoT
- [ ] App móvil complementaria

---

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

```
MIT License

Copyright (c) 2024 DataSense FX

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 👨‍💻 Autor

**Carlos Bañeza**

- Email: carlos@datasense.com
- GitHub: [@carlosbaneza](https://github.com/carlosbaneza)
- LinkedIn: [Carlos Bañeza](https://linkedin.com/in/carlosbaneza)

---

## 🙏 Agradecimientos

- Comunidad de JavaFX por la excelente documentación
- OpenPDF por la librería de generación de PDFs
- XChart por los componentes de visualización
- Stack Overflow por resolver innumerables dudas

---

## 📞 Soporte

¿Necesitas ayuda? Abre un [issue](https://github.com/carlosbaneza/DataSense-FX/issues) o contáctanos:

- 📧 Email: soporte@datasense.com
- 💬 Discord: [DataSense Community](https://discord.gg/datasense)
- 📖 Wiki: [Documentación Completa](https://github.com/carlosbaneza/DataSense-FX/wiki)

---

<div align="center">

**⭐ Si te gusta este proyecto, dale una estrella en GitHub ⭐**

Hecho con ❤️ y ☕ por el equipo de DataSense

[Volver arriba](#datasense-fx)

</div>
