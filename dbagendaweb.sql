/**
* Projeto 1: Agenda de contatos
* @author Jerry
*/

-- Listar bancos disponiveis no servidor
show databases;
-- criar um novo banco de dados
create database dbagenda;
-- Excluir banco de dados
drop database teste;
-- selecionar o banco de dados 
use dbagenda;


/* tabelas */
-- criar uma nova tabela
/* 
int ( tipo de dados:numeros inteiros)
primari key ( chave primaria)
auto_incremente (numeração automatica)
varchar (tipo de dados: String de caracteres)
not null (campo com preenchimento obrigatório)
*/
create table contatos (
	idcon int primary key auto_increment,
	nome varchar (50) not null,
    fone varchar (15) not null,
    email varchar (50) 
);
-- Listar tabelas do banco de dados
show tables;

-- descrever a estrutura da tabela
describe contatos;
-- Excluir uma tabela
drop table contatos;

/* CRUD (creat Read update Delete) */

/* CRUD creat */
-- inserir um novo contato
insert into contatos(nome,fone,email)
values('Ricardo Rossoni','972789001','12334@gmail.com');
insert into contatos(nome,email,fone)
values ('Bill Gates','bill@email.com','985458774');
insert into contatos(nome,fone,email)
values ('Belga com U','985458774','belga@gmail.com');
insert into contatos(nome,fone,email)
values ('rossoni','985458774','rrrrrra@gmail.com');
insert into contatos(nome,fone,email)
values ('Elias','985458775','Elias@gmail.com');
insert into contatos(nome,fone,email)
values ('Jerry','985458776','jerry@gmail.com');

/* CRUD Read */
-- listar todos contatos da tabela
select * from contatos;


-- listar os contatos por ordem alfabetica
select *from contatos order by nome;

-- listar campos especificos da tabela
Select nome,fone from contatos order by nome;

-- criar um apelido para os campos da tabela
select nome as Contato, fone as Telefone,
email as Email from contatos order by nome;

-- selecionar um contato especifico
select * from contatos where nome= 'Bill Gates';
select * from contatos where idcon=1;

/* CRUD Update */
update contatos set nome='Willian Gates', fone='2989-9090',
email='bill@outlook.com' where idcon=2;
update contatos set fone='99999-6666' where idcon=2;

/*crud delete */
delete from contatos where idcon=3;
delete from contatos where idcon=4;
 delete from contatos where idcon=6;