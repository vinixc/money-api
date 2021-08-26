CREATE SEQUENCE lancamento_seq INCREMENT 1 MINVALUE 1 START 1;

CREATE TABLE lancamento(
	id bigint primary key default nextval('lancamento_seq'),
	descricao varchar(50) not null,
	data_vencimento DATE not null,
	data_pagamento DATE,
	valor numeric(10,2) not null,
	observacao varchar(100),
	tipo varchar(20) not null,
	id_categoria bigint not null,
	id_pessoa bigint not null,
	constraint id_categoria_fk foreign key (id_categoria) references categoria(id),
	constraint id_pessoa_fk foreign key (id_pessoa) references pessoa(id)
);

INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Salario Mensal', '2021/08/01', null, 5000.0,'Salario do mes', 'RECEITA',5,1);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Salario Mensal', '2021/08/01', null, 2000.0,'Salario do mes', 'RECEITA',5,2);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Salario Mensal', '2021/08/01', null, 3000.0,'Salario do mes', 'RECEITA',5,3);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Salario Mensal', '2021/08/01', null, 5000.0,'Salario do mes', 'RECEITA',5,4);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Salario Mensal', '2021/08/01', null, 7000.0,'Salario do mes', 'RECEITA',5,5);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Salario Mensal', '2021/08/01', null, 1000.0,'Salario do mes', 'RECEITA',5,6);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Salario Mensal', '2021/08/01', null, 8000.0,'Salario do mes', 'RECEITA',5,7);

INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Gasolina', '2021/07/28', null, 100.0,'Gastos com Gasolina', 'DESPESA',5,1);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Gasolina', '2021/07/28', null, 50.0,'Gastos com Gasolina', 'DESPESA',5,2);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Gasolina', '2021/07/28', null, 20.0,'Gastos com Gasolina', 'DESPESA',5,3);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Gasolina', '2021/07/28', null, 400.0,'Gastos com Gasolina', 'DESPESA',5,4);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Gasolina', '2021/07/28', null, 200.0,'Gastos com Gasolina', 'DESPESA',5,5);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Gasolina', '2021/07/28', null, 100.0,'Gastos com Gasolina', 'DESPESA',5,6);
INSERT INTO lancamento (descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa)
VALUES ('Gasolina', '2021/07/28', null, 20.00,'Gastos com Gasolina', 'DESPESA',5,7);
