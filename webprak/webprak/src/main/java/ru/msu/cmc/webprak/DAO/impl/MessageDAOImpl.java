package ru.msu.cmc.webprak.DAO.impl;

import ru.msu.cmc.webprak.DAO.MessageDAO;
import ru.msu.cmc.webprak.models.File;
import ru.msu.cmc.webprak.models.Theme;
import ru.msu.cmc.webprak.models.User;
import ru.msu.cmc.webprak.models.Chapter;
import ru.msu.cmc.webprak.models.Message;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.models.Message;


import java.util.List;


@Repository
public class MessageDAOImpl extends CommonDAOImpl<Message, Long> implements MessageDAO {
    private MessageFileDAOImpl mfd;


    public MessageDAOImpl() {
        super(Message.class);
    }

    @Override
    public List<File> getFiles(Message message) {
        if (message == null)
            return null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String queryString = "SELECT NEW File(f.id, " +
                                        "f.name, " +
                                        "f.content) " +
                                        "FROM Message m " +
                    "JOIN MessageFile mf ON mf.message = m " +
                    "JOIN File f ON mf.file = f " +
                    "WHERE m.id = " + message.getId().toString();
            Query<File> query = session.createQuery(queryString, File.class);
            session.getTransaction().commit();
            return query.getResultList().size() == 0 ? null : query.getResultList();
        }
    }

    @Override
    public Long getMaxId() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Long> query = session.createQuery(
                    "SELECT cast(max(id_message) as java.lang.Long) FROM Message",
                    Long.class);
            session.getTransaction().commit();
            return query.getResultList().size() == 0 ? null : query.getResultList().get(0);
        }
    }

    @Override
    public boolean addMessage(Chapter chapter, Theme theme, User user, List<File> files, String content, Long date) {
        if (theme == null || user == null || content == null || content.isEmpty())
            return false;
        try (Session session = sessionFactory.openSession()) {

            save(new Message(theme, chapter, user, getMaxId() + 1, content, date));
            Message message = getById(getMaxId());
            if (files != null)
                for (int i = 0; i < files.size(); ++i) {
                    mfd.addRelation(message, files.get(i));
                }
            return true;
        }
    }
}