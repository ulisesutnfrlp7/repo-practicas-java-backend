import java.time.LocalDate;
import java.util.Scanner;

import exception.DepartamentoNoEncontradoException;
import exception.EmpleadoInactivoException;
import exception.EmpleadoNoEncontradoException;
import exception.PuestoNoEncontradoException;
import model.Departamento;
import model.Empleado;
import model.Puesto;
import service.DepartamentoService;
import service.EmpleadoService;
import service.PuestoService;
import util.Validador;

/*
    Punto de entrada del programa: muestra un menú por consola y delega
    cada operación en el service correspondiente.
 */
public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static DepartamentoService departamentoService = new DepartamentoService();
    private static PuestoService puestoService = new PuestoService();
    private static EmpleadoService empleadoService = new EmpleadoService();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = Validador.leerEntero(sc, "Elija una opción:");
            try {
                ejecutarOpcion(opcion);
            } catch (IllegalArgumentException | EmpleadoNoEncontradoException | EmpleadoInactivoException
                    | DepartamentoNoEncontradoException | PuestoNoEncontradoException e) {
                // capturamos acá las excepciones de negocio para que el menú no se corte
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 10);

        sc.close();
    }

    private static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                crearDepartamento();
                break;
            case 2:
                listarDepartamentos();
                break;
            case 3:
                crearPuesto();
                break;
            case 4:
                listarPuestos();
                break;
            case 5:
                registrarEmpleado();
                break;
            case 6:
                listarEmpleados();
                break;
            case 7:
                buscarEmpleado();
                break;
            case 8:
                modificarEmpleado();
                break;
            case 9:
                darDeBajaEmpleado();
                break;
            case 10:
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== Sistema de Gestión RRHH PyME ===");
        System.out.println("1. Crear departamento");
        System.out.println("2. Listar departamentos");
        System.out.println("3. Crear puesto");
        System.out.println("4. Listar puestos");
        System.out.println("5. Registrar empleado");
        System.out.println("6. Listar empleados");
        System.out.println("7. Buscar empleado por id");
        System.out.println("8. Modificar empleado");
        System.out.println("9. Dar de baja empleado");
        System.out.println("10. Salir");
    }

    private static void crearDepartamento() {
        String nombre = Validador.leerTexto(sc, "Nombre del departamento:");
        String descripcion = Validador.leerTexto(sc, "Descripción:");
        Departamento departamento = departamentoService.agregar(nombre, descripcion);
        System.out.println("Departamento creado -> " + departamento);
    }

    private static void listarDepartamentos() {
        if (departamentoService.listar().isEmpty()) {
            System.out.println("No hay departamentos cargados.");
            return;
        }
        for (Departamento departamento : departamentoService.listar()) {
            System.out.println(departamento);
        }
    }

    private static void crearPuesto() {
        listarDepartamentos();
        int idDepartamento = Validador.leerEntero(sc, "Id del departamento al que pertenece el puesto:");
        Departamento departamento = departamentoService.buscarPorId(idDepartamento);

        String nombre = Validador.leerTexto(sc, "Nombre del puesto:");
        String descripcion = Validador.leerTexto(sc, "Descripción:");
        String nivel = Validador.leerTexto(sc, "Nivel (Junior/Semi-Senior/Senior):");

        Puesto puesto = puestoService.agregar(nombre, descripcion, nivel, departamento);
        System.out.println("Puesto creado -> " + puesto);
    }

    private static void listarPuestos() {
        if (puestoService.listar().isEmpty()) {
            System.out.println("No hay puestos cargados.");
            return;
        }
        for (Puesto puesto : puestoService.listar()) {
            System.out.println(puesto);
        }
    }

    private static void registrarEmpleado() {
        listarDepartamentos();
        int idDepartamento = Validador.leerEntero(sc, "Id del departamento del empleado:");
        Departamento departamento = departamentoService.buscarPorId(idDepartamento);

        listarPuestos();
        int idPuesto = Validador.leerEntero(sc, "Id del puesto del empleado:");
        Puesto puesto = puestoService.buscarPorId(idPuesto);

        String nombre = Validador.leerTexto(sc, "Nombre:");
        String apellido = Validador.leerTexto(sc, "Apellido:");
        String dni = Validador.leerTexto(sc, "DNI:");
        String email = Validador.leerTexto(sc, "Email:");
        String telefono = Validador.leerTexto(sc, "Teléfono:");
        LocalDate fechaNacimiento = Validador.leerFecha(sc, "Fecha de nacimiento");
        LocalDate fechaIngreso = Validador.leerFecha(sc, "Fecha de ingreso");

        Empleado empleado = empleadoService.agregar(nombre, apellido, dni, email, telefono,
                fechaNacimiento, fechaIngreso, departamento, puesto);
        System.out.println("Empleado registrado -> " + empleado);
    }

    private static void listarEmpleados() {
        if (empleadoService.listar().isEmpty()) {
            System.out.println("No hay empleados cargados.");
            return;
        }
        for (Empleado empleado : empleadoService.listar()) {
            System.out.println(empleado);
        }
    }

    private static void buscarEmpleado() {
        int id = Validador.leerEntero(sc, "Id del empleado:");
        Empleado empleado = empleadoService.buscarPorId(id);
        System.out.println(empleado);
    }

    private static void modificarEmpleado() {
        int id = Validador.leerEntero(sc, "Id del empleado a modificar:");

        listarDepartamentos();
        int idDepartamento = Validador.leerEntero(sc, "Id del nuevo departamento:");
        Departamento departamento = departamentoService.buscarPorId(idDepartamento);

        listarPuestos();
        int idPuesto = Validador.leerEntero(sc, "Id del nuevo puesto:");
        Puesto puesto = puestoService.buscarPorId(idPuesto);

        String nombre = Validador.leerTexto(sc, "Nuevo nombre:");
        String apellido = Validador.leerTexto(sc, "Nuevo apellido:");
        String email = Validador.leerTexto(sc, "Nuevo email:");
        String telefono = Validador.leerTexto(sc, "Nuevo teléfono:");

        empleadoService.modificar(id, nombre, apellido, email, telefono, departamento, puesto);
        System.out.println("Empleado modificado correctamente.");
    }

    private static void darDeBajaEmpleado() {
        int id = Validador.leerEntero(sc, "Id del empleado a dar de baja:");
        empleadoService.darDeBaja(id);
        System.out.println("Empleado dado de baja correctamente.");
    }
}
