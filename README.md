# Repositório para versionamento do projeto de P2 (Programação Orientada a Objetos) Myfood.

# Relatório

## INTRODUÇÃO:
  O Myfood é um simples sistema de delivery onde é possível cadastrar empresas (restaurantes até então) e clientes, além de produtos para as empresas, de forma que possa ser feita uma ponte entre essas entidades e suas devidas demandas relacionadas a produtos, garantindo que cada cliente terá seu pedido gerado, processado e atendido corretamente.

## DESIGN ARQUITETURAL DO SISTEMA
  Através da facade centralizamos o sistema para simplificar o funcionamento dos subsistemas do projeto, foi criada uma camada **_Sistema_** que permite que a **_Facade_** interaja e consuma os subsistemas do projeto tendo assim acesso a suas funcionalidades básicas que foram específicadas no design do projeto, as principais camadas com as quais a **_Facade_** irá interagir através de **_Sistema_** são _Exceptions_,_Models_ e _Persistance_.

### 1 _Sistema_
  Classe central do projeto, ela possui as regras e filtros necessários para que o sistema funcione de forma adequada e esperada, é nela que instanciamos objetos de controle para gerenciar a permanência, além de popular as estruturas de permanência que usamos na camada de _Persistence_. Como atua de forma intermediária entre a _Facade_ e os subsistemas, optamos por usá-la como um aplicador das regras de negócio e demais lógicas necessárias para garantir o funcionamento adequado do projeto, haverá uma discussão nesse tópico posteriormente. 

### 1.1 _Exceptions_
  Criada para lidar com as exceções de forma organizada e centralizada, esta classe possui todas as excessões que são usadas pelo sistema para levantar erros relacionados à situações inesperadas durante o uso do sistema.

### 1.2 _Models_
  São as principais classes do projeto, elas estruturam usuários, empresas, produtos e pedidos, além de outras classes que compõem estas, inicialmente foi criada baseando-se na descrição de cada classe necessária para cumprimento de cada _User Story_ presente no projeto, más podem ser alteradas para a parte 2 do projeto.

### 1.3 _Persistence_
  São as classes responsáveis por administrar a persistência de dados para todas as demais classes do sistema, elas possuem métodos importantes para criação, manutenção e remoção de dados no formato de persistência escolhido, nesse caso, Xml.
