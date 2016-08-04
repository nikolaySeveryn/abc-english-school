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

INSERT INTO personalinfo (id, birthday, firstname, patronomic, phonenum, sirname) VALUES (8, '1990-08-01', 'Teacher', '', '', 'Test');
INSERT INTO personalinfo (id, birthday, firstname, patronomic, phonenum, sirname) VALUES (9, '1971-03-10', 'Administrator', '', '', 'Test');
INSERT INTO personalinfo (id, birthday, firstname, patronomic, phonenum, sirname) VALUES (10, '1955-11-14', 'Superuser', '', '', 'Test');


--
-- TOC entry 2036 (class 0 OID 17104)
-- Dependencies: 180
-- Data for Name: accountinfo; Type: TABLE DATA; Schema: abc; Owner: -
--
-- pass:"password"

INSERT INTO accountinfo (accountid, email, password, personal_info, isdisable) VALUES (8, 'nkstestemail2@mail.ru', 'X03MO1qnZdYdgyfeuILPmQ==', 8, false);
INSERT INTO accountinfo (accountid, email, password, personal_info, isdisable) VALUES (9, 'nkstestemail3@yandex.ru', 'X03MO1qnZdYdgyfeuILPmQ==', 9, false);
INSERT INTO accountinfo (accountid, email, password, personal_info, isdisable) VALUES (10, 'nkstestemail4@yandex.ru', 'X03MO1qnZdYdgyfeuILPmQ==', 10, false);



--
-- TOC entry 2035 (class 0 OID 17099)
-- Dependencies: 179
-- Data for Name: user; Type: TABLE DATA; Schema: abc; Owner: -
--

INSERT INTO "user" (id, account_info) VALUES (14, 8);
INSERT INTO "user" (id, account_info) VALUES (15, 9);
INSERT INTO "user" (id, account_info) VALUES (16, 10);
INSERT INTO "user" (id, account_info) VALUES (17, 10);


--
-- TOC entry 2032 (class 0 OID 17055)
-- Dependencies: 172
-- Data for Name: administrator; Type: TABLE DATA; Schema: abc; Owner: -
--

INSERT INTO administrator (id) VALUES (15);
INSERT INTO administrator (id) VALUES (16);



--
-- TOC entry 2034 (class 0 OID 17094)
-- Dependencies: 178
-- Data for Name: teacher; Type: TABLE DATA; Schema: abc; Owner: -
--

INSERT INTO teacher (id) VALUES (14);
INSERT INTO teacher (id) VALUES (17);


--
-- TOC entry 2037 (class 0 OID 17117)
-- Dependencies: 182
-- Data for Name: user_role; Type: TABLE DATA; Schema: abc; Owner: -
--

INSERT INTO user_role (accountinfo_accountid, role) VALUES (8, 'TEACHER');
INSERT INTO user_role (accountinfo_accountid, role) VALUES (9, 'ADMINISTRATOR');
INSERT INTO user_role (accountinfo_accountid, role) VALUES (10, 'ADMINISTRATOR');
INSERT INTO user_role (accountinfo_accountid, role) VALUES (10, 'TEACHER');


-- Completed on 2016-08-04 11:37:10 EEST

--
-- PostgreSQL database dump complete
--