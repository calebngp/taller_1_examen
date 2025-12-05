# ✅ Checklist de Entrega - Preparación del Entorno de Desarrollo

**Proyecto:** DevMatch AI  
**Alumno:** Caleb Nehemias  
**Fecha:** 2025

Este checklist verifica que todos los requisitos de la preparación del entorno de desarrollo estén completos.

---

## 1. Creación del Repositorio GitHub

### 1.1. Cuenta de GitHub
- [ ] Cuenta de GitHub creada y verificada
- [ ] Email verificado en GitHub
- [ ] Perfil configurado

### 1.2. Repositorio Público
- [ ] Repositorio público creado en GitHub
- [ ] Nombre del repositorio: `c5_taller_4` (o similar)
- [ ] Descripción del proyecto agregada
- [ ] Repositorio configurado correctamente

### 1.3. Configuración Inicial
- [ ] Git configurado localmente (`user.name` y `user.email`)
- [ ] Repositorio local inicializado (`git init`)
- [ ] Remote de GitHub configurado (`git remote add origin`)
- [ ] README.md creado y actualizado
- [ ] Primer commit realizado
- [ ] Cambios subidos al repositorio (`git push`)

**Evidencia:**
- [ ] Captura de pantalla del repositorio en GitHub
- [ ] URL del repositorio: `https://github.com/TU_USUARIO/c5_taller_4`

---

## 2. Instalación del Entorno Backend (Java)

### 2.1. SDKMAN!
- [ ] SDKMAN! instalado correctamente
- [ ] SDKMAN! cargado en el shell (`source ~/.sdkman/bin/sdkman-init.sh`)
- [ ] Versión de SDKMAN! verificada (`sdk version`)

**Evidencia:**
- [ ] Captura de pantalla de `sdk version`
- [ ] Comando ejecutado: `sdk version`

### 2.2. Java 17 o 21
- [ ] Java instalado mediante SDKMAN!
- [ ] Versión Java 17 o 21 instalada
- [ ] Java configurado como versión por defecto
- [ ] `java -version` muestra la versión correcta
- [ ] `JAVA_HOME` configurado (opcional pero recomendado)

**Evidencia:**
- [ ] Captura de pantalla de `java -version`
- [ ] Salida del comando guardada en `docs/screenshots/java_version.txt`

### 2.3. Maven
- [ ] Maven instalado mediante SDKMAN!
- [ ] Maven configurado como versión por defecto
- [ ] `mvn -version` muestra la versión correcta

**Evidencia:**
- [ ] Captura de pantalla de `mvn -version`
- [ ] Salida del comando guardada en `docs/screenshots/maven_version.txt`

### 2.4. Verificación de Versiones
- [ ] Script `scripts/verify_java_env.sh` ejecutado exitosamente
- [ ] Todas las verificaciones pasaron

**Evidencia:**
- [ ] Captura de pantalla del script de verificación
- [ ] Salida del script guardada

### 2.5. Estructura del Proyecto Java
- [ ] Estructura del proyecto Java creada
- [ ] `pom.xml` presente y configurado
- [ ] Estructura de directorios correcta (`src/main/java/`, etc.)
- [ ] Proyecto compila correctamente (`mvn clean compile`)

**Evidencia:**
- [ ] Captura de pantalla de la estructura de directorios
- [ ] Captura de pantalla de `mvn clean compile` exitoso

### 2.6. Subida al Repositorio
- [ ] Proyecto Java agregado al repositorio
- [ ] Commit realizado con mensaje descriptivo
- [ ] Cambios subidos a GitHub (`git push`)

**Evidencia:**
- [ ] Commit visible en GitHub
- [ ] Archivos Java visibles en el repositorio

---

## 3. Instalación del Entorno Frontend (Angular)

### 3.1. NVM (Node Version Manager)
- [ ] NVM instalado correctamente
- [ ] NVM cargado en el shell
- [ ] `nvm --version` muestra la versión
- [ ] NVM agregado al perfil de shell (`.zshrc` o `.bashrc`)

