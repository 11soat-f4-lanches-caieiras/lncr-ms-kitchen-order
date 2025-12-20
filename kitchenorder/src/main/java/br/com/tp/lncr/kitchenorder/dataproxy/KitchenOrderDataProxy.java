package br.com.tp.lncr.kitchenorder.dataproxy;

import br.com.tp.lncr.commons.integrations.customerorder.CustomerOrderIntegrationImpl;
import br.com.tp.lncr.commons.integrations.notifcation.NotificationIntegraionImpl;
import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderDTO;
import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderFoodItemDTO;
import br.com.tp.lncr.core.interfaces.kitchenorder.KitchenOrderDatabase;
import br.com.tp.lncr.kitchenorder.datasources.postgres.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class KitchenOrderDataProxy implements KitchenOrderDatabase {

    private final JpaKitchenOrderRepositoryImpl jpaKitchenOrderRepositoryImpl;
    private final JpaKitchenOrderRepository jpaKitchenOrderRepository;
    private final JpaKitchenOrderFoodItemRepositoryImpl jpaKitchenOrderFoodItemRepositoryImpl;
    private final JpaKitchenOrderFoodItemRepository jpaKitchenOrderFoodItemRepository;
    private final CustomerOrderIntegrationImpl customerOrderIntegrationImpl;
    private final NotificationIntegraionImpl notificationIntegraion;
    private final JpaKitchenOrderMapper jpaKitchenOrderMapper;

    public KitchenOrderDataProxy(JpaKitchenOrderRepositoryImpl jpaKitchenOrderRepositoryImpl,
                                 JpaKitchenOrderRepository jpaKitchenOrderRepository,
                                 JpaKitchenOrderFoodItemRepositoryImpl jpaKitchenOrderFoodItemRepositoryImpl,
                                 JpaKitchenOrderFoodItemRepository jpaKitchenOrderFoodItemRepository,
                                 CustomerOrderIntegrationImpl customerOrderIntegrationImpl,
                                 NotificationIntegraionImpl notificationIntegraion,
                                 JpaKitchenOrderMapper jpaKitchenOrderMapper) {
        this.jpaKitchenOrderRepositoryImpl = jpaKitchenOrderRepositoryImpl;
        this.jpaKitchenOrderRepository = jpaKitchenOrderRepository;
        this.jpaKitchenOrderFoodItemRepositoryImpl = jpaKitchenOrderFoodItemRepositoryImpl;
        this.jpaKitchenOrderFoodItemRepository = jpaKitchenOrderFoodItemRepository;
        this.customerOrderIntegrationImpl = customerOrderIntegrationImpl;
        this.notificationIntegraion = notificationIntegraion;
        this.jpaKitchenOrderMapper = jpaKitchenOrderMapper;
    }



    @Override
    public KitchenOrderDTO findById(Integer kitchenOrderId) {
        return findById(kitchenOrderId, false);
    }

    @Override
    public KitchenOrderDTO findById(Integer kitchenOrderId, Boolean includeFoodItems) {
        KitchenOrderDTO kitchenOrderDto = this.jpaKitchenOrderRepositoryImpl.findById(kitchenOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        includeFoodItems(kitchenOrderDto,includeFoodItems);
        return kitchenOrderDto;
    }

    @Transactional(readOnly = true)
    @Override
    public KitchenOrderDTO findByCustomerOrderId(Integer customerOrderId, Boolean includeFoodItems) {
        KitchenOrderDTO kitchenOrderDto = this.jpaKitchenOrderRepositoryImpl.findByCustomerOrderId(customerOrderId, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        includeFoodItems(kitchenOrderDto,includeFoodItems);
        return kitchenOrderDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<KitchenOrderDTO> findByStatusList(List<Integer> statusIdsList, Boolean includeFoodItems) {
        List<KitchenOrderDTO> kitchenOrderDTOList = this.jpaKitchenOrderRepositoryImpl.findByStatusId(statusIdsList,jpaKitchenOrderRepository,jpaKitchenOrderMapper);
        kitchenOrderDTOList.forEach(kitchenOrderDto -> includeFoodItems(kitchenOrderDto, includeFoodItems));
        return kitchenOrderDTOList;
    }


    @Override
    public List<KitchenOrderFoodItemDTO> findByKitchenOrderId(Integer kitchenOrderId) {
        return this.jpaKitchenOrderFoodItemRepositoryImpl.findByKitchenOrderId(kitchenOrderId,jpaKitchenOrderFoodItemRepository,jpaKitchenOrderMapper);
    }

    private void includeFoodItems(KitchenOrderDTO kitchenOrderDto, Boolean includeFoodItems) {
        if (includeFoodItems && (kitchenOrderDto != null)) {
            kitchenOrderDto.setFoodItems(findByKitchenOrderId(kitchenOrderDto.getId()));
        }
    }

    @Override
    public KitchenOrderDTO save(KitchenOrderDTO kitchenOrderDto) {
        kitchenOrderDto = this.jpaKitchenOrderRepositoryImpl.save(kitchenOrderDto, jpaKitchenOrderRepository, jpaKitchenOrderMapper);
        setKitchenOrderIdOnFoodItems(kitchenOrderDto);
        if (kitchenOrderDto.getFoodItems() != null && !kitchenOrderDto.getFoodItems().isEmpty())
            kitchenOrderDto.setFoodItems(this.jpaKitchenOrderFoodItemRepositoryImpl.saveAll(kitchenOrderDto.getFoodItems(),jpaKitchenOrderFoodItemRepository,jpaKitchenOrderMapper));
        return kitchenOrderDto;
    }

    @Override
    public void sendNotification(String notificationType, Integer artefactId, String message) {
        this.notificationIntegraion.sendNotification(notificationType,artefactId,message);
    }

    private void setKitchenOrderIdOnFoodItems(KitchenOrderDTO kitchenOrderDto) {
        Integer kitchenOrderId = kitchenOrderDto.getId();
        if (kitchenOrderDto.getFoodItems() != null && !kitchenOrderDto.getFoodItems().isEmpty())
            kitchenOrderDto.getFoodItems().forEach(
                foodItem -> foodItem.setKitchenOrderId(kitchenOrderId));
    }

    @Override
    public void updateCustomerOrderStatus(Integer customerOrderId, String status) {
        this.customerOrderIntegrationImpl.updateCustomerOrderStatus(customerOrderId, status);
    }


}
