package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.User;


public interface UserDAO extends CommonDAO<User, Long> {

    public boolean auth(String login, String password);

    public User getByLogin(String login);

    public boolean register(String login, String password, String rights, Long date);

    public boolean ban(String login);
}