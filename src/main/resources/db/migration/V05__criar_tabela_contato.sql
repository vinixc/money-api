create sequence contato_seq start 1 increment 1 minvalue 1;

create table contato(
id bigint primary key default nextval('contato_seq'),
id_pessoa bigint not null,
nome varchar(60) not null,
email varchar(100) not null,
telefone varchar(20) not null,
constraint fk_contato_pessoa_id foreign key (id_pessoa) references pessoa (id)
);

insert into contato (id_pessoa, nome, email, telefone) 
values (12,'Vinicius de Carvalho', 'vinicius.tecbcc@gmail.com','11951861619');
