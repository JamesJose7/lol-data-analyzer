package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.IntegranteHistory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IntegranteHistoryDaoImpl implements IntegranteHistoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<IntegranteHistory> findAll() {
        Session session = sessionFactory.openSession();
        List<IntegranteHistory> integrantes = session.createCriteria(IntegranteHistory.class).list();
        session.close();
        return integrantes;
    }

    @Override
    public IntegranteHistory findById(int id) {
        Session session = sessionFactory.openSession();
        IntegranteHistory integrante = session.get(IntegranteHistory.class, id);
        session.close();
        return integrante;
    }

    @Override
    public IntegranteHistory findByAccountId(int id) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(IntegranteHistory.class);
        IntegranteHistory integrante = (IntegranteHistory) criteria.add(Restrictions.eq("accountId", id))
                .uniqueResult();
        session.close();
        return integrante;
    }

    @Override
    public void save(IntegranteHistory integrante) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(integrante);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(IntegranteHistory integrante) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(integrante);
        session.getTransaction().commit();
        session.close();
    }
}
