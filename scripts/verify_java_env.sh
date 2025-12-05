#!/bin/bash

# Script de verificación del entorno Java
# Verifica la instalación de SDKMAN!, Java y Maven

echo "=========================================="
echo "🔍 Verificación del Entorno Java"
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
        $1 $2 2>&1 | head -n 3
        echo ""
        return 0
    else
        echo -e "${RED}❌ $1 NO está instalado${NC}"
        echo ""
        return 1
    fi
}

# Verificar SDKMAN!
echo "1. Verificando SDKMAN!..."
if [ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]; then
    source "$HOME/.sdkman/bin/sdkman-init.sh"
    echo -e "${GREEN}✅ SDKMAN! está instalado${NC}"
    sdk version
    echo ""
else
    echo -e "${RED}❌ SDKMAN! NO está instalado${NC}"
    echo "   Instala con: curl -s \"https://get.sdkman.io\" | bash"
    echo ""
fi

# Verificar Java
echo "2. Verificando Java..."
check_command "java" "-version"

# Verificar JAVA_HOME
echo "3. Verificando JAVA_HOME..."
if [ -n "$JAVA_HOME" ]; then
    echo -e "${GREEN}✅ JAVA_HOME está configurado: $JAVA_HOME${NC}"
else
    echo -e "${YELLOW}⚠️  JAVA_HOME no está configurado${NC}"
    if [ -n "$SDKMAN_CANDIDATES_DIR" ]; then
        echo "   Configura con: export JAVA_HOME=\$SDKMAN_CANDIDATES_DIR/java/current"
    fi
fi
echo ""

# Verificar Maven
echo "4. Verificando Maven..."
check_command "mvn" "-version"

# Verificar compilación del proyecto
echo "5. Verificando proyecto Java..."
if [ -f "pom.xml" ]; then
    echo -e "${GREEN}✅ pom.xml encontrado${NC}"
    echo "   Intentando compilar..."
    mvn clean compile -q
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✅ Proyecto compila correctamente${NC}"
    else
        echo -e "${RED}❌ Error al compilar el proyecto${NC}"
    fi
else
    echo -e "${YELLOW}⚠️  pom.xml no encontrado en el directorio actual${NC}"
fi
echo ""

echo "=========================================="
echo "✅ Verificación completada"
echo "=========================================="


