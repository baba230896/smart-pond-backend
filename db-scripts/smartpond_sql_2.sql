--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2
-- Dumped by pg_dump version 13.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE IF EXISTS ONLY public.customerinfo DROP CONSTRAINT IF EXISTS customerinfo_pkey;
DROP TABLE IF EXISTS public.customerinfo;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: customerinfo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customerinfo (
    userid character varying NOT NULL,
    password character varying NOT NULL
);


ALTER TABLE public.customerinfo OWNER TO postgres;

--
-- Name: customerinfo customerinfo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customerinfo
    ADD CONSTRAINT customerinfo_pkey PRIMARY KEY (userid);


--
-- PostgreSQL database dump complete
--

