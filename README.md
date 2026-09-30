# Sistema de Gestión de Recursos Humanos para PyME

**Preentrega — Aplicación de consola (Java SE, sin frameworks).**

> Estado actual: la información se guarda **en memoria** (`ArrayList`) y se pierde al cerrar el
> programa. Todavía no hay base de datos, API REST ni frontend: eso forma parte de las etapas
> siguientes del proyecto (ver la sección *Próximos pasos*).

## Datos de la entrega

| Campo | Valor |
| --- | --- |
| Alumno | Ulises Mateo Bucchino |
| Curso | Backend Java |
| Comisión | 26224 |
| Docente | Miguel Nefle |
| Tipo de entrega | Preentrega (entrega parcial del proyecto final) |
| Repositorio | `repo-practicas-java-backend` |

## Alcance de esta entrega

Esta preentrega resuelve el **núcleo del sistema**: la estructura organizacional básica de la
PyME y la gestión del personal, mediante un menú interactivo por consola.

| Incluido en esta entrega | Fuera del alcance (etapas posteriores) |
| --- | --- |
| Gestión de departamentos (alta y listado) | Proyectos y asignación de empleados a proyectos |
| Gestión de puestos (alta y listado) | Vacaciones y licencias |
| Gestión de empleados (alta, listado, búsqueda por id, modificación y baja) | Usuarios, autenticación y roles |
| Validaciones de datos y excepciones propias | Base de datos MySQL y persistencia (JPA/Hibernate) |
| Separación en capas (`model` / `service` / `util` / `exception`) | API REST, JSON y documentación OpenAPI/Swagger |
| Menú interactivo por consola | Frontend Angular y dashboard |
| — | Tests automatizados (JUnit/Mockito) y build con Maven |

## Requisitos

- **JDK** instalado (se necesita `javac`, no alcanza con un JRE). Verificar con:

  ```powershell
  javac -version
  java -version
  ```

- Una terminal (PowerShell o `cmd`) o VS Code con la extensión **Extension Pack for Java**
  (`vscjava.vscode-java-pack`).

No se necesita instalar nada más: el proyecto **no usa Maven ni Gradle** (no hay `pom.xml` ni
`build.gradle`) ni bibliotecas externas.

## Cómo compilar y ejecutar

Los paquetes (`model`, `service`, `util`, `exception`) cuelgan directamente de la raíz del
repositorio, así que **la compilación se hace desde la raíz** (donde está `Main.java`).

### PowerShell

```powershell
cd "ruta\al\repo-practicas-java-backend"

# 1) Compilar todas las fuentes a la carpeta out\
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java).FullName

# 2) Ejecutar el programa (Main está en el paquete por defecto)
java -cp out Main
```

### cmd.exe

```cmd
cd /d "ruta\al\repo-practicas-java-backend"
javac -encoding UTF-8 -d out Main.java exception\*.java model\*.java service\*.java util\*.java
java -cp out Main
```

### Otros detalles

- `-encoding UTF-8` es necesario porque los archivos tienen acentos y ñ. Si tu consola muestra
  mal las tildes, ejecutá `chcp 65001` antes de correr el programa.
- Empaquetado opcional en un `.jar` ejecutable (se genera fuera de `out/` para no incluirse a
  sí mismo; el `.jar` está ignorado por Git):

  ```powershell
  jar --create --file rrhh.jar --main-class Main -C out .
  java -jar rrhh.jar
  ```

- En VS Code: abrir `Main.java` y usar el botón **Run** (la extensión compila y ejecuta el
  proyecto sin necesidad de los comandos anteriores).
- La carpeta `out/` y los `.class` están ignorados por Git (ver `.gitignore`).

## Funcionalidades implementadas

El programa se maneja desde un menú por consola. Cada opción delega en el *service*
correspondiente, que es quien aplica las reglas de negocio.

| Opción | Módulo | Operación | Responsable |
| --- | --- | --- | --- |
| 1 | Departamentos | Crear | `DepartamentoService.agregar` |
| 2 | Departamentos | Listar | `DepartamentoService.listar` |
| 3 | Puestos | Crear (asociado a un departamento) | `PuestoService.agregar` |
| 4 | Puestos | Listar | `PuestoService.listar` |
| 5 | Empleados | Registrar | `EmpleadoService.agregar` |
| 6 | Empleados | Listar | `EmpleadoService.listar` |
| 7 | Empleados | Buscar por id | `EmpleadoService.buscarPorId` |
| 8 | Empleados | Modificar | `EmpleadoService.modificar` |
| 9 | Empleados | Dar de baja (lógica) | `EmpleadoService.darDeBaja` |
| 10 | Sistema | Salir | `Main.main` |

```
=== Sistema de Gestión RRHH PyME ===
1. Crear departamento
2. Listar departamentos
3. Crear puesto
4. Listar puestos
5. Registrar empleado
6. Listar empleados
7. Buscar empleado por id
8. Modificar empleado
9. Dar de baja empleado
10. Salir
```

