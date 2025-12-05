#!/bin/bash

# Script de verificación del entorno Docker
# Verifica la instalación de Docker y Docker Compose

echo "=========================================="
echo "🔍 Verificación del Entorno Docker"
echo "=========================================="
echo ""

# Colores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para verificar comando
check_command() {
    if command -v $1 &> /dev/null; then
        echo -e "${GREEN}✅ $1 está instalado${NC}"
        $1 $2 2>&1 | head -n 10
        echo ""
        return 0
    else
        echo -e "${RED}❌ $1 NO está instalado${NC}"
        echo ""
        return 1
    fi
}

# Verificar Docker
echo "1. Verificando Docker..."
if check_command "docker" "version"; then
    # Verificar si Docker daemon está corriendo
    if docker info &> /dev/null; then
        echo -e "${GREEN}✅ Docker daemon está corriendo${NC}"
    else
        echo -e "${RED}❌ Docker daemon NO está corriendo${NC}"
        echo "   Inicia Docker Desktop o el servicio de Docker"
    fi
    echo ""
fi

# Verificar Docker Compose
echo "2. Verificando Docker Compose..."
if check_command "docker" "compose version"; then
    echo -e "${GREEN}✅ Docker Compose está disponible${NC}"
else
    echo -e "${YELLOW}⚠️  Docker Compose no encontrado como plugin${NC}"
    if command -v docker-compose &> /dev/null; then
        echo -e "${GREEN}✅ docker-compose (standalone) está instalado${NC}"
        docker-compose --version
    fi
fi
echo ""

# Probar contenedor hello-world
echo "3. Probando contenedor hello-world..."
if docker info &> /dev/null; then
    echo "   Ejecutando: docker run hello-world"
    docker run --rm hello-world
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Contenedor hello-world ejecutado exitosamente${NC}"
    else
        echo -e "${RED}❌ Error al ejecutar contenedor hello-world${NC}"
    fi
else
    echo -e "${YELLOW}⚠️  No se puede probar (Docker daemon no está corriendo)${NC}"
fi
echo ""

# Verificar imágenes disponibles
echo "4. Imágenes Docker disponibles:"
if docker info &> /dev/null; then
    docker images
else
    echo -e "${YELLOW}⚠️  Docker daemon no está corriendo${NC}"
fi
echo ""

echo "=========================================="
echo "✅ Verificación completada"
echo "=========================================="


