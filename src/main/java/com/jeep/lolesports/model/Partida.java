package com.jeep.lolesports.model;

import com.jeep.lolesports.model.matches_data.ParticipantsStatsPar;
import com.jeep.lolesports.model.matches_data.TeamPar;
import com.jeep.lolesports.model.static_riot.Champion;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
public class Partida {
    @Id
    private long id;

    private long matchId;
    private long gameDuration;
    private int mapId;
    private String gameMode;
    private String gameType;

    private boolean matchWon;

    /* Champion data */
    private int championPlayedId;
    @Transient
    private Champion championPlayed;
    @Transient
    private ParticipantsStatsPar playerStats;


    @OneToMany(mappedBy = "partida",
            cascade = CascadeType.ALL/*,
            fetch = FetchType.EAGER*/)
    private List<TeamPar> teams;

    @OneToMany(mappedBy = "partida",
            cascade = CascadeType.ALL)
    private List<ParticipantsStatsPar> participantsStats;

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

    public List<TeamPar> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamPar> teams) {
        this.teams = teams;
    }

    public List<ParticipantsStatsPar> getParticipantsStats() {
        return participantsStats;
    }

    public void setParticipantsStats(List<ParticipantsStatsPar> participantsStats) {
        this.participantsStats = participantsStats;
    }

    public boolean isMatchWon() {
        return matchWon;
    }

    public void setMatchWon(boolean matchWon) {
        this.matchWon = matchWon;
    }

    public int getChampionPlayedId() {
        return championPlayedId;
    }

    public void setChampionPlayedId(int championPlayedId) {
        this.championPlayedId = championPlayedId;
    }

    public Champion getChampionPlayed() {
        return championPlayed;
    }

    public void setChampionPlayed(Champion championPlayed) {
        this.championPlayed = championPlayed;
    }

    public ParticipantsStatsPar getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(ParticipantsStatsPar playerStats) {
        this.playerStats = playerStats;
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
