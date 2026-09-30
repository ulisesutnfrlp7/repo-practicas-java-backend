package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.EmpleadoInactivoException;
import exception.EmpleadoNoEncontradoException;
import model.Departamento;
import model.Empleado;
import model.Puesto;
import util.Validador;

/*
    Guarda los empleados en memoria (se pierden al cerrar el programa).
    La baja de un empleado es lógica: cambia su estado a INACTIVO en lugar de eliminarlo,
    para conservar el historial.
 */
public class EmpleadoService {

    private List<Empleado> empleados = new ArrayList<>();
    private int siguienteId = 1;

    public Empleado agregar(String nombre, String apellido, String dni, String email, String telefono,
            LocalDate fechaNacimiento, LocalDate fechaIngreso, Departamento departamento, Puesto puesto) {
        Validador.validarNombre(nombre);
        Validador.validarApellido(apellido);
        Validador.validarDni(dni);
        Validador.validarEmail(email);
        Validador.validarTelefono(telefono);

        Empleado empleado = new Empleado(nombre, apellido, dni, email, telefono,
                fechaNacimiento, fechaIngreso, departamento, puesto);
        empleado.setId(siguienteId++);
        empleados.add(empleado);
        return empleado;
    }

    public List<Empleado> listar() {
        return empleados;
    }

    public Empleado buscarPorId(int id) {
        for (Empleado empleado : empleados) {
            if (empleado.getId() == id) {
                return empleado;
            }
        }
        throw new EmpleadoNoEncontradoException("No existe un empleado con id " + id + ".");
    }

    public void modificar(int id, String nombre, String apellido, String email, String telefono,
            Departamento departamento, Puesto puesto) {
        Empleado empleado = buscarPorId(id);
        if ("INACTIVO".equals(empleado.getEstado())) {
            throw new EmpleadoInactivoException("No se puede modificar un empleado dado de baja.");
        }

        Validador.validarNombre(nombre);
        Validador.validarApellido(apellido);
        Validador.validarEmail(email);
        Validador.validarTelefono(telefono);

        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setEmail(email);
        empleado.setTelefono(telefono);
        empleado.setDepartamento(departamento);
        empleado.setPuesto(puesto);
    }

    public void darDeBaja(int id) {
        Empleado empleado = buscarPorId(id);
        if ("INACTIVO".equals(empleado.getEstado())) {
            throw new EmpleadoInactivoException("El empleado ya se encuentra dado de baja.");
        }
        empleado.setEstado("INACTIVO");
        empleado.setFechaEgreso(LocalDate.now());
    }
}