### Detalle por módulo

- **Departamentos**: alta (nombre y descripción) y listado. El modelo tiene un atributo `activo`
  que se inicializa en `true`, pero todavía no hay opción para activar/desactivar ni para
  modificar o consultar un departamento en particular.
- **Puestos**: alta y listado. Al crear un puesto hay que indicar el **id de un departamento
  existente**; si el id no existe, la operación se cancela con un error. El puesto guarda
  nombre, descripción, nivel (texto libre, se sugiere Junior / Semi-Senior / Senior) y el
  departamento al que pertenece.
- **Empleados**:
  - *Registrar* (opción 5): pide el departamento y el puesto (mostrando primero los listados),
    los datos personales (nombre, apellido, DNI, email, teléfono), la fecha de nacimiento y la
    fecha de ingreso. El empleado queda en estado `ACTIVO` y el id lo asigna el service.
  - *Modificar* (opción 8): actualiza nombre, apellido, email, teléfono y la asignación de
    departamento/puesto. **No** permite cambiar DNI, fechas ni estado.
  - *Dar de baja* (opción 9): **baja lógica**. El empleado no se elimina de la lista: se le
    cambia el estado a `INACTIVO` y se registra la `fechaEgreso` con la fecha del día.

## Estructura del proyecto

```
repo-practicas-java-backend/
├── Main.java                    # Menú por consola y punto de entrada (main)
├── model/                       # Clases de dominio (datos + getters/setters)
│   ├── Departamento.java
│   ├── Empleado.java
│   └── Puesto.java
├── service/                     # Reglas de negocio y "repositorio" en memoria
│   ├── DepartamentoService.java
│   ├── EmpleadoService.java
│   └── PuestoService.java
├── util/
│   └── Validador.java           # Validaciones y lectura de datos por consola
├── exception/                   # Excepciones propias del negocio
│   ├── DepartamentoNoEncontradoException.java
│   ├── EmpleadoInactivoException.java
│   ├── EmpleadoNoEncontradoException.java
│   └── PuestoNoEncontradoException.java
├── .gitignore
└── README.md
```

### Organización en capas

```
Main  (consola / interfaz de usuario)
  │
  ▼
service  (reglas de negocio, búsquedas y almacenamiento en memoria)
  │
  ▼
model  (entidades del dominio)
```

`util.Validador` y las clases de `exception` son transversales: las usan tanto `Main` como los
*services*.

## Modelo de dominio

### `model.Departamento`

| Atributo | Tipo | Observaciones |
| --- | --- | --- |
| `id` | `int` | Lo asigna `DepartamentoService` (1, 2, 3, ...) |
| `nombre` | `String` | Obligatorio |
| `descripcion` | `String` | Puede quedar vacía |
| `activo` | `boolean` | Se inicializa en `true`; hoy no se modifica desde el menú |

### `model.Puesto`

| Atributo | Tipo | Observaciones |
| --- | --- | --- |
| `id` | `int` | Lo asigna `PuestoService` |
| `nombre` | `String` | Obligatorio |
| `descripcion` | `String` | Puede quedar vacía |
| `nivel` | `String` | Texto libre (Junior / Semi-Senior / Senior) |
| `departamento` | `Departamento` | Referencia **por objeto**, no por id |
| `activo` | `boolean` | Se inicializa en `true`; hoy no se modifica |

### `model.Empleado`

| Atributo | Tipo | Observaciones |
| --- | --- | --- |
| `id` | `int` | Lo asigna `EmpleadoService` |
| `nombre`, `apellido` | `String` | Obligatorios |
| `dni` | `String` | 7 u 8 dígitos |
| `email` | `String` | Debe contener `@` |
| `telefono` | `String` | Obligatorio |
| `fechaNacimiento` | `LocalDate` | Formato de carga `dd/MM/yyyy` |
| `fechaIngreso` | `LocalDate` | Formato de carga `dd/MM/yyyy` |
| `fechaEgreso` | `LocalDate` | Se completa automáticamente al dar de baja |
| `estado` | `String` | Nace en `ACTIVO` |
| `departamento` | `Departamento` | Referencia por objeto |
| `puesto` | `Puesto` | Referencia por objeto |

### Relaciones

```
        ┌───────────────┐
        │ DEPARTAMENTO  │
        └───────┬───────┘
                │ 1:N
        ┌───────▼───────┐
        │    PUESTO     │
        └───────▲───────┘
                │
        ┌───────┴───────┐
        │   EMPLEADO    │────► DEPARTAMENTO
        └───────────────┘
```

- Un **departamento** agrupa varios **puestos**.
- Un **empleado** pertenece a un **departamento** y ocupa un **puesto**.
- Las referencias se guardan como **objetos** (no como ids sueltos), igual que se haría con
  relaciones `@ManyToOne` al pasar a JPA.

### Estados posibles de un empleado