**Evidencia:**
- [ ] Captura de pantalla de `nvm --version`
- [ ] Comando ejecutado: `nvm --version`

### 3.2. Node.js LTS
- [ ] Node.js LTS instalado mediante NVM
- [ ] Node.js configurado como versión por defecto
- [ ] `node -v` muestra la versión LTS
- [ ] `npm -v` muestra la versión de npm

**Evidencia:**
- [ ] Captura de pantalla de `node -v` y `npm -v`
- [ ] Salida guardada en `docs/screenshots/node_version.txt` y `npm_version.txt`

### 3.3. Angular CLI
- [ ] Angular CLI instalado globalmente (`npm install -g @angular/cli`)
- [ ] `ng version` muestra la versión correcta
- [ ] Angular CLI funciona correctamente

**Evidencia:**
- [ ] Captura de pantalla de `ng version`
- [ ] Salida guardada en `docs/screenshots/angular_version.txt`

### 3.4. Verificación del Entorno
- [ ] Script `scripts/verify_angular_env.sh` ejecutado exitosamente
- [ ] Todas las verificaciones pasaron

**Evidencia:**
- [ ] Captura de pantalla del script de verificación
- [ ] Salida del script guardada

### 3.5. Proyecto Angular
- [ ] Proyecto Angular creado (`ng new devmatch-frontend`)
- [ ] Routing configurado (si se seleccionó)
- [ ] Estructura de directorios correcta
- [ ] Proyecto inicia correctamente (`ng serve`)

**Evidencia:**
- [ ] Captura de pantalla de la estructura del proyecto
- [ ] Captura de pantalla de `ng serve` ejecutándose
- [ ] Captura de pantalla del proyecto en `http://localhost:4200`

### 3.6. Subida al Repositorio
- [ ] Proyecto Angular agregado al repositorio
- [ ] `.gitignore` configurado para excluir `node_modules/`
- [ ] Commit realizado con mensaje descriptivo
- [ ] Cambios subidos a GitHub

**Evidencia:**
- [ ] Commit visible en GitHub
- [ ] Archivos Angular visibles en el repositorio (sin `node_modules/`)

---

## 4. Instalación y Prueba de Docker

### 4.1. Docker Desktop
- [ ] Docker Desktop instalado según el SO
- [ ] Docker Desktop configurado correctamente
- [ ] Docker Desktop iniciado y funcionando

**Evidencia:**
- [ ] Captura de pantalla de Docker Desktop abierto (si aplica)
- [ ] Documentación del proceso de instalación

### 4.2. Validación de Instalación
- [ ] `docker version` muestra la versión correcta
- [ ] `docker info` muestra información del sistema
- [ ] Docker daemon corriendo

**Evidencia:**
- [ ] Captura de pantalla de `docker version`
- [ ] Salida guardada en `docs/screenshots/docker_version.txt`

### 4.3. Contenedor hello-world
- [ ] Contenedor `hello-world` ejecutado exitosamente
- [ ] Mensaje "Hello from Docker!" mostrado
- [ ] Sin errores en la ejecución

**Evidencia:**
- [ ] Captura de pantalla de `docker run hello-world`
- [ ] Salida del comando guardada

### 4.4. Captura de Pantalla
- [ ] Capturas de pantalla guardadas en `docs/screenshots/docker/`
- [ ] Capturas organizadas y nombradas correctamente

**Evidencia:**
- [ ] Archivos de capturas presentes en el repositorio

### 4.5. Documentación en README
- [ ] Sección de Docker agregada al README
- [ ] Instrucciones de instalación documentadas
- [ ] Comandos de verificación documentados

**Evidencia:**
- [ ] README actualizado con información de Docker

---

## 5. Documentación y Evidencias

### 5.1. Capturas de Instalaciones
- [ ] Capturas de Java y Maven
- [ ] Capturas de Node.js y Angular
- [ ] Capturas de Docker
- [ ] Todas las capturas organizadas en `docs/screenshots/`

**Evidencia:**
- [ ] Directorio `docs/screenshots/` con todas las capturas

