#!/bin/bash

# Script de Verificación del Componente Angular "volver-inicio"
# Ejecutar: chmod +x VERIFICAR_COMPONENTE.sh && ./VERIFICAR_COMPONENTE.sh

echo "🔍 Verificando Componente Angular 'volver-inicio'"
echo "=================================================="
echo ""

# Colores
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para verificar archivo
check_file() {
    if [ -f "$1" ]; then
        echo -e "${GREEN}✅${NC} $1 existe"
        return 0
    else
        echo -e "${RED}❌${NC} $1 NO existe"
        return 1
    fi
}

# Función para verificar contenido
check_content() {
    if grep -q "$2" "$1" 2>/dev/null; then
        echo -e "${GREEN}✅${NC} '$2' encontrado en $1"
        return 0
    else
        echo -e "${RED}❌${NC} '$2' NO encontrado en $1"
        return 1
    fi
}

echo "📁 1. Verificando Estructura de Archivos"
echo "----------------------------------------"
check_file "src/main/webapp/app/volver-inicio/volver-inicio.component.ts"
check_file "src/main/webapp/app/volver-inicio/volver-inicio.component.html"
check_file "src/main/webapp/app/volver-inicio/volver-inicio.component.scss"
check_file "src/main/webapp/app/volver-inicio/volver-inicio.component.spec.ts"
echo ""

echo "📝 2. Verificando Código del Componente TypeScript"
echo "---------------------------------------------------"
if [ -f "src/main/webapp/app/volver-inicio/volver-inicio.component.ts" ]; then
    check_content "src/main/webapp/app/volver-inicio/volver-inicio.component.ts" "jhi-volver-inicio"
    check_content "src/main/webapp/app/volver-inicio/volver-inicio.component.ts" "Router"
    check_content "src/main/webapp/app/volver-inicio/volver-inicio.component.ts" "volverAlInicio"
    check_content "src/main/webapp/app/volver-inicio/volver-inicio.component.ts" "router.navigate"
fi
echo ""

echo "🎨 3. Verificando HTML del Componente"
echo "--------------------------------------"
if [ -f "src/main/webapp/app/volver-inicio/volver-inicio.component.html" ]; then
    check_content "src/main/webapp/app/volver-inicio/volver-inicio.component.html" "btn btn-primary"
    check_content "src/main/webapp/app/volver-inicio/volver-inicio.component.html" "volverAlInicio()"
    check_content "src/main/webapp/app/volver-inicio/volver-inicio.component.html" "Volver al inicio"
fi
echo ""

echo "🛣️  4. Verificando Routing"
echo "--------------------------"
check_file "src/main/webapp/app/app-routing.module.ts"
if [ -f "src/main/webapp/app/app-routing.module.ts" ]; then
    check_content "src/main/webapp/app/app-routing.module.ts" "path: 'volver'"
    check_content "src/main/webapp/app/app-routing.module.ts" "VolverInicioComponent"
fi
echo ""

echo "🧭 5. Verificando Navbar"
echo "------------------------"
check_file "src/main/webapp/app/layouts/navbar/navbar.component.html"
if [ -f "src/main/webapp/app/layouts/navbar/navbar.component.html" ]; then
    check_content "src/main/webapp/app/layouts/navbar/navbar.component.html" "routerLink=\"/volver\""
    check_content "src/main/webapp/app/layouts/navbar/navbar.component.html" "Volver"
fi
echo ""

echo "📦 6. Verificando Módulo de la Aplicación"
echo "-------------------------------------------"
check_file "src/main/webapp/app/app.module.ts"
if [ -f "src/main/webapp/app/app.module.ts" ]; then
    check_content "src/main/webapp/app/app.module.ts" "VolverInicioComponent"
    check_content "src/main/webapp/app/app.module.ts" "AppRoutingModule"
fi
echo ""

echo "📊 Resumen"
echo "=========="
echo ""
echo "Para ver el código completo de cada archivo:"
echo "  cat src/main/webapp/app/volver-inicio/volver-inicio.component.ts"
echo "  cat src/main/webapp/app/volver-inicio/volver-inicio.component.html"
echo "  cat src/main/webapp/app/app-routing.module.ts"
echo "  cat src/main/webapp/app/layouts/navbar/navbar.component.html"
echo ""
echo "Para ver la estructura completa:"
echo "  tree src/main/webapp/app/volver-inicio/ -L 1"
echo ""
echo "✅ Verificación completada!"


