INSERT INTO abc.accountinfo(
            accountId, email, firstname, sirname, isdeleted, login, password)
    VALUES (nextval('abc.user_id_seq'), 'root@email.em', '', 'root', false, 'root', 'jXiDhUMSc9Eei0O7ePOqQQ==');


INSERT INTO abc.user_role(accountinfo_accountid, role)
    SELECT accountid , 'TEACHER' FROM abc.accountinfo WHERE login LIKE 'root';

INSERT INTO abc.user_role(accountinfo_accountid, role)
    SELECT accountid , 'ADMINISTRATOR' FROM abc.accountinfo WHERE login LIKE 'root';

INSERT INTO abc.teacher(id, account_info)
    SELECT nextval('abc.teacher_id_seq'), accountid FROM abc.accountinfo WHERE login LIKE 'root';

INSERT INTO abc.administrator(id, account_info)
    SELECT nextval('abc.admin_id_seq'), accountid FROM abc.accountinfo WHERE login LIKE 'root';