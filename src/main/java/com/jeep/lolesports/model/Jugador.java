package com.jeep.lolesports.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public class Jugador {
    @Id
    private int id;
    @Column
    @NotNull
    @Size(min = 1, message = "*Al menos debe tener {min} caracter")
    private String nombreInvocador;
    @Column
    private int nivel;

    //ranked info
    @Column
    private String tipoColaRanked;
    @Column
    private int victoriasRanked;
    @Column
    private int derrotasRanked;
    @Column
    private String nivelRanked;
    @Column
    private String rangoRanked;
    @Column
    private String nombreLigaRanked;
    @Column
    private int puntosRanked;

    public Jugador(int id, String nombreInvocador, int nivel) {
        this.id = id;
        this.nombreInvocador = nombreInvocador;
        this.nivel = nivel;
    }

    public Jugador(int id, String nombreInvocador, int nivel, String tipoColaRanked, int victoriasRanked,
                   int derrotasRanked, String nivelRanked, String rangoRanked, String nombreLigaRanked, int puntosRanked) {
        this.id = id;
        this.nombreInvocador = nombreInvocador;
        this.nivel = nivel;
        this.tipoColaRanked = tipoColaRanked;
        this.victoriasRanked = victoriasRanked;
        this.derrotasRanked = derrotasRanked;
        this.nivelRanked = nivelRanked;
        this.rangoRanked = rangoRanked;
        this.nombreLigaRanked = nombreLigaRanked;
        this.puntosRanked = puntosRanked;
    }

    public Jugador() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreInvocador() {
        return nombreInvocador;
    }

    public void setNombreInvocador(String nombreInvocador) {
        this.nombreInvocador = nombreInvocador;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getTipoColaRanked() {
        return tipoColaRanked;
    }

    public void setTipoColaRanked(String tipoColaRanked) {
        this.tipoColaRanked = tipoColaRanked;
    }

    public int getVictoriasRanked() {
        return victoriasRanked;
    }

    public void setVictoriasRanked(int victoriasRanked) {
        this.victoriasRanked = victoriasRanked;
    }

    public int getDerrotasRanked() {
        return derrotasRanked;
    }

    public void setDerrotasRanked(int derrotasRanked) {
        this.derrotasRanked = derrotasRanked;
    }

    public String getNivelRanked() {
        return nivelRanked;
    }

    public void setNivelRanked(String nivelRanked) {
        this.nivelRanked = nivelRanked;
    }

    public String getRangoRanked() {
        return rangoRanked;
    }

    public void setRangoRanked(String rangoRanked) {
        this.rangoRanked = rangoRanked;
    }

    public String getNombreLigaRanked() {
        return nombreLigaRanked;
    }

    public void setNombreLigaRanked(String nombreLigaRanked) {
        this.nombreLigaRanked = nombreLigaRanked;
    }

    public int getPuntosRanked() {
        return puntosRanked;
    }

    public void setPuntosRanked(int puntosRanked) {
        this.puntosRanked = puntosRanked;
    }
}
