# Repositório para versionamento do projeto de P2 (Programação Orientada a Objetos) Myfood.

# Relatório



## INTRODUÇÃO:
  O Myfood é um simples sistema de delivery onde é possível cadastrar empresas (restaurantes até então) e clientes, além de produtos para as empresas, de forma que possa ser feita uma ponte entre essas entidades e suas devidas demandas relacionadas a produtos, garantindo que cada cliente terá seu pedido gerado, processado e atendido corretamente.

## DESIGN ARQUITETURAL DO SISTEMA
  Através da facade centralizamos o sistema para simplificar o funcionamento dos subsistemas do projeto, foi criada uma camada **_Sistema_** que permite que a **_Facade_** interaja e consuma os subsistemas do projeto tendo assim acesso a suas funcionalidades básicas que foram específicadas no design do projeto, as principais camadas com as quais a **_Facade_** irá interagir através de **_Sistema_** são _Exceptions_, _Models_ e _Persistance_.

### 1 _Sistema_
  Classe central do projeto, ela possui as regras e filtros necessários para que o sistema funcione de forma adequada e esperada, é nela que instanciamos objetos de controle para gerenciar a permanência, além de popular as estruturas de permanência que usamos na camada de _Persistence_. Como atua de forma intermediária entre a _Facade_ e os subsistemas, optamos por usá-la como um aplicador das regras de negócio e demais lógicas necessárias para garantir o funcionamento adequado do projeto, haverá uma discussão nesse tópico posteriormente. 

### 1.1 _Exceptions_
  Criada para lidar com as exceções de forma organizada e centralizada, esta classe possui todas as excessões que são usadas pelo sistema para levantar erros relacionados à situações inesperadas durante o uso do sistema.

### 1.2 _Models_
  São as principais classes do projeto, elas estruturam usuários, empresas, produtos e pedidos, além de outras classes que compõem estas, inicialmente foi criada baseando-se na descrição de cada classe necessária para cumprimento de cada _User Story_ presente no projeto, más podem ser alteradas para a parte 2 do projeto.

### 1.3 _Persistence_
  São as classes responsáveis por administrar a persistência de dados para todas as demais classes do sistema, elas possuem métodos importantes para criação, manutenção e remoção de dados no formato de persistência escolhido, nesse caso, Xml.



## PRINCIPAIS COMPONENTES

### 1 USUÁRIOS
  Usuários podem ser **Clientes** ou **Donos**, são a base de quem estará usando o sistema, os usuários do tipo **Dono** podem criar _Empresas_ e registrar _Produtos_ a serem vendidos nessas empresas, já o usuário do tipo **Cliente** pode fazer um _Pedido_ para uma _Empresa_ solicitando um _Produto_.
  A princípio optamos por tornar **Usuário** uma classe abstrata da qual herdariam **Cliente** e **Dono**, depois mudamos de ideia quanto a isso devido há não haver uma distinção explícita de **Cliente** para a classe base **Usuário**.

### 1.1 EMPRESAS E PRODUTOS
  Empresas são classes relacionadas à usuários do tipo **Dono**, apenas donos podem criar uma empresa e cadastrar produtos, além disso os pedidos feitos por **Clientes** são direcionados a essas empresas.
  Produtos são oferecidos por donos em suas empresas e podem ser solicitados por clientes.
  Atualmente apenas empresas do tipo _restaurante_ podem ser criadas, restaurantes herdam _empresa_ como sua classe base.

### 1.2 PEDIDOS
  Pedidos podem ser feitos por usuários do tipo **Cliente**, eles conterão informação de quem é o cliente que o solicitou, para qual _empresa_ e qual _produto_ foi solicitado, além de possuir um estado indicando se o pedido está aberto ou fechado (preparando).

### 1.3 MÉTODOS DE PERSISTÊNCIA
  Foram criados métodos de persistência para cada uma das classes principais descritas acima, esses métodos são **PersistenciaUsuario**, **PersistenciaEmpresa**, **PersistenciaProduto** e **PersistenciaPedido**, todas implementam a interface **_Persistencia_**, criada para garantir que tais métodos possuam o mínimo necessário para gerenciamento das estruturas de persistência, isso inclui métodos como _salvar_, _iniciar_, _remover_, _buscar_, _listar_, além de outros métodos que usarão a classe **_SerializacaoXML_** como controle para serializar os dados para xml e também desserializá-los para abastecimento das estruturas de persistência.
  Esses métodos geram os arquivos onde ficarão armazenados os dados e, após a limpeza das estruturas da memória da máquina, eles serão responsáveis por recuperar esses dados, interpretá-los, e alimentar as estruturas novamente com a informação a ser usada pelo sistema.

