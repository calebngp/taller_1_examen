# 📁 Estructura del Proyecto Angular

Este documento describe la estructura del proyecto Angular para DevMatch AI Frontend.

## 🎯 Creación del Proyecto

```bash
# Crear nuevo proyecto Angular
ng new devmatch-frontend

# Opciones recomendadas durante la creación:
# - Routing: Yes
# - Stylesheet format: CSS (o SCSS si prefieres)
# - Strict mode: Yes (recomendado)
```

## 📂 Estructura de Directorios

```
devmatch-frontend/
├── .angular/                    # Configuración interna de Angular CLI
├── .vscode/                     # Configuración de VS Code (opcional)
├── node_modules/                # Dependencias de npm (no se sube a Git)
├── src/                         # Código fuente principal
│   ├── app/                     # Módulo principal de la aplicación
│   │   ├── components/          # Componentes reutilizables
│   │   │   ├── developer-card/
│   │   │   ├── project-card/
│   │   │   └── matching-result/
│   │   ├── services/           # Servicios (lógica de negocio, HTTP)
│   │   │   ├── developer.service.ts
│   │   │   ├── project.service.ts
│   │   │   └── matching.service.ts
│   │   ├── models/             # Interfaces y modelos TypeScript
│   │   │   ├── developer.model.ts
│   │   │   ├── project.model.ts
│   │   │   └── matching.model.ts
│   │   ├── pages/              # Páginas/Vistas principales
│   │   │   ├── developers/
│   │   │   ├── projects/
│   │   │   └── matching/
│   │   ├── app.component.ts    # Componente raíz
│   │   ├── app.component.html
│   │   ├── app.component.css
│   │   ├── app.module.ts        # Módulo raíz (si usas NgModules)
│   │   └── app-routing.module.ts # Configuración de rutas
│   ├── assets/                 # Archivos estáticos (imágenes, fuentes, etc.)
│   │   ├── images/
│   │   └── icons/
│   ├── environments/           # Configuraciones por ambiente
│   │   ├── environment.ts      # Desarrollo
│   │   └── environment.prod.ts # Producción
│   ├── styles/                 # Estilos globales (opcional)
│   │   └── global.css
│   ├── index.html              # HTML principal
│   ├── main.ts                 # Punto de entrada de la aplicación
│   ├── styles.css              # Estilos globales
│   └── polyfills.ts            # Polyfills para compatibilidad
├── .editorconfig               # Configuración del editor
├── .gitignore                  # Archivos ignorados por Git
├── angular.json                # Configuración de Angular CLI
├── package.json                # Dependencias y scripts npm
├── package-lock.json           # Versiones exactas de dependencias
├── README.md                   # Documentación del proyecto
├── tsconfig.json               # Configuración de TypeScript
├── tsconfig.app.json           # Configuración TS para la app
└── tsconfig.spec.json          # Configuración TS para tests
```

## 🔧 Archivos de Configuración Importantes

### `package.json`
Define las dependencias del proyecto y scripts npm:

```json
{
  "name": "devmatch-frontend",
  "version": "1.0.0",
  "scripts": {
    "ng": "ng",
    "start": "ng serve",
    "build": "ng build",
    "watch": "ng build --watch --configuration development",
    "test": "ng test"
  },
  "dependencies": {
    "@angular/animations": "^17.0.0",
    "@angular/common": "^17.0.0",
    "@angular/compiler": "^17.0.0",
    "@angular/core": "^17.0.0",
    "@angular/forms": "^17.0.0",
    "@angular/platform-browser": "^17.0.0",
    "@angular/platform-browser-dynamic": "^17.0.0",
    "@angular/router": "^17.0.0",
    "rxjs": "~7.8.0",
    "tslib": "^2.3.0",
    "zone.js": "~0.14.0"
  }
}
```

### `angular.json`
Configuración del workspace de Angular:

```json
{
  "projects": {
    "devmatch-frontend": {
      "root": "",
      "sourceRoot": "src",
      "projectType": "application",
      "architect": {
        "build": {
          "builder": "@angular-devkit/build-angular:browser",
          "options": {
            "outputPath": "dist/devmatch-frontend",
            "index": "src/index.html",
            "main": "src/main.ts",
            "polyfills": "src/polyfills.ts",
            "styles": ["src/styles.css"],
            "scripts": []
          }
        },
        "serve": {
          "builder": "@angular-devkit/build-angular:dev-server",
          "options": {
            "port": 4200
          }
        }
      }
    }
  }
}
```

### `tsconfig.json`
Configuración de TypeScript:

