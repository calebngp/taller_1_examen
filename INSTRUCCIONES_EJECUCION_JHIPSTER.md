# 🚀 Instrucciones de Ejecución - Proyecto JHipster DevMatch

## ✅ Estado Actual

- ✅ Archivo JDL creado: `devmatch.jdl`
- ✅ Proyecto JHipster generado: `devmatch-jhipster/`
- ✅ Todas las entidades generadas correctamente
- ✅ Relaciones configuradas

## 📋 Entidades Generadas

1. **Technology** - Tecnologías/habilidades
2. **Developer** - Desarrolladores
3. **Project** - Proyectos
4. **Experience** - Experiencias de desarrolladores
5. **MatchResult** - Resultados de matching
6. **AuditHistory** - Historial de auditoría

## 🏃 Ejecutar el Proyecto

### Paso 1: Navegar al directorio del proyecto
```bash
cd devmatch-jhipster
```

### Paso 2: Instalar dependencias (si no se instalaron automáticamente)
```bash
npm install
```

### Paso 3: Compilar el proyecto
```bash
mvn clean install
```

### Paso 4: Ejecutar el proyecto
```bash
# Opción A: Usar Maven wrapper
./mvnw spring-boot:run

# Opción B: Usar Maven directamente
mvn spring-boot:run
```

### Paso 5: Acceder a la aplicación

Una vez que el proyecto esté ejecutándose, accede a:

- **Aplicación Principal**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console** (Base de datos): http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:devmatch`
  - Usuario: `sa`
  - Password: (dejar vacío)

## 🔐 Credenciales por Defecto

- **Usuario Administrador**: `admin`
- **Contraseña**: `admin`

## ✅ Validación de Entidades y Relaciones

### 1. Verificar Entidades en el Código

```bash
# Ver entidades generadas
ls -la src/main/java/com/devmatch/domain/

# Deberías ver:
# - Technology.java
# - Developer.java
# - Project.java
# - Experience.java
# - MatchResult.java
# - AuditHistory.java
```

### 2. Verificar Relaciones en el Código

Abre los archivos de entidades y verifica:

**Developer.java:**
- `@ManyToMany` con Technology (skills)
- `@OneToMany` con Experience (experiences)

**Project.java:**
- `@ManyToMany` con Technology (requiredTechnologies)

**Experience.java:**
- `@ManyToOne` con Developer

**MatchResult.java:**
- `@ManyToOne` con Project
- `@ManyToOne` con Developer

### 3. Verificar Tablas en la Base de Datos

1. Accede a H2 Console: http://localhost:8080/h2-console
2. Conéctate con las credenciales mencionadas arriba
3. Ejecuta:

```sql
-- Ver todas las tablas
SHOW TABLES;

-- Ver estructura de tablas principales
SELECT * FROM TECHNOLOGY;
SELECT * FROM DEVELOPER;
SELECT * FROM PROJECT;
SELECT * FROM EXPERIENCE;
SELECT * FROM MATCH_RESULT;
SELECT * FROM AUDIT_HISTORY;

-- Ver tablas de relaciones Many-to-Many
SELECT * FROM DEVELOPER_SKILLS;
SELECT * FROM PROJECT_TECHNOLOGIES;
```

### 4. Probar CRUD desde la Interfaz Web

1. Inicia sesión en http://localhost:8080
2. Navega a **Entities** en el menú
3. Prueba crear, editar y eliminar registros en:
   - Technology
   - Developer
   - Project
   - Experience
   - MatchResult
   - AuditHistory

### 5. Probar Relaciones

#### Crear Developer con Skills (Many-to-Many)
1. Ve a Entities → Developer → Create
2. Completa los campos básicos
3. En la sección "Skills", selecciona múltiples Technologies
4. Guarda y verifica

#### Crear Project con Required Technologies (Many-to-Many)
1. Ve a Entities → Project → Create
2. Completa los campos básicos
3. En "Required Technologies", selecciona múltiples Technologies
4. Guarda y verifica

#### Crear Experience para un Developer (One-to-Many)
1. Ve a Entities → Experience → Create
2. Completa los campos
3. Selecciona un Developer
4. Guarda y verifica

### 6. Probar API REST

Usa Swagger UI o Postman:

**Swagger UI**: http://localhost:8080/swagger-ui.html

Ejemplos de endpoints:
- `GET /api/technologies` - Listar tecnologías
- `POST /api/developers` - Crear desarrollador
- `GET /api/projects` - Listar proyectos
- `GET /api/experiences` - Listar experiencias

## 📸 Capturar Evidencias

Mientras ejecutas y validas, captura screenshots de:

1. ✅ Proyecto ejecutándose (terminal)
2. ✅ Página principal de la aplicación
3. ✅ Lista de entidades en el código
4. ✅ Relaciones en el código (anotaciones @ManyToMany, @OneToMany, etc.)
5. ✅ Tablas en H2 Console
6. ✅ CRUD funcionando (crear, editar, eliminar)
7. ✅ Relaciones funcionando (asignar skills a developer, etc.)
8. ✅ Swagger UI con endpoints disponibles

Guarda todas las capturas en: `docs/jhipster_evidencias/`

## 🐛 Solución de Problemas

### Error: "Port 8080 already in use"
```bash
# Encontrar proceso usando el puerto
lsof -ti:8080

# Matar el proceso
kill -9 $(lsof -ti:8080)

# O cambiar el puerto en application-dev.yml
```

### Error: "npm install failed"
```bash
# Limpiar cache de npm
npm cache clean --force

# Reinstalar
rm -rf node_modules package-lock.json
npm install
```

### Error: "Maven build failed"
```bash
# Limpiar y recompilar
mvn clean
mvn install -DskipTests
```

## 📚 Archivos Importantes

- **JDL**: `devmatch.jdl` (en el directorio raíz)
- **Proyecto Generado**: `devmatch-jhipster/`
- **Configuración**: `devmatch-jhipster/src/main/resources/config/application-dev.yml`
- **Entidades**: `devmatch-jhipster/src/main/java/com/devmatch/domain/`

## ✅ Checklist de Validación

- [ ] Proyecto compila sin errores
- [ ] Proyecto se ejecuta correctamente
- [ ] Todas las entidades están presentes en el código
- [ ] Todas las relaciones están configuradas correctamente
- [ ] Tablas se crean en la base de datos
- [ ] CRUD funciona desde la interfaz web
- [ ] Relaciones funcionan (Many-to-Many, One-to-Many)
- [ ] API REST funciona (Swagger UI)
- [ ] Todas las evidencias capturadas

---

**¡Proyecto listo para validar!** 🎉

