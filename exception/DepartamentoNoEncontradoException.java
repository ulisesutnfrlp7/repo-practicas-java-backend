package exception;

/* Excepción personalizada que se lanza cuando se busca un departamento por su id y no existe en el sistema.
 */
public class DepartamentoNoEncontradoException extends RuntimeException {
    public DepartamentoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
