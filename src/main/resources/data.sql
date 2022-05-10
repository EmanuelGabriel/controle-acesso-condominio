-- CRIANDO A TABELA CONTROLE DE ACESSO

CREATE TABLE tb_controle_acesso (
      	id UUID DEFAULT RANDOM_UUID() NOT NULL,
        numero_vaga varchar(10) NOT NULL,
        numero_placa varchar(10) NOT NULL,
        marca_veiculo varchar(70) NOT NULL,
        modelo_veiculo varchar(70) NOT NULL,
        cor_veiculo varchar(50) NOT NULL,
        data_registro timestamp NOT NULL,
        nome_responsavel varchar(100) NOT NULL,
        numero_apartamento varchar(10) NOT NULL,
        bloco varchar(30) not null,
        primary key (id)
 );


-- INSERINDO CARGA DE DADOS
INSERT INTO tb_controle_acesso(numero_vaga, numero_placa, marca_veiculo, modelo_veiculo, cor_veiculo, data_registro, nome_responsavel, numero_apartamento, bloco)
VALUES ('1', 'HUJ8298', 'Fiat', 'Fiat Uno', 'Verde', '2022-05-09', 'Pedro Alves Cabral', '201', 'A');

INSERT INTO tb_controle_acesso(numero_vaga, numero_placa, marca_veiculo, modelo_veiculo, cor_veiculo, data_registro, nome_responsavel, numero_apartamento, bloco)
VALUES ('2', 'LVY7734', 'Toyota', 'Corolla XEI', 'Prata', '2022-05-08', 'Francisco Souza de Paula', '301', 'B');

INSERT INTO tb_controle_acesso(numero_vaga, numero_placa, marca_veiculo, modelo_veiculo, cor_veiculo, data_registro, nome_responsavel, numero_apartamento, bloco)
VALUES ('3', 'TNK1298', 'Chevrolet', 'Celta LS', 'Prata', '2022-05-07', 'Luana Costa e Silva', '203', 'A');

INSERT INTO tb_controle_acesso(numero_vaga, numero_placa, marca_veiculo, modelo_veiculo, cor_veiculo, data_registro, nome_responsavel, numero_apartamento, bloco)
VALUES ('4', 'LVS2087', 'Chevrolet', 'Cruze LT', 'Vermelho', '2022-05-09', 'Eduardo Pinto Nery', '204', 'A');

INSERT INTO tb_controle_acesso(numero_vaga, numero_placa, marca_veiculo, modelo_veiculo, cor_veiculo, data_registro, nome_responsavel, numero_apartamento, bloco)
VALUES ('5', 'HMG0246', 'Nissan', 'Nissa Frontier', 'Branco', '2022-05-09', 'Júlio Cesar Cardoso', '205', 'A');

INSERT INTO tb_controle_acesso(numero_vaga, numero_placa, marca_veiculo, modelo_veiculo, cor_veiculo, data_registro, nome_responsavel, numero_apartamento, bloco)
VALUES ('6', 'GIQ7723', 'Jeep', 'Jeep Compass', 'Branco Perola', '2022-05-09', 'Francisca Eduarda Alves', '201', 'A');

insert into tb_controle_acesso (numero_vaga, numero_placa, marca_veiculo, modelo_veiculo, cor_veiculo, data_registro, nome_responsavel, numero_apartamento, bloco)
values ('7', 'HID4539', 'Toyota', 'Hillux LS', 'Prata', '2022-5-09', 'Maria Oliveira de Carvalho', '302', 'C');




