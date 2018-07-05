package com.jeep.lolesports.service;

import com.jeep.lolesports.dao.PartidaDao;
import com.jeep.lolesports.model.Integrante;
import com.jeep.lolesports.model.Partida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidaServiceImpl implements PartidaService {
    @Autowired
    private PartidaDao partidaDao;

    @Override
    public List<Partida> findAll() {
        return partidaDao.findAll();
    }

    @Override
    public Partida findById(Long id) {
        return partidaDao.findById(id);
    }

    @Override
    public void save(Partida partida) {
        partidaDao.save(partida);
    }

    @Override
    public List<Partida> findPlayerMatches(Integrante integrante) {
        return partidaDao.findPlayerMatches(integrante);
    }
}
