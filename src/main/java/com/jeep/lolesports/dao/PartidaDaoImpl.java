package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.Partida;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("Duplicates")
@Repository
public class PartidaDaoImpl implements PartidaDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Partida> findAll() {
        Session session = sessionFactory.openSession();

        List<Partida> partidas = session.createCriteria(Partida.class).list();

        session.close();
        return partidas;
    }

    @Override
    public Partida findById(long id) {
        Session session = sessionFactory.openSession();
        Partida integrante = session.get(Partida.class, id);
        session.close();

        return integrante;
    }

    @Override
    public void save(Partida partida) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(partida);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Partida> findPlayerMatches(long id) {
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(Partida.class);
        List<Partida> partidas = criteria.add(Restrictions.eq("integrante_id", id))
                .list();
        session.close();

        return partidas;
    }
}
