package Test.sbs.i_services;

import Test.sbs.dto.MyOrderDTO;

import java.util.List;

public interface IMyOrderService {
    List<MyOrderDTO> getAllOrders();
    MyOrderDTO getOneOrder(Long id);
    MyOrderDTO addNewOrder(MyOrderDTO myOrderDTO);
    MyOrderDTO replaceOrder(MyOrderDTO myOrderDTO, Long id);
    void deleteOrder(Long id);
}
