package Test.sbs.rest_controllers;

import Test.sbs.dto.MyOrderGoodDTO;
import Test.sbs.services_impl.MyOrderGoodServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class MyOrderGoodRestController {
    private final MyOrderGoodServiceImpl myOrderGoodService;

    public MyOrderGoodRestController(MyOrderGoodServiceImpl myOrderGoodService) {
        this.myOrderGoodService = myOrderGoodService;
    }

    @GetMapping("/good/my_order")
    public ResponseEntity<List<MyOrderGoodDTO>> getAllMyOrderGoods() {
        return ResponseEntity.ok(myOrderGoodService.getAllMyOrderGoods());
    }

    @GetMapping("/my_order/{my_orderId}/good")
    public ResponseEntity<List<MyOrderGoodDTO>> findByMyOrderId(@PathVariable Long my_orderId) {
        return ResponseEntity.ok(myOrderGoodService.findByMyOrderId(my_orderId));
    }

    @GetMapping("/good/{goodId}/my_order")
    public ResponseEntity<List<MyOrderGoodDTO>> findByGoodId(@PathVariable Long goodId) {
        return ResponseEntity.ok(myOrderGoodService.findByGoodId(goodId));
    }

    @GetMapping("/good/{goodId}/my_order/{my_orderId}")
    public ResponseEntity<MyOrderGoodDTO> findByMyGoodIdAndOrderId(@PathVariable Long goodId, @PathVariable Long my_orderId) {
        return ResponseEntity.ok(myOrderGoodService.findByMyGoodIdAndOrderId(goodId, my_orderId));
    }

    @PostMapping("/good/my_order")
    public ResponseEntity<MyOrderGoodDTO> newOrderGood(@RequestBody MyOrderGoodDTO newMyOrderGoodDTO) {
        return ResponseEntity.created(URI.create("/my_order/"))
                .body(myOrderGoodService.addNewMyOrderGood(newMyOrderGoodDTO));
    }

    @PutMapping("/good/{goodId}/my_order/{my_orderId}")
    public ResponseEntity<MyOrderGoodDTO> replaceOrderGood(@RequestBody MyOrderGoodDTO newMyOrderGoodDTO,
                                                           @PathVariable Long my_orderId,
                                                           @PathVariable Long goodId) {
        return ResponseEntity.ok(myOrderGoodService.replaceMyOrderGood(newMyOrderGoodDTO, my_orderId, goodId));
    }

    @DeleteMapping("/good/{goodId}/my_order/{my_orderId}")
    void deleteOrderGood(@PathVariable Long goodId,
                         @PathVariable Long my_orderId) {
        myOrderGoodService.deleteMyOrderGood(goodId, my_orderId);
    }
}
