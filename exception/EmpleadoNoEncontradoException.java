package exception;

/* Excepción personalizada que se lanza cuando se busca un empleado por su id y no existe en el sistema

    hereda de RuntimeException (excepciones no chequeadas): no obliga a quien usa el método a envolver la llamada en try/catch, pero sí permite capturarla cuando nos interesa.
 */
public class EmpleadoNoEncontradoException extends RuntimeException {
    public EmpleadoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
