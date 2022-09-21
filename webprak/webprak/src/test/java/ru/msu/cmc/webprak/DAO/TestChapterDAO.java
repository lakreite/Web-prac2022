package ru.msu.cmc.webprak.DAO;

import java.util.List;
import java.util.Collection;
import ru.msu.cmc.webprak.DAO.impl.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprak.models.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class TestChapterDAO {
    @Autowired
    private ChapterDAO chapterDAO = new ChapterDAOImpl();
    @Autowired
    private FileDAO fileDAO = new FileDAOImpl();
    @Autowired
    private MessageDAO messageDAO = new MessageDAOImpl();

    @Autowired
    private ThemeDAO themeDAO = new ThemeDAOImpl();

    @Autowired
    private UserDAO userDAO = new UserDAOImpl();

    @Autowired
    private MessageFileDAO messageFileDAO = new MessageFileDAOImpl();

    @Autowired
    private SessionFactory sessionFactory;


    @Test
    void testChapter() {
        Chapter chapter = chapterDAO.getByName("chapter1");
        assertEquals(chapter.getName(), "chapter1");
        assertEquals(chapter.getDate(), 2017);

        List<Theme> lT = chapterDAO.getThemes(chapter);
        assertEquals(lT.get(0).getId(), 1L);
        assertEquals(lT.get(0).getName(), "theme1");
        assertEquals(lT.get(0).getDate(), 2017L);

        assertEquals(lT.get(1).getId(), 2L);
        assertEquals(lT.get(1).getName(), "theme2");
        assertEquals(lT.get(1).getDate(), 2018L);

        assertEquals(lT.get(2).getId(), 3L);
        assertEquals(lT.get(2).getName(), "theme3");
        assertEquals(lT.get(2).getDate(), 2019L);

        assert(chapterDAO.addChapter("chapter_aboba", 2017L));
        chapter = chapterDAO.getByName("chapter_aboba");
        assertEquals(chapter.getName(), "chapter_aboba");
        assertEquals(chapter.getDate(), 2017L);
    }

    @Test
    void testFile() {
        assertEquals(5L, fileDAO.getMaxId());
        assert(fileDAO.addFile("file_aboba", "aboba"));
        assertEquals(6L, fileDAO.getMaxId());
        File file = fileDAO.getById(6L);
        assertEquals(file.getId(), 6L);
        assertEquals(file.getName(), "file_aboba");
        assertEquals(file.getContent(), "aboba");
    }

    @Test
    void testMessage() {
        List<File> files = messageDAO.getFiles(messageDAO.getById(1L));
        assertEquals(files.get(0).getId(), 1L);
        assertEquals(files.get(0).getName(), "file1");
        assertEquals(files.get(0).getContent(), "content1");

        assertEquals(files.get(1).getId(), 2L);
        assertEquals(files.get(1).getName(), "file2");
        assertEquals(files.get(1).getContent(), "content2");

        assertEquals(messageDAO.getMaxId(), 6);

        Theme theme = themeDAO.getById(1L);


        assert(messageDAO.addMessage(
                theme.getChapter(),
                themeDAO.getById(1L),
                userDAO.getByLogin("aboba1"),
                null,
                "abobamessage",
                2022L));
        Collection<Message> lm = messageDAO.getAll();
        Message message = messageDAO.getById(7L);
        assertEquals(message.getDate(), 2022L);
        assertEquals(message.getContent(), "abobamessage");
    }

    @Test
    void testMessageFile() {
        assertEquals(messageFileDAO.getMaxId(), 5L);
        assert(messageFileDAO.addRelation(messageDAO.getById(1L), fileDAO.getById(5L)));
        assertEquals(messageFileDAO.getMaxId(), 6L);
        MessageFile me = messageFileDAO.getById(6L);
        assertEquals(me.getFile().getId(), fileDAO.getById(5L).getId());
        assertEquals(me.getFile().getName(), fileDAO.getById(5L).getName());
        assertEquals(me.getFile().getContent(), fileDAO.getById(5L).getContent());
        assertEquals(me.getMessage().getId(), messageDAO.getById(1L).getId());
    }

    @Test
    void testTheme() {
        List<Theme> lt = themeDAO.getListByChapter("chapter1");

        assertEquals(lt.get(0).getId(), 1L);
        assertEquals(lt.get(0).getName(), "theme1");
        assertEquals(lt.get(0).getDate(), 2017L);

        assertEquals(lt.get(1).getId(), 2L);
        assertEquals(lt.get(1).getName(), "theme2");
        assertEquals(lt.get(1).getDate(), 2018L);

        assertEquals(lt.get(2).getId(), 3L);
        assertEquals(lt.get(2).getName(), "theme3");
        assertEquals(lt.get(2).getDate(), 2019L);

        List<Message> lm = themeDAO.getMessagesByTheme(lt.get(0));

        assertEquals(lm.get(0).getId(), 1L);
        assertEquals(lm.get(0).getContent(), "message1");
        assertEquals(lm.get(0).getDate(), 2017L);

        assertEquals(lm.get(1).getId(), 2L);
        assertEquals(lm.get(1).getContent(), "message2");
        assertEquals(lm.get(1).getDate(), 2018L);

        assertEquals(lm.get(2).getId(), 3L);
        assertEquals(lm.get(2).getContent(), "message3");
        assertEquals(lm.get(2).getDate(), 2019L);

        assertEquals(lm.get(3).getId(), 4L);
        assertEquals(lm.get(3).getContent(), "message1");
        assertEquals(lm.get(3).getDate(), 2018L);

        assertEquals(lm.get(4).getId(), 5L);
        assertEquals(lm.get(4).getContent(), "message2");
        assertEquals(lm.get(4).getDate(), 2019L);

        assertEquals(lm.get(5).getId(), 6L);
        assertEquals(lm.get(5).getContent(), "message1");
        assertEquals(lm.get(5).getDate(), 2019L);

        List<File> lf = themeDAO.getFilesByTheme(themeDAO.getById(1L));


        assertEquals(lf.get(0).getId(), 1L);
        assertEquals(lf.get(0).getName(), "file1");
        assertEquals(lf.get(0).getContent(), "content1");

        assertEquals(lf.get(1).getId(), 2L);
        assertEquals(lf.get(1).getName(), "file2");
        assertEquals(lf.get(1).getContent(), "content2");

        assertEquals(lf.get(2).getId(), 3L);
        assertEquals(lf.get(2).getName(), "file3");
        assertEquals(lf.get(2).getContent(), "content3");

        assertEquals(lf.get(3).getId(), 4L);
        assertEquals(lf.get(3).getName(), "file4");
        assertEquals(lf.get(3).getContent(), "content4");

        assertEquals(lf.get(4).getId(), 5L);
        assertEquals(lf.get(4).getName(), "file5");
        assertEquals(lf.get(4).getContent(), "content5");

        List<UserActivity> lua = themeDAO.getUsersActivity(themeDAO.getById(1L), 0L, 2020L);

        assertEquals(lua.get(0).getId(), "aboba1");
        assertEquals(lua.get(0).getPassword(), "password");
        assertEquals(lua.get(0).getRights(), 0L);
        assertEquals(lua.get(0).getDate(), 2016L);
        assertEquals(lua.get(0).getActivity(), 3L);

        assertEquals(lua.get(1).getId(), "aboba2");
        assertEquals(lua.get(1).getPassword(), "password");
        assertEquals(lua.get(1).getRights(), 0L);
        assertEquals(lua.get(1).getDate(), 2017L);
        assertEquals(lua.get(1).getActivity(), 2L);

        assertEquals(lua.get(2).getId(), "aboba3");
        assertEquals(lua.get(2).getPassword(), "password");
        assertEquals(lua.get(2).getRights(), 0L);
        assertEquals(lua.get(2).getDate(), 2018L);
        assertEquals(lua.get(2).getActivity(), 1L);
    }

    @Test
    void testUser() {
        assert(userDAO.auth("aboba1", "password"));
        assert(!userDAO.auth("aboba", "aboba"));

        User usr = userDAO.getByLogin("aboba1");

        assertEquals(usr.getLogin(), "aboba1");
        assertEquals(usr.getPassword(), "password");
        assertEquals(usr.getRights(), 0L);
        assertEquals(usr.getDate(), 2016L);

        assert(userDAO.register("banned", "password", "admin", 2016L));
        usr = userDAO.getByLogin("banned");
        assertEquals(usr.getLogin(), "banned");
        assertEquals(usr.getPassword(), "password");
        assertEquals(usr.getRights(), 0L);
        assertEquals(usr.getDate(), 2016L);

        assert(userDAO.ban("banned"));
        usr = userDAO.getByLogin("banned");
        assertEquals(usr.getLogin(), "banned");
        assertEquals(usr.getPassword(), "password");
        assertEquals(usr.getRights(), 2L);
        assertEquals(usr.getDate(), 2016L);
    }

    @BeforeEach
    void beforeEach() {
    }

    @BeforeAll
    @AfterEach
    void annihilation() {

    }

}