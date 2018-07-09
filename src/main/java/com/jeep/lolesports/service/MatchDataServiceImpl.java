package com.jeep.lolesports.service;

import com.jeep.lolesports.dao.MatchDataDao;
import com.jeep.lolesports.model.Partida;
import com.jeep.lolesports.model.matches_data.MatchData;
import com.jeep.lolesports.model.matches_data.ParticipantsStatsPar;
import com.jeep.lolesports.model.matches_data.TeamPar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchDataServiceImpl implements MatchDataService {
    @Autowired
    private MatchDataDao matchDataDao;

    @Override
    public MatchData findById(long id) {
        return matchDataDao.findById(id);
    }

    @Override
    public void save(MatchData matchData) {
        matchDataDao.save(matchData);
    }

    @Override
    public void delete(MatchData matchData) {
        matchDataDao.save(matchData);
    }

    @Override
    public List<ParticipantsStatsPar> findParticipantsStats(Partida partida) {
        return matchDataDao.findParticipantsStats(partida);
    }

    @Override
    public List<TeamPar> findTeamStats(Partida partida) {
        return matchDataDao.findTeamStats(partida);
    }
}
