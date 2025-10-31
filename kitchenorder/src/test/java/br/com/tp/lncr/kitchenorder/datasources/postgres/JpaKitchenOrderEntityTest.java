package br.com.tp.lncr.kitchenorder.datasources.postgres;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class JpaKitchenOrderEntityTest {

    @Test
    void constructorWithAllParametersShouldSetAllFields() {
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now();
        JpaKitchenOrderFoodItemEntity foodItem = new JpaKitchenOrderFoodItemEntity(1, 10, "Hamburguer", "Delicious burger", "No onions");

        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity(1, 123, 2, created, updated, List.of(foodItem));

        Assertions.assertEquals(1, entity.getId());
        Assertions.assertEquals(123, entity.getCustomerOrderId());
        Assertions.assertEquals(2, entity.getStatusId());
        Assertions.assertEquals(created, entity.getCreated());
        Assertions.assertEquals(updated, entity.getUpdated());
        Assertions.assertEquals(1, entity.getFoodItems().size());
    }

    @Test
    void defaultConstructorShouldCreateEmptyEntity() {
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();

        Assertions.assertNull(entity.getId());
        Assertions.assertNull(entity.getCustomerOrderId());
        Assertions.assertNull(entity.getStatusId());
        Assertions.assertNull(entity.getCreated());
        Assertions.assertNull(entity.getUpdated());
        Assertions.assertNull(entity.getFoodItems());
    }

    @Test
    void settersShouldUpdateFieldsCorrectly() {
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime updated = LocalDateTime.now().plusHours(1);
        JpaKitchenOrderFoodItemEntity foodItem = new JpaKitchenOrderFoodItemEntity();

        entity.setId(5);
        entity.setCustomerOrderId(456);
        entity.setStatusId(3);
        entity.setCreated(created);
        entity.setUpdated(updated);
        entity.setFoodItems(List.of(foodItem));

        Assertions.assertEquals(5, entity.getId());
        Assertions.assertEquals(456, entity.getCustomerOrderId());
        Assertions.assertEquals(3, entity.getStatusId());
        Assertions.assertEquals(created, entity.getCreated());
        Assertions.assertEquals(updated, entity.getUpdated());
        Assertions.assertEquals(1, entity.getFoodItems().size());
    }

    @Test
    void prePersistShouldSetCreatedDateTime() {
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        LocalDateTime beforeCall = LocalDateTime.now();

        entity.prePersist();

        Assertions.assertNotNull(entity.getCreated());
        Assertions.assertTrue(entity.getCreated().isAfter(beforeCall) || entity.getCreated().isEqual(beforeCall));
    }

    @Test
    void preUpdateShouldSetUpdatedDateTime() {
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();
        LocalDateTime beforeCall = LocalDateTime.now();

        entity.preUpdate();

        Assertions.assertNotNull(entity.getUpdated());
        Assertions.assertTrue(entity.getUpdated().isAfter(beforeCall) || entity.getUpdated().isEqual(beforeCall));
    }

    @Test
    void foodItemsCanBeEmptyList() {
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();

        entity.setFoodItems(Collections.emptyList());

        Assertions.assertNotNull(entity.getFoodItems());
        Assertions.assertTrue(entity.getFoodItems().isEmpty());
    }

    @Test
    void foodItemsCanBeNull() {
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();

        entity.setFoodItems(null);

        Assertions.assertNull(entity.getFoodItems());
    }

    @Test
    void multiplePrePersistCallsShouldUpdateCreatedDateTime() {
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();

        entity.prePersist();
        LocalDateTime firstCreated = entity.getCreated();


        entity.prePersist();
        LocalDateTime secondCreated = entity.getCreated();

        Assertions.assertTrue(secondCreated.isAfter(firstCreated) || secondCreated.isEqual(firstCreated));
    }

    @Test
    void multiplePreUpdateCallsShouldUpdateUpdatedDateTime() {
        JpaKitchenOrderEntity entity = new JpaKitchenOrderEntity();

        entity.preUpdate();
        LocalDateTime firstUpdated = entity.getUpdated();


        entity.preUpdate();
        LocalDateTime secondUpdated = entity.getUpdated();

        Assertions.assertTrue(secondUpdated.isAfter(firstUpdated) || secondUpdated.isEqual(firstUpdated));
    }
}
