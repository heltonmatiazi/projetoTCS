--
-- File generated with SQLiteStudio v3.1.1 on sáb abr 1 13:19:19 2017
--
-- Text encoding used: System (utf-8)
--
-- primeria versão da database do projeto, usei um client com GUI para o gerenciamento que facilitou bastante o processo
-- author : Helt
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Colecao
DROP TABLE IF EXISTS Colecao;

CREATE TABLE Colecao (
    idColecao      INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT
                           UNIQUE ON CONFLICT ROLLBACK
                           NOT NULL ON CONFLICT ROLLBACK,
    totalColecao   INTEGER,
    colecaoUsuario INTEGER REFERENCES usuario (idUsuario) 
                           NOT NULL
);


-- Table: container
DROP TABLE IF EXISTS container;

CREATE TABLE container (
    idContainer    INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT
                           NOT NULL ON CONFLICT ROLLBACK
                           UNIQUE ON CONFLICT ROLLBACK,
    nome           STRING,
    local          STRING,
    ultimaConsulta DATE,
    icone          BLOB
);


-- Table: obra
DROP TABLE IF EXISTS obra;

CREATE TABLE obra (
    idObra          INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT
                            UNIQUE ON CONFLICT ROLLBACK
                            NOT NULL ON CONFLICT ROLLBACK,
    imagem          BLOB,
    titulo          STRING,
    autor           STRING,
    ISBN            STRING,
    edicao          STRING,
    editora         STRING,
    anoDePublicacao INTEGER,
    emprestado      BOOLEAN,
    tagObra         INTEGER REFERENCES tag (idTag),
    containerObra   INTEGER REFERENCES container (idContainer) 
);


-- Table: tag
DROP TABLE IF EXISTS tag;

CREATE TABLE tag (
    idTag     INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT
                      NOT NULL ON CONFLICT ROLLBACK,
    nome      STRING  NOT NULL ON CONFLICT ROLLBACK,
    totalUsos INTEGER
);


-- Table: usuario
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    idUsuario INTEGER         PRIMARY KEY ON CONFLICT ROLLBACK
                              UNIQUE ON CONFLICT ROLLBACK
                              NOT NULL ON CONFLICT ROLLBACK,
    nome      STRING (1, 100) NOT NULL ON CONFLICT ROLLBACK
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
