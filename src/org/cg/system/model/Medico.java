package org.cg.system.model;

/**
 * POJO Médico - vinculado a Usuario y Especialidad.
 */
public class Medico {

    private int id;
    private int usuarioId;
    private String colegiado;
    private int especialidadId;
    private String telefono;
    private boolean activo;

    // Campos de unión (no persistidos directamente)
    private String firstName;
    private String lastName;
    private String email;
    private String especialidadNombre;

    public Medico() {
    }

    public Medico(int usuarioId, String colegiado, int especialidadId, String telefono) {
        this.usuarioId = usuarioId;
        this.colegiado = colegiado;
        this.especialidadId = especialidadId;
        this.telefono = telefono;
        this.activo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getColegiado() {
        return colegiado;
    }

    public void setColegiado(String colegiado) {
        this.colegiado = colegiado;
    }

    public int getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(int especialidadId) {
        this.especialidadId = especialidadId;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidadNombre() {
        return especialidadNombre;
    }

    public void setEspecialidadNombre(String especialidadNombre) {
        this.especialidadNombre = especialidadNombre;
    }

    public String getNombreCompleto() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }

    @Override
    public String toString() {
        return getNombreCompleto() + " - " + (especialidadNombre != null ? especialidadNombre : colegiado);
    }
}
