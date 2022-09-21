package ru.msu.cmc.webprak.DAO.impl;

import ru.msu.cmc.webprak.models.File;
import ru.msu.cmc.webprak.models.Message;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.DAO.MessageFileDAO;
import ru.msu.cmc.webprak.models.MessageFile;

@Repository
public class MessageFileDAOImpl extends CommonDAOImpl<MessageFile, Long> implements MessageFileDAO {
    public MessageFileDAOImpl() {
        super(MessageFile.class);
    }

    public Long getMaxId() {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            Query<Long> query = session.createQuery(
                    "SELECT CAST(max(m.id) as java.lang.Long) FROM MessageFile m",
                    Long.class);
            session.getTransaction().commit();
            return query.getResultList().size() == 0 ? null : query.getResultList().get(0);
        }
    }

    public boolean addRelation(Message message, File file) {
        if (message == null || file == null)
            return false;
        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM MessageFile " +
                    "WHERE id_message = " + message.getId().toString() +
                    " AND id_file = " + file.getId().toString();

            Query<MessageFile> query = session.createQuery(queryString, MessageFile.class);
            if (query.getResultList().size() != 0)
                return false;

            save(new MessageFile(getMaxId() + 1, message, file));
            return true;
        }
    }
}