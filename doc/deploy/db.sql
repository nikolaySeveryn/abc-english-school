INSERT INTO abc."user"(
            userid,email, firstname, login, password, phonenum, sirname)
    VALUES (nextval('abc.user_id_seq') ,'email@mail.ru', 'root', 'root', 'jXiDhUMSc9Eei0O7ePOqQQ==', '5678445827', '');
    
INSERT INTO abc.staff (staffid, patronomic)
VALUES ((SELECT userid FROM abc.user WHERE login LIKE 'root'), 'teacherovich');

INSERT INTO abc."user_role" (user_userid,role)
    VALUES ((SELECT userid FROM abc.user WHERE login LIKE 'root'), 'TEACHER');
INSERT INTO abc."user_role" (user_userid,role)
    VALUES ((SELECT userid FROM abc.user WHERE login LIKE 'root'), 'ADMINISTRATOR');