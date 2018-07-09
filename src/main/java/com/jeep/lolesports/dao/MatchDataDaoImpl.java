package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.Partida;
import com.jeep.lolesports.model.matches_data.MatchData;
import com.jeep.lolesports.model.matches_data.ParticipantsStatsPar;
import com.jeep.lolesports.model.matches_data.TeamPar;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("Duplicates")
@Repository
public class MatchDataDaoImpl implements MatchDataDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public MatchData findById(long id) {
        Session session = sessionFactory.openSession();
        MatchData matchData = session.get(MatchData.class, id);
        session.close();

        return matchData;
    }

    @Override
    public void save(MatchData matchData) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(matchData);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(MatchData matchData) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(matchData);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<ParticipantsStatsPar> findParticipantsStats(Partida partida) {
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(ParticipantsStatsPar.class);
        List<ParticipantsStatsPar> stats = criteria.add(Restrictions.eq("partida", partida))
                .list();
        session.close();

        return stats;
    }

    @Override
    public List<TeamPar> findTeamStats(Partida partida) {
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(TeamPar.class);
        List<TeamPar> stats = criteria.add(Restrictions.eq("partida", partida))
                .list();
        session.close();

        return stats;
    }
}
