INSERT INTO file_ (name_file, content) VALUES
    ( 'file1', 'content1'),
    ( 'file2', 'content2'),
    ( 'file3', 'content3'),
    ( 'file4', 'content4'),
    ( 'file5', 'content5');

INSERT INTO chapter_ (name_chapter, date_chapter) VALUES
    ('chapter1', 2017),
    ('chapter2', 2018),
    ('chapter3', 2019);

INSERT INTO user_ (login, password, rights, date_user) VALUES
    ('aboba1', 'password', 0, 2016),
    ('aboba2', 'password', 0, 2017),
    ('aboba3', 'password', 0, 2018),
    ('aboba4', 'password', 1, 2016),
    ('aboba5', 'password', 2, 2017);

INSERT INTO theme_ (id_chapter, name_theme, date_theme) VALUES
    (1,  'theme1', 2017),
    (1,  'theme2', 2018),
    (1,  'theme3', 2019),

    (2,  'theme1', 2018),
    (2,  'theme2', 2019),

    (3,  'theme1', 2019);

INSERT INTO message_ (id_theme, id_chapter, id_user, content_message, date_message) VALUES
    (1, 1, 1,  'message1', 2017),
    (1, 1, 1,  'message2', 2018),
    (1, 1, 1,  'message3', 2019),
    (1, 1, 2,  'message1', 2018),
    (1, 1, 2,  'message2', 2019),
    (1, 1, 3,  'message1', 2019);

INSERT INTO message_file_ (id_message, id_file) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 5);

