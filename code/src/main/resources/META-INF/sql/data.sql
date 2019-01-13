--
-- PostgreSQL database dump
--

-- Dumped from database version 10.6 (Ubuntu 10.6-1.pgdg18.04+1)
-- Dumped by pg_dump version 11.1 (Ubuntu 11.1-1.pgdg18.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--


INSERT INTO public.ro le (role) VAUES ('Client');
INSERT INTO public.role (role) VALUES ('HotelStaff');
INSERT INTO public.role (role) VALUES ('HotelManager');
INSERT INTO public.role (role) VALUES ('SystemManager');


