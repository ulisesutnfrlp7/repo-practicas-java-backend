package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
    Clase con métodos de validación reutilizables.
    Todos los métodos son estáticos: no necesitamos crear una instancia de Validador para usarlos. Se invocan directamente.
*/

public class Validador {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Validaciones de datos del empleado
    // Estos métodos lanzan una excepción si el dato es inválido,
    // no retornan nada: si terminan sin lanzar la excepción el dato es válido.

    public static void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
    }

    public static void validarApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
    }

    public static void validarDni(String dni) {
        // solo dígitos, entre 7 y 8 caracteres (DNI argentino)
        if (dni == null || !dni.matches("\\d{7,8}")) {
            throw new IllegalArgumentException("El DNI debe contener entre 7 y 8 dígitos numéricos.");
        }
    }

    public static void validarEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("El email no es válido.");
        }
    }

    public static void validarTelefono(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }
    }

    public static void validarEstado(String estado) {
        // único lugar donde se conocen los estados válidos de un empleado
        if (!"ACTIVO".equals(estado) && !"LICENCIA".equals(estado)
                && !"SUSPENDIDO".equals(estado) && !"INACTIVO".equals(estado)) {
            throw new IllegalArgumentException("El estado debe ser ACTIVO, LICENCIA, SUSPENDIDO o INACTIVO.");
        }
    }

    // Lectura por consola

    public static int leerEntero(Scanner sc, String mensaje) {
        // bucle infinito que se rompe cuando el usuario ingresa un entero válido.
        while (true) {
            System.out.println(mensaje);
            try {
                int valor = sc.nextInt();
                sc.nextLine(); // limpia el salto de línea pendiente
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número entero. Intente nuevamente.");
                sc.nextLine(); // limpia el salto de línea pendiente
            }
        }
    }

    public static double leerDouble(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            try {
                double valor = sc.nextDouble();
                sc.nextLine();
                return valor;
            } catch (Exception e) {
                System.out.println("Debe ingresar un número decimal (coma o punto).");
                sc.nextLine();
            }
        }
    }

    public static String leerTexto(Scanner sc, String mensaje) {
        // lectura simple de texto
        System.out.println(mensaje);
        return sc.nextLine();
    }

    public static LocalDate leerFecha(Scanner sc, String mensaje) {
        // bucle infinito que se rompe cuando el usuario ingresa una fecha válida.
        while (true) {
            System.out.println(mensaje + " (formato dd/MM/yyyy):");
            String texto = sc.nextLine();
            try {
                return LocalDate.parse(texto, FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Intente nuevamente.");
            }
        }
    }

}
