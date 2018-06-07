package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.Administrador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("Duplicates")
@Repository
public class AdministradorDaoImpl implements AdministradorDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Administrador administrador) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(administrador);
        session.getTransaction().commit();
        session.close();
    }
}
