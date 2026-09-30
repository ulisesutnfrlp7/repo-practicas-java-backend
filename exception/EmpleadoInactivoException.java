package exception;

/* Excepción personalizada que se lanza cuando se intenta modificar o volver a dar de baja
   a un empleado cuyo estado ya es INACTIVO.
 */
public class EmpleadoInactivoException extends RuntimeException {
    public EmpleadoInactivoException(String mensaje) {
        super(mensaje);
    }
}
