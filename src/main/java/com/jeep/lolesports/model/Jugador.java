package com.jeep.lolesports.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@MappedSuperclass
public class Jugador {
    @Id
    private String id;
    @Column(nullable = true)
    private String accountId;
    @Column
    @NotNull
    @Size(min = 1, message = "*Al menos debe tener {min} caracter")
    private String nombreInvocador;
    @Column
    private int nivel;

    @OneToMany(mappedBy = "integrante",
            cascade = CascadeType.ALL)
    private List<Partida> partidas;

    //solo ranked info
    private String tipoColaRankedSolo;
    private int victoriasRankedSolo;
    private int derrotasRankedSolo;
    private String nivelRankedSolo;
    private String rangoRankedSolo;
    private String nombreLigaRankedSolo;
    private int puntosRankedSolo;

    //flex ranked info
    private String tipoColaRankedFlex;
    private int victoriasRankedFlex;
    private int derrotasRankedFlex;
    private String nivelRankedFlex;
    private String rangoRankedFlex;
    private String nombreLigaRankedFlex;
    private int puntosRankedFlex;

    public Jugador(String id, String nombreInvocador, int nivel, String accountId) {
        this.id = id;
        this.nombreInvocador = nombreInvocador;
        this.nivel = nivel;
        this.accountId = accountId;
        //Default to unranked
        tipoColaRankedSolo = "UNRANKED";
        victoriasRankedSolo = 0;
        derrotasRankedSolo = 0;
        nivelRankedSolo = "UNRANKED";
        rangoRankedSolo = "";
        nombreLigaRankedSolo = "N/A";
        puntosRankedSolo = 0;

        tipoColaRankedFlex = "UNRANKED";
        victoriasRankedFlex = 0;
        derrotasRankedFlex = 0;
        nivelRankedFlex = "UNRANKED";
        rangoRankedFlex = "";
        nombreLigaRankedFlex = "N/A";
        puntosRankedFlex = 0;
    }

    public Jugador(String id, String nombreInvocador, int nivel, String accountId, String tipoColaRankedSolo, int victoriasRankedSolo,
                   int derrotasRankedSolo, String nivelRankedSolo, String rangoRankedSolo, String nombreLigaRankedSolo,
                   int puntosRankedSolo, String tipoColaRankedFlex, int victoriasRankedFlex, int derrotasRankedFlex,
                   String nivelRankedFlex, String rangoRankedFlex, String nombreLigaRankedFlex, int puntosRankedFlex) {
        this.id = id;
        this.nombreInvocador = nombreInvocador;
        this.nivel = nivel;
        this.accountId = accountId;
        this.tipoColaRankedSolo = tipoColaRankedSolo;
        this.victoriasRankedSolo = victoriasRankedSolo;
        this.derrotasRankedSolo = derrotasRankedSolo;
        this.nivelRankedSolo = nivelRankedSolo;
        this.rangoRankedSolo = rangoRankedSolo;
        this.nombreLigaRankedSolo = nombreLigaRankedSolo;
        this.puntosRankedSolo = puntosRankedSolo;
        this.tipoColaRankedFlex = tipoColaRankedFlex;
        this.victoriasRankedFlex = victoriasRankedFlex;
        this.derrotasRankedFlex = derrotasRankedFlex;
        this.nivelRankedFlex = nivelRankedFlex;
        this.rangoRankedFlex = rangoRankedFlex;
        this.nombreLigaRankedFlex = nombreLigaRankedFlex;
        this.puntosRankedFlex = puntosRankedFlex;
    }

    public Jugador() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTipoColaRankedSolo() {
        return tipoColaRankedSolo;
    }

    public void setTipoColaRankedSolo(String tipoColaRanked) {
        this.tipoColaRankedSolo = tipoColaRanked;
    }

    public int getVictoriasRankedSolo() {
        return victoriasRankedSolo;
    }

    public void setVictoriasRankedSolo(int victoriasRanked) {
        this.victoriasRankedSolo = victoriasRanked;
    }

    public int getDerrotasRankedSolo() {
        return derrotasRankedSolo;
    }

    public void setDerrotasRankedSolo(int derrotasRanked) {
        this.derrotasRankedSolo = derrotasRanked;
    }

    public String getNivelRankedSolo() {
        return nivelRankedSolo;
    }

    public void setNivelRankedSolo(String nivelRanked) {
        this.nivelRankedSolo = nivelRanked;
    }

    public String getRangoRankedSolo() {
        return rangoRankedSolo;
    }

    public void setRangoRankedSolo(String rangoRanked) {
        this.rangoRankedSolo = rangoRanked;
    }

    public String getNombreLigaRankedSolo() {
        return nombreLigaRankedSolo;
    }

    public void setNombreLigaRankedSolo(String nombreLigaRanked) {
        this.nombreLigaRankedSolo = nombreLigaRanked;
    }

    public int getPuntosRankedSolo() {
        return puntosRankedSolo;
    }

    public void setPuntosRankedSolo(int puntosRanked) {
        this.puntosRankedSolo = puntosRanked;
    }

    public String getTipoColaRankedFlex() {
        return tipoColaRankedFlex;
    }

    public void setTipoColaRankedFlex(String tipoColaRankedFlex) {
        this.tipoColaRankedFlex = tipoColaRankedFlex;
    }

    public int getVictoriasRankedFlex() {
        return victoriasRankedFlex;
    }

    public void setVictoriasRankedFlex(int victoriasRankedFlex) {
        this.victoriasRankedFlex = victoriasRankedFlex;
    }

    public int getDerrotasRankedFlex() {
        return derrotasRankedFlex;
    }

    public void setDerrotasRankedFlex(int derrotasRankedFlex) {
        this.derrotasRankedFlex = derrotasRankedFlex;
    }

    public String getNivelRankedFlex() {
        return nivelRankedFlex;
    }

    public void setNivelRankedFlex(String nivelRankedFlex) {
        this.nivelRankedFlex = nivelRankedFlex;
    }

    public String getRangoRankedFlex() {
        return rangoRankedFlex;
    }

    public void setRangoRankedFlex(String rangoRankedFlex) {
        this.rangoRankedFlex = rangoRankedFlex;
    }

    public String getNombreLigaRankedFlex() {
        return nombreLigaRankedFlex;
    }

    public void setNombreLigaRankedFlex(String nombreLigaRankedFlex) {
        this.nombreLigaRankedFlex = nombreLigaRankedFlex;
    }

    public int getPuntosRankedFlex() {
        return puntosRankedFlex;
    }

    public void setPuntosRankedFlex(int puntosRankedFlex) {
        this.puntosRankedFlex = puntosRankedFlex;
    }
}
