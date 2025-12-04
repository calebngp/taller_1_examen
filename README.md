# 🚀 DevMatch AI - Smart Developer-Project Matching

## 📚 Proyecto Académico - Arquitectura Híbrida Java + Python

**Alumno:** Caleb Nehemias  
**Fecha:** 15 de Octubre, 2025  
**Repositorio:** c5_taller_4

## Overview
DevMatch AI is an intelligent matching system that uses DeepSeek AI to analyze the compatibility between developers and projects. It includes a **Java 21 Server Launcher** that manages the Python Flask backend with modern features and robust process management.

## Features
- ✅ **Java 21 Server Launcher** - Modern process management with logging
- 🧠 AI-powered semantic analysis with DeepSeek
- 📊 Experience relevance evaluation  
- 💻 HTML report generation
- 🌐 Flask web interface with interactive menu
- 📱 Responsive design
- 🎯 Individual project views
- 🔄 Server restart and monitoring capabilities

## 🚀 Quick Start

### Method 1: Maven (Recommended)
```bash
cd /path/to/project
mvn clean compile exec:java
```

### Method 2: Executable JAR
```bash
mvn clean package
java -jar target/devmatch-launcher-java21.jar
```

### Method 3: Helper Script
```bash
./comandos.sh run
```

## 🛠️ Installation & Setup

### 📋 Prerequisites

#### Backend (Java)
- **Java 21 LTS** (recomendado usar SDKMAN!)
- **Apache Maven 3.9+** (recomendado usar SDKMAN!)
- **Python 3.7+** with virtual environment
- Ollama with DeepSeek model installed

#### Frontend (Angular)
- **Node.js LTS** (recomendado usar NVM)
- **npm** (incluido con Node.js)
- **Angular CLI** (instalado globalmente)

#### Containerización
- **Docker Desktop** (opcional pero recomendado)

### 🚀 Setup Rápido del Entorno

Para una instalación completa y automatizada del entorno de desarrollo, consulta la **[Guía Completa de Instalación](GUIA_INSTALACION_ENTORNO.md)**.

#### Opción 1: Instalación Automatizada (Recomendado)

```bash
# Ejecutar script de instalación completa
chmod +x scripts/setup_complete_env.sh
./scripts/setup_complete_env.sh
```

Este script instalará automáticamente:
- ✅ SDKMAN! (gestor de SDKs)
- ✅ Java 21 LTS
- ✅ Maven
- ✅ NVM (Node Version Manager)
- ✅ Node.js LTS
- ✅ Angular CLI
- ✅ Verificación de Docker

#### Opción 2: Instalación Manual

Sigue los pasos detallados en [GUIA_INSTALACION_ENTORNO.md](GUIA_INSTALACION_ENTORNO.md)

#### Verificación del Entorno

Después de la instalación, verifica que todo esté correcto:

```bash
# Verificación completa
chmod +x scripts/verify_complete_env.sh
./scripts/verify_complete_env.sh

# O verificar componentes individuales
./scripts/verify_java_env.sh      # Verificar Java y Maven
./scripts/verify_angular_env.sh   # Verificar Node.js y Angular
./scripts/verify_docker_env.sh    # Verificar Docker
```

### 📦 Instalación de Componentes Individuales

#### Backend Java

```bash
# 1. Instalar SDKMAN!
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# 2. Instalar Java 21
sdk install java 21.0.1-tem
sdk default java 21.0.1-tem

# 3. Instalar Maven
sdk install maven
sdk default maven

# 4. Verificar
java -version
mvn -version
```

#### Frontend Angular

```bash
# 1. Instalar NVM
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"

# 2. Instalar Node.js LTS
nvm install --lts
nvm use --lts
nvm alias default node

# 3. Instalar Angular CLI
npm install -g @angular/cli

# 4. Verificar
node -v
npm -v
ng version
```

#### Docker

