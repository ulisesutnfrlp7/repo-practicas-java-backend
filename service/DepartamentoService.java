package service;

import java.util.ArrayList;
import java.util.List;

import exception.DepartamentoNoEncontradoException;
import model.Departamento;
import util.Validador;

/*
    Guarda los departamentos en memoria (se pierden al cerrar el programa).
    Cumple el mismo rol que haría un repository con base de datos más adelante en el curso.
 */
public class DepartamentoService {

    private List<Departamento> departamentos = new ArrayList<>();
    private int siguienteId = 1;

    public Departamento agregar(String nombre, String descripcion) {
        Validador.validarNombre(nombre);
        Departamento departamento = new Departamento(nombre, descripcion);
        departamento.setId(siguienteId++);
        departamentos.add(departamento);
        return departamento;
    }

    public List<Departamento> listar() {
        return departamentos;
    }

    public Departamento buscarPorId(int id) {
        for (Departamento departamento : departamentos) {
            if (departamento.getId() == id) {
                return departamento;
            }
        }
        throw new DepartamentoNoEncontradoException("No existe un departamento con id " + id + ".");
    }
}
