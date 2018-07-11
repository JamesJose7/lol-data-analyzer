package com.jeep.lolesports.service;

import com.jeep.lolesports.dao.IntegranteHistoryDao;
import com.jeep.lolesports.model.IntegranteHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntegranteHistoryServiceImpl implements IntegranteHistoryService {
    @Autowired
    private IntegranteHistoryDao integranteHistoryDao;

    @Override
    public List<IntegranteHistory> findAll() {
        return integranteHistoryDao.findAll();
    }

    @Override
    public IntegranteHistory findById(int id) {
        return integranteHistoryDao.findById(id);
    }

    @Override
    public IntegranteHistory findByAccountId(int id) {
        return null;
    }

    @Override
    public void save(IntegranteHistory integrante) {
        integranteHistoryDao.save(integrante);
    }

    @Override
    public void delete(IntegranteHistory integrante) {
        integranteHistoryDao.delete(integrante);
    }
}
