package model;

/**
 * Modelo de dominio: representa un departamento de la estructura organizacional.
 *
 * Igual que en el ejemplo de Producto, los atributos son privados y se accede
 * a ellos mediante getters y setters.
 */
public class Departamento {

    private int id;
    private String nombre;
    private String descripcion;
    private boolean activo;

    // Constructor sin id: el id lo asigna el DepartamentoService al guardar.
    public Departamento(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = true;
    }

    public Departamento() {
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
                " | " + descripcion +
                " | Activo: " + activo;
    }
}
