# 🛠️ Guía Completa de Instalación del Entorno de Desarrollo

**Proyecto:** DevMatch AI  
**Alumno:** Caleb Nehemias  
**Fecha:** 2025

Esta guía detalla paso a paso la instalación y configuración del entorno de desarrollo completo para el proyecto DevMatch AI, incluyendo backend Java y frontend Angular.

---

## 📋 Tabla de Contenidos

1. [Creación del Repositorio GitHub](#1-creación-del-repositorio-github)
2. [Instalación del Entorno Backend (Java)](#2-instalación-del-entorno-backend-java)
3. [Instalación del Entorno Frontend (Angular)](#3-instalación-del-entorno-frontend-angular)
4. [Instalación y Prueba de Docker](#4-instalación-y-prueba-de-docker)
5. [Verificación del Entorno Completo](#5-verificación-del-entorno-completo)
6. [Ventajas de las Herramientas Utilizadas](#6-ventajas-de-las-herramientas-utilizadas)
7. [Reproducción del Entorno en Otra Máquina](#7-reproducción-del-entorno-en-otra-máquina)

---

## 1. Creación del Repositorio GitHub

### 1.1. Creación de cuenta en GitHub

Si no tienes una cuenta en GitHub:

1. Visita [https://github.com](https://github.com)
2. Haz clic en "Sign up"
3. Completa el formulario de registro
4. Verifica tu email

### 1.2. Creación del repositorio público

1. Inicia sesión en GitHub
2. Haz clic en el botón "+" en la esquina superior derecha
3. Selecciona "New repository"
4. Configura el repositorio:
   - **Repository name:** `c5_taller_4` (o el nombre que prefieras)
   - **Description:** "DevMatch AI - Sistema de Matching Inteligente con Java + Angular"
   - **Visibility:** Public
   - **NO marques** "Initialize with README" (si ya tienes archivos locales)
5. Haz clic en "Create repository"

### 1.3. Configuración inicial del repositorio

#### Configurar Git localmente (si no está configurado)

```bash
# Configurar nombre de usuario
git config --global user.name "Tu Nombre"

# Configurar email
git config --global user.email "tu.email@ejemplo.com"

# Verificar configuración
git config --list
```

#### Inicializar el repositorio local

```bash
# Navegar al directorio del proyecto
cd /Users/calebnehemias/c5_taller_4-main-v2

# Inicializar repositorio Git (si no está inicializado)
git init

# Agregar el remote de GitHub
git remote add origin https://github.com/TU_USUARIO/c5_taller_4.git

# Verificar el remote
git remote -v
```

#### Primer commit y push

```bash
# Agregar todos los archivos
git add .

# Crear el commit inicial
git commit -m "Initial commit: DevMatch AI project setup"

# Cambiar a la rama main (si es necesario)
git branch -M main

# Subir al repositorio remoto
git push -u origin main
```

---

## 2. Instalación del Entorno Backend (Java)

### 2.1. Instalación de SDKMAN!

SDKMAN! (Software Development Kit Manager) es una herramienta para gestionar múltiples versiones de SDKs como Java, Maven, Gradle, etc.

#### En macOS/Linux:

```bash
# Instalar SDKMAN!
curl -s "https://get.sdkman.io" | bash

# Cargar SDKMAN! en la sesión actual
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Verificar instalación
sdk version
```

#### En Windows (usando Git Bash o WSL):

```bash
# Instalar SDKMAN!
curl -s "https://get.sdkman.io" | bash

# Cargar SDKMAN!
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

### 2.2. Instalación de Java 17 o 21 mediante SDKMAN

```bash
# Listar versiones disponibles de Java
sdk list java

# Instalar Java 21 LTS (recomendado)
sdk install java 21.0.1-tem

# O instalar Java 17 LTS
sdk install java 17.0.9-tem

# Establecer como versión por defecto
sdk default java 21.0.1-tem

# Verificar instalación
java -version
javac -version
```

**Salida esperada:**
```
openjdk version "21.0.1" 2024-04-16
OpenJDK Runtime Environment Temurin-21.0.1+12 (build 21.0.1+12)
OpenJDK 64-Bit Server VM Temurin-21.0.1+12 (build 21.0.1+12, mixed mode, sharing)
```

### 2.3. Instalación de Maven mediante SDKMAN

```bash
# Listar versiones disponibles de Maven
sdk list maven

# Instalar Maven (última versión estable)
sdk install maven

# Establecer como versión por defecto
sdk default maven

# Verificar instalación
mvn -version
```

**Salida esperada:**
```
Apache Maven 3.9.6
Maven home: /Users/tu_usuario/.sdkman/candidates/maven/current
Java version: 21.0.1, vendor: Eclipse Adoptium
```

### 2.4. Verificación de versiones instaladas

Ejecuta el script de verificación:

```bash
# Dar permisos de ejecución
chmod +x scripts/verify_java_env.sh

# Ejecutar verificación
./scripts/verify_java_env.sh
```

O verifica manualmente:

```bash
# Verificar Java
java -version
echo $JAVA_HOME

# Verificar Maven
mvn -version

# Verificar SDKMAN
sdk version
```

### 2.5. Creación de la estructura inicial del proyecto Java

La estructura del proyecto Java ya está creada. Verifica que exista:

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── devmatch/
│   │           ├── entity/
│   │           ├── dto/
│   │           └── PythonServerLauncher.java
│   └── resources/
│       └── META-INF/
│           └── persistence.xml
├── test/
│   └── java/
│       └── com/
│           └── devmatch/
└── pom.xml
```

### 2.6. Compilar y probar el proyecto Java

```bash
# Limpiar y compilar
mvn clean compile

# Ejecutar tests
mvn test

# Empaquetar el proyecto
mvn clean package

# Ejecutar la aplicación
mvn exec:java
```

### 2.7. Subida del proyecto Java al repositorio

```bash
# Agregar cambios
git add .

# Commit
git commit -m "feat: Java backend setup with Maven and JPA entities"

# Push
git push origin main
```

---

## 3. Instalación del Entorno Frontend (Angular)

### 3.1. Instalación de NVM (Node Version Manager)

NVM permite gestionar múltiples versiones de Node.js en el mismo sistema.

#### En macOS/Linux:

```bash
# Instalar NVM
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash

# O usando wget
wget -qO- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash

# Cargar NVM en la sesión actual
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"

# Agregar al perfil de shell (para que se cargue automáticamente)
echo 'export NVM_DIR="$HOME/.nvm"' >> ~/.zshrc
echo '[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"' >> ~/.zshrc
echo '[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"' >> ~/.zshrc

# Recargar el perfil
source ~/.zshrc

# Verificar instalación
nvm --version
```

#### En Windows:

Usa [nvm-windows](https://github.com/coreybutler/nvm-windows):

1. Descarga el instalador desde: https://github.com/coreybutler/nvm-windows/releases
2. Ejecuta el instalador
3. Reinicia la terminal

### 3.2. Instalación de Node.js LTS con NVM

```bash
# Listar versiones disponibles de Node.js
nvm list-remote

# Instalar Node.js LTS (Long Term Support)
nvm install --lts

# O instalar una versión específica
nvm install 20.10.0

# Usar la versión instalada
nvm use --lts

# Establecer como versión por defecto
nvm alias default node

# Verificar instalación
node -v
npm -v
```

**Salida esperada:**
```
v20.10.0
10.2.3
```

### 3.3. Instalación de Angular CLI

```bash
# Instalar Angular CLI globalmente
npm install -g @angular/cli

# Verificar instalación
ng version
```

**Salida esperada:**
```
Angular CLI: 17.2.0
Node: 20.10.0
Package Manager: npm 10.2.3
```

### 3.4. Verificación del entorno (node, npm, ng version)

Ejecuta el script de verificación:

```bash
# Dar permisos de ejecución
chmod +x scripts/verify_angular_env.sh

# Ejecutar verificación
./scripts/verify_angular_env.sh
```

O verifica manualmente:

```bash
# Verificar Node.js
node -v
npm -v

# Verificar Angular CLI
ng version

# Verificar NVM
nvm --version
nvm current
```

### 3.5. Creación del proyecto Angular

```bash
# Navegar al directorio donde quieres crear el proyecto
cd /Users/calebnehemias/c5_taller_4-main-v2

# Crear nuevo proyecto Angular
ng new devmatch-frontend

# Durante la creación, selecciona:
# - ¿Incluir routing? → Yes
# - ¿Qué estilo de hojas de estilo? → CSS (o el que prefieras)

# Navegar al proyecto
cd devmatch-frontend

# Iniciar el servidor de desarrollo
ng serve

# El proyecto estará disponible en: http://localhost:4200
```

**Estructura del proyecto Angular:**
```
devmatch-frontend/
├── src/
│   ├── app/
│   ├── assets/
│   ├── environments/
│   ├── index.html
│   ├── main.ts
│   └── styles.css
├── angular.json
├── package.json
├── tsconfig.json
└── README.md
```

### 3.6. Subida del proyecto Angular al repositorio

```bash
# Desde el directorio raíz del proyecto
cd /Users/calebnehemias/c5_taller_4-main-v2

# Agregar el proyecto Angular
git add devmatch-frontend/

# Commit
git commit -m "feat: Angular frontend project setup"

# Push
git push origin main
```

**Nota:** Asegúrate de tener un `.gitignore` adecuado que excluya `node_modules/` y otros archivos generados.

---

## 4. Instalación y Prueba de Docker

### 4.1. Instalación de Docker Desktop según SO

#### macOS:

1. Descarga Docker Desktop desde: https://www.docker.com/products/docker-desktop/
2. Abre el archivo `.dmg` descargado
3. Arrastra Docker a la carpeta Applications
4. Abre Docker Desktop desde Applications
5. Sigue las instrucciones del asistente de instalación
6. Reinicia tu Mac si es necesario

#### Linux (Ubuntu/Debian):

```bash
# Actualizar paquetes
sudo apt-get update

# Instalar dependencias
sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

# Agregar la clave GPG oficial de Docker
sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

# Configurar el repositorio
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Instalar Docker Engine
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# Agregar usuario al grupo docker (para no usar sudo)
sudo usermod -aG docker $USER

# Reiniciar sesión o ejecutar:
newgrp docker
```

#### Windows:

1. Descarga Docker Desktop desde: https://www.docker.com/products/docker-desktop/
2. Ejecuta el instalador
3. Sigue las instrucciones del asistente
4. Reinicia Windows si es necesario

### 4.2. Validación de instalación con docker version

```bash
# Verificar versión de Docker
docker version

# Verificar información del sistema
docker info

# Verificar Docker Compose (si está instalado)
docker compose version
```

**Salida esperada:**
```
Client: Docker Engine - Community
 Version:           24.0.7
 API version:       1.43
 Go version:        go1.20.10
 ...
```

### 4.3. Ejecución del contenedor de prueba hello-world

```bash
# Ejecutar el contenedor hello-world
docker run hello-world
```

**Salida esperada:**
```
Hello from Docker!
This message shows that your installation appears to be working correctly.
...
```

### 4.4. Captura de pantalla como evidencia

Toma capturas de pantalla de:

1. `docker version` - Muestra la versión instalada
2. `docker run hello-world` - Muestra el contenedor funcionando
3. Docker Desktop abierto (si usas macOS/Windows)

Guarda las capturas en: `docs/screenshots/docker/`

### 4.5. Documentación en el README

La documentación de Docker ya está incluida en este documento y en el README principal.

---

## 5. Verificación del Entorno Completo

### 5.1. Script de verificación completa

Ejecuta el script de verificación:

```bash
# Dar permisos de ejecución
chmod +x scripts/verify_complete_env.sh

# Ejecutar verificación completa
./scripts/verify_complete_env.sh
```

### 5.2. Verificación manual

```bash
# Backend (Java)
echo "=== Java Environment ==="
java -version
mvn -version
sdk version

# Frontend (Angular)
echo "=== Angular Environment ==="
node -v
npm -v
ng version
nvm --version

# Docker
echo "=== Docker Environment ==="
docker version
docker run hello-world
```

---

## 6. Ventajas de las Herramientas Utilizadas

### 6.1. SDKMAN! (Software Development Kit Manager)

**Ventajas:**

- ✅ **Gestión de múltiples versiones:** Permite tener varias versiones de Java, Maven, Gradle, etc. instaladas simultáneamente
- ✅ **Cambio fácil entre versiones:** `sdk use java 21.0.1-tem` para cambiar rápidamente
- ✅ **Instalación simple:** Un solo comando para instalar cualquier SDK
- ✅ **Actualizaciones fáciles:** `sdk upgrade java` para actualizar
- ✅ **Sin conflictos:** Cada versión se instala en su propio directorio
- ✅ **Multiplataforma:** Funciona en macOS, Linux y Windows (con WSL)
- ✅ **Gestión centralizada:** Todas las herramientas en un solo lugar

**Ejemplo de uso:**
```bash
# Tener Java 17 y 21 instalados
sdk install java 17.0.9-tem
sdk install java 21.0.1-tem

# Cambiar entre versiones según el proyecto
sdk use java 17.0.9-tem  # Para proyecto legacy
sdk use java 21.0.1-tem  # Para proyecto nuevo
```

### 6.2. NVM (Node Version Manager)

**Ventajas:**

- ✅ **Múltiples versiones de Node.js:** Instala y gestiona diferentes versiones sin conflictos
- ✅ **Cambio rápido:** `nvm use 20.10.0` para cambiar de versión
- ✅ **Por proyecto:** Puedes tener un `.nvmrc` en cada proyecto
- ✅ **Sin sudo:** No necesitas permisos de administrador
- ✅ **Aislamiento:** Cada versión tiene sus propios módulos globales
- ✅ **Fácil actualización:** `nvm install node --latest-npm`
- ✅ **Compatibilidad:** Útil para proyectos que requieren versiones específicas

**Ejemplo de uso:**
```bash
# Proyecto Angular 17 requiere Node 18
cd proyecto-angular-17
nvm use 18.20.0

# Proyecto Node.js moderno requiere Node 20
cd proyecto-moderno
nvm use 20.10.0
```

### 6.3. Docker

**Ventajas:**

- ✅ **Consistencia:** "Funciona en mi máquina" → "Funciona en todas las máquinas"
- ✅ **Aislamiento:** Cada aplicación en su propio contenedor
- ✅ **Portabilidad:** Ejecuta la misma imagen en cualquier sistema
- ✅ **Reproducibilidad:** Mismo entorno en desarrollo, testing y producción
- ✅ **Escalabilidad:** Fácil escalar aplicaciones horizontalmente
- ✅ **Microservicios:** Ideal para arquitecturas de microservicios
- ✅ **CI/CD:** Integración perfecta con pipelines de CI/CD
- ✅ **Versionado:** Cada imagen tiene una versión específica
- ✅ **Rollback fácil:** Volver a versiones anteriores es trivial

**Ejemplo de uso:**
```bash
# Ejecutar PostgreSQL en un contenedor
docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=secret postgres:15

# Ejecutar Redis
docker run -d -p 6379:6379 redis:7-alpine

# Compartir el mismo entorno con todo el equipo
docker-compose up
```

---

## 7. Reproducción del Entorno en Otra Máquina

### 7.1. Requisitos previos

- Sistema operativo compatible (macOS, Linux, o Windows con WSL)
- Acceso a internet
- Permisos de administrador (para algunas instalaciones)

### 7.2. Pasos para reproducir el entorno

#### Paso 1: Clonar el repositorio

```bash
# Clonar el repositorio
git clone https://github.com/TU_USUARIO/c5_taller_4.git
cd c5_taller_4
```

#### Paso 2: Instalar SDKMAN! y Java

```bash
# Instalar SDKMAN!
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Instalar Java 21
sdk install java 21.0.1-tem
sdk default java 21.0.1-tem

# Instalar Maven
sdk install maven
sdk default maven

# Verificar
java -version
mvn -version
```

#### Paso 3: Instalar NVM y Node.js

```bash
# Instalar NVM
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"

# Instalar Node.js LTS
nvm install --lts
nvm use --lts
nvm alias default node

# Instalar Angular CLI
npm install -g @angular/cli

# Verificar
node -v
npm -v
ng version
```

#### Paso 4: Instalar Docker

Sigue las instrucciones de la sección [4.1. Instalación de Docker Desktop](#41-instalación-de-docker-desktop-según-so)

#### Paso 5: Configurar el proyecto Java

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Empaquetar
mvn clean package
```

#### Paso 6: Configurar el proyecto Angular

```bash
# Si el proyecto Angular ya existe en el repo
cd devmatch-frontend

# Instalar dependencias
npm install

# Iniciar servidor de desarrollo
ng serve
```

#### Paso 7: Verificar todo el entorno

```bash
# Ejecutar script de verificación
./scripts/verify_complete_env.sh
```

### 7.3. Script automatizado de setup

Puedes usar el script `scripts/setup_complete_env.sh` para automatizar todo el proceso:

```bash
# Dar permisos
chmod +x scripts/setup_complete_env.sh

# Ejecutar (requiere interacción del usuario)
./scripts/setup_complete_env.sh
```

---

## 📸 Evidencias y Capturas de Pantalla

### Comandos para generar evidencias:

```bash
# Crear directorio para evidencias
mkdir -p docs/screenshots

# Java
java -version > docs/screenshots/java_version.txt
mvn -version > docs/screenshots/maven_version.txt

# Node.js y Angular
node -v > docs/screenshots/node_version.txt
npm -v > docs/screenshots/npm_version.txt
ng version > docs/screenshots/angular_version.txt

# Docker
docker version > docs/screenshots/docker_version.txt
```

### Capturas de pantalla recomendadas:

1. ✅ Terminal mostrando `java -version`
2. ✅ Terminal mostrando `mvn -version`
3. ✅ Terminal mostrando `node -v` y `npm -v`
4. ✅ Terminal mostrando `ng version`
5. ✅ Terminal mostrando `docker version`
6. ✅ Terminal mostrando `docker run hello-world`
7. ✅ Docker Desktop abierto (si aplica)
8. ✅ Proyecto Angular ejecutándose en `http://localhost:4200`
9. ✅ Proyecto Java compilado exitosamente

---

## 🔗 Enlaces Útiles

- [SDKMAN! Documentation](https://sdkman.io/usage)
- [NVM Documentation](https://github.com/nvm-sh/nvm#readme)
- [Docker Documentation](https://docs.docker.com/)
- [Angular Documentation](https://angular.io/docs)
- [Maven Documentation](https://maven.apache.org/guides/)

---

## ✅ Checklist de Instalación

- [ ] Cuenta de GitHub creada
- [ ] Repositorio creado y configurado
- [ ] SDKMAN! instalado y funcionando
- [ ] Java 17 o 21 instalado mediante SDKMAN!
- [ ] Maven instalado mediante SDKMAN!
- [ ] NVM instalado y funcionando
- [ ] Node.js LTS instalado mediante NVM
- [ ] Angular CLI instalado globalmente
- [ ] Docker instalado y funcionando
- [ ] Contenedor hello-world ejecutado exitosamente
- [ ] Proyecto Java compilado correctamente
- [ ] Proyecto Angular creado y funcionando
- [ ] Todas las capturas de pantalla tomadas
- [ ] README actualizado con documentación
- [ ] Cambios subidos al repositorio GitHub

---

**Última actualización:** 2025  
**Versión:** 1.0.0