```json
{
  "compileOnSave": false,
  "compilerOptions": {
    "baseUrl": "./",
    "outDir": "./dist/out-tsc",
    "forceConsistentCasingInFileNames": true,
    "strict": true,
    "noImplicitOverride": true,
    "noPropertyAccessFromIndexSignature": true,
    "noImplicitReturns": true,
    "noFallthroughCasesInSwitch": true,
    "sourceMap": true,
    "declaration": false,
    "downlevelIteration": true,
    "experimentalDecorators": true,
    "moduleResolution": "node",
    "importHelpers": true,
    "target": "ES2022",
    "module": "ES2022",
    "lib": ["ES2022", "dom"]
  }
}
```

## 🏗️ Estructura Recomendada para DevMatch

### Componentes

```
src/app/components/
├── developer-card/
│   ├── developer-card.component.ts
│   ├── developer-card.component.html
│   ├── developer-card.component.css
│   └── developer-card.component.spec.ts
├── project-card/
│   └── ...
└── matching-result/
    └── ...
```

### Servicios

```
src/app/services/
├── developer.service.ts      # CRUD de desarrolladores
├── project.service.ts        # CRUD de proyectos
├── matching.service.ts       # Lógica de matching
└── api.service.ts           # Servicio base para HTTP
```

### Modelos

```
src/app/models/
├── developer.model.ts
├── project.model.ts
├── technology.model.ts
├── experience.model.ts
└── matching-result.model.ts
```

### Páginas/Rutas

```
src/app/pages/
├── developers/
│   ├── developers.component.ts
│   ├── developers.component.html
│   └── developer-detail/
│       └── developer-detail.component.ts
├── projects/
│   └── ...
└── matching/
    └── ...
```

## 📝 Comandos Útiles

### Desarrollo

```bash
# Iniciar servidor de desarrollo
ng serve

# Iniciar en puerto específico
ng serve --port 4201

# Compilar para producción
ng build --configuration production

# Compilar con watch mode
ng build --watch
```

### Generación de Código

```bash
# Generar componente
ng generate component components/developer-card

# Generar servicio
ng generate service services/developer

# Generar módulo
ng generate module modules/shared

# Generar guard
ng generate guard guards/auth
```

### Testing

```bash
# Ejecutar tests unitarios
ng test

# Ejecutar tests con coverage
ng test --code-coverage

# Ejecutar e2e tests
ng e2e
```

## 🔗 Integración con Backend

### Configuración de API

En `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:3000/api'
};
```

En `src/environments/environment.prod.ts`:

```typescript
export const environment = {
  production: true,
  apiUrl: 'https://api.devmatch.com/api'
};
```

### Ejemplo de Servicio HTTP

```typescript
// src/app/services/developer.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Developer } from '../models/developer.model';

@Injectable({
  providedIn: 'root'
})
export class DeveloperService {
  private apiUrl = `${environment.apiUrl}/developers`;

  constructor(private http: HttpClient) {}

  getDevelopers(): Observable<Developer[]> {
    return this.http.get<Developer[]>(this.apiUrl);
  }

  getDeveloper(id: number): Observable<Developer> {
    return this.http.get<Developer>(`${this.apiUrl}/${id}`);
  }

  createDeveloper(developer: Developer): Observable<Developer> {
    return this.http.post<Developer>(this.apiUrl, developer);
  }

  updateDeveloper(id: number, developer: Developer): Observable<Developer> {
    return this.http.put<Developer>(`${this.apiUrl}/${id}`, developer);
  }

  deleteDeveloper(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
```

## 📦 Dependencias Recomendadas

### Para DevMatch Frontend

```bash
# HTTP Client (ya incluido)
# @angular/common/http

# Forms (ya incluido)
# @angular/forms

# Instalar dependencias adicionales si es necesario
npm install @angular/material @angular/cdk        # Material Design
npm install bootstrap                             # Bootstrap CSS
npm install axios                                 # Cliente HTTP alternativo
npm install rxjs                                  # Programación reactiva (ya incluido)
```

## 🚀 Despliegue

### Build para Producción

```bash
# Compilar para producción
ng build --configuration production

# Los archivos compilados estarán en: dist/devmatch-frontend/
```

### Desplegar en GitHub Pages

```bash
# Instalar angular-cli-ghpages
npm install -g angular-cli-ghpages

# Build y deploy
ng build --configuration production --base-href=/devmatch-frontend/
npx angular-cli-ghpages --dir=dist/devmatch-frontend
```

## 📚 Recursos Adicionales

- [Angular Documentation](https://angular.io/docs)
- [Angular CLI Documentation](https://angular.io/cli)
- [RxJS Documentation](https://rxjs.dev/)
- [TypeScript Documentation](https://www.typescriptlang.org/docs/)

---

**Última actualización:** 2025  
**Versión:** 1.0.0


