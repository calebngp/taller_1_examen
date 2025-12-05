#!/bin/bash

# Script para iniciar el entorno completo de DevMatch AI
# Uso: ./iniciar_entorno.sh

echo "🚀 Iniciando entorno DevMatch AI..."
echo ""

# Verificar que existe el entorno virtual
if [ ! -d "venv" ] && [ ! -d ".venv" ]; then
    echo "📦 Creando entorno virtual..."
    python3 -m venv venv
    ln -s venv .venv 2>/dev/null
fi

# Activar entorno virtual e instalar dependencias si es necesario
if [ ! -f "venv/bin/python" ]; then
    echo "❌ Error: No se pudo crear el entorno virtual"
    exit 1
fi

# Verificar si Flask está instalado
if ! venv/bin/python -c "import flask" 2>/dev/null; then
    echo "📥 Instalando dependencias de Python..."
    venv/bin/pip install --upgrade pip
    venv/bin/pip install -r requirements.txt
    # Instalar psycopg2-binary por separado si falla
    venv/bin/pip install psycopg2-binary 2>/dev/null || venv/bin/pip install psycopg2
fi

# Verificar compatibilidad SQLAlchemy con Python 3.13
echo "🔍 Verificando compatibilidad de dependencias..."
if venv/bin/python -c "import sqlalchemy; from flask_sqlalchemy import SQLAlchemy" 2>/dev/null; then
    echo "✅ Dependencias compatibles"
else
    echo "⚠️  Actualizando SQLAlchemy para compatibilidad con Python 3.13..."
    venv/bin/pip install --upgrade sqlalchemy flask-sqlalchemy
fi

# Compilar proyecto Java
echo "🔨 Compilando proyecto Java..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ Error al compilar el proyecto"
    exit 1
fi

# Iniciar servidor
echo ""
echo "✅ Entorno listo. Iniciando servidor..."
echo ""
mvn exec:java

