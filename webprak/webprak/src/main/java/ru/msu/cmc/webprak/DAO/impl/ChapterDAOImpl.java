package ru.msu.cmc.webprak.DAO.impl;

import ru.msu.cmc.webprak.models.Theme;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.ChapterDAO;
import ru.msu.cmc.webprak.models.Chapter;

import java.util.List;

@Repository
public class ChapterDAOImpl extends CommonDAOImpl<Chapter, Long> implements ChapterDAO {
    public ChapterDAOImpl() {
        super(Chapter.class);
    }

    @Override
    public boolean addChapter(String name_chapter, Long date_chapter) {
        try (Session session = sessionFactory.openSession()) {
            if (name_chapter == null || date_chapter == null || name_chapter.equals(""))
                return false;
            if (getByName(name_chapter) != null)
                return false;

            save(new Chapter(0L, name_chapter, date_chapter));
            return true;
        }
    }

    @Override
    public Chapter getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            if (name == null || name.equals(""))
                return null;
            session.beginTransaction();
            Query<Chapter> query = session.createQuery(
                    "FROM Chapter c WHERE c.name = " + "\'" + name + "\'", Chapter.class);
            session.getTransaction().commit();
            if (query.getResultList().size() == 0)
                return null;
            return query.getResultList().get(0);
        }
    }

    @Override
    public List<Theme> getThemes(Chapter chapter) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String queryString = "FROM Theme WHERE id_chapter = ";
            queryString += "\'" + chapter.getId() + "\'";

            Query<Theme> query = session.createQuery(queryString, Theme.class);
            session.getTransaction().commit();
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }
}