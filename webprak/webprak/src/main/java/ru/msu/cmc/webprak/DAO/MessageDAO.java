package ru.msu.cmc.webprak.DAO;

import java.util.List;

import ru.msu.cmc.webprak.models.Message;
import ru.msu.cmc.webprak.models.File;
import ru.msu.cmc.webprak.models.Theme;
import ru.msu.cmc.webprak.models.User;
import ru.msu.cmc.webprak.models.Chapter;

public interface MessageDAO extends CommonDAO<Message, Long> {
    public List<File> getFiles(Message message);


    public Long getMaxId();
    public boolean addMessage(Chapter chapter, Theme theme, User user, List<File> files, String message, Long date);
}