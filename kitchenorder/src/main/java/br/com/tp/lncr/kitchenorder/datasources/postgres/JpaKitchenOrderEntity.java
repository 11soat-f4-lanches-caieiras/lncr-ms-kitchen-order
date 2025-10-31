package br.com.tp.lncr.kitchenorder.datasources.postgres;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "kitchen_order",
        schema = "public",
        uniqueConstraints = @UniqueConstraint(name = "kitchen_order_customer_order_id_uk", columnNames = "customerOrderId"),
        indexes = {
                @Index(name = "kitchen_order_id_idx", columnList = "id"),
                @Index(name = "kitchen_order_customer_order_id_idx", columnList = "customerOrderId"),
                @Index(name = "kitchen_order_status_id_idx", columnList = "statusId"),
                @Index(name = "kitchen_order_created_idx", columnList = "created")
        })
public class JpaKitchenOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kitchen_order_id_seq")
    @SequenceGenerator(name = "kitchen_order_id_seq", sequenceName = "kitchen_order_id_seq", allocationSize = 1)
    private Integer id;
    private Integer customerOrderId;
    private Integer statusId;
    private LocalDateTime created;
    private LocalDateTime updated;


    @Transient
    private List<JpaKitchenOrderFoodItemEntity> foodItems;

    public JpaKitchenOrderEntity(Integer id, Integer customerOrderId, Integer statusId, LocalDateTime created, LocalDateTime updated, List<JpaKitchenOrderFoodItemEntity> foodItems) {
        this.id = id;
        this.customerOrderId = customerOrderId;
        this.statusId = statusId;
        this.created = created;
        this.updated = updated;
        this.foodItems = foodItems;
    }

    public JpaKitchenOrderEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public Integer getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(Integer customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDateTime.now();
    }

    public List<JpaKitchenOrderFoodItemEntity> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<JpaKitchenOrderFoodItemEntity> foodItems) {
        this.foodItems = foodItems;
    }
}
