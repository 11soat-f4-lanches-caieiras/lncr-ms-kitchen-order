package br.com.tp.lncr.kitchenorder.datasources.postgres;

import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaKitchenOrderRepositoryImpl {

       public KitchenOrderDTO save(KitchenOrderDTO kitchenOrderDTO, JpaKitchenOrderRepository jpaKitchenOrderRepository, JpaKitchenOrderMapper jpaKitchenOrderMapper) {
        JpaKitchenOrderEntity jpaKitchenOrderEntity = jpaKitchenOrderMapper.kitchenOrderDTOtoJpa(kitchenOrderDTO);
        return jpaKitchenOrderMapper.jpaKitchenOrderToDTO(jpaKitchenOrderRepository.save(jpaKitchenOrderEntity));
    }

    public KitchenOrderDTO findById(Integer id, JpaKitchenOrderRepository jpaKitchenOrderRepository, JpaKitchenOrderMapper jpaKitchenOrderMapper) {
        JpaKitchenOrderEntity jpaKitchenOrderEntity =jpaKitchenOrderRepository.findById(id).orElse(null);
        return jpaKitchenOrderMapper.jpaKitchenOrderToDTO(jpaKitchenOrderEntity);

    }

    public List<KitchenOrderDTO> findByStatusId(List<Integer> statusIdList, JpaKitchenOrderRepository jpaKitchenOrderRepository, JpaKitchenOrderMapper jpaKitchenOrderMapper) {
        List<JpaKitchenOrderEntity> jpaKitchenOrderEntities =jpaKitchenOrderRepository.findByStatusIdList(statusIdList);
        return jpaKitchenOrderEntities.stream()
                .map(jpaKitchenOrderMapper::jpaKitchenOrderToDTO)
                .toList();
    }

    public KitchenOrderDTO updateStatusByCustomerOrderId(Integer customerOrderId, Integer statusId, JpaKitchenOrderRepository jpaKitchenOrderRepository, JpaKitchenOrderMapper jpaKitchenOrderMapper) {
        JpaKitchenOrderEntity jpaKitchenOrderEntity = jpaKitchenOrderRepository.findById(customerOrderId).orElse(null);
        if (jpaKitchenOrderEntity != null) {
            jpaKitchenOrderEntity.setStatusId(statusId);
            jpaKitchenOrderEntity = jpaKitchenOrderRepository.save(jpaKitchenOrderEntity);
            return jpaKitchenOrderMapper.jpaKitchenOrderToDTO(jpaKitchenOrderEntity);
        }
        return null;
    }

    public KitchenOrderDTO findByCustomerOrderId(Integer customerOrderId, JpaKitchenOrderRepository jpaKitchenOrderRepository, JpaKitchenOrderMapper jpaKitchenOrderMapper) {
        JpaKitchenOrderEntity jpaKitchenOrderEntity = jpaKitchenOrderRepository.findByCustomerOrderId(customerOrderId);
        return jpaKitchenOrderMapper.jpaKitchenOrderToDTO(jpaKitchenOrderEntity);
    }
}
