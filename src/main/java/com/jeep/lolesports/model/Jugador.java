package com.jeep.lolesports.model;

public class Jugador {
    private int id;
    private String nombreInvocador;
    private int nivel;

    public Jugador(int id, String nombreInvocador, int nivel) {
        this.id = id;
        this.nombreInvocador = nombreInvocador;
        this.nivel = nivel;
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
}
