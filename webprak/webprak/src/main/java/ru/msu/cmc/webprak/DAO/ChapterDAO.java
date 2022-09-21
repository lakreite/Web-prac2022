package ru.msu.cmc.webprak.DAO;

import java.util.List;

import ru.msu.cmc.webprak.models.Chapter;
import ru.msu.cmc.webprak.models.Theme;

public interface ChapterDAO extends CommonDAO<Chapter, Long> {
    public Chapter getByName(String name);

    public List<Theme> getThemes(Chapter chapter);

    public boolean addChapter(String name_chapter, Long date_chapter);
}