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



    /*@Override
    @PostMapping()
    public ResponseEntity<KitchenOrderResponse> createKitchenOrder(@RequestBody KitchenOrder kitchenOrder) {
        KitchenOrder createdKitchenOrder = kitchenOrderServices.createKitchenOrder(kitchenOrder);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", kitchenOrderConfig.getLocationPrefix() + "/" + createdKitchenOrder.getId())
                .body(new KitchenOrderResponse());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<KitchenOrderResponse> getKitchenOrderById(@PathVariable Integer id,
                                                                    @RequestParam(name = "includeFoodItems", required = false, defaultValue = "true") Boolean includeFoodItems) {
        KitchenOrder kitchenOrder = kitchenOrderServices.findById(id, includeFoodItems);
        return new ResponseEntity<KitchenOrderResponse>(new KitchenOrderResponse(kitchenOrder), HttpStatus.OK);
    }


    @Override
    @GetMapping("/customerOrder/{customerOrderId}")
    public ResponseEntity<KitchenOrderResponse> getKitchenOrderByCustomerOrderId(@PathVariable(name = "customerOrderId") Integer customerOrderId,
                                                                                 @RequestParam(name = "includeFoodItems", required = false, defaultValue = "true") Boolean includeFoodItems) {
        return new ResponseEntity<KitchenOrderResponse>(new KitchenOrderResponse(kitchenOrderServices.getKitchenOrderByCustomerOrderById(customerOrderId, includeFoodItems)), HttpStatus.OK);

    }

    @Override
    @GetMapping("/status/{status}")
    public ResponseEntity<KitchenOrderListResponse>
    getKitchenOrderByStatus(@PathVariable(name = "status") String status,
                            @RequestParam(name = "includeFoodItems", required = false, defaultValue = "true") Boolean includeFoodItems) {
        List<KitchenOrder> kitchenOrders = kitchenOrderServices.findByStatus(status, includeFoodItems);
        if (kitchenOrders == null) {
            return new ResponseEntity<KitchenOrderListResponse>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<KitchenOrderListResponse>(new KitchenOrderListResponse(kitchenOrders), HttpStatus.OK);
    }

    @Override
    @PatchMapping("/{id}/updateStatus/{newStatus}")
    public ResponseEntity<KitchenOrderResponse> updateOrderStatusById(@PathVariable(name = "id", required = true) Integer id,
                                                                      @PathVariable(name = "newStatus", required = true) String newStatus,
                                                                      @RequestParam(name = "forceUpdate", required = false, defaultValue = "false") Boolean forceUpdate,
                                                                      @RequestParam(name = "updateCustomerOrder", required = false, defaultValue = "true") Boolean updateCustomerOrder) {
        KitchenOrder kitchenOrder = kitchenOrderServices.updateStatusById(id, newStatus, forceUpdate, updateCustomerOrder);

        return new ResponseEntity<KitchenOrderResponse>(new KitchenOrderResponse(kitchenOrder), HttpStatus.OK);

    }*/
}
