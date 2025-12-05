# 🚀 Guía Completa: Generación de Proyecto JHipster desde JDL

## 📋 Tabla de Contenidos
1. [Requisitos Previos](#requisitos-previos)
2. [Paso 1: Diseño del Modelo JDL](#paso-1-diseño-del-modelo-jdl)
3. [Paso 2: Validación del JDL](#paso-2-validación-del-jdl)
4. [Paso 3: Generación del Proyecto JHipster](#paso-3-generación-del-proyecto-jhipster)
5. [Paso 4: Configuración y Ejecución](#paso-4-configuración-y-ejecución)
6. [Paso 5: Validación de Entidades y Relaciones](#paso-5-validación-de-entidades-y-relaciones)
7. [Paso 6: Captura de Evidencias](#paso-6-captura-de-evidencias)

---

## 📦 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

### 1. Node.js y npm
```bash
# Verificar instalación
node -v  # Debe ser v18 o superior
npm -v
```

### 2. JHipster CLI
```bash
# Instalar JHipster globalmente
npm install -g generator-jhipster

# Verificar instalación
jhipster --version
```

### 3. Java 17 o superior
```bash
# Verificar instalación
java -version
```

### 4. Maven
```bash
# Verificar instalación
mvn -v
```

### 5. JDL Studio (Opcional pero recomendado)
- Accede a: https://start.jhipster.tech/jdl-studio/
- Permite visualizar y validar el modelo JDL antes de generar el proyecto

---

## 📝 Paso 1: Diseño del Modelo JDL

### 1.1. Archivo JDL Creado

El archivo `devmatch.jdl` contiene el modelo completo del sistema DevMatch con las siguientes entidades:

- **Technology**: Tecnologías/habilidades del sistema
- **Developer**: Desarrolladores registrados
- **Project**: Proyectos disponibles
- **Experience**: Experiencias de los desarrolladores
- **MatchResult**: Resultados de matching
- **AuditHistory**: Historial de auditoría

### 1.2. Relaciones Definidas

- **Developer ↔ Technology**: Many-to-Many (Skills)
- **Project ↔ Technology**: Many-to-Many (Required Technologies)
- **Developer → Experience**: One-to-Many
- **MatchResult → Project**: Many-to-One
- **MatchResult → Developer**: Many-to-One

### 1.3. Ubicación del Archivo

```
/devmatch.jdl
```

---

## ✅ Paso 2: Validación del JDL

### 2.1. Validación en JDL Studio

1. Abre tu navegador y ve a: https://start.jhipster.tech/jdl-studio/
2. Copia el contenido del archivo `devmatch.jdl`
3. Pega el contenido en JDL Studio
4. Verifica que no haya errores de sintaxis
5. Revisa el diagrama visual de entidades y relaciones

**📸 Captura de pantalla:** `docs/jhipster_evidencias/01_jdl_studio_validacion.png`

### 2.2. Validación Local con JHipster

```bash
# Validar el archivo JDL
jhipster jdl devmatch.jdl --dry-run
```

Si no hay errores, verás un mensaje de éxito.

**📸 Captura de pantalla:** `docs/jhipster_evidencias/02_validacion_local.png`

---

## 🏗️ Paso 3: Generación del Proyecto JHipster

### 3.1. Crear Directorio para el Proyecto

```bash
# Crear directorio para el nuevo proyecto
mkdir devmatch-jhipster
cd devmatch-jhipster
```

### 3.2. Generar Proyecto desde JDL

```bash
# Desde el directorio del proyecto original, copiar el JDL
cp ../devmatch.jdl .

# Generar el proyecto JHipster
jhipster jdl devmatch.jdl
```

Durante la generación, JHipster te preguntará algunas opciones. Puedes usar las siguientes respuestas:

- **Application type**: Monolith
- **Base name**: devmatch
- **Package name**: com.devmatch
- **Database**: SQL (H2 para desarrollo, PostgreSQL para producción)
- **Build tool**: Maven
- **Client framework**: Angular
- **Enable translation**: No (o Yes si lo necesitas)

**📸 Captura de pantalla:** `docs/jhipster_evidencias/03_generacion_proyecto.png`

### 3.3. Estructura Generada

Después de la generación, deberías ver una estructura similar a:

```
devmatch-jhipster/
├── src/
│   ├── main/
│   │   ├── java/com/devmatch/
│   │   │   ├── domain/
│   │   │   │   ├── Technology.java
│   │   │   │   ├── Developer.java
│   │   │   │   ├── Project.java
│   │   │   │   ├── Experience.java
│   │   │   │   ├── MatchResult.java
│   │   │   │   └── AuditHistory.java
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── web/rest/
│   │   └── resources/
│   └── test/
└── pom.xml
```

**📸 Captura de pantalla:** `docs/jhipster_evidencias/04_estructura_generada.png`

---

## ⚙️ Paso 4: Configuración y Ejecución

### 4.1. Instalar Dependencias

```bash
# Instalar dependencias del frontend
npm install

# Compilar el proyecto (Maven descargará dependencias automáticamente)
mvn clean install
```

**📸 Captura de pantalla:** `docs/jhipster_evidencias/05_instalacion_dependencias.png`

### 4.2. Configurar Base de Datos (Opcional)

Si quieres usar PostgreSQL en lugar de H2:

1. Edita `src/main/resources/application-dev.yml`
2. Configura la conexión a PostgreSQL:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/devmatch
    username: tu_usuario
    password: tu_password
```

### 4.3. Ejecutar el Proyecto

#### Opción A: Ejecutar Backend y Frontend por separado

```bash
# Terminal 1: Backend
mvn spring-boot:run

# Terminal 2: Frontend
npm start
```

#### Opción B: Ejecutar todo junto (si está configurado)

```bash
mvn spring-boot:run
```

El proyecto estará disponible en:
- **Backend API**: http://localhost:8080
- **Frontend**: http://localhost:4200 (si se ejecuta por separado)
- **Swagger UI**: http://localhost:8080/swagger-ui.html

**📸 Captura de pantalla:** `docs/jhipster_evidencias/06_proyecto_ejecutando.png`

---

## ✅ Paso 5: Validación de Entidades y Relaciones

### 5.1. Verificar Entidades Generadas

1. Navega a la carpeta de entidades:
```bash
ls -la src/main/java/com/devmatch/domain/
```

2. Verifica que todas las entidades estén presentes:
   - Technology.java
   - Developer.java
   - Project.java
   - Experience.java
   - MatchResult.java
   - AuditHistory.java

**📸 Captura de pantalla:** `docs/jhipster_evidencias/07_entidades_generadas.png`

### 5.2. Verificar Relaciones en el Código

Abre algunos archivos de entidades y verifica las anotaciones de relaciones:

**Developer.java:**
```java
@ManyToMany
@JoinTable(name = "developer_skills", ...)
private Set<Technology> skills;

@OneToMany(mappedBy = "developer", ...)
private Set<Experience> experiences;
```

**Project.java:**
```java
@ManyToMany
@JoinTable(name = "project_technologies", ...)
private Set<Technology> requiredTechnologies;
```

**📸 Captura de pantalla:** `docs/jhipster_evidencias/08_relaciones_codigo.png`

### 5.3. Verificar Base de Datos

1. Accede a la consola H2 (si usas H2):
   - URL: http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:devmatch`
   - Usuario: `sa`
   - Password: (vacío)

2. Ejecuta consultas para verificar tablas:
```sql
SHOW TABLES;

SELECT * FROM TECHNOLOGY;
SELECT * FROM DEVELOPER;
SELECT * FROM PROJECT;
SELECT * FROM EXPERIENCE;
SELECT * FROM MATCH_RESULT;
SELECT * FROM AUDIT_HISTORY;
```

**📸 Captura de pantalla:** `docs/jhipster_evidencias/09_tablas_bd.png`

### 5.4. Probar CRUD desde la Interfaz Web

1. Accede a http://localhost:4200 (o http://localhost:8080)
2. Inicia sesión (usuario por defecto: `admin` / `admin`)
3. Navega a las secciones de entidades:
   - Entities → Technology
   - Entities → Developer
   - Entities → Project
   - Entities → Experience

4. Prueba crear, editar y eliminar registros

**📸 Captura de pantalla:** `docs/jhipster_evidencias/10_crud_interface.png`

### 5.5. Probar Relaciones

1. **Crear un Developer con Skills:**
   - Crea un Developer
   - Asigna múltiples Technologies como skills
   - Guarda y verifica

2. **Crear un Project con Required Technologies:**
   - Crea un Project
   - Asigna múltiples Technologies como requeridas
   - Guarda y verifica

3. **Crear Experiences para un Developer:**
   - Crea un Developer
   - Crea múltiples Experiences asociadas
   - Verifica la relación

**📸 Captura de pantalla:** `docs/jhipster_evidencias/11_relaciones_funcionando.png`

### 5.6. Verificar API REST

Usa Swagger UI o Postman para probar los endpoints:

```bash
# Listar Developers
GET http://localhost:8080/api/developers

# Crear Developer
POST http://localhost:8080/api/developers
Content-Type: application/json

{
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "experienceLevel": "Intermediate"
}

# Listar Projects
GET http://localhost:8080/api/projects
```

**📸 Captura de pantalla:** `docs/jhipster_evidencias/12_api_rest.png`

---

## 📸 Paso 6: Captura de Evidencias

### 6.1. Checklist de Evidencias

Asegúrate de capturar las siguientes evidencias:

- [ ] ✅ Validación del JDL en JDL Studio
- [ ] ✅ Validación local del JDL
- [ ] ✅ Proceso de generación del proyecto
- [ ] ✅ Estructura de archivos generada
- [ ] ✅ Instalación de dependencias
- [ ] ✅ Proyecto ejecutándose
- [ ] ✅ Entidades generadas en el código
- [ ] ✅ Relaciones en el código
- [ ] ✅ Tablas en la base de datos
- [ ] ✅ Interfaz CRUD funcionando
- [ ] ✅ Relaciones funcionando en la interfaz
- [ ] ✅ API REST funcionando

### 6.2. Guardar Evidencias

Todas las capturas de pantalla deben guardarse en:
```
docs/jhipster_evidencias/
```

### 6.3. Crear Documento de Resumen

Crea un documento `RESUMEN_JHIPSTER.md` con:
- Fecha de generación
- Versión de JHipster usada
- Entidades generadas
- Relaciones validadas
- Problemas encontrados y soluciones
- Conclusiones

---

## 🐛 Solución de Problemas

### Error: "JHipster not found"
```bash
npm install -g generator-jhipster
```

### Error: "Java version incompatible"
Asegúrate de usar Java 17 o superior:
```bash
java -version
```

### Error: "Maven not found"
Instala Maven o usa el wrapper:
```bash
./mvnw clean install
```

### Error: "Port already in use"
Cambia el puerto en `application.yml`:
```yaml
server:
  port: 8081
```

---

## 📚 Recursos Adicionales

- [Documentación oficial de JHipster](https://www.jhipster.tech/)
- [JDL Studio](https://start.jhipster.tech/jdl-studio/)
- [Guía de JDL](https://www.jhipster.tech/jdl/)

---

## ✅ Checklist Final

- [ ] JDL diseñado y validado
- [ ] Proyecto JHipster generado exitosamente
- [ ] Proyecto ejecutándose correctamente
- [ ] Todas las entidades presentes
- [ ] Todas las relaciones funcionando
- [ ] CRUD operativo desde la interfaz
- [ ] API REST funcionando
- [ ] Todas las evidencias capturadas
- [ ] Documentación completa

---

**Fecha de creación:** $(date)
**Autor:** Sistema DevMatch AI