```
ACTIVO · LICENCIA · SUSPENDIDO · INACTIVO
```

Están definidos en `Validador.validarEstado`. Hoy el menú solo genera `ACTIVO` (al registrar) e
`INACTIVO` (al dar de baja): no hay opción para pasar a `LICENCIA` o `SUSPENDIDO`.

## Reglas de negocio y validaciones

Todas las validaciones de datos están centralizadas en `util.Validador` y se aplican desde los
*services* antes de guardar.

| Dato | Regla | Mensaje si no cumple |
| --- | --- | --- |
| Nombre | No nulo ni vacío | `El nombre no puede estar vacío.` |
| Apellido | No nulo ni vacío | `El apellido no puede estar vacío.` |
| DNI | Solo dígitos, 7 u 8 caracteres | `El DNI debe contener entre 7 y 8 dígitos numéricos.` |
| Email | No vacío y debe contener `@` | `El email no es válido.` |
| Teléfono | No vacío | `El teléfono no puede estar vacío.` |
| Estado | `ACTIVO`, `LICENCIA`, `SUSPENDIDO` o `INACTIVO` | `El estado debe ser ACTIVO, LICENCIA, SUSPENDIDO o INACTIVO.` |
| Fecha | Formato `dd/MM/yyyy` y fecha real | `Formato de fecha inválido. Intente nuevamente.` |
| Número entero | Debe ser un entero | `Debe ingresar un número entero. Intente nuevamente.` |

Además:

- **Ids automáticos**: cada service lleva su propio contador (`siguienteId`) que arranca en 1.
- **Integridad referencial manual**: para crear un puesto o registrar un empleado se busca el
  departamento (y el puesto) por id; si no existe, se corta la operación con una excepción.
- **Estado inicial**: todo empleado nuevo se registra en estado `ACTIVO`.
- **Baja lógica e irreversible**: dar de baja cambia el estado a `INACTIVO` y setea `fechaEgreso`.
  No se puede dar de baja dos veces al mismo empleado ni modificar a uno ya dado de baja.
- **Lectura robusta**: `leerEntero` y `leerFecha` repiten la pregunta hasta recibir un dato válido,
  así que un error de tipeo no interrumpe el programa.

## Manejo de errores

Las excepciones propias heredan de `RuntimeException` (no chequeadas): no obligan a envolver las
llamadas en `try/catch`, pero permiten capturarlas donde interesa.

| Excepción | Cuándo se lanza |
| --- | --- |
| `DepartamentoNoEncontradoException` | Se busca un departamento por id y no existe |
| `PuestoNoEncontradoException` | Se busca un puesto por id y no existe |
| `EmpleadoNoEncontradoException` | Se busca, modifica o da de baja un empleado con id inexistente |
| `EmpleadoInactivoException` | Se intenta modificar o volver a dar de baja un empleado `INACTIVO` |
| `IllegalArgumentException` | Los datos no pasan las validaciones de `Validador` |

En `Main`, el bucle del menú envuelve cada operación en un `try/catch` que muestra el mensaje y
devuelve el control al menú (el programa **no se cierra** por un error de negocio):

```java
try {
    ejecutarOpcion(opcion);
} catch (IllegalArgumentException | EmpleadoNoEncontradoException | EmpleadoInactivoException
        | DepartamentoNoEncontradoException | PuestoNoEncontradoException e) {
    System.out.println("Error: " + e.getMessage());
}
```

Todos los mensajes van a la salida estándar (`System.out.println`); no se usa ningún framework de
*logging*.

## Próximos pasos

Camino previsto hacia el sistema completo, en orden de dependencia:

| Etapa | Qué agrega |
| --- | --- |
| 1 | Maven, layout `src/main/java` y paquete base (`com.utn.rrhh` o similar) |
| 2 | Tests unitarios con JUnit sobre los *services* (y Mockito más adelante) |
| 3 | Spring Boot + Spring Data JPA + MySQL: persistencia real en lugar de `ArrayList` |
| 4 | API REST con DTOs, *mapper*, validación con Bean Validation y manejo centralizado de errores; documentación con OpenAPI/Swagger |
| 5 | Usuarios, roles (`ADMIN`, `RRHH`, `RESPONSABLE`, `EMPLEADO`) y autenticación con Spring Security + JWT |
| 6 | Módulos nuevos: proyectos, asignaciones, vacaciones, licencias e historial de puestos |
| 7 | Frontend Angular (por funcionalidades, con guardas de rutas por rol) y dashboard |

## Sobre este README

Este documento describe **únicamente lo que el repositorio contiene hoy** (la preentrega). El
alcance completo del proyecto final —modelo conceptual ampliado, frontend, seguridad y dashboard—
excede esta entrega y se irá incorporando en las etapas de la sección anterior.

Toda la información de este README (estructura de archivos, mensajes, validaciones y casos de
prueba) está tomada directamente del código fuente del repositorio.
