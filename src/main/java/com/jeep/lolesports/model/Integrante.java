package com.jeep.lolesports.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Integrante extends Jugador {
    @Column
    @NotNull
    @Size(min = 5, max = 14, message = "*Debe tener mínimo {min} caracteres y máximo {max}")
    @Pattern(regexp = "[0-9]+", message = "*Solo se aceptan caracteres numéricos")
    private String cedula;
    @Column
    @NotNull
    @Size(min = 2, message = "*Al menos debe tener {min} caracteres")
    private String nombreIntegrante;
    @Column
    @NotNull
    private int ciclo;
    @Column
    @NotNull
    private String carrera;
    @Column
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])", message = "*Ingresar un e-mail válido")
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
