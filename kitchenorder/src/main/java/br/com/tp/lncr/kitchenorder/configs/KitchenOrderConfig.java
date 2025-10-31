package br.com.tp.lncr.kitchenorder.configs;

import br.com.tp.lncr.core.adapters.kitchenorder.KitchenOrderControllerImpl;
import br.com.tp.lncr.core.interfaces.kitchenorder.KitchenOrderController;
import br.com.tp.lncr.core.interfaces.kitchenorder.KitchenOrderDatabase;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "lncr.kitchen-order")
public class KitchenOrderConfig {

    private String locationPrefix;

    public String getLocationPrefix() {
        return locationPrefix;
    }

    public void setLocationPrefix(String locationPrefix) {
        this.locationPrefix = locationPrefix;
    }

    @Bean
    public KitchenOrderController kitchenOrderController(KitchenOrderDatabase kitchenOrderDatabase){
        return new KitchenOrderControllerImpl(kitchenOrderDatabase);
    }

}
