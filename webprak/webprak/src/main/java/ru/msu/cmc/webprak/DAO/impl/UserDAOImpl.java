package ru.msu.cmc.webprak.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.UserDAO;
import ru.msu.cmc.webprak.models.User;
import org.hibernate.query.Query;

@Repository
public class UserDAOImpl extends CommonDAOImpl<User, Long> implements UserDAO {
    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public boolean auth(String login, String password) {
        try (Session session = sessionFactory.openSession()) {
            User temp = getByLogin(login);
            if (temp == null)
                return false;

            if (temp.getPassword().trim().equals(password.trim()))
                return true;
            return false;
        }
    }

    @Override
    public User getByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "FROM User u WHERE u.login = \'" + login + "\'", User.class);
            session.getTransaction().commit();
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList().get(0);
        }
    }

    @Override
    public boolean register(String login, String password, String rights, Long date) {
        if (login == null || password == null)
            return false;
        if (login.equals("") || password.equals(""))
            return false;
        if (!rights.equals("admin") && !rights.equals("user"))
            return false;
        if (getByLogin(login) != null)
            return false;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (rights == "admin")
                session.save(new User(0L, login, password, 0L, date));
            else
                session.save(new User(0L, login, password, 1L, date));
            session.getTransaction().commit();
            return true;
        }
    }

    public boolean ban(String login) {
        User user = getByLogin(login);
        if (user == null)
            return false;
        user.setRights(2L); //0 admin, 1 user, 2 banned
        update(user);
        return true;
    }
}