package br.com.tp.lncr.kitchenorder.datasources.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface JpaKitchenOrderFoodItemRepository extends JpaRepository<JpaKitchenOrderFoodItemEntity, Integer> {

    @Query(value = "select * from kitchen_order_food_item where kitchen_order_id = :kitchenOrderId", nativeQuery = true)
    List<JpaKitchenOrderFoodItemEntity> findByKitchenOrderId(@Param("kitchenOrderId") Integer kitchenOrderId);
}
