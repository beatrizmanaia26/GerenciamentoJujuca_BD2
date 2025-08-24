# Gerenciamento Jujuca Bombonier 

## Estrutura do Projeto:

![codigo1](./imagens/estruturaProjeto.png)

## Tema:

Esse projeto tem como intuito fornecer uma organização do comércio da doceria Jujuca Bombonier da FEI, bem como facilitar o controle de vendas e lucros do local.

## Como sistemas2 será implementado:
 S2 será implementado para CRUD dos bancos, inserção e busca de dados de acordo com o que cada banco é responsável por armazenar

-linguagens utilizadas para comunicar com cada banco de dados:
    BD1: Java <br>
    BD2: Node ou Python <br>
    BD3: go

## Bancos de dados:
 
 ### BD1 Relacional (SQL): PostgreeSQL
 Escolhemos esse banco de dados para guardar todas as informações referentes aos clientes e vendedores, afinal são dados bem estruturados e com várias características semelhantes, o Postgree também foi escolhido dentre todas as opções de bancos relacionais devido ao conhecimento básico que possuímos desse banco.

 #### Modelo Entidade Relacionamento:

 ![codigo1](./imagens/merjujuca.png)

 ### BD2 Não Relacional (NoSQL): MongoDB

 Esse banco de dados foi escolhido para armazenar informações dos produtos, escolhemos esse banco devido ao conhecimento prévio que possuímos 

 ### BD3 Não Relacional (NoSQL): Cassandra
 Escolhemos esse banco para armazenar histórico de compra da loja e do histórico de estoque dos produtos, esoclhemos esse banco pois ele  é muito bom para isso.

## Como executar o projeto

### Serviços e recursos utilizados:

1-configurar bancos de dados que serão utilizados:

a)Cassanda (para windows)

b)MongoDB 

c)PostGreeSQL 
