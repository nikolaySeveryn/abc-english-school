--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.13
-- Dumped by pg_dump version 9.3.13
-- Started on 2016-08-04 11:37:10 EEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = abc, pg_catalog;

--
-- TOC entry 2033 (class 0 OID 17081)
-- Dependencies: 176
-- Data for Name: personalinfo; Type: TABLE DATA; Schema: abc; Owner: -
--
-- staff

INSERT INTO abc.personalinfo (id, birthday, firstname, patronomic, phonenum, sirname) VALUES (1, '1990-08-01', 'Teacher', '', '', 'Test');
INSERT INTO abc.personalinfo (id, birthday, firstname, patronomic, phonenum, sirname) VALUES (2, '1971-03-10', 'Administrator', '', '', 'Test');
INSERT INTO abc.personalinfo (id, birthday, firstname, patronomic, phonenum, sirname) VALUES (3, '1955-11-14', 'Superuser', '', '', 'Test');

--students & parrent


INSERT INTO abc.personalinfo (id, birthday, firstname, patronomic, phonenum, sirname) VALUES (4, '2016-08-01', 'Student', '1', '', 'Test');
INSERT INTO abc.personalinfo (id, birthday, firstname, patronomic, phonenum, sirname) VALUES (5, '1969-08-12', 'Parent', '1', '54678645453', 'Test');
INSERT INTO abc.personalinfo (id, birthday, firstname, patronomic, phonenum, sirname) VALUES (6, '2001-06-12', 'Student', '2', '56-00-80', 'Test');
INSERT INTO abc.personalinfo (id, birthday, firstname, patronomic, phonenum, sirname) VALUES (7, '1975-12-30', 'Parent', '2', '', 'Test');



ALTER SEQUENCE abc.personal_info_id_seq RESTART WITH 8;


--
-- TOC entry 2036 (class 0 OID 17104)
-- Dependencies: 180
-- Data for Name: accountinfo; Type: TABLE DATA; Schema: abc; Owner: -
--
-- pass:"password"

INSERT INTO abc.accountinfo (accountid, email, password, personal_info, isfired) VALUES (1, 'nkstestemail2@mail.ru', 'X03MO1qnZdYdgyfeuILPmQ==', 1, false);
INSERT INTO abc.accountinfo (accountid, email, password, personal_info, isfired) VALUES (2, 'nkstestemail3@yandex.ru', 'X03MO1qnZdYdgyfeuILPmQ==', 2, false);
INSERT INTO abc.accountinfo (accountid, email, password, personal_info, isfired) VALUES (3, 'nkstestemail4@yandex.ru', 'X03MO1qnZdYdgyfeuILPmQ==', 3, false);

INSERT INTO abc.accountinfo (accountid, email, isfired, password, personal_info) VALUES (4, 'NksTestEmail1@gmail.com', false, 'Kyr3kp7wKjT5N3zz1OSJaQ==', 4);
INSERT INTO abc.accountinfo (accountid, email, isfired, password, personal_info) VALUES (5, 'nkstestemail5@yandex.ru', false, '3CS1V+BwoLuqdsRp0nj2pQ==', 6);

ALTER SEQUENCE abc.account_id_seq RESTART WITH 6;



--
-- TOC entry 2035 (class 0 OID 17099)
-- Dependencies: 179
-- Data for Name: user; Type: TABLE DATA; Schema: abc; Owner: -
--

INSERT INTO "abc"."user" (userid, account_info) VALUES (1, 1);
INSERT INTO "abc"."user" (userid, account_info) VALUES (2, 2);
INSERT INTO "abc"."user" (userid, account_info) VALUES (3, 3);
INSERT INTO "abc"."user" (userid, account_info) VALUES (4, 3);
                          
INSERT INTO "abc"."user" (userid, account_info) VALUES (5, 4);
INSERT INTO "abc"."user" (userid, account_info) VALUES (6, 5);

ALTER SEQUENCE abc.user_id_seq RESTART WITH 7;


--Data for Name: staff; Type: TABLE DATA; Schema: abc; Owner: -

INSERT INTO abc.staff (id) VALUES (1);
INSERT INTO abc.staff (id) VALUES (2);
INSERT INTO abc.staff (id) VALUES (3);
INSERT INTO abc.staff (id) VALUES (4);


--
-- TOC entry 2032 (class 0 OID 17055)
-- Dependencies: 172
-- Data for Name: administrator; Type: TABLE DATA; Schema: abc; Owner: -
--

INSERT INTO abc.administrator (id) VALUES (2);
INSERT INTO abc.administrator (id) VALUES (3);



--
-- TOC entry 2034 (class 0 OID 17094)
-- Dependencies: 178
-- Data for Name: teacher; Type: TABLE DATA; Schema: abc; Owner: -
--

INSERT INTO abc.teacher (id) VALUES (1);
INSERT INTO abc.teacher (id) VALUES (4);



-- Data for Name: student; Type: TABLE DATA; Schema: abc; Owner: postgres

INSERT INTO abc.parent (id, email, personal_info) VALUES(1, 'parent email', 5);
INSERT INTO abc.parent (id, email, personal_info) VALUES(2, '', 7);

ALTER SEQUENCE abc.parrent_id_seq RESTART WITH 3;


--
-- TOC entry 2016 (class 0 OID 17395)
-- Dependencies: 177
-- Data for Name: student; Type: TABLE DATA; Schema: abc; Owner: postgres
--

INSERT INTO abc.student (discount, moneybalance, id, parent_id) VALUES (5, 0, 5, 1);
INSERT INTO abc.student (discount, moneybalance, id, parent_id) VALUES (0, 0, 6, 2);


--
-- TOC entry 2037 (class 0 OID 17117)
-- Dependencies: 182
-- Data for Name: user_role; Type: TABLE DATA; Schema: abc; Owner: -
--

INSERT INTO abc.user_role (accountinfo_accountid, role) VALUES (1, 'TEACHER');
INSERT INTO abc.user_role (accountinfo_accountid, role) VALUES (2, 'ADMINISTRATOR');
INSERT INTO abc.user_role (accountinfo_accountid, role) VALUES (3, 'ADMINISTRATOR');
INSERT INTO abc.user_role (accountinfo_accountid, role) VALUES (3, 'TEACHER');
INSERT INTO abc.user_role (accountinfo_accountid, role) VALUES (4, 'STUDENT');
INSERT INTO abc.user_role (accountinfo_accountid, role) VALUES (5, 'STUDENT');


--
-- TOC entry 2015 (class 0 OID 17374)
-- Dependencies: 174
-- Data for Name: group; Type: TABLE DATA; Schema: abc; Owner: postgres
--

INSERT INTO "abc"."group" (id, level, name, tarif, teacher) VALUES (1, 'BEGINER', 'TestGroup1', 4000, 1);
INSERT INTO "abc"."group" (id, level, name, tarif, teacher) VALUES (2, 'UPPER_INTERMEDIATE', 'TestGroup2', 9000, 4);

ALTER SEQUENCE abc.group_id_seq RESTART WITH 3;


--
-- TOC entry 2016 (class 0 OID 17418)
-- Dependencies: 181
-- Data for Name: student_group; Type: TABLE DATA; Schema: abc; Owner: postgres
--

INSERT INTO abc.student_group (student_id, group_id) VALUES (5, 1);
INSERT INTO abc.student_group (student_id, group_id) VALUES (6, 1);