#language: pt

Funcionalidade: Configuração do KitchenOrder
  Como sistema de pedidos da cozinha
  Eu quero configurar corretamente o controlador de pedidos
  Para que o sistema possa processar pedidos adequadamente

  Cenário: Configurar o location prefix
    Dado que tenho um KitchenOrderConfig
    Quando eu definir o location prefix como "/api/kitchenorders"
    Então o location prefix deve ser "/api/kitchenorders"

  Cenário: Obter o location prefix padrão
    Dado que tenho um KitchenOrderConfig novo
    Quando eu consultar o location prefix
    Então o location prefix deve estar vazio ou nulo

  Cenário: Criar bean do KitchenOrderController
    Dado que tenho um KitchenOrderConfig
    E tenho um KitchenOrderDatabase mockado
    Quando eu criar o bean KitchenOrderController
    Então o controller deve ser criado com sucesso
    E o controller não deve ser nulo

  Cenário: Atualizar location prefix
    Dado que tenho um KitchenOrderConfig com location prefix "/api/v1"
    Quando eu atualizar o location prefix para "/api/v2"
    Então o location prefix deve ser "/api/v2"

