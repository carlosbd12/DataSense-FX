# ✅ Implementación MySQL Simplificada

La conexión con MySQL para gestión de usuarios ha sido **implementada exitosamente**.

⚠️ **IMPORTANTE**: Esta versión usa **contraseñas en TEXTO PLANO** para simplificar el desarrollo.  
**NUNCA usar esto en producción** - Solo para desarrollo y pruebas.

---

## 🎉 **Estado del Proyecto**

✅ **Compilación**: BUILD SUCCESS  
✅ **Dependencias**: MySQL Connector descargado  
✅ **Módulos Java**: Configurados correctamente  
✅ **Autenticación**: Texto plano (simplificado)  
✅ **BCrypt**: Eliminado para simplificar  

---

## 📋 **Pasos para Usar la Aplicación**

### **1. Instalar MySQL**

Si aún no tienes MySQL instalado:

```bash
# macOS
brew install mysql
brew services start mysql

# Linux
sudo apt install mysql-server
sudo systemctl start mysql
```

---

### **2. Crear la Base de Datos**

Ejecuta el script SQL incluido:

```bash
# Conectar a MySQL
mysql -u root -p

# Dentro de MySQL, ejecutar:
source /Users/carlosbaneza/IdeaProjects/DataSense-FX/DataSenseFX/database/setup.sql
```

Esto creará:
- Base de datos `datasensefx`
- Tabla `usuarios` con contraseñas en texto plano
- 3 usuarios de prueba

---

### **3. Configurar la Contraseña**

Edita el archivo: `src/main/resources/database.properties`

```properties
db.password=TU_PASSWORD_MYSQL_AQUI
```

**⚠️ IMPORTANTE**: Cambia `root` por tu contraseña real de MySQL.

---

### **4. Ejecutar la Aplicación**

Desde tu IDE (IntelliJ IDEA, Eclipse, etc.) o desde terminal:

```bash
cd /Users/carlosbaneza/IdeaProjects/DataSense-FX/DataSenseFX
mvn javafx:run
```

---

### **5. Iniciar Sesión**

Usa estas credenciales de prueba:

| Email | Contraseña | Rol |
|-------|-----------|-----|
| `admin@datasense.com` | `admin` | ADMIN_PLATAFORMA |
| `gestor@datasense.com` | `gestor` | GESTOR_EDIFICIO |
| `operador@datasense.com` | `operador` | RESPONSABLE_PLANTA |

---

## 🔧 **Cambios Realizados**

### **Archivos Eliminados:**
- ❌ `PasswordUtil.java` - Utilidades de encriptación BCrypt
- ❌ `GeneratePasswordHash.java` - Generador de hashes
- ❌ `PasswordUtilTest.java` - Tests de BCrypt

### **Archivos Modificados:**
- ✅ `pom.xml` - Eliminada dependencia de jBCrypt
- ✅ `module-info.java` - Eliminado `requires jbcrypt;`
- ✅ `UserDAO.java` - Comparación directa de contraseñas
- ✅ `AuthService.java` - Validación simple sin BCrypt
- ✅ `database/setup.sql` - Contraseñas en texto plano

---

## 🎯 **Funcionalidades Implementadas**

✅ Login con email y contraseña (texto plano)  
✅ Gestión de sesión con UserSession  
✅ Pool de conexiones HikariCP  
✅ CRUD completo de usuarios (UserDAO)  
✅ Validación simple de email y contraseña  

---

## ⚠️ **Advertencias de Seguridad**

🚨 **Las contraseñas se guardan en TEXTO PLANO en la base de datos**  
🚨 **Cualquiera con acceso a la BD puede ver las contraseñas**  
🚨 **NO usar en producción**  
🚨 **Solo para desarrollo y aprendizaje**  

---

## 🔄 **Para Volver a BCrypt (Producción)**

Si en el futuro quieres volver a usar encriptación:

1. Agregar dependencia jBCrypt al `pom.xml`
2. Agregar `requires jbcrypt;` al `module-info.java`
3. Restaurar `PasswordUtil.java`
4. Actualizar `UserDAO.java` y `AuthService.java`
5. Actualizar `setup.sql` con hashes BCrypt

---

## 🚀 **Próximos Pasos (Opcional)**

Si quieres extender la funcionalidad:

1. Crear pantalla de gestión de usuarios en la UI
2. Implementar recuperación de contraseña
3. Agregar auditoría de sesiones
4. Configuración personalizada por usuario

---

**¡Todo listo para usar!** 🎉

**Recuerda**: Esta es una versión simplificada para desarrollo. Para producción, siempre usa encriptación.

