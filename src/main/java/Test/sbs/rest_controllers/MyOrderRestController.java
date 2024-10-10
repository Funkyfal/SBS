package Test.sbs.rest_controllers;

import Test.sbs.dto.MyOrderDTO;
import Test.sbs.services_impl.MyOrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class MyOrderRestController {
    private final MyOrderServiceImpl myOrderService;

    MyOrderRestController(MyOrderServiceImpl myOrderService) {
        this.myOrderService = myOrderService;
    }

    @GetMapping("/my_order")
    public ResponseEntity<List<MyOrderDTO>> getAllOrders() {
        return ResponseEntity.ok(myOrderService.getAllOrders());
    }

    @GetMapping("/my_order/{id}")
    public ResponseEntity<MyOrderDTO> getOneOrder(@PathVariable Long id) {
        return ResponseEntity.ok(myOrderService.getOneOrder(id));
    }

    @PostMapping("/my_order")
    public ResponseEntity<MyOrderDTO> newOrder(@RequestBody MyOrderDTO newMyOrderDTO) {
        return ResponseEntity.created(URI.create("/my_order/"))
                .body(myOrderService.addNewOrder(newMyOrderDTO));
    }

    @PutMapping("/my_order/{id}")
    public ResponseEntity<MyOrderDTO> replaceOrder(@RequestBody MyOrderDTO newMyOrder, @PathVariable Long id) {
        return ResponseEntity.ok(myOrderService.replaceOrder(newMyOrder, id));
    }

    @DeleteMapping("/my_order/{id}")
    public void deleteOrder(@PathVariable Long id) {
        myOrderService.deleteOrder(id);
    }
}
