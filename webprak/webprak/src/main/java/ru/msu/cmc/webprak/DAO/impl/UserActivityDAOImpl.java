package ru.msu.cmc.webprak.DAO.impl;

import ru.msu.cmc.webprak.DAO.UserActivityDAO;
import ru.msu.cmc.webprak.models.UserActivity;
import org.springframework.stereotype.Repository;

@Repository
public class UserActivityDAOImpl extends CommonDAOImpl<UserActivity, String> implements UserActivityDAO {
    public UserActivityDAOImpl() {
        super(UserActivity.class);
    }
}