- **macOS/Windows:** Descarga Docker Desktop desde [docker.com](https://www.docker.com/products/docker-desktop/)
- **Linux:** Sigue las instrucciones en [GUIA_INSTALACION_ENTORNO.md](GUIA_INSTALACION_ENTORNO.md#41-instalación-de-docker-desktop-según-so)

### 🎯 Crear Proyecto Angular

```bash
# Crear nuevo proyecto Angular
ng new devmatch-frontend

# Durante la creación, selecciona:
# - ¿Incluir routing? → Yes
# - ¿Qué estilo de hojas de estilo? → CSS (o el que prefieras)

# Navegar al proyecto
cd devmatch-frontend

# Instalar dependencias
npm install

# Iniciar servidor de desarrollo
ng serve

# El proyecto estará disponible en: http://localhost:4200
```

### Install Ollama and DeepSeek
```bash
# Install Ollama (if not already installed)
curl -fsSL https://ollama.ai/install.sh | sh

# Pull DeepSeek model
ollama pull deepseek-r1:1.5b
```

## 🎓 REQUISITOS ACADÉMICOS CUMPLIDOS

### ✅ 1. Entidades JPA (30%)
**Ubicación:** `src/main/java/com/devmatch/entity/`

- ✅ `Developer.java` - Entidad completa con @Entity, @Table, @Id, relaciones
- ✅ `Project.java` - Mapeo correcto con validaciones
- ✅ `Technology.java` - Relaciones ManyToMany bidireccionales
- ✅ `Experience.java` - Relación ManyToOne con Developer

**Características:**
- Todas las anotaciones requeridas: @Entity, @Table, @Id, @GeneratedValue
- Relaciones: @OneToMany, @ManyToOne, @ManyToMany, @JoinTable
- Validaciones: @NotNull, @Size, @Email
- Nombres en camelCase siguiendo convenciones Java

### ✅ 2. CRUDs REST (40%)
**Ubicación:** `api_routes.py`

**4 CRUDs completos con 20 endpoints:**

1. **Developer CRUD** (5 endpoints)
2. **Project CRUD** (5 endpoints)
3. **Technology CRUD** (5 endpoints)
4. **Experience CRUD** (5 endpoints)

**Códigos HTTP implementados:**
- 200 OK - Operaciones exitosas
- 201 Created - Creación de recursos
- 204 No Content - Eliminación exitosa
- 400 Bad Request - Validación fallida
- 404 Not Found - Recurso no encontrado

### ✅ 3. Validaciones (10%)
- Validaciones en entidades JPA con anotaciones
- Manejo de errores con códigos HTTP apropiados
- Mensajes descriptivos en respuestas

### ✅ 4. Buenas Prácticas (10%)
- Estructura en capas: entity/, dto/, service/
- Código limpio y documentado
- Logging con SLF4J
- Separación de responsabilidades

### ✅ 5. Documentación (10%)
- `README_ARQUITECTURA.md` - Documentación técnica completa
- `GUIA_EVALUACION.md` - Guía para evaluación del profesor
- `ENTREGA_FINAL.md` - Resumen de entregables
- `GUIA_PRESENTACION.md` - Guía para presentar el proyecto
- Comentarios Javadoc en código

## 📚 DOCUMENTACIÓN ADICIONAL

### 🛠️ Configuración del Entorno de Desarrollo

- 🚀 [RESUMEN_SETUP_ENTORNO.md](RESUMEN_SETUP_ENTORNO.md) - **Resumen ejecutivo del setup**
- 📖 [GUIA_INSTALACION_ENTORNO.md](GUIA_INSTALACION_ENTORNO.md) - **Guía completa de instalación del entorno**
- 🐙 [GUIA_GITHUB_SETUP.md](GUIA_GITHUB_SETUP.md) - Configuración de repositorio GitHub
- 📁 [ESTRUCTURA_PROYECTO_ANGULAR.md](ESTRUCTURA_PROYECTO_ANGULAR.md) - Estructura del proyecto Angular
- ✅ [CHECKLIST_ENTREGA_ENTORNO.md](CHECKLIST_ENTREGA_ENTORNO.md) - Checklist de entrega

### 📖 Documentación del Proyecto

- 📖 [README_ARQUITECTURA.md](README_ARQUITECTURA.md) - Arquitectura detallada
- 👨‍🏫 [GUIA_EVALUACION.md](GUIA_EVALUACION.md) - Para el profesor
- 📊 [RESUMEN_VISUAL.md](RESUMEN_VISUAL.md) - Resumen visual del proyecto
- 🎤 [GUIA_PRESENTACION.md](GUIA_PRESENTACION.md) - Guía de presentación

### Install Python Dependencies
```bash
# Install Flask (only needed for web server)
pip install -r requirements.txt
```

## Usage Options

### Option 1: Generate Static HTML Report (Recommended)
This generates an HTML file that you can open directly in any web browser:

```bash
python modelai3.py
```

This will:
- Generate a beautiful HTML report with interactive elements
- Save it as `devmatch_results.html`
- Display console output as well
- No server needed - just open the HTML file in your browser

### Option 2: Run Flask Web Server
For a dynamic web interface with API endpoints:

```bash
python flask_server.py
```

Then visit:
- **Main Interface**: http://localhost:3000
- **API Endpoint**: http://localhost:3000/api/results
- **Individual Projects**: http://localhost:3000/project/1, /project/2, /project/3

## Features Breakdown

### Technical Matching
- Calculates percentage of required technologies that the developer possesses
- Direct skill-to-requirement comparison

### AI Analysis with DeepSeek
- **Technical Affinity**: AI evaluation of technical fit
- **Motivational Affinity**: How well the developer's motivation aligns with the project
- **Experience Relevance**: How relevant past experiences are to the project domain
- **Smart Comments**: Natural language explanation of the match

### Experience Integration
The system now considers relevant experiences that might not be directly technical but could enhance project understanding:

- **Ana López**: Barista experience → Coffee shop system understanding
- **Carlos Pérez**: Personal trainer background → Fitness app insights
- **Lucía Martínez**: Teaching experience → Educational platform expertise

## Project Structure

```
├── modelai3.py          # Main matching system with HTML generation
├── flask_server.py      # Flask web server (optional)
├── requirements.txt     # Python dependencies
├── devmatch_results.html # Generated HTML report
└── README.md           # This file
```

## Sample Projects & Developers

### Projects
1. **Coffee Shop Ordering System** - Web application for cafeteria management
2. **Fitness Mobile App** - Progress tracking and health recommendations
3. **Online Course Platform** - Educational content with payment integration

### Developers
1. **Ana López** - Java/Spring Boot developer with barista experience
2. **Carlos Pérez** - Mobile developer with fitness background
3. **Lucía Martínez** - Python/Django developer with teaching experience

## HTML Report Features

- 📊 **Interactive Progress Bars**: Visual representation of matching scores
- 🎨 **Modern Design**: Responsive layout with gradient backgrounds
- 📱 **Mobile Friendly**: Works perfectly on all device sizes
- 🔍 **Detailed Analysis**: Shows skills, experiences, and AI insights
- ⚡ **Fast Loading**: Static HTML with embedded CSS

## API Endpoints (Flask Server)

- `GET /` - Main HTML interface
- `GET /api/results` - JSON API with all matching results
- `GET /project/{id}` - Detailed view for specific project

## Customization

### Adding New Projects
Edit the `projects` list in `modelai3.py`:

```python
projects = [
    {
        "id": 4,
        "name": "Your Project Name",
        "description": "Project description",
        "required_technologies": ["Python", "React"],
        "experience_level": "Intermediate",
        "project_type": "Web",
        "status": "Open"
    }
]
```

### Adding New Developers
Edit the `developers` list in `modelai3.py`:

```python
developers = [
    {
        "id": 4,
        "name": "New Developer",
        "skills": ["Python", "React"],
        "experience_level": "Intermediate",
        "motivation": "Developer motivation",
        "experiences": [
            "Relevant experience 1",
            "Relevant experience 2"
        ]
    }
]
```

## Troubleshooting

### DeepSeek Not Working
- Make sure Ollama is running: `ollama serve`
- Check if DeepSeek model is installed: `ollama list`
- Pull the model if missing: `ollama pull deepseek-r1:1.5b`

### Flask Server Issues
- Install Flask: `pip install flask`
- Check if port 3000 is available
- Try running on different port: modify the `port=3000` parameter

### HTML Report Not Generating
- Check file permissions in the current directory
- Ensure Python has write access to the folder

## License
This project is open source and available under the MIT License.
