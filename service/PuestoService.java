package service;

import java.util.ArrayList;
import java.util.List;

import exception.PuestoNoEncontradoException;
import model.Departamento;
import model.Puesto;
import util.Validador;

/*
    Guarda los puestos en memoria (se pierden al cerrar el programa).
 */
public class PuestoService {

    private List<Puesto> puestos = new ArrayList<>();
    private int siguienteId = 1;

    public Puesto agregar(String nombre, String descripcion, String nivel, Departamento departamento) {
        Validador.validarNombre(nombre);
        Puesto puesto = new Puesto(nombre, descripcion, nivel, departamento);
        puesto.setId(siguienteId++);
        puestos.add(puesto);
        return puesto;
    }

    public List<Puesto> listar() {
        return puestos;
    }

    public Puesto buscarPorId(int id) {
        for (Puesto puesto : puestos) {
            if (puesto.getId() == id) {
                return puesto;
            }
        }
        throw new PuestoNoEncontradoException("No existe un puesto con id " + id + ".");
    }
}
