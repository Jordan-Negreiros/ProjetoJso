-- Database: curso-jsp

-- DROP DATABASE "curso-jsp";

CREATE DATABASE "curso-jsp"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE SEQUENCE public.serialcategoria
    INCREMENT 1
    START 6
    MINVALUE 1
    MAXVALUE 99999999999999999
    CACHE 1;

ALTER SEQUENCE public.serialcategoria
    OWNER TO postgres;

CREATE SEQUENCE public.serialfone
    INCREMENT 1
    START 11
    MINVALUE 1
    MAXVALUE 922337203654775807
    CACHE 1;

ALTER SEQUENCE public.serialfone
    OWNER TO postgres;

CREATE SEQUENCE public.serialproduto
    INCREMENT 1
    START 26
    MINVALUE 1
    MAXVALUE 922337203654775807
    CACHE 1;

ALTER SEQUENCE public.serialproduto
    OWNER TO postgres

CREATE TABLE public.categoria
(
    id bigint NOT NULL DEFAULT nextval('serialcategoria'::regclass),
    nome character varying COLLATE pg_catalog."default",
    CONSTRAINT categoria_pk PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.categoria
    OWNER to postgres;


CREATE SEQUENCE public.serialuser
    INCREMENT 1
    START 180
    MINVALUE 1
    MAXVALUE 922337203654775807
    CACHE 1;

ALTER SEQUENCE public.serialuser
    OWNER TO postgr

CREATE TABLE public.produto
(
    id bigint NOT NULL DEFAULT nextval('serialproduto'::regclass),
    nome character varying(255) COLLATE pg_catalog."default",
    quantidade bigint,
    preco numeric,
    categoria_id bigint,
    CONSTRAINT categoria_fk FOREIGN KEY (categoria_id)
        REFERENCES public.categoria (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.produto
    OWNER to postgres;

CREATE TABLE public.usuario
(
    login character varying COLLATE pg_catalog."default",
    senha character varying COLLATE pg_catalog."default",
    id bigint NOT NULL DEFAULT nextval('serialuser'::regclass),
    nome character varying COLLATE pg_catalog."default",
    cep character varying(255) COLLATE pg_catalog."default",
    rua character varying(255) COLLATE pg_catalog."default",
    bairro character varying(255) COLLATE pg_catalog."default",
    cidade character varying(255) COLLATE pg_catalog."default",
    estado character varying(255) COLLATE pg_catalog."default",
    ibge character varying(255) COLLATE pg_catalog."default",
    fotobase64 text COLLATE pg_catalog."default",
    contenttype text COLLATE pg_catalog."default",
    curriculobase64 text COLLATE pg_catalog."default",
    contenttypecurriculo text COLLATE pg_catalog."default",
    fotobase64miniatura text COLLATE pg_catalog."default",
    ativo boolean,
    sexo character varying(50) COLLATE pg_catalog."default",
    perfil character varying(20) COLLATE pg_catalog."default"
)

    TABLESPACE pg_default;

ALTER TABLE public.usuario
    OWNER to postgres;