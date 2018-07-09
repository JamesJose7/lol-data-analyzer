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

    @Column
    @NotNull
    private String genero;
    @Column
    @NotNull
    private String fechaCumple;
    @Column
    @NotNull
    private String celular;
    @Column
    @NotNull
    private String rolPrimario;
    @Column
    @NotNull
    private String rolSecundario;






    public Integrante(Jugador jugador, String cedula, String nombreIntegrante, int ciclo, String carrera, String email,String genero ,String fechaCumple, String celular, String rolPrimario, String rolSecundario) {
        super(jugador.getId(), jugador.getNombreInvocador(), jugador.getNivel(), jugador.getAccountId(), jugador.getTipoColaRankedSolo(),
                jugador.getVictoriasRankedSolo(), jugador.getDerrotasRankedSolo(), jugador.getNivelRankedSolo(),
                jugador.getRangoRankedSolo(), jugador.getNombreLigaRankedSolo(), jugador.getPuntosRankedSolo(),
                jugador.getTipoColaRankedFlex(), jugador.getVictoriasRankedFlex(), jugador.getDerrotasRankedFlex(),
                jugador.getNivelRankedFlex(), jugador.getRangoRankedFlex(), jugador.getNombreLigaRankedFlex(),
                jugador.getPuntosRankedFlex());
        this.cedula = cedula;
        this.nombreIntegrante = nombreIntegrante;
        this.ciclo = ciclo;
        this.carrera = carrera;
        this.email = email;
        this.genero = genero;
        this.fechaCumple = fechaCumple;
        this.celular = celular;
        this.rolPrimario = rolPrimario;
        this.rolSecundario = rolSecundario;
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
    public String getGenero() {
        return genero; }

    public String getFechaCumple() {
        return fechaCumple; }

    public String getCelular() {
        return celular; }

    public String getRolPrimario() {
        return rolPrimario; }

    public String getRolSecundario() {
        return rolSecundario; }

    public void setGenero(String genero) {
        this.genero = genero; }

    public void setFechaCumple(String fechaCumple) {
        this.fechaCumple = fechaCumple; }

    public void setCelular(String celular) {
        this.celular = celular; }

    public void setRolPrimario(String rolPrimario) {
        this.rolPrimario = rolPrimario;
    }

    public void setRolSecundario(String rolSecundario) {
        this.rolSecundario = rolSecundario;
    }

}
