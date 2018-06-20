package com.jeep.lolesports.dao;

import com.jeep.lolesports.model.Role;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("Duplicates")
@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Role role) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(role);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Role findByName(String name) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Role.class);
        Role role = (Role) criteria.add(Restrictions.eq("name", name))
                .uniqueResult();
        session.close();
        return role;
    }
}
