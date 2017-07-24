package com.jeep.lolesports.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Integrante extends Jugador {
    @Column
    private String cedula;
    @Column
    private String nombreIntegrante;
    @Column
    private int ciclo;
    @Column
    private String carrera;
    @Column
    private String email;

    public Integrante(int id, String nombreInvocador, int nivel, String tipoColaRanked,
                      int victoriasRanked, int derrotasRanked, String nivelRanked,
                      String rangoRanked, String nombreLigaRanked, int puntosRanked, String cedula,
                      String nombreIntegrante, int ciclo, String carrera, String email) {
        super(id, nombreInvocador, nivel, tipoColaRanked, victoriasRanked, derrotasRanked, nivelRanked, rangoRanked, nombreLigaRanked, puntosRanked);
        this.cedula = cedula;
        this.nombreIntegrante = nombreIntegrante;
        this.ciclo = ciclo;
        this.carrera = carrera;
        this.email = email;
    }

    public Integrante(Jugador jugador, String cedula, String nombreIntegrante, int ciclo, String carrera, String email) {
        super(jugador.getId(), jugador.getNombreInvocador(), jugador.getNivel(), jugador.getTipoColaRanked(), jugador.getVictoriasRanked(),
                jugador.getDerrotasRanked(), jugador.getNivelRanked(), jugador.getRangoRanked(), jugador.getNombreLigaRanked(), jugador.getPuntosRanked());
        this.cedula = cedula;
        this.nombreIntegrante = nombreIntegrante;
        this.ciclo = ciclo;
        this.carrera = carrera;
        this.email = email;
    }

    public Integrante() {}

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombreIntegrante() {
        return nombreIntegrante;
    }

    public void setNombreIntegrante(String nombreIntegrante) {
        this.nombreIntegrante = nombreIntegrante;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
