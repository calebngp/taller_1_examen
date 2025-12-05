#!/bin/bash

# Script de verificación del entorno Angular
# Verifica la instalación de NVM, Node.js, npm y Angular CLI

echo "=========================================="
echo "🔍 Verificación del Entorno Angular"
echo "=========================================="
echo ""

# Colores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Cargar NVM si existe
if [ -s "$HOME/.nvm/nvm.sh" ]; then
    export NVM_DIR="$HOME/.nvm"
    [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
fi

# Función para verificar comando
check_command() {
    if command -v $1 &> /dev/null; then
        echo -e "${GREEN}✅ $1 está instalado${NC}"
        $1 $2 2>&1 | head -n 5
        echo ""
        return 0
    else
        echo -e "${RED}❌ $1 NO está instalado${NC}"
        echo ""
        return 1
    fi
}

# Verificar NVM
echo "1. Verificando NVM..."
if command -v nvm &> /dev/null || [ -s "$HOME/.nvm/nvm.sh" ]; then
    echo -e "${GREEN}✅ NVM está instalado${NC}"
    if command -v nvm &> /dev/null; then
        nvm --version
        echo "   Versión actual de Node: $(nvm current)"
    fi
    echo ""
else
    echo -e "${RED}❌ NVM NO está instalado${NC}"
    echo "   Instala con: curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.0/install.sh | bash"
    echo ""
fi

# Verificar Node.js
echo "2. Verificando Node.js..."
check_command "node" "-v"

# Verificar npm
echo "3. Verificando npm..."
check_command "npm" "-v"

# Verificar Angular CLI
echo "4. Verificando Angular CLI..."
if command -v ng &> /dev/null; then
    echo -e "${GREEN}✅ Angular CLI está instalado${NC}"
    ng version
    echo ""
else
    echo -e "${RED}❌ Angular CLI NO está instalado${NC}"
    echo "   Instala con: npm install -g @angular/cli"
    echo ""
fi

# Verificar proyecto Angular (si existe)
echo "5. Verificando proyecto Angular..."
if [ -d "devmatch-frontend" ] && [ -f "devmatch-frontend/angular.json" ]; then
    echo -e "${GREEN}✅ Proyecto Angular encontrado${NC}"
    cd devmatch-frontend
    
    # Verificar node_modules
    if [ -d "node_modules" ]; then
        echo -e "${GREEN}✅ Dependencias instaladas${NC}"
    else
        echo -e "${YELLOW}⚠️  Dependencias no instaladas${NC}"
        echo "   Instala con: npm install"
    fi
    
    cd ..
else
    echo -e "${YELLOW}⚠️  Proyecto Angular no encontrado${NC}"
    echo "   Crea con: ng new devmatch-frontend"
fi
echo ""

echo "=========================================="
echo "✅ Verificación completada"
echo "=========================================="


