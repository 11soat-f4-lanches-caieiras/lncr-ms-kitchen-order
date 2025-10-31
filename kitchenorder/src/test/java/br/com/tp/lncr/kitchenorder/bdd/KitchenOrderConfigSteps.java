package br.com.tp.lncr.kitchenorder.bdd;

import br.com.tp.lncr.core.interfaces.kitchenorder.KitchenOrderController;
import br.com.tp.lncr.core.interfaces.kitchenorder.KitchenOrderDatabase;
import br.com.tp.lncr.kitchenorder.configs.KitchenOrderConfig;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class KitchenOrderConfigSteps {

    private KitchenOrderConfig config;
    private KitchenOrderDatabase mockDatabase;
    private KitchenOrderController controller;
    private String locationPrefix;

    @Dado("que tenho um KitchenOrderConfig")
    public void queTenhoUmKitchenOrderConfig() {
        config = new KitchenOrderConfig();
    }

    @Quando("eu definir o location prefix como {string}")
    public void euDefinirOLocationPrefixComo(String prefix) {
        config.setLocationPrefix(prefix);
        locationPrefix = prefix;
    }

    @Então("o location prefix deve ser {string}")
    public void oLocationPrefixDeveSer(String expectedPrefix) {
        assertEquals(expectedPrefix, config.getLocationPrefix());
    }

    @Dado("que tenho um KitchenOrderConfig novo")
    public void queTenhoUmKitchenOrderConfigNovo() {
        config = new KitchenOrderConfig();
    }

    @Quando("eu consultar o location prefix")
    public void euConsultarOLocationPrefix() {
        locationPrefix = config.getLocationPrefix();
    }

    @Então("o location prefix deve estar vazio ou nulo")
    public void oLocationPrefixDeveEstarVazioOuNulo() {
        assertTrue(locationPrefix == null || locationPrefix.isEmpty());
    }

    @Dado("tenho um KitchenOrderDatabase mockado")
    public void tenhoUmKitchenOrderDatabaseMockado() {
        mockDatabase = mock(KitchenOrderDatabase.class);
    }

    @Quando("eu criar o bean KitchenOrderController")
    public void euCriarOBeanKitchenOrderController() {
        controller = config.kitchenOrderController(mockDatabase);
    }

    @Então("o controller deve ser criado com sucesso")
    public void oControllerDeveSerCriadoComSucesso() {
        assertNotNull(controller);
    }

    @Então("o controller não deve ser nulo")
    public void oControllerNaoDeveSerNulo() {
        assertNotNull(controller);
    }

    @Dado("que tenho um KitchenOrderConfig com location prefix {string}")
    public void queTenhoUmKitchenOrderConfigComLocationPrefix(String prefix) {
        config = new KitchenOrderConfig();
        config.setLocationPrefix(prefix);
    }

    @Quando("eu atualizar o location prefix para {string}")
    public void euAtualizarOLocationPrefixPara(String newPrefix) {
        config.setLocationPrefix(newPrefix);
    }
}

