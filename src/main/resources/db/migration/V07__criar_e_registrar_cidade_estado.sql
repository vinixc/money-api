CREATE SEQUENCE estado_seq START 1 INCREMENT 1 MINVALUE 1;
CREATE TABLE estado(
  id bigint primary key default nextval('estado_seq'),
  nome varchar not null
);

INSERT INTO estado (nome) VALUES('Acre');
INSERT INTO estado (nome) VALUES('Alagoas');
INSERT INTO estado (nome) VALUES('Amazonas');
INSERT INTO estado (nome) VALUES('Amapá');
INSERT INTO estado (nome) VALUES('Bahia');
INSERT INTO estado (nome) VALUES('Ceará');
INSERT INTO estado (nome) VALUES('Distrito Federal');
INSERT INTO estado (nome) VALUES('Espírito Santo');
INSERT INTO estado (nome) VALUES('Goiás');
INSERT INTO estado (nome) VALUES('Maranhão');
INSERT INTO estado (nome) VALUES('Minas Gerais');
INSERT INTO estado (nome) VALUES('Mato Grosso do Sul');
INSERT INTO estado (nome) VALUES('Mato Grosso');
INSERT INTO estado (nome) VALUES('Pará');
INSERT INTO estado (nome) VALUES('Paraíba');
INSERT INTO estado (nome) VALUES('Pernambuco');
INSERT INTO estado (nome) VALUES('Piauí');
INSERT INTO estado (nome) VALUES('Paraná');
INSERT INTO estado (nome) VALUES('Rio de Janeiro');
INSERT INTO estado (nome) VALUES('Rio Grande do Norte');
INSERT INTO estado (nome) VALUES('Rondônia');
INSERT INTO estado (nome) VALUES('Roraima');
INSERT INTO estado (nome) VALUES('Rio Grande do Sul');
INSERT INTO estado (nome) VALUES('Santa Catarina');
INSERT INTO estado (nome) VALUES('Sergipe');
INSERT INTO estado (nome) VALUES('São Paulo');
INSERT INTO estado (nome) VALUES('Tocantins');

CREATE SEQUENCE  cidade_seq START 1 INCREMENT 1 MINVALUE 1;
CREATE TABLE  cidade(
  id bigint primary key default nextval('cidade_seq'),
  nome varchar not null,
  id_estado bigint not null,
  constraint fk_cidade_id_estado foreign key (id_estado) references estado(id)
);



INSERT INTO cidade (nome, id_estado) VALUES ('Belo Horizonte', 11);
INSERT INTO cidade (nome, id_estado) VALUES ('Uberlândia', 11);
INSERT INTO cidade (nome, id_estado) VALUES ('Uberaba', 11);
INSERT INTO cidade (nome, id_estado) VALUES ('São Paulo', 26);
INSERT INTO cidade (nome, id_estado) VALUES ('Campinas', 26);
INSERT INTO cidade (nome, id_estado) VALUES ('Rio de Janeiro', 19);
INSERT INTO cidade (nome, id_estado) VALUES ('Angra dos Reis', 19);
INSERT INTO cidade (nome, id_estado) VALUES ('Goiânia', 9);
INSERT INTO cidade (nome, id_estado) VALUES ('Caldas Novas', 9);

ALTER TABLE pessoa DROP COLUMN cidade;
ALTER TABLE pessoa DROP COLUMN estado;
ALTER TABLE pessoa ADD COLUMN id_cidade bigint;
ALTER TABLE pessoa ADD CONSTRAINT fk_pessoa_idcidade FOREIGN KEY (id_cidade) REFERENCES cidade(id);

UPDATE pessoa set id_cidade = 2;
