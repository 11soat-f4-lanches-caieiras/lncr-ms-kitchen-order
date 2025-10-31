package br.com.tp.lncr.kitchenorder.apis;

import br.com.tp.lncr.commons.model.ResponseListModel;
import br.com.tp.lncr.commons.model.ResponseModel;
import br.com.tp.lncr.commons.utils.ResponseEntityModelUtil;
import br.com.tp.lncr.kitchenorder.configs.KitchenOrderConfig;
import br.com.tp.lncr.kitchenorder.dataproxy.KitchenOrderDataProxy;
import br.com.tp.lncr.core.dtos.kitchenorder.KitchenOrderDTO;
import br.com.tp.lncr.core.interfaces.kitchenorder.KitchenOrderController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitchenOrders")
public class KitchenOrderRestControllerImpl implements KitchenOrderRestController {

    public final KitchenOrderController kichenOrderController;
    public final KitchenOrderDataProxy kitchenOrderDataProxy;
    public final KitchenOrderConfig kitchenOrderConfig;

    public KitchenOrderRestControllerImpl(KitchenOrderController kichenOrderController, KitchenOrderDataProxy kitchenOrderDataProxy, KitchenOrderConfig kitchenOrderConfig) {
        this.kichenOrderController = kichenOrderController;
        this.kitchenOrderDataProxy = kitchenOrderDataProxy;
        this.kitchenOrderConfig = kitchenOrderConfig;
    }

    @Override
    @PostMapping
    public ResponseEntity<ResponseModel<KitchenOrderDTO>> createKitchenOrder(@RequestBody KitchenOrderDTO kitchenOrderDTO) {
        kitchenOrderDTO = this.kichenOrderController.createKitchenOrder(kitchenOrderDTO);
        return ResponseEntityModelUtil.created(null, kitchenOrderConfig.getLocationPrefix() + "/" + kitchenOrderDTO.getId());
    }

    @Override
    @GetMapping("/{kitchenOrderId}")
    public ResponseEntity<ResponseModel<KitchenOrderDTO>> getKitchenOrderById(@PathVariable(name="kitchenOrderId") Integer kitchenOrderId,
                                                                              @RequestParam(name = "includeFoodItems", required = false, defaultValue = "false") Boolean includeFoodItems) {
        KitchenOrderDTO kitchenOrderDTO = this.kichenOrderController.getKitchenOrderById(kitchenOrderId, includeFoodItems);
        return ResponseEntityModelUtil.ok(kitchenOrderDTO);
    }

    @Override
    @GetMapping("/customerOrder/{customerOrderId}")
    public ResponseEntity<ResponseModel<KitchenOrderDTO>> getKitchenOrderByCustomerOrderId(@PathVariable(name = "customerOrderId") Integer customerOrderId,
                                                                                           @RequestParam(name = "includeFoodItems", required = false, defaultValue = "false") Boolean includeFoodItems) {
        KitchenOrderDTO kitchenOrderDTO = this.kichenOrderController.getKitchenOrderByCustomerOrderId(customerOrderId, includeFoodItems);
        return ResponseEntityModelUtil.ok(kitchenOrderDTO);
    }

    @Override
    @GetMapping("/status/{statusList}")
    public ResponseEntity<ResponseListModel<KitchenOrderDTO>> getKitchenOrderByStatusList(@PathVariable(name = "statusList") List<String> statusList,
                                                                                          @RequestParam(name = "includeFoodItems", required = false, defaultValue = "false") Boolean includeFoodItems) {
        List<KitchenOrderDTO> kitchenOrderDTO = this.kichenOrderController.getKitchenOrderByStatusList(statusList, includeFoodItems);
        return ResponseEntityModelUtil.listOK(kitchenOrderDTO);
    }

    @Override
    @PatchMapping("/{kitchenOrderId}/updateStatus/{newStatus}")
    public ResponseEntity<ResponseModel<KitchenOrderDTO>> updateOrderStatusById(@PathVariable(name="kitchenOrderId") Integer kitchenOrderId,
                                                                                @PathVariable(name="newStatus") String newStatus,
                                                                                @RequestParam(name="forceUpdate",required = false,defaultValue = "false") Boolean forceUpdate,
                                                                                @RequestParam(name = "updateCustomerOrder", required = false, defaultValue = "true") Boolean updateCustomerOrder) {
        KitchenOrderDTO kitchenOrderDTO  = this.kichenOrderController.updateOrderStatusById(kitchenOrderId, newStatus, forceUpdate, updateCustomerOrder);
        return ResponseEntityModelUtil.ok(kitchenOrderDTO);
    }


}
