CREATE SEQUENCE usuario_seq INCREMENT 1 MINVALUE 1 START 1;

CREATE TABLE usuario(
	id bigint primary key default nextval('usuario_seq'),
	nome varchar(50) NOT NULL,
	email varchar(50) NOT NULL,
	senha varchar(150) NOT NULL
);

INSERT INTO usuario(nome,email,senha) VALUES ('Administrador', 'admin@moneyapi.com.br','$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO usuario(nome,email,senha) VALUES ('Maria Silva', 'maria@moneyapi.com.br','$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

CREATE SEQUENCE permissao_seq INCREMENT 1 MINVALUE 1 START 1;
CREATE TABLE permissao(
	id bigint primary key default nextval('permissao_seq'),
	descricao varchar(50) NOT NULL
);

INSERT INTO permissao(descricao) VALUES ('ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao(descricao) VALUES ('ROLE_PESQUISAR_CATEGORIA');

INSERT INTO permissao(descricao) VALUES ('ROLE_CADASTRAR_PESSOA');
INSERT INTO permissao(descricao) VALUES ('ROLE_REMOVER_PESSOA');
INSERT INTO permissao(descricao) VALUES ('ROLE_PESQUISAR_PESSOA');

INSERT INTO permissao(descricao) VALUES ('ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissao(descricao) VALUES ('ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissao(descricao) VALUES ('ROLE_PESQUISAR_LANCAMENTO');

CREATE TABLE usuario_permissao(
	id_usuario bigint not null,
	id_permissao bigint not null,
	constraint usuario_permissao_pk primary key (id_usuario,id_permissao),
	constraint usuario_permissao_usu_fk foreign key (id_usuario) references usuario(id),
	constraint usuario_permissao_perm_fk foreign key (id_permissao) references permissao(id)
); 

--Permissao do admin 
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,1);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,2);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,3);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,4);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,5);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,6);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,7);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(1,8);

--Maria
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(2,2);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(2,5);
INSERT INTO usuario_permissao(id_usuario,id_permissao) VALUES(2,8);
