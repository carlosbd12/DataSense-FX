# 🚀 Guía de Configuración MySQL para DataSenseFX

Esta guía te ayudará a configurar la autenticación con MySQL en tu aplicación DataSenseFX.

---

## 📦 **¿Qué se ha implementado?**

✅ **Modelo de Usuario** (`User.java`)
- Campos: id, email, nombre, passwordHash, rol, activo, fechaCreacion, ultimoAcceso

✅ **DAO (Data Access Object)** (`UserDAO.java`)
- CRUD completo de usuarios
- Autenticación con verificación de contraseña
- Gestión de estado activo/inactivo
- Actualización de último acceso

✅ **Servicio de Autenticación** (`AuthService.java`)
- Login con email y contraseña
- Logout
- Registro de nuevos usuarios
- Cambio de contraseña
- Validaciones de seguridad

✅ **Utilidades de Seguridad** (`PasswordUtil.java`)
- Encriptación BCrypt
- Verificación de contraseñas
- Validación de email y contraseña

✅ **Gestión de Sesión** (`UserSession.java` actualizado)
- Almacena objeto User completo
- Mantiene compatibilidad con código existente

✅ **Controlador de Login** (`LoginController.java` actualizado)
- Autenticación contra base de datos MySQL
- Manejo de errores mejorado

✅ **Configuración de Base de Datos**
- `database.properties`: Configuración de conexión
- `DatabaseManager.java`: Pool de conexiones HikariCP
- `setup.sql`: Script de creación de tablas

---

## 🛠️ **Pasos de Instalación**

### **1. Instalar MySQL**

#### **macOS:**
```bash
brew install mysql
brew services start mysql
mysql_secure_installation
```

#### **Windows:**
Descarga desde: https://dev.mysql.com/downloads/installer/

#### **Linux:**
```bash
sudo apt install mysql-server
sudo systemctl start mysql
sudo mysql_secure_installation
```

---

### **2. Crear la Base de Datos**

```bash
# Conectar a MySQL
mysql -u root -p

# Ejecutar el script
source /ruta/completa/a/DataSenseFX/database/setup.sql

# O ejecutar manualmente:
CREATE DATABASE datasensefx;
USE datasensefx;
# ... copiar contenido de setup.sql
```

---

### **3. Configurar la Aplicación**

Edita `src/main/resources/database.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/datasensefx?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.username=root
db.password=TU_PASSWORD_MYSQL
db.driver=com.mysql.cj.jdbc.Driver
```

**⚠️ Cambia `TU_PASSWORD_MYSQL` por tu contraseña real de MySQL.**

---

### **4. Compilar el Proyecto**

```bash
cd DataSenseFX
mvn clean install
```

Esto descargará las nuevas dependencias:
- `mysql-connector-j` (8.2.0)
- `jbcrypt` (0.4)

---

### **5. Ejecutar la Aplicación**

```bash
mvn javafx:run
```

O desde tu IDE (IntelliJ IDEA, Eclipse, etc.)

---

## 🔑 **Usuarios de Prueba**

El script `setup.sql` crea 3 usuarios automáticamente:

| Email | Contraseña | Rol |
|-------|-----------|-----|
| `operador@datasense.com` | `operador` | RESPONSABLE_PLANTA |
| `gestor@datasense.com` | `gestor` | GESTOR_EDIFICIO |
| `admin@datasense.com` | `admin` | ADMIN_PLATAFORMA |

---

## 📁 **Estructura de Archivos Creados/Modificados**

```
DataSenseFX/
├── pom.xml (✏️ modificado - nuevas dependencias)
├── database/
│   ├── setup.sql (✨ nuevo)
│   └── README.md (✨ nuevo)
├── src/main/java/org/example/datasensefx/
│   ├── model/
│   │   └── User.java (✨ nuevo)
│   ├── dao/
│   │   └── UserDAO.java (✨ nuevo)
│   ├── services/
│   │   └── AuthService.java (✨ nuevo)
│   ├── utils/
│   │   ├── PasswordUtil.java (✨ nuevo)
│   │   ├── GeneratePasswordHash.java (✨ nuevo)
│   │   ├── UserSession.java (✏️ modificado)
│   │   └── DatabaseManager.java (ya existía)
│   └── controllers/
│       └── LoginController.java (✏️ modificado)
└── src/main/resources/
    └── database.properties (✨ nuevo)
```

---

## 🧪 **Probar la Implementación**

### **Test 1: Verificar Conexión a BD**

Ejecuta este código en `DatabaseManager`:
```java
public static void main(String[] args) {
    if (testConnection()) {
        System.out.println("✅ Conexión exitosa");
        printPoolStats();
    } else {
        System.out.println("❌ Error de conexión");
    }
}
```

### **Test 2: Generar Hashes de Contraseñas**

Ejecuta `GeneratePasswordHash.java`:
```bash
mvn exec:java -Dexec.mainClass="org.example.datasensefx.utils.GeneratePasswordHash"
```

### **Test 3: Login con Base de Datos**

1. Ejecuta la aplicación
2. Ingresa: `admin@datasense.com` / `admin`
3. Deberías ver en consola: `✅ Autenticación exitosa: admin@datasense.com`

---

## 🔐 **Seguridad Implementada**

✅ **Contraseñas encriptadas** con BCrypt (no se guardan en texto plano)
✅ **Validación de email** con expresión regular
✅ **Validación de contraseña** (mínimo 6 caracteres)
✅ **Usuarios activos/inactivos** (control de acceso)
✅ **Pool de conexiones** con HikariCP (eficiente y seguro)
✅ **Prepared Statements** (prevención de SQL Injection)

---

## 📊 **Funcionalidades Disponibles**

### **AuthService:**
- `login(email, password)` - Autenticar usuario
- `logout()` - Cerrar sesión
- `registerUser(...)` - Registrar nuevo usuario
- `changePassword(...)` - Cambiar contraseña
- `isAuthenticated()` - Verificar sesión activa

### **UserDAO:**
- `findByEmail(email)` - Buscar usuario por email
- `findById(id)` - Buscar usuario por ID
- `findAll()` - Listar todos los usuarios
- `create(user)` - Crear nuevo usuario
- `update(user)` - Actualizar usuario
- `delete(id)` - Eliminar usuario
- `authenticate(email, password)` - Autenticar
- `changePassword(userId, newPassword)` - Cambiar contraseña
- `activateUser(userId)` - Activar usuario
- `deactivateUser(userId)` - Desactivar usuario

---

## ❌ **Solución de Problemas**

### **Error: "No se encontró database.properties"**
- Verifica que el archivo esté en `src/main/resources/database.properties`
- Ejecuta `mvn clean install` para copiar recursos

### **Error: "Access denied for user 'root'"**
- Verifica la contraseña en `database.properties`
- Asegúrate de que MySQL esté corriendo

### **Error: "Unknown database 'datasensefx'"**
- Ejecuta el script `setup.sql` para crear la base de datos

### **Error: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"**
- Ejecuta `mvn clean install` para descargar dependencias

---

## 🎯 **Próximos Pasos (Opcional)**

1. **Pantalla de Registro de Usuarios** (CRUD completo en la UI)
2. **Recuperación de Contraseña** (envío de email)
3. **Auditoría de Sesiones** (tabla `sesiones`)
4. **Configuración por Usuario** (tabla `configuracion_usuario`)
5. **Roles y Permisos** (control de acceso granular)

---

¡Listo! Tu aplicación ahora usa MySQL para autenticación de usuarios. 🎉

