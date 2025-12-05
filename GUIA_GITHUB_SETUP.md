# 🐙 Guía Rápida de Configuración de GitHub

Esta guía te ayudará a configurar tu repositorio en GitHub paso a paso.

## 📋 Requisitos Previos

- Cuenta de GitHub (crear en [github.com](https://github.com) si no tienes)
- Git instalado en tu sistema
- Proyecto local listo para subir

## 🚀 Pasos para Configurar el Repositorio

### 1. Crear Cuenta en GitHub (si no la tienes)

1. Visita [https://github.com](https://github.com)
2. Haz clic en "Sign up"
3. Completa el formulario:
   - Username (ejemplo: `calebnehemias`)
   - Email
   - Password
4. Verifica tu email
5. Completa el perfil (opcional)

### 2. Crear el Repositorio en GitHub

1. Inicia sesión en GitHub
2. Haz clic en el botón **"+"** en la esquina superior derecha
3. Selecciona **"New repository"**
4. Completa el formulario:
   - **Repository name:** `c5_taller_4` (o el nombre que prefieras)
   - **Description:** "DevMatch AI - Sistema de Matching Inteligente con Java + Angular"
   - **Visibility:** 
     - ✅ **Public** (recomendado para proyectos académicos)
     - ⬜ Private
   - **NO marques** "Add a README file" (ya tienes uno)
   - **NO marques** "Add .gitignore" (ya tienes uno)
   - **NO marques** "Choose a license" (a menos que quieras)
5. Haz clic en **"Create repository"**

### 3. Configurar Git Localmente

#### Verificar si Git está instalado

```bash
git --version
```

Si no está instalado:
- **macOS:** `brew install git` o descarga desde [git-scm.com](https://git-scm.com)
- **Linux:** `sudo apt-get install git` (Ubuntu/Debian)
- **Windows:** Descarga Git for Windows desde [git-scm.com](https://git-scm.com)

#### Configurar Git (si no está configurado)

```bash
# Configurar tu nombre
git config --global user.name "Tu Nombre"

# Configurar tu email (usa el mismo de GitHub)
git config --global user.email "tu.email@ejemplo.com"

# Verificar configuración
git config --list
```

### 4. Inicializar el Repositorio Local

```bash
# Navegar al directorio del proyecto
cd /Users/calebnehemias/c5_taller_4-main-v2

# Verificar si ya es un repositorio Git
git status

# Si no es un repositorio, inicializarlo
git init

# Agregar el remote de GitHub
git remote add origin https://github.com/TU_USUARIO/c5_taller_4.git

# Verificar el remote
git remote -v
```

**Nota:** Reemplaza `TU_USUARIO` con tu username de GitHub.

### 5. Primer Commit y Push

```bash
# Verificar el estado
git status

# Agregar todos los archivos
git add .

# Crear el commit inicial
git commit -m "Initial commit: DevMatch AI project setup

- Java backend with Maven
- Angular frontend setup
- Docker configuration
- Complete environment setup documentation
- Verification scripts"

# Cambiar a la rama main (si es necesario)
git branch -M main

# Subir al repositorio remoto
git push -u origin main
```

**Nota:** Si es la primera vez, GitHub te pedirá autenticarte. Sigue las instrucciones en pantalla.

### 6. Verificar en GitHub

1. Visita tu repositorio: `https://github.com/TU_USUARIO/c5_taller_4`
2. Verifica que todos los archivos estén presentes
3. Verifica que el README.md se muestre correctamente

## 🔐 Autenticación con GitHub

### Opción 1: Personal Access Token (Recomendado)

1. Ve a GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Haz clic en "Generate new token (classic)"
3. Configura:
   - **Note:** "DevMatch Project"
   - **Expiration:** 90 days (o el que prefieras)
   - **Scopes:** Marca `repo` (acceso completo a repositorios)
4. Haz clic en "Generate token"
5. **Copia el token** (solo se muestra una vez)
6. Úsalo como password cuando Git te pida credenciales

### Opción 2: SSH Keys

```bash
# Generar SSH key
ssh-keygen -t ed25519 -C "tu.email@ejemplo.com"

# Copiar la clave pública
cat ~/.ssh/id_ed25519.pub

# Agregar la clave en GitHub:
# Settings → SSH and GPG keys → New SSH key
# Pega el contenido de la clave pública

# Cambiar el remote a SSH
git remote set-url origin git@github.com:TU_USUARIO/c5_taller_4.git
```

## 📝 Comandos Git Útiles

### Trabajo Diario

```bash
# Ver estado de los archivos
git status

# Agregar archivos específicos
git add archivo.txt
git add directorio/

# Agregar todos los archivos
git add .

# Crear commit
git commit -m "Descripción del cambio"

# Subir cambios
git push origin main

# Bajar cambios
git pull origin main
```

### Ver Historial

```bash
# Ver commits
git log

# Ver commits en una línea
git log --oneline

# Ver cambios en un archivo
git diff archivo.txt
```

### Ramas

```bash
# Crear nueva rama
git branch nombre-rama

# Cambiar de rama
git checkout nombre-rama

# Crear y cambiar de rama
git checkout -b nombre-rama

# Ver ramas
git branch
```

## 🎯 Estructura de Commits Recomendada

Usa mensajes de commit descriptivos:

```bash
# Formato: tipo: descripción breve

# Ejemplos:
git commit -m "feat: add Java backend setup with Maven"
git commit -m "feat: add Angular frontend project"
git commit -m "docs: add environment setup guide"
git commit -m "fix: correct Docker installation steps"
git commit -m "chore: update .gitignore for Angular"
```

**Tipos comunes:**
- `feat:` Nueva funcionalidad
- `fix:` Corrección de bug
- `docs:` Cambios en documentación
- `chore:` Tareas de mantenimiento
- `refactor:` Refactorización de código

## ✅ Checklist de Verificación

- [ ] Cuenta de GitHub creada y verificada
- [ ] Repositorio público creado
- [ ] Git configurado localmente
- [ ] Repositorio local inicializado
- [ ] Remote de GitHub configurado
- [ ] Primer commit realizado
- [ ] Cambios subidos a GitHub
- [ ] Archivos visibles en GitHub
- [ ] README.md se muestra correctamente

## 🔗 Enlaces Útiles

- [GitHub Docs](https://docs.github.com/)
- [Git Documentation](https://git-scm.com/doc)
- [GitHub Guides](https://guides.github.com/)
- [Git Cheat Sheet](https://education.github.com/git-cheat-sheet-education.pdf)

## 🆘 Solución de Problemas

### Error: "remote origin already exists"

```bash
# Ver remotes actuales
git remote -v

# Eliminar remote existente
git remote remove origin

# Agregar nuevo remote
git remote add origin https://github.com/TU_USUARIO/c5_taller_4.git
```

### Error: "failed to push some refs"

```bash
# Bajar cambios primero
git pull origin main --allow-unrelated-histories

# Resolver conflictos si hay
# Luego subir
git push origin main
```

### Error: "authentication failed"

- Verifica que tu Personal Access Token sea correcto
- O configura SSH keys
- O usa GitHub CLI: `gh auth login`

---

**Última actualización:** 2025


