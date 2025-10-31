package br.com.tp.lncr.kitchenorder.datasources.postgres;

import jakarta.persistence.*;

@Entity
@Table(name = "kitchen_order_food_item",
        schema = "public",
        indexes = {
                @Index(name = "kitchen_order_item_id_idx", columnList = "id"),
                @Index(name = "kitchen_order_item_kitchen_order_id_idx", columnList = "kitchenOrderId")
        })
public class JpaKitchenOrderFoodItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kitchen_order_food_item_id_seq")
    @SequenceGenerator(name = "kitchen_order_food_item_id_seq", sequenceName = "kitchen_order_food_item_id_seq", allocationSize = 1)
    private Integer id;
    private Integer kitchenOrderId;
    private String name;
    private String description;
    private String notes;

    public JpaKitchenOrderFoodItemEntity(Integer id, Integer kitchenOrderId, String name, String description, String notes) {
        this.id = id;
        this.kitchenOrderId = kitchenOrderId;
        this.name = name;
        this.description = description;
        this.notes = notes;
    }

    public JpaKitchenOrderFoodItemEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKitchenOrderId() {
        return kitchenOrderId;
    }

    public void setKitchenOrderId(Integer kitchenOrderId) {
        this.kitchenOrderId = kitchenOrderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
