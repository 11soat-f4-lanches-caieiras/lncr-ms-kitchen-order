#language: pt

Funcionalidade: Tratamento de Exceções do KitchenOrder
  Como sistema de tratamento de exceções
  Eu quero capturar e processar exceções de pedidos da cozinha
  Para que erros sejam tratados de forma adequada

  Cenário: Tratar exceção de pedido da cozinha não encontrado
    Dado que ocorreu uma KitchenOrderException com código 404
    E a mensagem é "Pedido da cozinha não encontrado"
    Quando o handler processar a exceção
    Então deve retornar uma resposta HTTP com status 404
    E a resposta deve conter a mensagem "Pedido da cozinha não encontrado"

  Cenário: Tratar exceção de pedido da cozinha com erro de validação
    Dado que ocorreu uma KitchenOrderException com código 400
    E a mensagem é "Dados inválidos para o pedido"
    Quando o handler processar a exceção
    Então deve retornar uma resposta HTTP com status 400
    E a resposta deve conter a mensagem "Dados inválidos para o pedido"

  Cenário: Tratar exceção de conflito no pedido da cozinha
    Dado que ocorreu uma KitchenOrderException com código 409
    E a mensagem é "Conflito ao processar pedido"
    Quando o handler processar a exceção
    Então deve retornar uma resposta HTTP com status 409
    E a resposta deve conter a mensagem "Conflito ao processar pedido"

  Cenário: Tratar exceção de erro interno do servidor
    Dado que ocorreu uma KitchenOrderException com código 500
    E a mensagem é "Erro interno ao processar pedido"
    Quando o handler processar a exceção
    Então deve retornar uma resposta HTTP com status 500
    E a resposta deve conter a mensagem "Erro interno ao processar pedido"

