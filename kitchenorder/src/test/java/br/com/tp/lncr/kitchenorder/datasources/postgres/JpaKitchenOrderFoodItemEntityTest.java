package br.com.tp.lncr.kitchenorder.datasources.postgres;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JpaKitchenOrderFoodItemEntityTest {

    @Test
    void constructorWithAllParametersShouldSetAllFields() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity(1, 10, "Hamburguer", "Delicious burger", "No onions");

        Assertions.assertEquals(1, entity.getId());
        Assertions.assertEquals(10, entity.getKitchenOrderId());
        Assertions.assertEquals("Hamburguer", entity.getName());
        Assertions.assertEquals("Delicious burger", entity.getDescription());
        Assertions.assertEquals("No onions", entity.getNotes());
    }

    @Test
    void defaultConstructorShouldCreateEmptyEntity() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();

        Assertions.assertNull(entity.getId());
        Assertions.assertNull(entity.getKitchenOrderId());
        Assertions.assertNull(entity.getName());
        Assertions.assertNull(entity.getDescription());
        Assertions.assertNull(entity.getNotes());
    }

    @Test
    void settersShouldUpdateFieldsCorrectly() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();

        entity.setId(5);
        entity.setKitchenOrderId(15);
        entity.setName("Pizza");
        entity.setDescription("Margherita pizza");
        entity.setNotes("Extra cheese");

        Assertions.assertEquals(5, entity.getId());
        Assertions.assertEquals(15, entity.getKitchenOrderId());
        Assertions.assertEquals("Pizza", entity.getName());
        Assertions.assertEquals("Margherita pizza", entity.getDescription());
        Assertions.assertEquals("Extra cheese", entity.getNotes());
    }

    @Test
    void nameCanBeNull() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();

        entity.setName(null);

        Assertions.assertNull(entity.getName());
    }

    @Test
    void descriptionCanBeNull() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();

        entity.setDescription(null);

        Assertions.assertNull(entity.getDescription());
    }

    @Test
    void notesCanBeNull() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();

        entity.setNotes(null);

        Assertions.assertNull(entity.getNotes());
    }

    @Test
    void nameCanBeEmptyString() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();

        entity.setName("");

        Assertions.assertEquals("", entity.getName());
    }

    @Test
    void descriptionCanBeEmptyString() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();

        entity.setDescription("");

        Assertions.assertEquals("", entity.getDescription());
    }

    @Test
    void notesCanBeEmptyString() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();

        entity.setNotes("");

        Assertions.assertEquals("", entity.getNotes());
    }

    @Test
    void kitchenOrderIdCanBeNull() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();

        entity.setKitchenOrderId(null);

        Assertions.assertNull(entity.getKitchenOrderId());
    }

    @Test
    void idCanBeNull() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity();

        entity.setId(null);

        Assertions.assertNull(entity.getId());
    }

    @Test
    void constructorWithNullValuesShouldWork() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity(null, null, null, null, null);

        Assertions.assertNull(entity.getId());
        Assertions.assertNull(entity.getKitchenOrderId());
        Assertions.assertNull(entity.getName());
        Assertions.assertNull(entity.getDescription());
        Assertions.assertNull(entity.getNotes());
    }

    @Test
    void constructorWithMixedNullAndValidValuesShouldWork() {
        JpaKitchenOrderFoodItemEntity entity = new JpaKitchenOrderFoodItemEntity(1, null, "Sandwich", null, "Special request");

        Assertions.assertEquals(1, entity.getId());
        Assertions.assertNull(entity.getKitchenOrderId());
        Assertions.assertEquals("Sandwich", entity.getName());
        Assertions.assertNull(entity.getDescription());
        Assertions.assertEquals("Special request", entity.getNotes());
    }
}
