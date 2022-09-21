package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.MessageFile;
import ru.msu.cmc.webprak.models.Message;
import ru.msu.cmc.webprak.models.File;


public interface MessageFileDAO extends CommonDAO<MessageFile, Long> {
    public Long getMaxId();

    public boolean addRelation(Message message, File file);
}