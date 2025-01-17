PGDMP  '    +                 }            biblioBD    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                        0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    34450    biblioBD    DATABASE     }   CREATE DATABASE "biblioBD" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';
    DROP DATABASE "biblioBD";
                postgres    false            �            1259    34472    emprunts    TABLE     �   CREATE TABLE public.emprunts (
    id integer NOT NULL,
    membre_id integer,
    livre_id integer,
    date_emprunt date,
    date_retour_prevue date,
    date_retour_effective date
);
    DROP TABLE public.emprunts;
       public         heap    postgres    false            �            1259    34471    emprunts_id_seq    SEQUENCE     �   CREATE SEQUENCE public.emprunts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.emprunts_id_seq;
       public          postgres    false    220                       0    0    emprunts_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.emprunts_id_seq OWNED BY public.emprunts.id;
          public          postgres    false    219            �            1259    34452    livres    TABLE     �   CREATE TABLE public.livres (
    id integer NOT NULL,
    titre character varying(255) NOT NULL,
    auteur character varying(255),
    categorie character varying(255),
    nombre_exemplaires integer
);
    DROP TABLE public.livres;
       public         heap    postgres    false            �            1259    34451    livres_id_seq    SEQUENCE     �   CREATE SEQUENCE public.livres_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.livres_id_seq;
       public          postgres    false    216                       0    0    livres_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.livres_id_seq OWNED BY public.livres.id;
          public          postgres    false    215            �            1259    34461    membres    TABLE     �   CREATE TABLE public.membres (
    id integer NOT NULL,
    nom character varying(255) NOT NULL,
    email character varying(255),
    date_adhesion date
);
    DROP TABLE public.membres;
       public         heap    postgres    false            �            1259    34460    membres_id_seq    SEQUENCE     �   CREATE SEQUENCE public.membres_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.membres_id_seq;
       public          postgres    false    218                       0    0    membres_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.membres_id_seq OWNED BY public.membres.id;
          public          postgres    false    217            \           2604    34475    emprunts id    DEFAULT     j   ALTER TABLE ONLY public.emprunts ALTER COLUMN id SET DEFAULT nextval('public.emprunts_id_seq'::regclass);
 :   ALTER TABLE public.emprunts ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    220    220            Z           2604    34455 	   livres id    DEFAULT     f   ALTER TABLE ONLY public.livres ALTER COLUMN id SET DEFAULT nextval('public.livres_id_seq'::regclass);
 8   ALTER TABLE public.livres ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            [           2604    34464 
   membres id    DEFAULT     h   ALTER TABLE ONLY public.membres ALTER COLUMN id SET DEFAULT nextval('public.membres_id_seq'::regclass);
 9   ALTER TABLE public.membres ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    218    218            �          0    34472    emprunts 
   TABLE DATA           t   COPY public.emprunts (id, membre_id, livre_id, date_emprunt, date_retour_prevue, date_retour_effective) FROM stdin;
    public          postgres    false    220   
       �          0    34452    livres 
   TABLE DATA           R   COPY public.livres (id, titre, auteur, categorie, nombre_exemplaires) FROM stdin;
    public          postgres    false    216   H       �          0    34461    membres 
   TABLE DATA           @   COPY public.membres (id, nom, email, date_adhesion) FROM stdin;
    public          postgres    false    218   @                  0    0    emprunts_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.emprunts_id_seq', 2, true);
          public          postgres    false    219                       0    0    livres_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.livres_id_seq', 6, true);
          public          postgres    false    215                       0    0    membres_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.membres_id_seq', 5, true);
          public          postgres    false    217            d           2606    34477    emprunts emprunts_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.emprunts
    ADD CONSTRAINT emprunts_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.emprunts DROP CONSTRAINT emprunts_pkey;
       public            postgres    false    220            ^           2606    34459    livres livres_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.livres
    ADD CONSTRAINT livres_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.livres DROP CONSTRAINT livres_pkey;
       public            postgres    false    216            `           2606    34470    membres membres_email_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.membres
    ADD CONSTRAINT membres_email_key UNIQUE (email);
 C   ALTER TABLE ONLY public.membres DROP CONSTRAINT membres_email_key;
       public            postgres    false    218            b           2606    34468    membres membres_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.membres
    ADD CONSTRAINT membres_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.membres DROP CONSTRAINT membres_pkey;
       public            postgres    false    218            e           2606    34483    emprunts emprunts_livre_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.emprunts
    ADD CONSTRAINT emprunts_livre_id_fkey FOREIGN KEY (livre_id) REFERENCES public.livres(id);
 I   ALTER TABLE ONLY public.emprunts DROP CONSTRAINT emprunts_livre_id_fkey;
       public          postgres    false    220    4702    216            f           2606    34478     emprunts emprunts_membre_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.emprunts
    ADD CONSTRAINT emprunts_membre_id_fkey FOREIGN KEY (membre_id) REFERENCES public.membres(id);
 J   ALTER TABLE ONLY public.emprunts DROP CONSTRAINT emprunts_membre_id_fkey;
       public          postgres    false    220    4706    218            �   .   x�3�4�4�4202�50�54�3�,�D�����)Cf�r��qqq Cy�      �   �   x�e�1N�0E��)|���� ��m !hig���=Y��r��##+��~����;j����d#O5�r&Uj�=aA�����\��,�.�Ia@Ui쫌&O��)9���
K�]kzx����/R�?r����fLc&(~]�l��ܜq�e]��(��D0HT����͔�������ÑU9"D��Dc��E��
Y"&���H�S%�Z�����a��H�P��xs~���]o�/�1?�{iu      �   w   x�3�t�H�Q��OI-�P�鹉�9z����FF�����&\F����٨��B��sz$��V*�&��qBi�M8����2s�K�<�*M9C�2�R+��b���� �%A�     