#!/bin/bash

# ============================================================
# Script para Validar Archivo JDL
# DevMatch AI - Sistema de Matching
# ============================================================

set -e

# Colores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Directorio del proyecto
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
JDL_FILE="$PROJECT_DIR/devmatch.jdl"

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}Validador de Archivo JDL${NC}"
echo -e "${BLUE}DevMatch AI${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

# Verificar que existe el archivo JDL
if [ ! -f "$JDL_FILE" ]; then
    echo -e "${RED}❌ Error: No se encontró el archivo devmatch.jdl${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Archivo JDL encontrado: $JDL_FILE${NC}"

# Verificar que JHipster está instalado
if ! command -v jhipster &> /dev/null; then
    echo -e "${YELLOW}⚠️  JHipster no está instalado. Instalando...${NC}"
    npm install -g generator-jhipster
fi

echo -e "${GREEN}✅ JHipster está instalado${NC}"
jhipster --version

echo ""
echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}Validando archivo JDL...${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

# Crear directorio temporal para validación
TEMP_DIR=$(mktemp -d)
cd "$TEMP_DIR"

# Copiar JDL
cp "$JDL_FILE" .

# Validar con dry-run
if jhipster jdl devmatch.jdl --dry-run 2>&1 | tee validation.log; then
    echo ""
    echo -e "${GREEN}========================================${NC}"
    echo -e "${GREEN}✅ Validación exitosa${NC}"
    echo -e "${GREEN}========================================${NC}"
    echo ""
    echo -e "${BLUE}El archivo JDL es válido y puede ser usado para generar el proyecto.${NC}"
    rm -rf "$TEMP_DIR"
    exit 0
else
    echo ""
    echo -e "${RED}========================================${NC}"
    echo -e "${RED}❌ Errores encontrados en el JDL${NC}"
    echo -e "${RED}========================================${NC}"
    echo ""
    echo -e "${YELLOW}Revisa el log de validación para más detalles.${NC}"
    cat validation.log
    rm -rf "$TEMP_DIR"
    exit 1
fi

