# Clínica Médica SaludVital

Sistema de gestión de clínica médica (MySQL 8 + JavaFX 21 + DAO Pattern).

**Fases implementadas: 1 → 4** (MySQL, GitFlow/estructura, Base Core, Modelo & DAO).

## Requisitos

- JDK 21
- Apache NetBeans (recomendado 21+)
- MySQL 8.x
- JavaFX SDK 21
- MySQL Connector/J 8.x

## Estructura de paquetes

```
org.cg.system
├── main          → Principal (punto de entrada)
├── model         → POJOs (Usuario, Especialidad, Medico, Paciente, Cita)
├── dao           → Interfaces DAO
├── dao.impl      → Implementaciones con CallableStatement
├── util          → Conexion (Singleton)
├── manager       → SessionContext (Singleton)
├── exception     → DaoException, AppException
└── resources     → (futuro FXML / CSS)
```

## Base de datos

1. Abre MySQL (Workbench o CLI) y ejecuta en orden:

```bash
mysql -u root -p < sql/01-schema.sql
mysql -u root -p < sql/02-stored-procedures.sql
mysql -u root -p < sql/03-seeds.sql
```

2. Copia la configuración:

```bash
cp db.properties.example src/db.properties
```

Edita `src/db.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/saludvital_db?serverTimezone=UTC
db.user=root
db.password=TU_PASSWORD
```

## Abrir en NetBeans

1. **File → Open Project** → selecciona la carpeta `SaludVitalApp`
2. Configura las librerías:
   - Click derecho en el proyecto → **Properties → Libraries**
   - Añade **MySQL Connector/J 8.x** (classpath)
   - Añade **JavaFX 21** (modulepath) o configura `run.jvmargs` con la ruta de tu JavaFX SDK:

```
--module-path "C:\ruta\a\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml
```

3. **Run → Set Project Configuration → Customize** o edita `nbproject/project.properties` si es necesario.

4. Ejecuta la clase `org.cg.system.main.Principal`

## Comandos útiles (GitFlow básico)

```bash
cd SaludVitalApp
git init
git checkout -b develop
git add .
git commit -m "chore: init repo con estructura MVC+DAO y fases 1-4"

# Feature branches (ejemplo)
git checkout -b feature/db-schema develop
# ... trabajo ...
git checkout develop
git merge --no-ff feature/db-schema
```

## Credenciales de prueba

| Usuario     | Password  | Rol            |
|-------------|-----------|----------------|
| admin       | admin123  | ADMIN          |
| dr.garcia   | admin123  | MEDICO         |
| dr.lopez    | admin123  | MEDICO         |
| recep1      | admin123  | RECEPCIONISTA  |

## Entidades principales

- **Usuario** – autenticación y roles (ADMIN / MEDICO / RECEPCIONISTA)
- **Especialidad** – catálogo médico
- **Medico** – vinculado a Usuario + Especialidad
- **Paciente** – datos demográficos
- **Cita** – proceso crítico (agendar / confirmar / atender / cancelar)

Todos los CRUD se realizan exclusivamente mediante **Stored Procedures** y `CallableStatement` (sin SQL embebido en Java).

## Próximas fases (no incluidas)

- **Fase 5**: JavaFX UI (Login, TableViews, flujo de citas)
- **Fase 6**: Merge, README final, tag v1.0.0
