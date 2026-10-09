package org.cg.system.model;

import java.sql.Timestamp;

/**
 * POJO Cita - proceso crítico / transaccional de la clínica.
 */
public class Cita {

    private int id;
    private int pacienteId;
    private int medicoId;
    private Timestamp fechaHora;
    private String estado;
    private String motivo;
    private String notas;
    private Timestamp fechaCreacion;

    // Campos de unión
    private String pacienteNombre;
    private String medicoNombre;

    public Cita() {
    }

    public Cita(int pacienteId, int medicoId, Timestamp fechaHora, String estado,
            String motivo, String notas) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.motivo = motivo;
        this.notas = notas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public String getMedicoNombre() {
        return medicoNombre;
    }

    public void setMedicoNombre(String medicoNombre) {
        this.medicoNombre = medicoNombre;
    }

    @Override
    public String toString() {
        return "Cita #" + id + " - " + (pacienteNombre != null ? pacienteNombre : pacienteId)
                + " con " + (medicoNombre != null ? medicoNombre : medicoId)
                + " [" + estado + "]";
    }
}
