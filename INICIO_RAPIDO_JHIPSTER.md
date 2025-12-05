# 🚀 Inicio Rápido - Generación de Proyecto JHipster

## ⚡ Pasos Rápidos (5 minutos)

### 1. Verificar Requisitos
```bash
# Verificar Node.js
node -v  # Debe ser v18+

# Verificar Java
java -version  # Debe ser 17+

# Verificar Maven
mvn -v

# Instalar JHipster si no está instalado
npm install -g generator-jhipster
```

### 2. Validar JDL
```bash
# Opción A: Usar el script
./scripts/validate_jdl.sh

# Opción B: Validar manualmente
jhipster jdl devmatch.jdl --dry-run
```

### 3. Generar Proyecto
```bash
# Opción A: Usar el script (recomendado)
./scripts/generate_jhipster.sh

# Opción B: Generar manualmente
mkdir devmatch-jhipster
cd devmatch-jhipster
cp ../devmatch.jdl .
jhipster jdl devmatch.jdl
```

### 4. Instalar y Ejecutar
```bash
cd devmatch-jhipster

# Instalar dependencias
npm install
mvn clean install

# Ejecutar proyecto
mvn spring-boot:run
```

### 5. Acceder a la Aplicación
- **Backend API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:devmatch`
  - Usuario: `sa`
  - Password: (vacío)

## 📸 Capturar Evidencias

Mientras ejecutas los pasos, captura screenshots y guárdalos en:
```
docs/jhipster_evidencias/
```

Ver checklist completo en `docs/jhipster_evidencias/README.md`

## 🆘 Problemas Comunes

### Error: "command not found: jhipster"
```bash
npm install -g generator-jhipster
```

### Error: "Java version incompatible"
```bash
# Verificar versión
java -version

# Si es menor a 17, instalar Java 17+
# En macOS con Homebrew:
brew install openjdk@17
```

### Error: "Port 8080 already in use"
```bash
# Opción 1: Detener proceso en puerto 8080
lsof -ti:8080 | xargs kill -9

# Opción 2: Cambiar puerto en application.yml
```

## 📚 Documentación Completa

Para más detalles, consulta:
- [Guía Completa](GUIA_JHIPSTER.md)
- [Resumen Ejecutivo](RESUMEN_JHIPSTER.md)

---

**¡Listo para comenzar!** 🎉

