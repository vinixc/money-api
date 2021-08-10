
CREATE SEQUENCE categoria_seq INCREMENT 1 MINVALUE 1 START 1;
CREATE TABLE categoria(
	id bigint primary key default nextval('categoria_seq'),
	nome varchar(50) NOT NULL
);

INSERT INTO categoria (nome) VALUES ('Lazer');
INSERT INTO categoria (nome) VALUES ('Alimentação');
INSERT INTO categoria (nome) VALUES ('Supermercado');
INSERT INTO categoria (nome) VALUES ('Farmácia');
INSERT INTO categoria (nome) VALUES ('Outros');