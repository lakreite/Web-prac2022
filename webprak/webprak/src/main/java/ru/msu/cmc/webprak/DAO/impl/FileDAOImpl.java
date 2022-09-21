package ru.msu.cmc.webprak.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.FileDAO;
import ru.msu.cmc.webprak.models.File;

@Repository
public class FileDAOImpl extends CommonDAOImpl<File, Long> implements FileDAO {
    public FileDAOImpl() {
        super(File.class);
    }

    public Long getMaxId() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Long> query = session.createQuery(
                    "SELECT cast(max(id_file) as java.lang.Long) FROM File",
                    Long.class);
            session.getTransaction().commit();
            return query.getResultList().size() == 0 ? null : query.getResultList().get(0);
        }
    }

    public boolean addFile(String name_file, String content) {
        if (name_file == null || name_file.isEmpty() || content == null || content.length() == 0)
            return false;
        try (Session session = sessionFactory.openSession()) {
            Long newID = getMaxId() + 1;
            save(new File(newID, name_file, content));
            return true;
        }
    }
}