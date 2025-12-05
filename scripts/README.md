# 📜 Scripts de Configuración y Verificación

Este directorio contiene scripts para automatizar la instalación y verificación del entorno de desarrollo.

## 📋 Scripts Disponibles

### 🔍 Scripts de Verificación

#### `verify_java_env.sh`
Verifica la instalación del entorno Java:
- SDKMAN!
- Java 17 o 21
- Maven
- Compilación del proyecto

**Uso:**
```bash
chmod +x scripts/verify_java_env.sh
./scripts/verify_java_env.sh
```

#### `verify_angular_env.sh`
Verifica la instalación del entorno Angular:
- NVM
- Node.js LTS
- npm
- Angular CLI
- Proyecto Angular (si existe)

**Uso:**
```bash
chmod +x scripts/verify_angular_env.sh
./scripts/verify_angular_env.sh
```

#### `verify_docker_env.sh`
Verifica la instalación de Docker:
- Docker instalado
- Docker daemon corriendo
- Docker Compose disponible
- Contenedor hello-world funcionando

**Uso:**
```bash
chmod +x scripts/verify_docker_env.sh
./scripts/verify_docker_env.sh
```

#### `verify_complete_env.sh`
Verifica todo el entorno completo:
- Ejecuta todos los scripts de verificación
- Muestra resumen completo

**Uso:**
```bash
chmod +x scripts/verify_complete_env.sh
./scripts/verify_complete_env.sh
```

### 🚀 Scripts de Instalación

#### `setup_complete_env.sh`
Instalación automatizada completa del entorno:
- SDKMAN!
- Java 21 LTS
- Maven
- NVM
- Node.js LTS
- Angular CLI
- Verificación de Docker

**Uso:**
```bash
chmod +x scripts/setup_complete_env.sh
./scripts/setup_complete_env.sh
```

**Nota:** Este script requiere interacción del usuario y permisos de administrador para algunas operaciones.

## 🎯 Flujo de Trabajo Recomendado

### 1. Instalación Inicial

```bash
# Opción A: Instalación automatizada
./scripts/setup_complete_env.sh

# Opción B: Instalación manual (ver GUIA_INSTALACION_ENTORNO.md)
```

### 2. Verificación

```bash
# Verificar todo el entorno
./scripts/verify_complete_env.sh
```

### 3. Desarrollo

```bash
# Verificar antes de trabajar
./scripts/verify_java_env.sh      # Si trabajas en backend
./scripts/verify_angular_env.sh   # Si trabajas en frontend
```

## 🔧 Requisitos

- Bash shell (macOS, Linux, o WSL en Windows)
- Permisos de ejecución en los scripts
- Acceso a internet (para descargar herramientas)

## 📝 Notas

- Los scripts usan colores para mejor legibilidad
- Los scripts verifican si las herramientas ya están instaladas
- Los scripts proporcionan mensajes de error claros
- Todos los scripts son idempotentes (pueden ejecutarse múltiples veces)

## 🆘 Solución de Problemas

### Script no ejecutable

```bash
chmod +x scripts/nombre_script.sh
```

### Error de permisos

Algunos comandos pueden requerir `sudo` (especialmente en Linux).

### Script no encontrado

Asegúrate de ejecutar desde el directorio raíz del proyecto:
```bash
cd /Users/calebnehemias/c5_taller_4-main-v2
./scripts/verify_complete_env.sh
```

## 📚 Documentación Relacionada

- [GUIA_INSTALACION_ENTORNO.md](../GUIA_INSTALACION_ENTORNO.md) - Guía completa
- [RESUMEN_SETUP_ENTORNO.md](../RESUMEN_SETUP_ENTORNO.md) - Resumen ejecutivo
- [CHECKLIST_ENTREGA_ENTORNO.md](../CHECKLIST_ENTREGA_ENTORNO.md) - Checklist de entrega

---

**Última actualización:** 2025


