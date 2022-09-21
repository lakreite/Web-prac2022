package ru.msu.cmc.webprak.DAO.impl;

import ru.msu.cmc.webprak.models.File;
import ru.msu.cmc.webprak.models.Message;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.ThemeDAO;
import ru.msu.cmc.webprak.models.Theme;
import ru.msu.cmc.webprak.models.UserActivity;

import java.util.List;

@Repository
public class ThemeDAOImpl extends CommonDAOImpl<Theme, Long> implements ThemeDAO {
    public ThemeDAOImpl() {
        super(Theme.class);
    }

    @Override
    public List<Theme> getListByChapter(String chapter) {
        if (chapter == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String queryString = "SELECT NEW Theme(t.id, t.chapter, t.name, t.date) FROM Theme t " +
                    "JOIN Chapter c ON c = t.chapter" +
                    " WHERE c.name = ";
            queryString += "\'" + chapter + "\'";

            Query<Theme> query = session.createQuery(queryString, Theme.class);
            session.getTransaction().commit();
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<Message> getMessagesByTheme(Theme theme) {
        if (theme == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String queryString = "FROM Message WHERE id_theme = ";
            queryString +=  theme.getId().toString();

            Query<Message> query = session.createQuery(queryString, Message.class);
            session.getTransaction().commit();
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<File> getFilesByTheme(Theme theme) {
        if (theme == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String queryString = "SELECT NEW File(f.id, f.name, f.content) FROM Message m " +
            "JOIN MessageFile mf ON mf.message = m " +
            "JOIN File f ON mf.file = f " +
            "WHERE m.theme.id = " + theme.getId().toString();
            Query<File> query = session.createQuery(queryString, File.class);
            session.getTransaction().commit();
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public List<UserActivity> getUsersActivity(Theme theme, Long from, Long to) {
        if (from > to || theme == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String queryString = "SELECT NEW ru.msu.cmc.webprak.models.UserActivity(u.login, u.password, u.rights, u.date, CAST(count(*) as java.lang.Long)) " +
                    "FROM Theme t " +
                    "JOIN Message m ON t = m.theme " +
                    "JOIN User u ON m.user = u " +
                    "WHERE t.id = " + theme.getId().toString() +
                    " AND m.date > " + from.toString() +
                    " AND m.date < " + to.toString() +
                    " GROUP BY u.id, u.password, u.rights, u.date";
            Query<UserActivity> query = session.createQuery(queryString, UserActivity.class);
            session.getTransaction().commit();
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }
}