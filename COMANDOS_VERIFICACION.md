# 📸 Comandos de Verificación para Capturas de Pantalla

Este documento lista todos los comandos necesarios para verificar las instalaciones y tomar capturas de pantalla como evidencia.

## 🚀 Opción Rápida: Script Automatizado

Ejecuta el script que muestra todos los comandos de forma organizada:

```bash
./scripts/generate_evidence.sh
```

Este script te mostrará cada verificación paso a paso, pausando entre cada una para que puedas tomar capturas de pantalla.

---

## 📋 Comandos Individuales

### 1. Java

```bash
# Verificar versión de Java
java -version

# Verificar JAVA_HOME (si está configurado)
echo $JAVA_HOME

# Verificar compilador Java
javac -version
```

**Captura de pantalla:** Muestra la salida de `java -version`

---

### 2. Maven

```bash
# Verificar versión de Maven
mvn -version
```

**Captura de pantalla:** Muestra la salida completa de `mvn -version`

---

### 3. Node.js y npm

```bash
# Verificar versión de Node.js
node -v

# Verificar versión de npm
npm -v

# Verificar versión de NVM
nvm --version

# Ver versión actual de Node.js en uso
nvm current

# Listar versiones instaladas
nvm list
```

**Captura de pantalla:** Muestra `node -v`, `npm -v` y `nvm --version` juntos

---

### 4. Angular CLI

```bash
# Verificar versión de Angular CLI
ng version
```

**Captura de pantalla:** Muestra la salida completa de `ng version`

---

### 5. Docker

```bash
# Verificar versión de Docker
docker version

# Ver información del sistema Docker
docker info

# Probar contenedor hello-world
docker run hello-world

# Verificar Docker Compose (si está instalado)
docker compose version
# O
docker-compose --version
```

**Captura de pantalla:** 
- Muestra `docker version`
- Muestra la salida de `docker run hello-world`

---

## 🎯 Comandos Adicionales Útiles

### SDKMAN!

```bash
# Verificar versión de SDKMAN!
sdk version

# Listar versiones instaladas de Java
sdk list java | grep installed

# Listar versiones instaladas de Maven
sdk list maven | grep installed
```

### Verificación Completa del Proyecto

```bash
# Verificar que el proyecto Java compila
mvn clean compile

# Verificar estructura del proyecto
tree -L 3 -I 'target|node_modules'  # Si tienes tree instalado
# O
find . -maxdepth 3 -type d | grep -v node_modules | grep -v target
```

---

## 📸 Guía para Capturas de Pantalla

### Recomendaciones

1. **Tamaño de terminal:** Usa una terminal con buen tamaño (al menos 80x24 caracteres)
2. **Fuente legible:** Usa una fuente monospace clara (Consolas, Monaco, Fira Code)
3. **Colores:** Los colores ayudan, pero asegúrate de que sean legibles
4. **Una captura por herramienta:** Toma una captura separada para cada herramienta
5. **Incluir el prompt:** Asegúrate de que el comando sea visible en la captura

### Organización de Archivos

Guarda las capturas en:

```
docs/screenshots/
├── java_version.png
├── maven_version.png
├── node_npm_version.png
├── angular_version.png
├── docker_version.png
└── docker_hello_world.png
```

---

## 🔧 Scripts de Verificación

### Verificación Individual

```bash
# Solo Java y Maven
./scripts/verify_java_env.sh

# Solo Node.js y Angular
./scripts/verify_angular_env.sh

# Solo Docker
./scripts/verify_docker_env.sh
```

### Verificación Completa

```bash
# Todo el entorno
./scripts/verify_complete_env.sh
```

---

## 💾 Guardar Salida en Archivo

Si prefieres guardar la salida en texto en lugar de capturas:

```bash
# Guardar toda la verificación en un archivo
./scripts/generate_evidence.sh > docs/screenshots/evidencias_completas.txt 2>&1

# O comandos individuales
java -version > docs/screenshots/java_version.txt 2>&1
mvn -version > docs/screenshots/maven_version.txt 2>&1
node -v > docs/screenshots/node_version.txt 2>&1
npm -v > docs/screenshots/npm_version.txt 2>&1
ng version > docs/screenshots/angular_version.txt 2>&1
docker version > docs/screenshots/docker_version.txt 2>&1
docker run hello-world > docs/screenshots/docker_hello_world.txt 2>&1
```

---

## ✅ Checklist de Capturas

- [ ] Captura de `java -version`
- [ ] Captura de `mvn -version`
- [ ] Captura de `node -v` y `npm -v`
- [ ] Captura de `nvm --version` (opcional pero recomendado)
- [ ] Captura de `ng version`
- [ ] Captura de `docker version`
- [ ] Captura de `docker run hello-world`
- [ ] Todas las capturas guardadas en `docs/screenshots/`

---

## 🎬 Ejemplo de Sesión Completa

```bash
# 1. Crear directorio para evidencias
mkdir -p docs/screenshots

# 2. Ejecutar script de generación de evidencias
./scripts/generate_evidence.sh

# Durante la ejecución, toma capturas de pantalla de cada sección

# 3. O guardar en archivos de texto
./scripts/generate_evidence.sh > docs/screenshots/evidencias.txt 2>&1

# 4. Verificar que todo esté guardado
ls -lh docs/screenshots/
```

---

**Última actualización:** 2025

