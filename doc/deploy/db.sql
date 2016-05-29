INSERT INTO abc.personalInfo(id, email, firstname, sirname)
	VALUES(nextval('abc.abc.personal_info_id_seq'), 'root@email.em', '', 'root');

INSERT INTO abc.accountinfo(
            accountId, isdeleted, login, password, personal_info)
    SELECT nextval('abc.account_id_seq'), false, 'root', 'Y6nw6nu5gFB5a2SehUgYRQ==', id FROM abc.personalInfo WHERE email = 'root@email.em';


INSERT INTO abc.user_role(accountinfo_accountid, role)
    SELECT accountid , 'TEACHER' FROM abc.accountinfo WHERE login LIKE 'root';

INSERT INTO abc.user_role(accountinfo_accountid, role)
    SELECT accountid , 'ADMINISTRATOR' FROM abc.accountinfo WHERE login LIKE 'root';

INSERT INTO abc.user (id, account_info)
    SELECT nextval('abc.user_id_seq'), accountid FROM abc.accountinfo WHERE login LIKE 'root';

INSERT INTO abc.teacher(id)
    SELECT currval('abc.user_id_seq');

INSERT INTO abc.administrator(id)
    SELECT currval('abc.user_id_seq');