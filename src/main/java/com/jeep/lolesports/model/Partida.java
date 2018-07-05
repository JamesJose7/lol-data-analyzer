package com.jeep.lolesports.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Partida {
    @Id
    private long id;

    private long matchId;
    private long gameDuration;
    private int mapId;
    private String gameMode;
    private String gameType;

    @ManyToOne/*(fetch = FetchType.EAGER)*/
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Integrante integrante;

    public Partida() {}

    public Partida(PartidaBuilder builder) {
        this.id = builder.id;
        this.matchId = builder.matchId;
        this.gameDuration = builder.gameDuration;
        this.mapId = builder.mapId;
        this.gameMode = builder.gameMode;
        this.gameType = builder.gameType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Integrante getIntegrante() {
        return integrante;
    }

    public void setIntegrante(Integrante integrante) {
        this.integrante = integrante;
    }

    public static class PartidaBuilder {
        private long id;
        public long matchId;
        private long gameDuration;
        private int mapId;
        private String gameMode;
        private String gameType;

        public PartidaBuilder(long id) {
            this.id = id;
        }

        public PartidaBuilder withMatchId(long matchId) {
            this.matchId = matchId;
            return this;
        }

        public PartidaBuilder withGameDuration(long gameDuration) {
            this.gameDuration = gameDuration;
            return this;
        }

        public PartidaBuilder withMapId(int mapId) {
            this.mapId = mapId;
            return this;
        }

        public PartidaBuilder withGameMode(String gameMode) {
            this.gameMode = gameMode;
            return this;
        }

        public PartidaBuilder withGameType(String gameType) {
            this.gameType = gameType;
            return this;
        }

        public Partida build() {
            return new Partida(this);
        }
    }
}
