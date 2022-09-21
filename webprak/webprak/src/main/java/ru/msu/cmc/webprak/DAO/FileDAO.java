package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.File;


public interface FileDAO extends CommonDAO<File, Long> {

    public boolean addFile(String name_file, String content);

    public Long getMaxId();
}