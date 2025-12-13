# REVISIÓN DEL PROYECTO DATASENSEFX
## Sistema de Monitorización Energética

**Fecha:** Diciembre 2025  
**Versión:** 1.0-SNAPSHOT  
**Autor:** Carlos Bañeza

---

## 1. REQUISITOS DEL SISTEMA

### 1.1 Requisitos Funcionales

El sistema **DataSenseFX** es una plataforma de monitorización y gestión energética diseñada para:

- **Autenticación y Control de Acceso**: Sistema de login con gestión de roles diferenciados
- **Dashboard de Monitorización**: Visualización centralizada de métricas energéticas
- **Gestión de Dispositivos**: Administración de sensores y dispositivos IoT
- **Generación de Informes**: Reportes de consumo y eficiencia energética
- **Configuración del Sistema**: Panel administrativo para gestión de la plataforma

### 1.2 Requisitos No Funcionales

- **Rendimiento**: Pool de conexiones optimizado con HikariCP
- **Escalabilidad**: Arquitectura modular preparada para crecimiento
- **Usabilidad**: Interfaz gráfica intuitiva basada en JavaFX
- **Seguridad**: Sistema de roles con permisos diferenciados
- **Mantenibilidad**: Código estructurado siguiendo patrón MVC

### 1.3 Requisitos Técnicos

| Componente | Especificación |
|------------|----------------|
| **Java** | JDK 21 |
| **Framework UI** | JavaFX 21.0.6 |
| **Gestor de Dependencias** | Maven 3.x |
| **Base de Datos** | SQL (preparado para MySQL/PostgreSQL) |
| **Pool de Conexiones** | HikariCP 7.0.2 |

---

## 2. INVESTIGACIÓN Y MÓDULOS/LIBRERÍAS UTILIZADAS

### 2.1 Librerías JavaFX

**JavaFX 21.0.6** - Framework principal de interfaz gráfica
- `javafx-controls`: Componentes UI (botones, tablas, formularios)
- `javafx-fxml`: Separación vista-controlador mediante archivos FXML
- `javafx-web`: Integración de contenido web
- `javafx-swing`: Compatibilidad con componentes Swing legacy

### 2.2 Librerías de UI Extendidas

**ControlsFX 11.2.1**
- Controles avanzados no incluidos en JavaFX estándar
- Diálogos personalizados y validaciones

**FormsFX 11.6.0**
- Framework especializado en creación de formularios
- Binding automático de datos

**ValidatorFX 0.6.1**
- Validación de campos en tiempo real
- Reglas de validación personalizables

**Ikonli 12.3.1**
- Biblioteca de iconos vectoriales
- Soporte para Font Awesome, Material Design, etc.

**BootstrapFX 0.4.0**
- Estilos CSS inspirados en Bootstrap
- Diseño responsive y moderno

**TilesFX 21.0.9**
- Componentes de visualización tipo dashboard
- Gráficos y widgets para métricas en tiempo real

### 2.3 Gestión de Datos

**HikariCP 7.0.2**
- Pool de conexiones de alto rendimiento
- Gestión eficiente de recursos de base de datos
- Configuración optimizada para producción

**Java SQL (java.sql)**
- API estándar para acceso a bases de datos
- JDBC para conectividad

### 2.4 Testing

**JUnit Jupiter 5.12.1**
- Framework de testing unitario moderno
- Soporte para Java 21

**Mockito 5.7.0**
- Framework para crear objetos mock
- Integración con JUnit 5

---

## 3. CASOS DE USO

### 3.1 Diagrama de Actores

```
┌─────────────────────────────────────┐
│         ACTORES DEL SISTEMA         │
├─────────────────────────────────────┤
│ 1. Responsable Energético (Planta)  │
│ 2. Gestor de Edificio               │
│ 3. Administrador de Plataforma      │
└─────────────────────────────────────┘
```

### 3.2 Casos de Uso Principales

#### CU-01: Iniciar Sesión
- **Actor**: Todos los usuarios
- **Descripción**: Autenticación mediante usuario y contraseña
- **Flujo Principal**:
  1. Usuario selecciona tipo de usuario (operador/gestor/admin)
  2. Ingresa contraseña
  3. Sistema valida credenciales
  4. Sistema asigna rol y redirige al dashboard

#### CU-02: Visualizar Dashboard
- **Actor**: Todos los usuarios autenticados
- **Descripción**: Acceso a panel principal con métricas
- **Precondición**: Usuario autenticado
- **Flujo Principal**:
  1. Sistema muestra dashboard según rol
  2. Usuario visualiza métricas relevantes
  3. Usuario puede navegar a otras secciones

#### CU-03: Gestionar Dispositivos
- **Actor**: Responsable Energético, Administrador
- **Descripción**: Administración de sensores y dispositivos IoT
- **Restricción**: Gestor de Edificio NO tiene acceso

#### CU-04: Generar Informes
- **Actor**: Todos los usuarios
- **Descripción**: Visualización y generación de reportes
- **Nota**: Gestor de Edificio tiene acceso prioritario a esta función

#### CU-05: Configurar Sistema
- **Actor**: Administrador de Plataforma (exclusivo)
- **Descripción**: Gestión de parámetros del sistema
- **Restricción**: Solo accesible para rol ADMIN_PLATAFORMA

### 3.3 Matriz de Permisos por Rol

| Funcionalidad | Responsable Planta | Gestor Edificio | Admin Plataforma |
|---------------|:------------------:|:---------------:|:----------------:|
| Dashboard     | ✅ | ✅ | ✅ |
| Dispositivos  | ✅ | ❌ | ✅ |
| Informes      | ✅ | ✅ | ✅ |
| Configuración | ❌ | ❌ | ✅ |

---

## 4. ARQUITECTURA DEL SISTEMA

### 4.1 Patrón Arquitectónico

El sistema implementa el patrón **MVC (Model-View-Controller)** adaptado a JavaFX:

```
┌─────────────────────────────────────────────┐
│           ARQUITECTURA MVC                  │
├─────────────────────────────────────────────┤
│                                             │
│  ┌─────────┐      ┌────────────┐            │
│  │  VIEW   │◄─────┤ CONTROLLER │            │
│  │ (FXML)  │      │   (Java)   │            │
│  └─────────┘      └──────┬─────┘            │
│                          │                  │
│                          ▼                  │
│                   ┌──────────┐              │
│                   │  MODEL   │              │
│                   │  (Java)  │              │
│                   └──────────┘              │
│                          │                  │
│                          ▼                  │
│                   ┌──────────┐              │
│                   │ DATABASE │              │
│                   └──────────┘              │
└─────────────────────────────────────────────┘
```

