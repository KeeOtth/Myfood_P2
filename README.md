# Repositório para versionamento do projeto de P2 (Programação Orientada a Objetos) Myfood.

# Relatório
Alunos:
- Beatriz Rodrigues Cavalcante 
- Gabriel Gomes de Oliveira


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

## PADRÕES DE PROJETO

#### Facade
- Fornece uma interface unificada para um conjunto de interfaces em um subsistema, através dessa interface "Superior" ela se torna a mais simples de utilizar.
- Permite a interação com o sistema de forma unificada.
- Atende à oportunidade da necessidade de interação com o sistema em um nível mais alto.
- A Facade recebe os comandos do EasyAccept e chama os métodos no sistema.

## DISCUSSÃO
  Este tópico tem como objetivo exclarecer algumas dúvidas nossas e possívelmente do leitor quanto a escolhas no projeto para essa primeira parte

#### Sobre as classes:
Pode ser incômodo e talvez incorreto não termos uma classe cliente, eu disse préviamente que inicialmente tínhamos uma más optamos por remover, o motivo é que não há uma distinção entre cliente e a classe base usuário descrita explicitamente na declaração das classes na us1_1 nem nas subsequentes, por motivos de "conveniência" isso nos motivou a remover a classe, no entanto percebemos posteriormente que isso seria ruim para a organização geral do projeto e também seria problemático pois gera complicações que pretendo explicar em tópicos a seguir.
Antes de concluir essa parte gostaria de dizer que consideramos alguns cenários como o primeiro que adotamos, isto é, **Usuário** como uma classe base abstrata e **Dono** e **Cliente** herdando desta, além disso também consideramos criar por conta própria um novo atributo na classe usuário que seria o atributo "tipo_usuário", uma string que apenas conteria informação dizendo se este usuário é um dono ou um cliente, depois de pensarmos sobre, decidimos que iremos implementar essa estratégia no futuro.

#### Sobre o uso de instanceof:
Note que na classe Sistema há o uso do operador instanceof para podermos usar o filtro que alimenta as estruturas com os dados desserializados do arquivo XML, sabemos que há algumas discussões sobre o uso desse operador ser considerado code-smell, ou seja _um trecho do código fonte que pode ser um indicativo de um problema_ (a grosso modo), acreditamos no entando que no contexto de uso dele não se trata de um problema, além disso há a discussão sobre ele quebrar o polimorfismo, pois cada classe deveria ser capaz de identificar-se e distinguir-se do resto, no entando, dado o formato do projeto e os desafios que fomos encontrando, nos deparamos com algumas possibilidades para resolver o problema de distinção entre **Cliente** e **Dono**, uma vez que **Cliente** não se distingue de forma explícita de **Usuário**, essas possibilidades seriam:
- Usar os métodos getClass().getSimpleName().equals() para verificar se é ou não um objeto da classe **Dono**.
⋅⋅⋅ Acabamos optando por essa estratégia, o que no final das contas não nos pareceu muito diferente de usar "instanceof".
- Implementar um atributo de "tipo" na classe usuário, como descrito anteriormente.
⋅⋅⋅ Apesar de não parecer muito diferente dos métodos anteriores, parece ser a forma mais organizada de fazer pois dessa forma seria responsabilidade da classe se identificar através de métodos e atributos próprios da classe, pretendemos aplicar essa abordagem no futuro.

#### Sobre as validações:
Como usamos a camada **_Sistema_** para intermediar o relacionamento entre a **_Facade_** e o resto do sistema, optamos por colocar as regras de negócio, filtros e validações em seus métodos, o principal motivo disso é que ao tentar colocá-los dentro das classes (onde supostamente deveriam estar) nos deparamos com vários problemas de estruturação, provavelmente relacionados com a forma em que o projeto foi concebido nas user_stories, estamos cientes de que isso pode ser visto de forma negativa, no entando foi a única solução que encontramos dentro do nosso contexto para fazer com que o projeto funcionasse de forma esperada.

#### Conclusão:
Aprendemos muito nessa primeira parte más ainda há muito a se aprender, os comentários que eu fiz no tópico de discussão são decorrentes de uma auto-análise feita encima do nosso projeto, pretendemos implementar soluções para problemas descritos neste tópico e possivelmente atualizar a nossa estratégia e abordagem para certos comportamentos do sistema, o motivo disto não ter sido feito ainda é a falta de tempo, estamos ambos sobrecarregados com projetos de outras disciplinas além de pesquisa e outras atividades, como ambos também estamos matriculados na disciplina de Projeto e Análise de Algoritmos, boa parte do nosso tempo de estudo está sendo dedicado à ela por conta do intervalo apertado entre provas e da quantidade de coisas a serem feitas (listas de exercícios, simulados, etc...).
Agradecemos a compreensão.