### 5.2. Comandos Utilizados
- [ ] Comandos registrados en `GUIA_INSTALACION_ENTORNO.md`
- [ ] Comandos documentados con explicaciones
- [ ] Ejemplos de salida incluidos

**Evidencia:**
- [ ] Documentación completa en `GUIA_INSTALACION_ENTORNO.md`

### 5.3. Ventajas de Herramientas
- [ ] Ventajas de SDKMAN! documentadas
- [ ] Ventajas de NVM documentadas
- [ ] Ventajas de Docker documentadas
- [ ] Ejemplos de uso incluidos

**Evidencia:**
- [ ] Sección en `GUIA_INSTALACION_ENTORNO.md` con ventajas

### 5.4. Pasos para Reproducir
- [ ] Guía de reproducción del entorno creada
- [ ] Pasos claros y secuenciales
- [ ] Scripts de automatización disponibles

**Evidencia:**
- [ ] Sección en `GUIA_INSTALACION_ENTORNO.md` con pasos de reproducción
- [ ] Script `scripts/setup_complete_env.sh` disponible

---

## 6. Entrega Final

### 6.1. URL del Repositorio GitHub
- [ ] URL del repositorio disponible
- [ ] Repositorio es público
- [ ] Repositorio accesible

**URL del Repositorio:** `https://github.com/TU_USUARIO/c5_taller_4`

### 6.2. Commits Organizados
- [ ] Commits con mensajes descriptivos
- [ ] Commits organizados lógicamente
- [ ] Historial de commits claro

**Evidencia:**
- [ ] Historial de commits visible en GitHub

### 6.3. README Completo
- [ ] README.md actualizado
- [ ] Instrucciones de instalación incluidas
- [ ] Enlaces a documentación adicional
- [ ] Estructura del proyecto documentada

**Evidencia:**
- [ ] README.md completo y actualizado

### 6.4. Documentación Adicional
- [ ] `GUIA_INSTALACION_ENTORNO.md` completo
- [ ] `ESTRUCTURA_PROYECTO_ANGULAR.md` creado (si aplica)
- [ ] Scripts de verificación funcionando
- [ ] Scripts de instalación funcionando

**Evidencia:**
- [ ] Todos los archivos de documentación presentes

---

## 📋 Resumen de Archivos Requeridos

### Documentación
- [ ] `README.md` - Actualizado con instrucciones de setup
- [ ] `GUIA_INSTALACION_ENTORNO.md` - Guía completa de instalación
- [ ] `ESTRUCTURA_PROYECTO_ANGULAR.md` - Estructura del proyecto Angular
- [ ] `CHECKLIST_ENTREGA_ENTORNO.md` - Este checklist

### Scripts
- [ ] `scripts/verify_java_env.sh` - Verificación Java/Maven
- [ ] `scripts/verify_angular_env.sh` - Verificación Angular/Node
- [ ] `scripts/verify_docker_env.sh` - Verificación Docker
- [ ] `scripts/verify_complete_env.sh` - Verificación completa
- [ ] `scripts/setup_complete_env.sh` - Instalación automatizada

### Evidencias
- [ ] `docs/screenshots/` - Directorio con capturas de pantalla
- [ ] Capturas de Java, Maven, Node, Angular, Docker

### Proyectos
- [ ] Proyecto Java compilando correctamente
- [ ] Proyecto Angular creado y funcionando

---

## ✅ Verificación Final

Antes de entregar, ejecuta:

```bash
# Verificación completa del entorno
./scripts/verify_complete_env.sh

# Verificar que todos los archivos estén en el repositorio
git status

# Verificar commits
git log --oneline

# Verificar que todo esté subido
git push origin main
```

---

## 📝 Notas Finales

- [ ] Todas las tareas del checklist completadas
- [ ] Todas las evidencias recopiladas
- [ ] Documentación completa y clara
- [ ] Repositorio organizado y actualizado
- [ ] Listo para entrega

---

**Fecha de Entrega:** _______________  
**Estado:** ⬜ Pendiente | ⬜ En Progreso | ⬜ Completado

---

**Última actualización:** 2025


