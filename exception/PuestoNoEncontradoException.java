package exception;

/* Excepción personalizada que se lanza cuando se busca un puesto por su id y no existe en el sistema.
 */
public class PuestoNoEncontradoException extends RuntimeException {
    public PuestoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
