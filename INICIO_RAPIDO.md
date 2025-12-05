# 🚀 Inicio Rápido del Entorno

## ✅ Estado Actual

- ✅ Entorno virtual Python creado (`venv/`)
- ✅ Dependencias de Python instaladas (Flask, SQLAlchemy, etc.)
- ✅ Proyecto Java compilado correctamente
- ✅ Symlink `.venv` creado para compatibilidad con el launcher

## 🎯 Cómo Iniciar el Servidor

### Opción 1: Usando el Script Automático (Recomendado)

```bash
./iniciar_entorno.sh
```

### Opción 2: Manual (Paso a Paso)

```bash
# 1. Compilar el proyecto
mvn clean compile

# 2. Iniciar el servidor
mvn exec:java
```

### Opción 3: Directamente con Python (si prefieres)

```bash
# Activar entorno virtual
source venv/bin/activate

# Iniciar servidor Flask directamente
python app.py
```

## 🔍 Verificar que Funciona

Una vez iniciado, el servidor debería estar disponible en:
- **URL Principal:** http://localhost:3000
- **API Endpoints:** http://localhost:3000/api/developers, etc.

Para verificar:
```bash
curl http://localhost:3000
```

## ⚠️ Solución de Problemas

### Si el servidor no inicia:

1. **Verificar que el entorno virtual existe:**
   ```bash
   ls -la venv/bin/python
   ```

2. **Verificar que Flask está instalado:**
   ```bash
   venv/bin/python -c "import flask; print(flask.__version__)"
   ```

3. **Verificar que PostgreSQL está corriendo (si usas base de datos):**
   ```bash
   psql -U calebnehemias -d devmatch_ai -c "SELECT 1;"
   ```

4. **Ver logs del servidor:**
   - Si usas `mvn exec:java`, los logs aparecen en la terminal
   - Si usas `python app.py`, también aparecen en la terminal

### Si hay errores de base de datos:

El proyecto puede funcionar sin base de datos si no la necesitas para la demostración del componente Angular. El componente `volver-inicio` no requiere base de datos.

## 📝 Notas Importantes

- El servidor Flask se ejecuta en el puerto **3000**
- El launcher Java busca el entorno virtual en `.venv/bin/python`
- Si cambias algo en el código Python, reinicia el servidor

## 🎓 Para Mostrar el Componente Angular

El componente Angular `volver-inicio` está en:
- `src/main/webapp/app/volver-inicio/`

Para verificar el componente (sin necesidad del servidor Flask):
```bash
./VERIFICAR_COMPONENTE.sh
```

---

**¡Listo para iniciar!** 🚀


