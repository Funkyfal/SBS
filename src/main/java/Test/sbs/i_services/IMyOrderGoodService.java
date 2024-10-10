package Test.sbs.i_services;

import Test.sbs.dto.MyOrderGoodDTO;

import java.util.List;

public interface IMyOrderGoodService {
    List<MyOrderGoodDTO> getAllMyOrderGoods();

    List<MyOrderGoodDTO> findByMyOrderId(Long myOrderId);

    List<MyOrderGoodDTO> findByGoodId(Long goodId);

    MyOrderGoodDTO findByMyGoodIdAndOrderId(Long goodId, Long myOrderId);

    MyOrderGoodDTO addNewMyOrderGood(MyOrderGoodDTO newMyOrderGoodDTO);

    MyOrderGoodDTO replaceMyOrderGood(MyOrderGoodDTO newMyOrderGoodDTO,
                                      Long myOrderId,
                                      Long goodId);

    void deleteMyOrderGood(Long goodId,
                         Long myOrderId);
}
