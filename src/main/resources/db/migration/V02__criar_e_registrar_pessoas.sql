CREATE SEQUENCE pessoa_seq INCREMENT 1 MINVALUE 1 START 1;

CREATE TABLE pessoa(
	id bigint primary key default nextval('pessoa_seq'),
	nome varchar(60) NOT NULL,
	ativo boolean NOT NULL,
	logradouro varchar(60),
	numero varchar(10),
	complemento varchar(40),
	bairro varchar(40),
	cep varchar(10),
	cidade varchar(40),
	estado varchar(2)
);

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado)
VALUES ('Vinicius de Carvalho', true, 'Rua Jose Fernandes Caldas', '300', 'CASA 1', 'JARDIM NOVO', '04159-031', 'São Paulo', 'SP');


INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado)
VALUES ('Yasmin de Carvalho', true, 'Rua Jose Fernandes Caldas', '300', 'CASA 2', 'JARDIM NOVO', '04159-031', 'São Paulo', 'SP');


INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado)
VALUES ('Pedro Souza', true, 'Rua das Lagrimas', '10', 'Em frente a pizzaria', 'Porto', '14159-131', 'Vila das moringas', 'SP');


INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado)
VALUES ('Daniel Fernandes', true, 'Rua das aparecidas', '5', 'portao azul', 'cidade grande', '24259-021', 'São Paulo', 'SP');


INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado)
VALUES ('Wanderson Luiz da Silva', true, 'Rua Quito', '85', 'casa verde', 'Planalto', '54259-031', 'São Paulo', 'SP');


INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado)
VALUES ('Celso de Souza', true, 'Rua imaginaria', '10', 'casa 1', 'jardim da imaginacao', '54122-431', 'São Paulo', 'SP');


INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado)
VALUES ('Gustavo Novo', true, 'Rua das moringas', '100', 'em frente escola', 'jardim belo', '14129-034', 'São Paulo', 'SP');