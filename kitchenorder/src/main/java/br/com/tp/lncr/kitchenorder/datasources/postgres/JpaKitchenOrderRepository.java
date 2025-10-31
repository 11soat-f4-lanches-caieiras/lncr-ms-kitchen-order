package br.com.tp.lncr.kitchenorder.datasources.postgres;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface JpaKitchenOrderRepository extends JpaRepository<JpaKitchenOrderEntity, Integer> {

    @Query(value = "select * from kitchen_order where status_id in(:statusIdList)", nativeQuery = true)
    List<JpaKitchenOrderEntity> findByStatusIdList(@Param("statusIdList") List<Integer> statusIdList);

    @Query(value = "select * from kitchen_order where customer_order_id =:customerOrderId", nativeQuery = true)
    JpaKitchenOrderEntity findByCustomerOrderId(@Param("customerOrderId") Integer customerOrderId);


}
