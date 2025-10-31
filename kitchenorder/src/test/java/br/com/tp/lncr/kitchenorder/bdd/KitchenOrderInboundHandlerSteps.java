package br.com.tp.lncr.kitchenorder.bdd;

import br.com.tp.lncr.core.exceptions.KitchenOrderException;
import br.com.tp.lncr.core.model.ResponseMetadata;
import br.com.tp.lncr.kitchenorder.handlers.KitchenOrderInboundHandler;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class KitchenOrderInboundHandlerSteps {

    private KitchenOrderInboundHandler handler;
    private KitchenOrderException exception;
    private ResponseEntity<Object> response;
    private Integer errorCode;

    @Dado("que ocorreu uma KitchenOrderException com código {int}")
    public void queOcorreuUmaKitchenOrderExceptionComCodigo(Integer codigo) {
        errorCode = codigo;
        handler = new KitchenOrderInboundHandler();
    }

    @Dado("a mensagem é {string}")
    public void aMensagemE(String mensagem) {
        exception = new KitchenOrderException(mensagem, errorCode);
    }

    @Quando("o handler processar a exceção")
    public void oHandlerProcessarAExcecao() {
        response = handler.handleKitchenOrderException(exception);
    }

    @Então("deve retornar uma resposta HTTP com status {int}")
    public void deveRetornarUmaRespostaHTTPComStatus(Integer expectedStatus) {
        assertNotNull(response);
        assertEquals(expectedStatus, response.getStatusCode().value());
    }

    @Então("a resposta deve conter a mensagem {string}")
    public void aRespostaDeveConterAMensagem(String expectedMessage) {
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertInstanceOf(ResponseMetadata.class, response.getBody(), "O corpo da resposta deveria ser uma instância de ResponseMetadata");
        ResponseMetadata metadata = (ResponseMetadata) response.getBody();
        assertEquals(expectedMessage, metadata.getMessage(),
            "A resposta deveria conter a mensagem: " + expectedMessage + ", mas contém: " + metadata.getMessage());
    }
}

