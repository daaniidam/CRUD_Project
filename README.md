# CRUD Project — Cliente JavaFX de gestión bancaria

Aplicación de escritorio **JavaFX** para la gestión de una entidad bancaria
(clientes, cuentas y movimientos). Es el **cliente** de una arquitectura
cliente–servidor: consume una API REST servida por un backend Java EE
independiente (`CRUDBankServerSide`).

> ℹ️ **Proyecto de equipo.** Desarrollado en grupo como proyecto formativo
> (ver [Créditos](#créditos)). Esta copia recopila el trabajo y lo documenta
> para el portfolio personal.

## Funcionalidades

- **Inicio de sesión (Sign In):** según las credenciales se entra como
  **administrador** o como **usuario normal**.
- **Administrador:** crea, consulta, actualiza y elimina *Customers* (clientes).
- **Usuario normal:** crea cuentas asociadas a su propio cliente, de tipo
  **estándar** o **crédito**.
- **Movimientos:** asociados a una cuenta, permiten registrar **pagos** y
  **depósitos**.
- **Informes:** generación de informes de clientes, cuentas y movimientos con
  **JasperReports** (`.jrxml` / `.jasper`).
- **Cambio de contraseña** desde la propia aplicación.

## Arquitectura

```
┌─────────────────────────┐        REST (JSON/XML)        ┌───────────────────────────┐
│   CRUD_Project (este)    │  ───────────────────────────▶ │   CRUDBankServerSide       │
│   Cliente JavaFX         │   http://localhost:8080/...   │   Backend Java EE (Payara) │
│   (Jersey JAX-RS client) │ ◀───────────────────────────  │   + MySQL                  │
└─────────────────────────┘                                └───────────────────────────┘
```

El cliente ataca por defecto a la URL base:
`http://localhost:8080/CRUDBankServerSide/webresources`.

> ⚠️ **Importante:** este repositorio contiene **solo el cliente**. Para que la
> aplicación funcione de extremo a extremo necesitas el backend
> `CRUDBankServerSide` desplegado en un servidor Payara con su base de datos
> MySQL. Sin ese servidor la aplicación arranca pero no podrá comunicarse con
> los servicios REST.

## Estructura del proyecto

```
CRUD_Project/
├── pom.xml                            # Build Maven (mvn javafx:run / mvn package)
├── src/
│   ├── config.properties             # URL base del backend REST (configurable)
│   ├── CRUD_Project.java              # Punto de entrada (JavaFX Application)
│   └── CRUD_Project/
│       ├── logic/                     # Clientes REST (Jersey/JAX-RS)
│       │   ├── Config.java            # Lee la URL del backend (sys prop / config.properties)
│       │   ├── AccountRESTClient.java
│       │   ├── CustomerRESTClient.java
│       │   └── MovementRESTClient.java
│       ├── model/                     # Entidades (Account, Customer, Movement, AccountType)
│       └── ui/                        # Controladores + vistas FXML
│           ├── *.fxml
│           ├── *Controller.java
│           └── report/                # Plantillas JasperReports (.jrxml/.jasper)
└── test/                             # Tests de interfaz (JUnit 4 + TestFX)
```

## Tecnologías

- **Java 17+** + **JavaFX 21** (interfaz con FXML)
- **Jersey / JAX-RS** (`javax.ws.rs`) para el cliente REST
- **JasperReports** para informes
- **JUnit 4 + TestFX** para los tests de interfaz
- **Maven** como sistema de construcción

## Requisitos

- **JDK 17 o superior** (probado con Temurin 21)
- **Maven 3.8+**
- El backend `CRUDBankServerSide` (Payara + MySQL) en ejecución para la
  funcionalidad completa (las dependencias JavaFX/Jersey/JasperReports las
  resuelve Maven automáticamente).

## Cómo ejecutar

1. Despliega y arranca el backend `CRUDBankServerSide` en Payara con su base de
   datos MySQL.
2. Configura la URL del backend si tu contexto/host difiere del de referencia,
   editando `src/config.properties` o pasando `-Dbase.uri=...`:
   ```
   base.uri=http://localhost:8080/crudserversideexample/webresources
   ```
3. Ejecuta la aplicación con Maven:
   ```bash
   mvn clean javafx:run
   ```
   (o genera el jar con `mvn clean package`).
4. Accede como administrador con las credenciales por defecto:
   - **Usuario:** `admin`
   - **Contraseña:** `admin`

> **Nota sobre el login:** el acceso `admin/admin` está implementado en el
> cliente como atajo temporal para pruebas (ver `SignInController`). El resto de
> usuarios se autentican contra el backend. En un entorno real convendría mover
> también esa validación al servidor.

## Capturas

Capturas reales de la aplicación JavaFX funcionando **de extremo a extremo**
contra el backend REST (`CRUDBankServerSide` sobre Payara + MySQL), con datos
reales de clientes, cuentas y movimientos.

| Inicio de sesión | Registro |
|:---:|:---:|
| ![Sign In](docs/img/signin.png) | ![Sign Up](docs/img/signup.png) |

**Gestión de clientes (Customers CRUD)**

![Customers](docs/img/customer.png)

**Gestión de cuentas (Accounts)**

![Accounts](docs/img/account.png)

**Movimientos (Movements)**

![Movements](docs/img/movement.png)

## Créditos

Proyecto de equipo desarrollado por:

- **Daniel López López** ([@daaniidam](https://github.com/daaniidam))
- Chad
- Imad

## Licencia

Distribuido bajo licencia [MIT](LICENSE).
