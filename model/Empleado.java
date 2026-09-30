package model;

import java.time.LocalDate;

/**
 * Modelo de dominio: representa un empleado de la empresa.
 *
 * El estado por defecto de un empleado nuevo es ACTIVO; la baja se
 * gestiona de forma lógica cambiando el estado a INACTIVO (ver EmpleadoService).
 */
public class Empleado {

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private LocalDate fechaIngreso;
    private LocalDate fechaEgreso;
    private String estado;
    private Departamento departamento;
    private Puesto puesto;

    // Constructor sin id: el id lo asigna el EmpleadoService al guardar.
    public Empleado(String nombre, String apellido, String dni, String email, String telefono,
            LocalDate fechaNacimiento, LocalDate fechaIngreso, Departamento departamento, Puesto puesto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaIngreso = fechaIngreso;
        this.departamento = departamento;
        this.puesto = puesto;
        this.estado = "ACTIVO";
    }

    public Empleado() {
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(LocalDate fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | " + nombre + " " + apellido +
                " | DNI: " + dni +
                " | Estado: " + estado +
                " | Departamento: " + (departamento != null ? departamento.getNombre() : "-") +
                " | Puesto: " + (puesto != null ? puesto.getNombre() : "-");
    }
}
