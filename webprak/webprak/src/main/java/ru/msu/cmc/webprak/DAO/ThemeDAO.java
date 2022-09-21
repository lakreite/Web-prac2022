package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Theme;
import ru.msu.cmc.webprak.models.Message;
import ru.msu.cmc.webprak.models.File;
import ru.msu.cmc.webprak.models.UserActivity;

import java.util.List;


public interface ThemeDAO extends CommonDAO<Theme, Long> {

    public List<Theme> getListByChapter(String chapter);

    public List<Message> getMessagesByTheme(Theme theme);

    public List<File> getFilesByTheme(Theme theme);

    public List<UserActivity> getUsersActivity(Theme theme, Long from, Long to);
}