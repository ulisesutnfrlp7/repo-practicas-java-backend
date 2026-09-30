package model;

/**
 * Modelo de dominio: representa un puesto dentro de un departamento.
 */
public class Puesto {

    private int id;
    private String nombre;
    private String descripcion;
    private String nivel;
    private Departamento departamento;
    private boolean activo;

    // Constructor sin id: el id lo asigna el PuestoService al guardar.
    public Puesto(String nombre, String descripcion, String nivel, Departamento departamento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.departamento = departamento;
        this.activo = true;
    }

    public Puesto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | " + nombre +
                " | Nivel: " + nivel +
                " | Departamento: " + (departamento != null ? departamento.getNombre() : "-") +
                " | Activo: " + activo;
    }
}
