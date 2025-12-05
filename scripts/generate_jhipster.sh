#!/bin/bash

# ============================================================
# Script para Generar Proyecto JHipster desde JDL
# DevMatch AI - Sistema de Matching
# ============================================================

set -e  # Salir si hay algún error

# Colores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Directorio del proyecto
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
JDL_FILE="$PROJECT_DIR/devmatch.jdl"
OUTPUT_DIR="$PROJECT_DIR/devmatch-jhipster"

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}Generador de Proyecto JHipster${NC}"
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

# Verificar Java
if ! command -v java &> /dev/null; then
    echo -e "${RED}❌ Error: Java no está instalado${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Java está instalado${NC}"
java -version

# Verificar Maven
if ! command -v mvn &> /dev/null; then
    echo -e "${YELLOW}⚠️  Maven no está instalado. Se usará el wrapper si está disponible.${NC}"
fi

# Crear directorio de salida
if [ -d "$OUTPUT_DIR" ]; then
    echo -e "${YELLOW}⚠️  El directorio $OUTPUT_DIR ya existe.${NC}"
    read -p "¿Deseas eliminarlo y crear uno nuevo? (y/n): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        rm -rf "$OUTPUT_DIR"
        echo -e "${GREEN}✅ Directorio eliminado${NC}"
    else
        echo -e "${YELLOW}⚠️  Usando directorio existente${NC}"
    fi
fi

mkdir -p "$OUTPUT_DIR"
cd "$OUTPUT_DIR"

# Copiar archivo JDL
cp "$JDL_FILE" .

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}Generando proyecto JHipster...${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

# Generar proyecto
jhipster jdl devmatch.jdl

echo ""
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}✅ Proyecto generado exitosamente${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo -e "${BLUE}Próximos pasos:${NC}"
echo -e "1. cd $OUTPUT_DIR"
echo -e "2. npm install"
echo -e "3. mvn clean install"
echo -e "4. mvn spring-boot:run"
echo ""
echo -e "${BLUE}El proyecto estará disponible en:${NC}"
echo -e "- Backend: http://localhost:8080"
echo -e "- Swagger: http://localhost:8080/swagger-ui.html"
echo ""

