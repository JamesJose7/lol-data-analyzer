package com.jeep.lolesports.service;

import com.jeep.lolesports.dao.IntegranteDao;
import com.jeep.lolesports.model.Integrante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntegranteServiceImpl implements IntegranteService {
    @Autowired
    private IntegranteDao integranteDao;

    @Override
    public List<Integrante> findAll() {
        return integranteDao.findAll();
    }

    @Override
    public Integrante findById(Long id) {
        return integranteDao.findById(id);
    }

    @Override
    public void save(Integrante integrante) {
        integranteDao.save(integrante);
    }

    @Override
    public void delete(Integrante integrante) {
        integranteDao.delete(integrante);
    }
}
